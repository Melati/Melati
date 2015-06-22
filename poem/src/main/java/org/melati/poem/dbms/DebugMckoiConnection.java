/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
 *     tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
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
public final class DebugMckoiConnection {

    private DebugMckoiConnection () {}

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
