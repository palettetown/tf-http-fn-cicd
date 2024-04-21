
package functions;

import com.google.cloud.functions.HttpFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;
import java.io.IOException;

public class HelloWorld implements HttpFunction {
  // Simple function to return "Hello World"
  @Override
  public void service(HttpRequest request, HttpResponse response)
      throws IOException {
    BufferedWriter writer = response.getWriter();
    writer.write("Hello World!");
    
    // Get a new datasource from the method we defined before
    DataSource dataSource = CloudSqlConnectionPoolFactory.createConnectionPool();

    // Run a query and get the result
    ResultSet rs = null;
    Connection conn = null;
	try {
		conn = dataSource.getConnection();
		rs = conn.prepareStatement("select * from birds").executeQuery();
		
	    // print the results to the console
	    while (rs.next()) {
	      System.out
	          .println("name: " + rs.getString("bird") + " description:" + rs.getString("description"));
	    }  
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {
			if (rs != null)
				rs.close();
			if (conn !=null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

  }
}