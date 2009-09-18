package org.melati.poem.dbms.test;

import java.io.PrintWriter;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * @author timp
 * @since 3 May 2007
 *
 */
public class DebugSQLServerConnection {
  private java.sql.Connection con = null;

  private final String url = "jdbc:sqlserver://";

  private final String serverName = "localhost";

  private final String portNumber = "1433";

  private final String databaseName = "melatijunit";

  private final String userName = "sa";

  private final String password = "";

  // Informs the driver to use server a side-cursor,
  // which permits more than one active statement
  // on a connection.
  //private final String selectMethod = "cursor";

  /**
   * Constructor.
   */
  public DebugSQLServerConnection() {
  }

  private String getConnectionUrl() {
    return url + serverName + ":" + portNumber + ";databaseName="
            + databaseName + ";selectMethod=cursor;";
  }

  private java.sql.Connection getConnection() {
    try {
      Class<?> driverClass = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      if (driverClass == null) 
        throw new RuntimeException("No class found");
      Driver driver = (Driver) driverClass.newInstance();

      DriverManager.setLogWriter(new PrintWriter(System.out));
      DriverManager.registerDriver(driver);
      
      con = java.sql.DriverManager.getConnection(getConnectionUrl(), userName,
              password);
      if (con != null)
        System.out.println("Connection Successful!");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error Trace in getConnection() : " + e.getMessage());
    }
    return con;
  }

  /**
   * Display the driver properties, database details
   */
  public void displayDbProperties() {
    java.sql.DatabaseMetaData dm = null;
    java.sql.ResultSet rs = null;
    try {
      con = this.getConnection();
      if (con != null) {
        dm = con.getMetaData();
        System.out.println("Driver Information");
        System.out.println("\tDriver Name: " + dm.getDriverName());
        System.out.println("\tDriver Version: " + dm.getDriverVersion());
        System.out.println("\nDatabase Information ");
        System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
        System.out.println("\tDatabase Version: "
                + dm.getDatabaseProductVersion());
        System.out.println("Avalilable Catalogs ");
        rs = dm.getCatalogs();
        while (rs.next()) {
          System.out.println("\tcatalog: " + rs.getString(1));
        }
        rs.close();
        rs = null;
        closeConnection();
      } else
        System.out.println("Error: No active Connection");
    } catch (Exception e) {
      e.printStackTrace();
    }
    dm = null;
  }

  private void closeConnection() {
    try {
      if (con != null)
        con.close();
      con = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Main.
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    DebugSQLServerConnection myDbTest = new DebugSQLServerConnection();
    myDbTest.displayDbProperties();
  }
}
