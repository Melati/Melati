/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2002 Peter Kehl
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *    Peter Kehl peterk@paneris.org
 */
package org.melati.poem.dbms;

/* 
  In org.melati.LogicalDatabase.properties:
org.melati.LogicalDatabase.melatitest.dbmsclass=org.melati.poem.dbms.FirstSQL
# FirstSQL Enterprise Client/Server:
#org.melati.LogicalDatabase.melatitest.url= jdbc:dbcp://localhost:8000;user=adm;password=firstsql
# FirstSQL Pro Embedded: set maxtransactions to 0!
org.melati.LogicalDatabase.melatitest.maxtransactions=0
##Run recovery.sh first, if DB was shutdown incorrectly.
org.melati.LogicalDatabase.melatitest.url= jdbc:dbcp://database.path=/work/firstsqlpro/db;user=adm;password=firstsql
##org.melati.LogicalDatabase.melatitest.url=jdbc:dbcp://database.path=/work/firstsqlpro/db;recover=yes;user=adm;password=firstsql
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
import java.sql.SQLException;
import org.melati.util.UnexpectedExceptionException;

/**
 * A Driver for FirstSQL ( NOT WORKING YET!!!)
 */

public class FirstSQL extends AnsiStandard {

  public static java.io.PrintStream logStream = System.err;

  public FirstSQL() {
    setDriverClassName("COM.FirstSQL.Dbcp.DbcpDriver");  
  }


//It does the same error, even if we don't override it -
// using AnsiStandard.getConnection(...)
  public Connection getConnection(String url, String user, String password) 
      throws ConnectionFailurePoemException {
/*      Properties info = new Properties();
      //if (user != null) info.put("user", user);
      //if (password != null) info.put("password", password);
      
      try {
       Class.forName(getDriverClassName());
       logStream.println();
       logStream.println(url);
       logStream.println(getDriverClassName());
       logStream.println();
       return java.sql.DriverManager.getConnection(url, info);
      } catch (ClassNotFoundException e) {
        throw new ConnectionFailurePoemException( 
            new SQLException("JDBC driver class not found."));
      }
      catch (SQLException e) {
          throw new ConnectionFailurePoemException(e);
      }
*/
    try {
      Class.forName("COM.FirstSQL.Dbcp.DbcpDriver");
      java.sql.Connection connect1;
      java.util.Properties info;
      info = new java.util.Properties();
      //info.put("user", "demo");
      connect1 = java.sql.DriverManager.getConnection(
        "jdbc:dbcp://local;database.path=" + 
        "/work/firstsqlpro/db;user=adm;password=firstsql",info);
      return connect1;
    } catch (SQLException e) {
      throw new ConnectionFailurePoemException(e);
    } catch (Exception e) {
      throw new UnexpectedExceptionException(e);
    }
  }
}








