/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 David Warnock
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
 *     David Warnock (david@sundayta.co.uk)
 *     Sundayta Ltd
 *     International House, 
 *     174 Three Bridges Road, 
 *     Crawley, West Sussex 
 *     RH10 1LE, UK
 *
 */

package org.melati.poem.dbms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.melati.poem.Table;

import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.BinaryPoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.LongPoemType;

import org.melati.poem.DuplicateKeySQLPoemException;
import org.melati.poem.NoSuchColumnPoemException;
import org.melati.poem.SeriousPoemException;
import org.melati.poem.SQLPoemException;


 /**
  * A Driver for Postgresql (http://www.postgresql.org/)
  **/

public class Postgresql extends AnsiStandard {

  public Postgresql() {
    setDriverClassName("org.postgresql.Driver");
  }


  /**
   * Don't quote, let postgres uppercase it. 
   * 
   * @see org.melati.poem.dbms.Dbms#getJdbcMetadataName(java.lang.String)
   */
  public String getJdbcMetadataName(String name) {
    return name;
  }


  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#canDropColumns(java.sql.Connection)
   */
  public boolean canDropColumns(Connection con) throws SQLException {
    if (con instanceof org.postgresql.jdbc1.AbstractJdbc1Connection) {
      return ((org.postgresql.jdbc1.AbstractJdbc1Connection)con).
                   haveMinimumServerVersion("7.3");
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#preparedStatementPlaceholder(org.melati.poem.PoemType)
   */
  public String preparedStatementPlaceholder(PoemType type) {
    if (type instanceof IntegerPoemType)
      return "CAST(? AS INT4)";
    else if (type instanceof LongPoemType)
      return "CAST(? AS INT8)";
    else if (type instanceof DoublePoemType)
      return "CAST(? AS FLOAT8)";
    else 
      return "?";
  }

  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#getStringSqlDefinition(int)
   */
  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0) { 
       return "TEXT";
    }
       return super.getStringSqlDefinition(size);
  }

  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#getBinarySqlDefinition(int)
   */
  public String getBinarySqlDefinition(int size) {
   // BLOBs in Postgres are represented as OIDs pointing to the data
    return "OID";
  }

  /**
   * An Object Id <code>PoemType</code>.
   */
  public static class OidPoemType extends IntegerPoemType {
      public OidPoemType(boolean nullable) {
          super(Types.INTEGER, "OID", nullable);
      }

      protected boolean _canRepresent(SQLPoemType other) {
          return other instanceof BinaryPoemType;
      }

      public PoemType canRepresent(PoemType other) {
          return other instanceof BinaryPoemType &&
                 !(!getNullable() && 
       ((BinaryPoemType)other).getNullable()) ? other : null;
      }
  }

  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#defaultPoemTypeOfColumnMetaData(java.sql.ResultSet)
   */
  public SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
      throws SQLException {
    return
    md.getString("TYPE_NAME").equals("oid") ?
        new OidPoemType(md.getInt("NULLABLE") ==
                            DatabaseMetaData.columnNullable) :
        super.defaultPoemTypeOfColumnMetaData(md);
  }

  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#exceptionForUpdate(org.melati.poem.Table, java.lang.String, boolean, java.sql.SQLException)
   */
  public SQLPoemException exceptionForUpdate(
      Table table, String sql, boolean insert, SQLException e) {

    String m = e.getMessage();

      // Postgres's duplicate key message is:
      // "Cannot insert a duplicate key into unique index user_login_index"

    if (m != null &&
        m.indexOf("duplicate key") >= 0) {

      // We call POEM's own indexes <table>_<column>_index:
      // see Table.dbCreateIndex

      int s, u;
      if (m.endsWith("_index\n") &&
         (s = m.lastIndexOf(' ')) >= 0 && (u = m.indexOf('_', s+1)) >= 0) {
        String colname = m.substring(u+1, m.length() - 7);
        try {
          return new DuplicateKeySQLPoemException(table.getColumn(colname),
                                                  sql, insert, e);
        }
        catch (NoSuchColumnPoemException f) {
          throw new SeriousPoemException(
               "Duplicate Key exception thrown on a non-existant column",f);
        }
      }
      return new DuplicateKeySQLPoemException(table, sql, insert, e);
    }
      else
        return super.exceptionForUpdate(table, sql, insert, e);
  }

  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)
   */
  public String caseInsensitiveRegExpSQL(String term1, String term2) {
    return term1 + " ~* " + term2;
  }

}
