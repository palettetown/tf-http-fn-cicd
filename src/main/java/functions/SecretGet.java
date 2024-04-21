package functions;

import java.io.IOException;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.ProjectName;
import com.google.cloud.secretmanager.v1.Replication;
import com.google.cloud.secretmanager.v1.Secret;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient.ListSecretsPagedResponse;
import com.google.cloud.secretmanager.v1.SecretName;
import com.google.cloud.secretmanager.v1.SecretPayload;
import com.google.cloud.secretmanager.v1.SecretVersion;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import com.google.protobuf.ByteString;
//import java.util.zip.CRC32C;
//import java.util.zip.Checksum;

public class SecretGet {

  public void quickstart() throws Exception {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "my-second-project-418213";
    String secretId = "my-db-password";
   // quickstart(projectId, secretId);
    //getSecret(projectId, secretId);
    //listSecrets(projectId);
    accessSecretVersion(projectId, secretId, "1");
  }

  public void quickstart(String projectId, String secretId) throws Exception {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      // Build the parent name from the project.
      ProjectName projectName = ProjectName.of(projectId);

      // Create the parent secret.
      Secret secret =
          Secret.newBuilder()
              .setReplication(
                  Replication.newBuilder()
                      .setAutomatic(Replication.Automatic.newBuilder().build())
                      .build())
              .build();

      Secret createdSecret = client.createSecret(projectName, secretId, secret);

      // Add a secret version.
      SecretPayload payload =
          SecretPayload.newBuilder().setData(ByteString.copyFromUtf8("hello world!")).build();
      SecretVersion addedVersion = client.addSecretVersion(createdSecret.getName(), payload);

      // Access the secret version.
      AccessSecretVersionResponse response = client.accessSecretVersion(addedVersion.getName());

      // Print the secret payload.
      //
      // WARNING: Do not print the secret in a production environment - this
      // snippet is showing how to access the secret material.
      String data = response.getPayload().getData().toStringUtf8();
      System.out.printf("Plaintext: %s\n", data);
    }
  }
  
  // Access the payload for the given secret version if one exists. The version
  // can be a version number as a string (e.g. "5") or an alias (e.g. "latest").
  public static void accessSecretVersion(String projectId, String secretId, String versionId)
      throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, versionId);

      // Access the secret version.
      AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);

      // Verify checksum. The used library is available in Java 9+.
      // If using Java 8, you may use the following:
      // https://github.com/google/guava/blob/e62d6a0456420d295089a9c319b7593a3eae4a83/guava/src/com/google/common/hash/Hashing.java#L395
      byte[] data = response.getPayload().getData().toByteArray();
//      Checksum checksum = new CRC32C();
//      checksum.update(data, 0, data.length);
//      if (response.getPayload().getDataCrc32C() != checksum.getValue()) {
//        System.out.printf("Data corruption detected.");
//        return;
//      }

      // Print the secret payload.
      //
      // WARNING: Do not print the secret in a production environment - this
      // snippet is showing how to access the secret material.
      String payload = response.getPayload().getData().toStringUtf8();
      System.out.printf("Haha! Secret passsword plain text is: %s\n", payload);
    }
  }  
  
  // Get an existing secret version.
  public static void getSecretVersion(String projectId, String secretId, String versionId)
      throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      // Build the name from the version.
      SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, versionId);

      // Create the secret.
      SecretVersion version = client.getSecretVersion(secretVersionName);
      System.out.printf("Secret version %s, state %s\n", version.getName(), version.getState());
    }
  }  
  
  // Get an existing secret.
  public static void getSecret(String projectId, String secretId) throws IOException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      // Build the name.
      SecretName secretName = SecretName.of(projectId, secretId);

      // Create the secret.
      Secret secret = client.getSecret(secretName);
           
      
      // Get the replication policy.
      String replication = "";
      if (secret.getReplication().getAutomatic() != null) {
        replication = "AUTOMATIC";
      } else if (secret.getReplication().getUserManaged() != null) {
        replication = "MANAGED";
      } else {
        throw new IllegalStateException("Unknown replication type");
      }
      
      System.out.printf("Secret %s, replication %s\n", secret.getName(), replication);
    }
  }
  
//List all secrets for a project
 public static void listSecrets(String projectId) throws IOException {
   // Initialize client that will be used to send requests. This client only needs to be created
   // once, and can be reused for multiple requests. After completing all of your requests, call
   // the "close" method on the client to safely clean up any remaining background resources.
   try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
     // Build the parent name.
     ProjectName projectName = ProjectName.of(projectId);

     // Get all secrets.
     ListSecretsPagedResponse pagedResponse = client.listSecrets(projectName);

     // List all secrets.
     pagedResponse
         .iterateAll()
         .forEach(
             secret -> {
               System.out.printf("Secret %s\n", secret.getName());
             });
   }
 }
}