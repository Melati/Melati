/*
 * Copyright (C) 2004 Tim Pizey
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

import org.melati.poem.*;
import org.melati.poem.SQLType;

import java.sql.*;

/**
 * A Driver for Oracle (http://www.oracle.com/).
 */
public class Oracle extends AnsiStandard {

  /**
   * Oracle does not have a pleasant <code>TEXT</code>
   * datatype, so we use an arbetary value in a
   * <code>VARCHAR</code>.
   */
  public static int oracleTextHack = 4000;

  /**
   * Constructor.
   */
  public Oracle() {
    setDriverClassName("oracle.jdbc.OracleDriver");
  }

  /*

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
*/

  /**
   * Accommodate String/CLOB distinction.
   *
   * @param size the string length (-1 means no limit)
   * @return the SQL definition for a string of this size
   * @see org.melati.poem.dbms.AnsiStandard#getStringSqlDefinition(int)
   */
  @Override
  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0) {
      return "CLOB";
    }
    return super.getStringSqlDefinition(size);
  }

  @Override
  public String getLongSqlDefinition() {
    return "NUMBER";
  }

  @Override
  public String getSqlDefinition(String sqlTypeName) {
    if (sqlTypeName.equals("BOOLEAN")) {
      return ("CHAR(1)");
    }
    return super.getSqlDefinition(sqlTypeName);
  }

  @Override
  public String sqlBooleanValueOfRaw(Object raw) {
    if ((Boolean) raw)
      return "1";
    else
      return "0";
  }

  /**
   * Translates a Oracle String into a Poem <code>StringPoemType</code>.
   */
  public static class OracleStringPoemType extends StringPoemType {

    /**
     * Constructor.
     *
     * @param nullable nullability
     * @param size     size
     */
    public OracleStringPoemType(boolean nullable, int size) {
      super(nullable, size);
    }

    protected boolean _canRepresent(SQLPoemType<?> other) {
      return ((sqlTypeCode() == Types.VARCHAR || sqlTypeCode() == Types.CLOB)
          && (other.sqlTypeCode() == Types.VARCHAR || other.sqlTypeCode() == Types.CLOB)
      ) &&
          (
              (getSize() == oracleTextHack && ((StringPoemType) other).getSize() == -1)
                  ||
                  (getSize() == -1 &&
                      ((StringPoemType) other).getSize() == oracleTextHack)
                  ||
                  ((getSize() >= ((StringPoemType) other).getSize())));
    }

    /**
     * {@inheritDoc}
     *
     * @see org.melati.poem.BasePoemType#canRepresent(PoemType)
     */
    public <O> PoemType<O> canRepresent(PoemType<O> other) {
      return other instanceof StringPoemType &&
          _canRepresent((StringPoemType) other) &&
          !(!getNullable() && (other).getNullable()) ?
          other : null;
    }

  }

  @Override
  public String getBinarySqlDefinition(int size) throws SQLException {
    return "BLOB";
  }

  @Override
  public String unreservedName(String name) {
    if (name.equalsIgnoreCase("user")) name = "melati_" + name;
    if (name.equalsIgnoreCase("group")) name = "melati_" + name;
    return name.toUpperCase();
  }

  @Override
  public String melatiName(String name) {
    if (name == null) return null;
    if (name.equalsIgnoreCase("melati_user")) name = "user";
    if (name.equalsIgnoreCase("melati_group")) name = "group";
    return name.toLowerCase();
  }

  @Override
  public <S, O> PoemType<O> canRepresent(PoemType<S> storage, PoemType<O> type) {
    if ((storage instanceof IntegerPoemType &&
        type instanceof BigDecimalPoemType) &&
        !(!storage.getNullable() && type.getNullable())) {
      return type;
    }
    if ((storage instanceof IntegerPoemType &&
        type instanceof LongPoemType) &&
        !(!storage.getNullable() && type.getNullable())) {
      return type;
    } else {
      return storage.canRepresent(type);
    }
  }

  @Override
  public SQLPoemType<?> defaultPoemTypeOfColumnMetaData(ResultSet md)
      throws SQLException {
    System.err.println("TYPE_NAME:" + md.getString("TYPE_NAME"));
    //ResultSetMetaData rsmd = md.getMetaData();
    //int cols = rsmd.getColumnCount();
    //for (int i = 1; i <= cols; i++) {
    //String table = rsmd.getTableName(i);
    //System.err.println("table name: " + table);
    //String column = rsmd.getColumnName(i);
    //System.err.println("column name: " + column);
    //int type = rsmd.getColumnType(i);
    //System.err.println("type: " + type);
    //String typeName = rsmd.getColumnTypeName(i);
    //System.err.println("type Name: " + typeName);
    //String className = rsmd.getColumnClassName(i);
    //System.err.println("class Name: " + className);
    //System.err.println("String val: " + md.getString(i));
    //System.err.println("");
    //}

    if (md.getString("TYPE_NAME").equals("VARCHAR2"))
      return
          new OracleStringPoemType(
              md.getInt("NULLABLE") ==
                  DatabaseMetaData.columnNullable,
              md.getInt("COLUMN_SIZE"));
    if (md.getString("TYPE_NAME").equals("CLOB"))
      return
          new OracleStringPoemType(
              md.getInt("NULLABLE") ==
                  DatabaseMetaData.columnNullable,
              md.getInt("COLUMN_SIZE"));
    if (md.getString("TYPE_NAME").equals("CHAR"))
      return
          new OracleBooleanPoemType(
              md.getInt("NULLABLE") ==
                  DatabaseMetaData.columnNullable);
    if (md.getString("TYPE_NAME").equals("BLOB"))
      return new BinaryPoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable,
          md.getInt("COLUMN_SIZE"));
    if (md.getString("TYPE_NAME").equals("FLOAT"))
      return new DoublePoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
    if (
        md.getString("TYPE_NAME").equals("NUMBER"))
      return new IntegerPoemType(
          md.getInt("NULLABLE") == DatabaseMetaData.columnNullable);

    return super.defaultPoemTypeOfColumnMetaData(md);
  }

  @Override
  public String getForeignKeyDefinition(String tableName, String fieldName,
                                        String targetTableName, String targetTableFieldName, String fixName) {
    String q = " ADD (CONSTRAINT FK_" + tableName + "_" + fieldName + ") " +
        "FOREIGN KEY (" + getQuotedName(fieldName) + ") " +
        "REFERENCES " + getQuotedName(targetTableName) +
        "(" + getQuotedName(targetTableFieldName) + ")";
    // Not currently implemented by Oracle,
    // another reason for not using the DB to control these things
    //if (fixName.equals("prevent"))
    //  q += " ON DELETE NO ACTION";
    if (fixName.equals("delete"))
      q += " ON DELETE CASCADE";
    if (fixName.equals("clear"))
      q += " ON DELETE SET NULL";
    return q;
  }

  @Override
  public String getPrimaryKeyDefinition(String fieldName) {
    return " ADD (CONSTRAINT PK_" + fieldName +
        " PRIMARY KEY(" + getQuotedName(fieldName) + "))";
  }

  @Override
  public String booleanTrueExpression(Column<Boolean> booleanColumn) {
    return booleanColumn.fullQuotedName() + "=1";
  }

  @Override
  public String getSqlDefaultValue(SQLType<?> sqlType) {
    if (sqlType instanceof BooleanPoemType) {
      return ("0");
    }
    return super.getSqlDefaultValue(sqlType);
  }

  /**
   * Translates an Oracle Boolean into a Poem <code>BooleanPoemType</code>.
   */
  public static class OracleBooleanPoemType extends BooleanPoemType {

    /**
     * Constructor.
     *
     * @param nullable nullability
     */
    public OracleBooleanPoemType(boolean nullable) {
      super(nullable);
    }

    protected Boolean _getRaw(ResultSet rs, int col) throws SQLException {
      synchronized (rs) {
        boolean v = rs.getBoolean(col);
        return rs.wasNull() ? null : (v ? Boolean.TRUE : Boolean.FALSE);
      }
    }

    protected void _setRaw(PreparedStatement ps, int col, Object bool)
        throws SQLException {
      ps.setInt(col, (Boolean) bool ? 1 : 0);
    }

  }
}
