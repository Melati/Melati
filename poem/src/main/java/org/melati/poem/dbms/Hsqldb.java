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

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.melati.poem.BooleanPoemType;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.User;
import org.melati.util.StringUtils;

/**
 * A Driver for HSQLDB (http://www.hsqldb.org/).
 *
 * Note that HSQLDB uppercases any name that isn't quoted, 
 * this strictness uncovered a few loopholes,
 * now all names in Melati should be quoted.
 *
 **/

public class Hsqldb extends AnsiStandard {

  /**
   * HSQLDB does not have a pleasant <code>TEXT</code> 
   * datatype, so we use an arbetary value in a 
   * <code>VARCHAR</code>.
   */
  public static int hsqldbTextHack = 266;

  public Hsqldb() {
    setDriverClassName("org.hsqldb.jdbcDriver");
  }

  /** 
   * The default is to keep everything in memory.
   * This allows for the db to be written to the disk.
   * 
   * @see org.melati.poem.dbms.Dbms#createTableSql()
   */
  public String createTableSql() {
    return "CREATE CACHED TABLE ";
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
  public String getStringSqlDefinition(int size) {
    if (size < 0)
      return "VARCHAR(" + hsqldbTextHack + ")";
    return "VARCHAR(" + size + ")";
  }

  public String getLongSqlDefinition() {
    return "BIGINT";
  }

  /* (non-Javadoc)
   * @see org.melati.poem.dbms.Dbms#getBinarySqlDefinition(int)
   */
  public String getBinarySqlDefinition(int size) throws SQLException {
    return "LONGVARBINARY";
  }

  public PoemType canRepresent(PoemType storage, PoemType type) {
    if (storage instanceof StringPoemType && type instanceof StringPoemType) {

      if (((StringPoemType) storage).getSize() == hsqldbTextHack
        && ((StringPoemType) type).getSize() == -1) {
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
    //ResultSetMetaData rsmd = md.getMetaData();
    // 1.7.3 introduces a Boolean type, which a Bit is inferred to be 
    if (md.getString("TYPE_NAME").equals("BOOLEAN"))
      return new HsqldbBooleanPoemType(
                    md.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
    if (md.getString("TYPE_NAME").equals("BIT"))
      return new HsqldbBooleanPoemType(
                    md.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
    else
      return super.defaultPoemTypeOfColumnMetaData(md);
  }

 /**
  * Translates an HSQLDB Boolean into a Poem <code>BooleanPoemType</code>.
  */ 
  public static class HsqldbBooleanPoemType extends BooleanPoemType {
    public HsqldbBooleanPoemType(boolean nullable) {
      super(nullable);
    }

    protected Object _getRaw(ResultSet rs, int col) throws SQLException {
      synchronized (rs) {
        int i = rs.getInt(col);
        return rs.wasNull() ? null : (i == 1 ? Boolean.TRUE : Boolean.FALSE);
      }
    }

    protected void _setRaw(PreparedStatement ps, int col, Object bool) 
        throws SQLException {
      ps.setInt(col, ((Boolean) bool).booleanValue() ? 1 : 0);
    }

  }

  public String getQuotedName(String name) {
    StringBuffer b = new StringBuffer();
    StringUtils.appendQuoted(b, unreservedName(name), '"');
    return b.toString();
  }

  public String unreservedName(String name) {
    if(name.equalsIgnoreCase("UNIQUE")) name = "MELATI_" + name.toUpperCase();
    if(name.equalsIgnoreCase("CONSTRAINT")) name = "MELATI_" + name.toUpperCase();
    return name.toUpperCase();
  }

  public String melatiName(String name) {
    if(name.equalsIgnoreCase("MELATI_UNIQUE")) name = "unique";
    if(name.equalsIgnoreCase("MELATI_CONSTRAINT")) name = "constraint";
    return name.toLowerCase();
  }

  /**
   * Work around a feature in HSQLDB where it seems that you need 
   * to use an unquoted string to get index info but a quoted name 
   * to enable use of lowercase names. 
   * 
   * @see org.melati.poem.dbms.Dbms#getJdbcMetadataName(java.lang.String)
   **/
  public String getJdbcMetadataName(String name) {
    return name;
  }
  
  /**
   * Hsqldb gets its scope confused unless inner table is aliased.
   *
   * @todo Test that there are no results if the user does not have
   * the capability but some other user does, because it seems to
   * me (in my ignorance) that such a test will fail, JimW.
   */
  public String givesCapabilitySQL(User user, String capabilityExpr) {
    return "SELECT * FROM "
      + getQuotedName("groupmembership")
      + " WHERE "
      + getQuotedName("user")
      + " = "
      + user.troid()
      + " AND "
      + "EXISTS ( "
      + "SELECT "
      + getQuotedName("groupcapability")
      + "."
      + getQuotedName("group")
      + " "
      + "FROM "
      + getQuotedName("groupcapability")
      + ", "
      + getQuotedName("groupmembership")
      + " AS GM2"
      + " WHERE "
      + getQuotedName("groupcapability")
      + "."
      + getQuotedName("group")
      + " = "
      + "GM2."
      + getQuotedName("group")
      + " AND "
      + getQuotedName("capability")
      + " = "
      + capabilityExpr
      + ")";
  }

  /** 
   * Note that this is NOT case insensitive.
   * 
   * @see org.melati.poem.dbms.Dbms#caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)
   */
  public String caseInsensitiveRegExpSQL(String term1, String term2) {
    if (StringUtils.isQuoted(term2)) {
      term2 = term2.substring(1, term2.length() - 1);
    } 
    term2 = StringUtils.quoted(StringUtils.quoted(term2, '%'), '\'');
    
    return term1 + " LIKE " + term2;
  }

}
