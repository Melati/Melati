/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2002 Tim Pizey
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
 *     Tim Pizey (timp@paneris.org)
 *
 */

package org.melati.poem.dbms;

import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.melati.poem.User;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.BinaryPoemType;
import org.melati.poem.StringPoemType;

 /**
  * A Database Management System from http://www.mckoi.com/.
  **/
public class Mckoi extends AnsiStandard {

    public Mckoi() {
        setDriverClassName("com.mckoi.JDBCDriver");
    }


    public boolean supportsIndex() {
      return false;
    }

    public String getBinarySqlDefinition(int size) throws SQLException {
        // BLOBs in Postgres are represented as OIDs pointing to the data
        return "LONGVARBINARY";
    }

    public String getStringSqlDefinition(int size) throws SQLException {
        if (size < 0) { 
            return "TEXT";
        }
        return super.getStringSqlDefinition(size);
    }

    public String getQuotedName (String name) {
     //McKoi doesn't quote names
      if (name.equals("unique")) return super.getQuotedName(name);
      if (name.equals("from")) return super.getQuotedName(name);
      return name;
    }

    public String getSqlDefinition(String sqlTypeName) throws SQLException {
        if (sqlTypeName.equals("INT")) {
            return ("INTEGER");
        }
        // FIXME - FLOAT8 hardcoded in poem/DoublePoemType
        if (sqlTypeName.equals("FLOAT8")) {
            return ("DOUBLE");
        }
        return super.getSqlDefinition(sqlTypeName);
    }

    public PoemType canRepresent(PoemType storage, PoemType type) {
      if (storage instanceof StringPoemType &&
          type instanceof StringPoemType) {

        if (((StringPoemType)storage).getSize() == 2147483647 &&
              ((StringPoemType)type).getSize() == -1) {
             return type;
          } else {
             return storage.canRepresent(type);
          }
      } else if (storage instanceof BinaryPoemType &&
                 type instanceof BinaryPoemType) {
        if (((BinaryPoemType)storage).getSize() == 2147483647 &&
             ((BinaryPoemType)type).getSize() == -1) {
          return type;
        } else {
             return storage.canRepresent(type);
          }
      } else {
        return storage.canRepresent(type);
      }
    }

    public SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
        throws SQLException {
      ResultSetMetaData rsmd= md.getMetaData();

      if( md.getString("TYPE_NAME").equals("NUMERIC") )
        return new DoublePoemType(md.getInt("NULLABLE")==
            DatabaseMetaData.columnNullable );
      else
        return super.defaultPoemTypeOfColumnMetaData(md);
    }


  public String givesCapabilitySQL(User user, String capabilityExpr) {
    return
        "SELECT groupmembership.*  " + 
        "FROM groupmembership LEFT JOIN groupcapability " +
        "ON groupmembership." + getQuotedName("group") +
        " =  groupcapability." + getQuotedName("group") + " " +
        "WHERE " + getQuotedName("user") + " = " + user.troid() + " " +
        "AND groupcapability." + getQuotedName("group") + " IS NOT NULL " +
        "AND capability = " + capabilityExpr;
  }



}












