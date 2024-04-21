# tf-http-fn-cicd
http cloud function CICD

--role to enable services 
--permission: serviceusage.services.enable
roles/serviceusage.serviceUsageAdmin

--IAM permission "cloudscheduler.jobs.create"
roles/cloudscheduler.admin

gcloud sql connect my-instance --database bird_encyclopedia

CREATE TABLE birds (
  id SERIAL PRIMARY KEY,
  bird VARCHAR(256),
  description VARCHAR(1024)
);


INSERT INTO birds (bird , description) VALUES 
('pigeon', 'common in cities'),
('eagle', 'bird of prey');

Role: Cloud SQL Client 
Execute Cloud SQL

https://cloud.google.com/sql/docs/postgres/connect-overview#java
https://cloud.google.com/sql/docs/postgres/connect-connectors
https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory/blob/main/docs/jdbc.md
https://www.sohamkamani.com/java/cloudsql/
https://cloud.google.com/scheduler/docs/configuring/cron-job-schedules
https://cloud.google.com/scheduler/docs/creating#gcloud
https://cloud.google.com/looker/docs/reference/param-view-timezone-values
https://cloud.google.com/about/locations#lightbox-regions-map
https://cloud.google.com/iam/docs/understanding-roles
https://cloud.google.com/scheduler/docs/tut-gcf-pub-sub
https://www.googlecloudcommunity.com/gc/Serverless/Cloud-Run-to-Cloud-SQL-Connection-stops-working-after-1-hour/td-p/161579
https://stackoverflow.com/questions/58047596/gcp-failed-to-update-metadata-for-cloud-sql-instance
https://docs.github.com/en/actions/security-guides/using-secrets-in-github-actions
https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory/issues/267

https://stackoverflow.com/questions/11847144/heroku-psql-fatal-remaining-connection-slots-are-reserved-for-non-replication
https://stackoverflow.com/questions/73686592/manually-increase-number-of-connections-to-be-used-in-hikari-pool
https://stackoverflow.com/questions/54732562/how-do-i-properly-close-a-hikaricp-connection-pool

gcloud run deploy [SERVICE_NAME] ... --allow-unauthenticated
This permission is included in both the Owner and Cloud Run Admin roles
https://cloud.google.com/run/docs/authenticating/public
  gcloud run services add-iam-policy-binding [SERVICE_NAME] \
    --member="allUsers" \
    --role="roles/run.invoker"


Use of DataSource:

    When you call getConnection() on a javax.sql.DataSource, the JDBC driver implementation manages the pooling and reuse of Connection objects transparently for you. This means that multiple calls to getConnection() may return the same underlying Connection instance or a different one from a pool, depending on the implementation and configuration of the DataSource.

    Here's how it typically works:

    Connection Pooling: Most modern JDBC drivers and DataSource implementations include connection pooling capabilities. When you call getConnection() multiple times, the DataSource manages a pool of Connection objects behind the scenes.
    Reuse of Connections: When you call getConnection(), the DataSource checks if there are available connections in the pool. If there is an idle connection available, it will be returned to you. If not, a new connection may be created based on the pool configuration (e.g., minimum and maximum pool size).
    Pooling Configuration: The behavior of connection pooling can be configured through properties of the DataSource or through a separate connection pooling library if you're using one (such as Apache DBCP or HikariCP). Configuration options typically include parameters like minimum and maximum pool size, timeout settings, and validation of idle connections.
    Pooling Benefits: Connection pooling helps improve application performance by reducing the overhead of creating and closing connections for each database operation. It also helps manage resources efficiently, especially in multi-threaded environments.
    In summary, when you call getConnection() on a javax.sql.DataSource, the DataSource implementation handles the details of managing connections from a pool, and it may reuse the same Connection instance or provide a new one from the pool based on the current state of the pool and its configuration. You don't need to worry about managing the actual Connection objects manually; focus on using the connection for your database operations and ensure that you close the connection properly when you're done with it.

    You should always close the Connection object returned from the connection pool after you finish using it. Closing the connection releases the associated database resources and returns the connection back to the pool for reuse by other parts of your application.

     1.   Get a Connection: Obtain a connection from the DataSource by calling getConnection().
     2.   Use the Connection: Perform your database operations (such as executing queries or updates) using the obtained connection.
     3.   Close the Connection: After you finish using the connection, always close it using the close() method. This is crucial for resource management and proper functioning of the connection pool.

     E.g.
        import javax.sql.DataSource;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;

        public class DatabaseOperations {
            private DataSource dataSource; // Initialize this with your DataSource

            public void performDatabaseOperation() {
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try {
                    connection = dataSource.getConnection();
                    // Use the connection for database operations
                    preparedStatement = connection.prepareStatement("SELECT * FROM my_table");
                    resultSet = preparedStatement.executeQuery();
                    // Process the ResultSet or perform other operations

                } catch (SQLException e) {
                    // Handle exceptions appropriately
                    e.printStackTrace();

                } finally {
                    // Close resources in reverse order of creation
                    try {
                        if (resultSet != null) resultSet.close();
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


