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
//import java.sql.ResultSetMetaData;
import org.melati.poem.Persistable;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.BinaryPoemType;
import org.melati.poem.StringPoemType;

/**
 * A Driver for Mckoidb (http://www.mckoi.com/).
*/
public class Mckoi extends AnsiStandard {

  /**
   * Constructor.
   */
  public Mckoi() {
    setDriverClassName("com.mckoi.JDBCDriver");
  }

  /**
   * Can this still be true.
   * @return false
   */
  public boolean supportsIndex() {
    return false;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getBinarySqlDefinition(int)
   */
  public String getBinarySqlDefinition(int size) {
      // BLOBs in Postgres are represented as OIDs pointing to the data
    return "LONGVARBINARY";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getStringSqlDefinition(int)
   */
  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0) { 
      return "TEXT";
    }
    return super.getStringSqlDefinition(size);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getQuotedName(java.lang.String)
   */
  public String getQuotedName (String name) {
    //McKoi doesn't quote names
    if (name.equals("unique")) return super.getQuotedName(name);
    if (name.equals("from")) return super.getQuotedName(name);
    return name;
  }

 /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getSqlDefinition(java.lang.String)
   * @todo Check against modern McKoi 
   */
  public String getSqlDefinition(String sqlTypeName) {
    if (sqlTypeName.equals("INT")) {
      return ("INTEGER");
    }
    /*
    if (sqlTypeName.equals("DOUBLE PRECISION")) {
      return ("DOUBLE");
    }
    */
    return super.getSqlDefinition(sqlTypeName);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#canRepresent
   */
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

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#defaultPoemTypeOfColumnMetaData
   */
  public SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
      throws SQLException {
//      ResultSetMetaData rsmd= md.getMetaData();

    if(md.getString("TYPE_NAME").equals("NUMERIC"))
      return new DoublePoemType(md.getInt("NULLABLE")==
                                DatabaseMetaData.columnNullable);
    else
      return super.defaultPoemTypeOfColumnMetaData(md);
  }


  /**
   * @param user
   * @param capabilityExpr
   * @return
   */
  public String givesCapabilitySQL(Persistable user, String capabilityExpr) {
    return
        "SELECT groupmembership.*  " + 
        "FROM groupmembership LEFT JOIN groupcapability " +
        "ON groupmembership." + getQuotedName("group") +
        " =  groupcapability." + getQuotedName("group") + " " +
        "WHERE " + getQuotedName("user") + " = " + user.getTroid() + " " +
        "AND groupcapability." + getQuotedName("group") + " IS NOT NULL " +
        "AND capability = " + capabilityExpr;
  }
  
  /** 
   * @see org.melati.poem.dbms.Dbms#getForeignKeyDefinition
   * @todo find out foreign key syntax
   */
  public String getForeignKeyDefinition(String tableName, String fieldName, 
      String targetTableName, String targetTableFieldName, String fixName) {
    StringBuffer sb = new StringBuffer();
    sb.append(" ADD FOREIGN KEY (" + getQuotedName(fieldName) + ") REFERENCES " + 
              getQuotedName(targetTableName) + 
              "(" + getQuotedName(targetTableFieldName) + ")");
    if (fixName.equals("prevent"))
      sb.append(" ON DELETE NO ACTION");      
    if (fixName.equals("delete"))
      sb.append(" ON DELETE CASCADE");      
    if (fixName.equals("clear"))
      sb.append(" ON DELETE SET NULL");      
    return sb.toString();
  }

  /** 
   * @see org.melati.poem.dbms.Dbms#getPrimaryKeyDefinition
   * @todo find out primary key syntax
   */
  public String getPrimaryKeyDefinition(String fieldName) {
    return " ADD PRIMARY KEY (" + getQuotedName(fieldName) + ")";
  }


}












