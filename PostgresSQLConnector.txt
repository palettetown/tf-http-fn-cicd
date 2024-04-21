package functions;

import javax.sql.DataSource;
import com.google.cloud.sql.postgres.SocketFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class PostgresSQLConnector {
	
    // Connection parameters
    private static final String INSTANCE_CONNECTION_NAME = "my-second-project-418213:us-central1:my-instance";
    private static final String DATABASE_NAME = "bird_encyclopedia";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private Connection connection = null;

  public static DataSource createConnectionPool() {
	  
      String jdbcUrl = "jdbc:postgresql:///" + DATABASE_NAME + "?" 
    		    + "cloudSqlInstance=" + INSTANCE_CONNECTION_NAME 
    		    + "&socketFactory=com.google.cloud.sql.postgres.SocketFactory" 
    		    + "&user=" + USERNAME 
    		    + "&password=" + PASSWORD;

      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(String.format(jdbcUrl));
      config.setUsername(USERNAME);
      config.setPassword(PASSWORD);
      config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
      config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);

      // Initialize the connection pool using the configuration object.
      return new HikariDataSource(config);
  }
}