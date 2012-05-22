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
 *     Tim Pizey (timp At paneris.org)
 *
 */

package org.melati.poem.dbms;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.melati.poem.BinaryPoemType;
import org.melati.poem.Column;
import org.melati.poem.PoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.Table;
import org.melati.poem.util.StringUtils;

/**
 * A Driver for HSQLDB (http://www.hsqldb.org/).
 *
 * Note that HSQLDB uppercases any name that isn't quoted. 
 *
 **/

public class Hsqldb extends AnsiStandard {

  // 2010/10/25 Have experienced issues with records no being committed
  //       st.execute("SET WRITE_DELAY FALSE");
  // had no effect

  /**
   * HSQLDB does not have a pleasant <code>TEXT</code> 
   * datatype, so we use an arbitrary value in a 
   * <code>VARCHAR</code>.
   */
  public static int hsqldbTextHack = 266;
  // Version 2.2.8 introduces a text field
  public static int hsqldbTextSize1 = 1048576;
  public static int hsqldbTextSize2 = 16777216;

  /**
   * Constructor.
   */
  public Hsqldb() {
    setDriverClassName("org.hsqldb.jdbcDriver");
  }

  /**
   * Shut the db down nicely.
   * 
   * @see org.melati.poem.dbms.Dbms#disconnect()
   */
  @Override
  public void shutdown(Connection connection)  throws SQLException { 
    if (!connection.isClosed()) {
      Statement st = connection.createStatement();
      try { 
        st.execute("SHUTDOWN SCRIPT");
      } catch (SQLException e) { 
        // This seems to be caused by a shutdownhook race
        if (!e.getMessage().equals("Access is denied: Session is closed")) 
          throw e;
      } finally { 
        st.close();
      }
    }
  }

  /** 
   * The default is to keep everything in memory,
   * this allows for the db to be written to the disk.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.dbms.Dbms#createTableSql()
   * @see org.melati.poem.dbms.AnsiStandard#createTableSql()
   */
  @Override
  public String createTableTypeQualifierSql(Table<?> table) {
    String tableType;
    if (table == null || table.getDbmsTableType() == null)
      tableType = "CACHED ";
    else
      tableType = table.getDbmsTableType() + " "; 
      
    return tableType;
  }

  /*
   *  0.7.2 and earlier did not have a Boolean type; 
   *  there is one in 0.7.3 onwards. 
   *   
   * @see org.melati.poem.dbms.Dbms#getSqlDefinition(java.lang.String)
   */

  /*
   public String getSqlDefinition(String sqlTypeName) {
    if (sqlTypeName.equals("BOOLEAN")) {
      return ("BIT");
    }
    return super.getSqlDefinition(sqlTypeName);
  }
  */
  /**
   * @see org.melati.poem.dbms.AnsiStandard#getStringSqlDefinition(int)
   */
  @Override
  public String getStringSqlDefinition(int size) {
    if (size < 0)
      return "LONGVARCHAR";
      //return "VARCHAR(" + hsqldbTextHack + ")";
    return "VARCHAR(" + size + ")";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getLongSqlDefinition()
   */
  @Override
  public String getLongSqlDefinition() {
    return "BIGINT";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getBinarySqlDefinition(int)
   */
  @Override
  public String getBinarySqlDefinition(int size) throws SQLException {
    return "LONGVARBINARY";
  }

  /**
   * Accommodate our String size hack. 
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#canRepresent
   */
  @Override
  public <S,O>PoemType<O> canRepresent(PoemType<S> storage, PoemType<O> type) {
    if (storage instanceof StringPoemType && type instanceof StringPoemType) {
      if (
          (((StringPoemType)storage).getSize() == hsqldbTextHack
          || 
          ((StringPoemType)storage).getSize() == hsqldbTextSize1
          || 
          ((StringPoemType)storage).getSize() == hsqldbTextSize2
          )
              && ((StringPoemType)type).getSize() == -1
              && !(!storage.getNullable() && type.getNullable())  // Nullable may represent not nullable
      ) {
        return type;
      } else {
        return storage.canRepresent(type);
      }
    } else if (storage instanceof BinaryPoemType && type instanceof BinaryPoemType) {
      if (
           (
             (((BinaryPoemType)storage).getSize() == 0)
             || 
             (((BinaryPoemType)storage).getSize() > ((BinaryPoemType)type).getSize())
           )
           && 
           !(!storage.getNullable() && type.getNullable())  // Nullable may represent not nullable
      ) {
        return type;
      } else {
        return storage.canRepresent(type);
      }
    } else {
      return super.canRepresent(storage, type);
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#unreservedName(java.lang.String)
   */
  @Override
  public String unreservedName(String name) {
    if(name.equalsIgnoreCase("UNIQUE")) name = "MELATI_" + name.toUpperCase();
    if(name.equalsIgnoreCase("CONSTRAINT")) name = "MELATI_" + name.toUpperCase();
    //if(name.equalsIgnoreCase("USERS")) name = "MELATI_" + name.toUpperCase();
    return name.toUpperCase();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#melatiName(java.lang.String)
   */
  @Override
  public String melatiName(String name) {
    if (name == null) return name;
    if(name.equalsIgnoreCase("MELATI_UNIQUE")) name = "UNIQUE";
    if(name.equalsIgnoreCase("MELATI_CONSTRAINT")) name = "CONSTRAINT";
    //if(name.equalsIgnoreCase("MELATI_USERS")) name = "USERS";
    return name.toLowerCase();
  }
  
  /** 
   * Note that this is NOT case insensitive.
   * 
   * {@inheritDoc}
   * 
   * @see org.melati.poem.dbms.Dbms#caseInsensitiveRegExpSQL
   */
  @Override
  public String caseInsensitiveRegExpSQL(String term1, String term2) {
    if (StringUtils.isQuoted(term2)) {
      term2 = term2.substring(1, term2.length() - 1);
    } 
    term2 = StringUtils.quoted(StringUtils.quoted(term2, '%'), '\'');
    
    return term1 + " LIKE " + term2;
  }
  
  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getForeignKeyDefinition
   */
  @Override
  public String getForeignKeyDefinition(String tableName, String fieldName, 
                                        String targetTableName, 
                                        String targetTableFieldName, 
                                        String fixName) {
    StringBuffer sb = new StringBuffer();
    sb.append(" ADD FOREIGN KEY (" + getQuotedName(fieldName) + ") REFERENCES " + 
              getQuotedName(targetTableName) + 
              "(" + getQuotedName(targetTableFieldName) + ")");
    // Not currently implemented by hsqldb, 
    //another reason for not using the DB to control these things
    //if (fixName.equals("prevent"))
    //  sb.append(" ON DELETE NO ACTION");
    // There is an "ON DELETE SET DEFAULT" 
    
    if (fixName.equals("delete"))
      sb.append(" ON DELETE CASCADE");      
    if (fixName.equals("clear"))
      sb.append(" ON DELETE SET NULL");
    
    return sb.toString();
  }

  /**
   * Bad smell.
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getJdbcMetadataName(java.lang.String)
   */
  @Override
  public String getJdbcMetadataName(String name) {
    return name.toUpperCase();
  }
  /**
   * Our current version does not honour COMMENT. 
   */
   @Override
   public String alterColumnAddCommentSQL(Column<?> column, String comment) {
     return null;
   }

}
