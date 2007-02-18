/**
 * 
 */
package org.melati.poem.dbms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author toby
 * @since 8 Feb 2007 copied from http://www.mckoi.com/database/UseEmbeddedApp.html
 *
 */
public class DebugMckoiConnection {

    public static void main(String[] args) {

      // Register the Mckoi JDBC Driver
      try {
        Class.forName("com.mckoi.JDBCDriver").newInstance();
      }
      catch (Exception e) {
        System.out.println(
          "Unable to register the JDBC Driver.\n" +
          "Make sure the JDBC driver is in the\n" +
          "classpath.\n");
        System.exit(1);
      }

      // This URL specifies we are connecting with a local database
      // within the file system.  './db.conf' is the path of the
      // configuration file of the database to embed.
      String url = "jdbc:mckoi:local://./db.conf";

      // The username / password to connect under.
      String username = "as";
      String password = "as";

      // Make a connection with the local database.
      Connection connection;
      try {
        connection = DriverManager.getConnection(url, username, password);
      }
      catch (SQLException e) {
        System.out.println(
          "Unable to make a connection to the database.\n" +
          "The reason: " + e.getMessage());
        System.exit(1);
        return;
      }

      try {
      
        // .... Use 'connection' to talk to database ....

        Statement s = connection.createStatement();
        s.executeUpdate("create table t11  (id int not null, name varchar(22))");

        // Close the connection when finished,
        connection.close();
        System.out.println("Finished");
      }
      catch (SQLException e) {
        System.err.println(
          "An error occured\n" +
          "The SQLException message is: " + e.getMessage());
        return;
      }

    }

  }
