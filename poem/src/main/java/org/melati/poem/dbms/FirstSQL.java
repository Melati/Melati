package org.melati.poem.dbms;

/* In org.melati.LogicalDatabase.properties:
org.melati.LogicalDatabase.melatitest.dbmsclass=org.melati.poem.dbms.FirstSQL
# FirstSQL Enterprise Client/Server:
#org.melati.LogicalDatabase.melatitest.url=jdbc:dbcp://localhost:8000;user=adm;password=firstsql
# FirstSQL Pro Embedded: set maxtransactions to 0!
org.melati.LogicalDatabase.melatitest.maxtransactions=0
##Run recovery.sh first, if DB was shutdown incorrectly.
org.melati.LogicalDatabase.melatitest.url=jdbc:dbcp://local;database.path=/work/firstsqlpro/db;user=adm;password=firstsql
##org.melati.LogicalDatabase.melatitest.url=jdbc:dbcp://local;database.path=/work/firstsqlpro/db;recover=yes;user=adm;password=firstsql
org.melati.LogicalDatabase.melatitest.user=
org.melati.LogicalDatabase.melatitest.pass=
*/

/* Added to tomcat.policy:
grant codeBase "file:#{tomcat.home}/webapps/mt/WEB-INF/lib/-" {
    permission java.security.AllPermission;
};
*/

/* //Code from FirstSQL example, working alone with embedded DB.
   //Exactly the same code put in my FirstSQL.getConnection()
   //doesn't work, same classpath...
   
public class FirstSQLtest {

   public static void main(String argv[]) throws Exception {
     Class.forName("COM.FirstSQL.Dbcp.DbcpDriver");
     java.sql.Connection connect1, connect2;
     java.util.Properties info;
     info = new java.util.Properties();
      //info.put("user", "demo");
     connect1 = java.sql.DriverManager.getConnection(
         "jdbc:dbcp://local;database.path=/work/firstsqlpro/db;user=adm;password=firstsql",info);

  //Just once, as we're in single user:
  // connect2 = java.sql.DriverManager.getConnection(
         "jdbc:dbcp://local;database.path=/work/firstsqlpro/db;user=adm;password=firstsql",info);
  }
}
*/


import java.sql.Connection;
import org.melati.poem.ConnectionFailurePoemException;

public class FirstSQL extends AnsiStandard {

  static public java.io.PrintStream logStream= System.err;

  public FirstSQL() {
    setDriverClassName("COM.FirstSQL.Dbcp.DbcpDriver");  
  }


//It does the same error, even if we don't override it -
// using AnsiStandard.getConnection(...)
  public Connection getConnection(String url, String user, String password) throws ConnectionFailurePoemException {
/*      Properties info = new Properties();
      //if (user != null) info.put("user", user);
      //if (password != null) info.put("password", password);
      
      try {
        Class.forName( getDriverClassName() );
       logStream.println();
       logStream.println( url);
       logStream.println( getDriverClassName() );
       logStream.println();
       return java.sql.DriverManager.getConnection(url, info);
      }
      catch (ClassNotFoundException e) {
          throw new ConnectionFailurePoemException( new SQLException("JDBC driver class not found.") );
      }
      catch (SQLException e) {
          throw new ConnectionFailurePoemException(e);
      }
*/
  try{
    Class.forName("COM.FirstSQL.Dbcp.DbcpDriver");
    java.sql.Connection connect1, connect2;
    java.util.Properties info;
    info = new java.util.Properties();
    //info.put("user", "demo");
    connect1 = java.sql.DriverManager.getConnection(
        "jdbc:dbcp://local;database.path=/work/firstsqlpro/db;user=adm;password=firstsql",info);
    return connect1;
  }
  catch(Exception e) {}
  return null;
  }
}








