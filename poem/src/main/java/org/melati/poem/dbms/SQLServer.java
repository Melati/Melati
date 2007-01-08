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
 *     David Warnock (david At sundayta.co.uk)
 *     Sundayta Ltd
 *     International House, 
 *     174 Three Bridges Road, 
 *     Crawley, West Sussex 
 *     RH10 1LE, UK
 *
 */
package org.melati.poem.dbms;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.melati.poem.Column;
import org.melati.poem.DatePoemType;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.TimestampPoemType;

/**
 * A Driver for the Microsoft SQL server.
 * 
 * @todo Testing required, code has been added to keep up with the interface 
 * without testing.
 */
public class SQLServer extends AnsiStandard {

  /**
   * SQL Server does not have a pleasant <code>TEXT</code> datatype, so we use
   * an arbetary value in a <code>VARCHAR</code>.
   */
  public static final int sqlServerTextHack = 2333;

  /**
   * Constructor.
   */
  public SQLServer() {
    //buggy
    //setDriverClassName("com.merant.datadirect.jdbc.sqlserver.SQLServerDriver");
    //setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver"); //does not work
    //setDriverClassName("com.ashna.jturbo.driver.Driver"); //works
    //setDriverClassName("com.jnetdirect.jsql.JSQLDriver"); //works
    // does not return indices without schema name
    //setDriverClassName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
    setDriverClassName("com.inet.tds.TdsDriver");
    //FreeTDS driver now have many unimplemented features and => does not work.
  }

  /**
   * Get the user we are connected as and return that as the schema.
   * {@inheritDoc}
   * 
   * @see org.melati.poem.dbms.Dbms#getSchema()
   * @see org.melati.poem.dbms.AnsiStandard#getSchema()
   * @see org.melati.poem.dbms.Ansistandard#getConnection()
   */
  public String getSchema() {
    return schema;
  }

  // Commented out as PMD objects to over riding method which only call 
  // super.
  //public String getQuotedName(String name) {
    //if you don't want to set 'use ANSI quoted identifiers' database property
    //to 'true' (on SQL Server)

    /*
     * if(name.equalsIgnoreCase("nullable")) return "\"" + name+"\"";
     * if(name.equalsIgnoreCase("unique")) return "\"" + name+"\"";
     * if(name.equalsIgnoreCase("user")) return "q" + name;
     * if(name.equalsIgnoreCase("group")) return "q" + name; return name;
     */

    //if you already set 'use ANSI quoted identifiers' property to 'true'
    //return super.getQuotedName(name);
  //}

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getSqlDefinition(java.lang.String)
   */
  public String getSqlDefinition(String sqlTypeName) {
    if (sqlTypeName.equals("BOOLEAN")) {
      return ("BIT");
    }
    if (sqlTypeName.equals("DATE")) {
      return ("DATETIME");
    }
    if (sqlTypeName.equals("TIMESTAMP")) {
      return ("DATETIME");
    }
    return super.getSqlDefinition(sqlTypeName);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getStringSqlDefinition(int)
   */
  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0) { // Don't use TEXT as it doesn't support
      //indexing or comparison
      return "VARCHAR(" + sqlServerTextHack + ")";
    }
    return super.getStringSqlDefinition(size);
  }

  /**
   * Translates a MSSQL String into a Poem <code>StringPoemType</code>.
   */
  public static class MSSQLStringPoemType extends StringPoemType {

    /**
     * Constructor.
     * @param nullable nullability
     * @param size length
     */
    public MSSQLStringPoemType(boolean nullable, int size) {
      super(nullable, size);
    }

    // MSSQL returns metadata info size 2147483647 for its TEXT type
    // We set size to sqlServerTextHack for our Text type
    protected boolean _canRepresent(SQLPoemType other) {
      return (getSize() < 0 || getSize() == 2147483647
          || getSize() == sqlServerTextHack || getSize() >= ((StringPoemType) other)
          .getSize());
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.BasePoemType#canRepresent(PoemType)
     */
    public PoemType canRepresent(PoemType other) {
      return other instanceof StringPoemType
          && _canRepresent((StringPoemType) other)
          && !(!getNullable() && ((StringPoemType) other).getNullable())
          ? other
          : null;
    }

  }

  /**
   * Translates a MSSQL Date into a Poem <code>DatePoemType</code>.
   */
  public static class MSSQLDatePoemType extends DatePoemType {

    /**
     * Constructor.
     * @param nullable nullability
     */
    public MSSQLDatePoemType(boolean nullable) {
      super(Types.DATE, "DATETIME", nullable);
    }

  }

  /**
   * Translates a MSSQL Date into a Poem <code>TimestampPoemType</code>.
   */
  public static class MSSQLTimestampPoemType extends TimestampPoemType {

    /**
     * Constructor.
     * @param nullable nullability
     */
    public MSSQLTimestampPoemType(boolean nullable) {
      super(Types.TIMESTAMP, "DATETIME", nullable);
    }

  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#defaultPoemTypeOfColumnMetaData(
   *                                            java.sql.ResultSet)
   */
  public SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
      throws SQLException {

    /*
     * ResultSetMetaData rsmd = md.getMetaData(); int cols =
     * rsmd.getColumnCount(); for (int i = 1; i <= cols; i++) { String table =
     * rsmd.getTableName(i); System.err.println("table name: " + table); String
     * column = rsmd.getColumnName(i); System.err.println("column name: " +
     * column); int type = rsmd.getColumnType(i); System.err.println("type: " +
     * type);s String typeName = rsmd.getColumnTypeName(i);
     * System.err.println("type Name: " + typeName); String className =
     * rsmd.getColumnClassName(i); System.err.println("class Name: " +
     * className); System.err.println("String val: " + md.getString(i));
     * System.err.println(""); }
     */
    if (md.getString("TYPE_NAME").equals("text"))
      return new MSSQLStringPoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable, md
              .getInt("COLUMN_SIZE"));
    // We use a magic number for text fields
    if (md.getString("TYPE_NAME").equals("varchar")
        && md.getInt("COLUMN_SIZE") == sqlServerTextHack)
      return new MSSQLStringPoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable, md
              .getInt("COLUMN_SIZE"));
    if (md.getString("TYPE_NAME").equals("char"))
      return new StringPoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable, md
              .getInt("COLUMN_SIZE"));
    if (md.getString("TYPE_NAME").equals("datetime"))
      return new MSSQLDatePoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
    /*
     * // MSSQL returns type -2 (BINARY) not 93 (TIMESTAMP) They don't mean what
     * we mean by timestamp if( md.getString("TYPE_NAME").equals("timestamp"))
     * return new TimestampPoemType(md.getInt("NULLABLE")==
     * DatabaseMetaData.columnNullable);
     */
    return super.defaultPoemTypeOfColumnMetaData(md);
  }

  /**
   * Ignore <TT>dtproperties</TT> as it is a 'System' table used to store
   * Entity Relationship diagrams which haVE a jdbc type of TABLE when it should
   * probably have a jdbc type of 'SYSTEM TABLE'.
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#melatiName(java.lang.String)
   */
  public String melatiName(String name) {
    if (name == null)
      return null;
    if (name.equalsIgnoreCase("dtproperties"))
      return null;
    return name;
  }

  /**
   * MSSQL cannot index TEXT fields. Probably means that if you are serious
   * about using MSSQL you should use a varchar.
   * 
   * If a field is defined as Text in the DSD we use a VARCHAR. Not sure what
   * happens if a legacy db really uses TEXT.
   * 
   * @return whether it is allowed.
   */
  public boolean canBeIndexed(Column column) {
    PoemType t = column.getType();
    if (t instanceof StringPoemType && ((StringPoemType) t).getSize() < 0)
      return false;
    return true;
  }
  /**
   * Slightly different - embrace and extend.
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#getForeignKeyDefinition
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

}

