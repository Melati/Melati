/*
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
 *     Crawley, 
 *     West Sussex RH10 1LE, UK
 *
 */

package org.melati.poem.dbms;

import org.melati.poem.*;
import org.melati.poem.SQLType;
import org.melati.poem.util.StringUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * An SQL 92 compliant Database Management System. 
 * <p>
 * Should there ever be such a
 * thing then you wouldn't need to extend this, but all DBs used with Melati so
 * far have needed to extend the standard with their own variations.
 */
public class AnsiStandard implements Dbms {

  protected String schema;
  private boolean driverLoaded = false;
  private String driverClassName = null;
  private Driver driver = null;

  protected synchronized String getDriverClassName() {
    if (driverClassName == null)
      throw new PoemBugPoemException(
          "No Driver Classname set in dbms specific class");

    return driverClassName;
  }

  protected synchronized void setDriverClassName(String name) {
    driverClassName = name;
  }

  @Override
  public void unloadDriver() {
    driver = null;
    setDriverLoaded(false);
  }

  protected synchronized boolean getDriverLoaded() {
    return driverLoaded;
  }

  protected synchronized void setDriverLoaded(boolean loaded) {
    driverLoaded = loaded;
  }

  @Override
  public String getSchema() {
    return null;
  }

  @Override
  public void shutdown(Connection connection)  
    throws SQLException{    
  }

  @Override
  public boolean canDropColumns(){
    return true;
  }

  @Override
  public boolean canStoreBlobs(){
    return true;
  }

  protected synchronized void loadDriver() {
    Class<?> driverClass;
    try {
      driverClass = Class.forName(getDriverClassName());
    } catch (ClassNotFoundException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
    
    setDriverLoaded(true);

    try {
      driver = (Driver)driverClass.newInstance();
    } catch (java.lang.Exception e) {
      // ... otherwise, "something went wrong" and I don't here care what
      // or have the wherewithal to do anything about it :)
      throw new UnexpectedExceptionPoemException(e);
    }
  }

  /**
   * The default windows installation of MySQL has autocommit set true, 
   * which throws an SQLException when one issues a commit.
   * 
   */
  @Override
  public Connection getConnection(String url, String user, String password)
      throws ConnectionFailurePoemException {
    schema = user;
    try {
      if (!getDriverLoaded()) loadDriver();

      Connection c;
      if (driver != null) {
        Properties info = new Properties();
        if (user != null)
          info.put("user", user);
        if (password != null)
          info.put("password", password);

        c = driver.connect(url, info);
        if (c == null) 
          throw new SQLException(
                    "Null connection from driver using url: " + 
                      url + 
                      " user: " + 
                      user + 
                      " password: " + password);
      } else { 
        c = DriverManager.getConnection(url, user, password);
        if (c == null) 
          throw new SQLException(
                    "Null connection from DriverManager using url: " + 
                    url + 
                    " user: " + 
                    user + 
                    " password: " + password);
      }
      if (c.getAutoCommit())
        c.setAutoCommit(false);
        //c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); 
      return c;
    } catch (Exception e) { 
      throw new ConnectionFailurePoemException(e);
    }
  }

  @Override
  public String preparedStatementPlaceholder(PoemType<?> type) {
    return "?";
  }
  
  @Override
  public String createTableSql(Table<?> table) {
    StringBuffer sqb = new StringBuffer();
    sqb.append("CREATE " + createTableTypeQualifierSql(table) + 
               "TABLE " + table.quotedName() + " (");
    Enumeration<Column<?>> columns = table.columns();
    int colCount = 0;
    while (columns.hasMoreElements()) { 
      Column<?> col = (Column<?>)columns.nextElement();
      if (colCount != 0)
        sqb.append(", ");
      colCount++;
      sqb.append(col.quotedName() + " " +
              col.getSQLType().sqlDefinition(this));
      
    }
    sqb.append(")");
    sqb.append(createTableOptionsSql());
    return sqb.toString();
  }

  @Override
  public String createTableTypeQualifierSql(Table<?> table) {
    return "";
  }

  @Override
  public String createTableOptionsSql() {
    return "";
  }

  @Override
  public String tableInitialisationSql(Table<?> table) {
    return null;
  }

  @Override
  public String getSqlDefinition(String sqlTypeName) {
    return sqlTypeName;
  }

  @Override
  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0)
      throw new SQLException(
          "Unlimited length not supported in AnsiStandard STRINGs");

    return "VARCHAR(" + size + ")";
  }

  @Override
  public String getLongSqlDefinition() {
    return "INT8";
  }

  @Override
  public String getBinarySqlDefinition(int size) throws SQLException {
    if (size < 0)
      throw new SQLException(
          "Unlimited length not supported in AnsiStandard BINARYs");

    return "LONGVARBINARY(" + size + ")";
  }

  @Override
  public String getFixedPtSqlDefinition(int scale, int precision)
      throws SQLException {
    if (scale < 0)
      throw new SQLException(
          "negative scale not supported " + 
          "in AnsiStandard DECIMALs");
    if (precision <= 0)
      throw new SQLException(
          "nonpositive precision not supported " + 
          "in AnsiStandard DECIMALs");

    return "DECIMAL(" + precision + "," + scale + ")";
  }

  @Override
  public String sqlBooleanValueOfRaw(Object raw) {
    return raw.toString();
  }

  @Override
  public <S,O>PoemType<O> canRepresent(PoemType<S> storage, PoemType<O> type) {
    return storage.canRepresent(type);
  }

  private SQLPoemType<?> unsupported(String sqlTypeName, ResultSet md)
      throws UnsupportedTypePoemException {
    UnsupportedTypePoemException e;
    try {
      e = new UnsupportedTypePoemException(md.getString("TABLE_NAME"), 
          md.getString("COLUMN_NAME"), 
          md.getShort("DATA_TYPE"), 
          sqlTypeName, md.getString("TYPE_NAME"));
    } catch (SQLException ee) {
      throw new UnsupportedTypePoemException(sqlTypeName);
    }

    throw e;
  }

  @Override
  public SQLPoemType<?> defaultPoemTypeOfColumnMetaData(ResultSet columnsMetaData)
      throws SQLException {
    int typeCode = columnsMetaData.getShort("DATA_TYPE");
    boolean nullable = columnsMetaData.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
    int width = columnsMetaData.getInt("COLUMN_SIZE");
    int scale = columnsMetaData.getInt("DECIMAL_DIGITS");

    switch (typeCode) {
      case Types.BIT :
        return new BooleanPoemType(nullable);
      case Types.TINYINT :
        return unsupported("TINYINT", columnsMetaData);
      case Types.SMALLINT :
        return unsupported("SMALLINT", columnsMetaData);
      case Types.INTEGER :
        return new IntegerPoemType(nullable);
      case Types.BIGINT :
        return new LongPoemType(nullable);

      case Types.FLOAT :
        return unsupported("FLOAT", columnsMetaData);
      case Types.REAL :
        return new DoublePoemType(nullable);
      case Types.DOUBLE :
        return new DoublePoemType(nullable);

      case Types.NUMERIC :
        return new BigDecimalPoemType(nullable, width, scale);
      case Types.DECIMAL :
        return new BigDecimalPoemType(nullable, width, scale);

      case Types.CHAR :
        return unsupported("CHAR", columnsMetaData);
      case Types.VARCHAR :
        return new StringPoemType(nullable, width == 0 ? -1 : width);
      case Types.LONGVARCHAR :
        return new StringPoemType(nullable, width == 0 ? -1 : width);

      case Types.DATE :
        return new DatePoemType(nullable);
      case Types.TIME :
        return new TimePoemType(nullable);
      case Types.TIMESTAMP :
        return new TimestampPoemType(nullable);

      case Types.BINARY :
        return unsupported("BINARY", columnsMetaData);
      case Types.VARBINARY :
        return new BinaryPoemType(nullable, width);
      case Types.LONGVARBINARY :
        return new BinaryPoemType(nullable, width);

      case Types.NULL :
        return unsupported("NULL", columnsMetaData);

      case Types.OTHER :
        return unsupported("OTHER", columnsMetaData);


        // Following introduced since 1.1
      case Types.JAVA_OBJECT : 
        return unsupported("JAVA_OBJECT", columnsMetaData);
      case Types.DISTINCT : 
        return unsupported("DISTINCT", columnsMetaData);
      case Types.STRUCT : 
        return unsupported("STRUCT", columnsMetaData);
      case Types.ARRAY : 
        return unsupported("ARRAY", columnsMetaData);
      case Types.BLOB : 
        return unsupported("BLOB", columnsMetaData);
      case Types.CLOB:
        return new StringPoemType(nullable, width == 0 ? -1 : width);
      case Types.REF:
        return unsupported("REF", columnsMetaData);
      case Types.DATALINK : 
        return unsupported("DATLINK", columnsMetaData);

      case Types.BOOLEAN : 
        return new BooleanPoemType(nullable);
      default :
        return unsupported("<code not in Types.java!>", columnsMetaData);
    }
  }

  @Override
  public SQLPoemException exceptionForUpdate(Table<?> table, String sql,
      boolean insert, SQLException e) {
    return new ExecutingSQLPoemException(sql, e);
  }

  @Override
  public SQLPoemException exceptionForUpdate(Table<?> table, PreparedStatement ps,
      boolean insert, SQLException e) {
    return exceptionForUpdate(table, ps == null ? null : ps.toString(), insert,
        e);
  }

  @Override
  public String getQuotedName(String name) {
    StringBuffer b = new StringBuffer();
    StringUtils.appendQuoted(b, unreservedName(name), '"');
    return b.toString();
  }

  @Override
  public String getQuotedValue(SQLType<?> sqlType, String value) {
    if (sqlType instanceof BooleanPoemType) {
      return value;
    }
    if (sqlType instanceof DoublePoemType) {
      return value;
    }
    if (sqlType instanceof LongPoemType) {
      return value;
    }
    if (sqlType instanceof BinaryPoemType) {
      return StringUtils.quoted(value,'\'');
    }
    if (sqlType instanceof BigDecimalPoemType) {
      return value;
    }
    if (sqlType instanceof DatePoemType) {
      return StringUtils.quoted(value,'\'');
    }
    if (sqlType instanceof TimestampPoemType) {
      return StringUtils.quoted(value,'\'');
    }
    if (sqlType instanceof TimePoemType) {
      return StringUtils.quoted(value,'\'');
    }
    if (sqlType instanceof PasswordPoemType) {
      return StringUtils.quoted(value,'\'');
    }
    if (sqlType instanceof StringPoemType) {
      return StringUtils.quoted(value,'\'');
    }
    if (sqlType instanceof IntegrityFixPoemType) {
      return value;
    }
    if (sqlType instanceof IntegerPoemType) {
      return value;
    }
    throw new PoemBugPoemException("Unrecognised sqlType: " + sqlType);
    
  }

  @Override
  public String getJdbcMetadataName(String name) {
    return name;
  }

  /**
   * A pair of functions for getting around keywords which make your 
   * JDBC driver barf, as 'group' does for MySQL.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.dbms.Dbms#unreservedName(java.lang.String)
   * @see org.melati.poem.dbms.MySQL#unreservedName
   * @see org.melati.poem.dbms.MySQL#melatiName
   */
  public String unreservedName(String name) {
    return name;
  }

  @Override
  public String melatiName(String name) {
    return name;
  }

  /**
   * MySQL requires a length argument when creating an index on a BLOB or TEXT
   * column.
   * 
   * @see org.melati.poem.dbms.MySQL#getIndexLength
   */
  @Override
  public String getIndexLength(Column<?> column) {
    return "";
  }

  /**
   * MSSQL cannot index a TEXT column. But neither can it compare them so we
   * don't use it, we use VARCHAR(255).
   */
  @Override
  public boolean canBeIndexed(Column<?> column) {
    return true;
  }

  /**
   * MySQL had no EXISTS keyword, from 4.1 onwards it does.
   * NOTE There is a bootstrap problem here, we need to use the 
   * unchecked troid, otherwise we get a stack overflow.
   *
   * @see org.melati.poem.dbms.MySQL#givesCapabilitySQL
   */
  @Override
  public String givesCapabilitySQL(Integer userTroid, String capabilityExpr) {
    return "SELECT * FROM " + getQuotedName("groupMembership") + " WHERE "
        + getQuotedName("user") + " = " + userTroid + " AND "
        + "EXISTS ( " + "SELECT " + getQuotedName("groupCapability") + "."
        + getQuotedName("group") + " FROM "
        + getQuotedName("groupCapability") + " WHERE "
        + getQuotedName("groupCapability") + "." + getQuotedName("group")
        + " = " + getQuotedName("groupMembership") + "."
        + getQuotedName("group") + " AND " + getQuotedName("capability")
        + " = " + capabilityExpr + ")";
  }

  /**
   * This is the Postgresql syntax.
   */
  @Override
  public String caseInsensitiveRegExpSQL(String term1, String term2) {
    if (StringUtils.isQuoted(term2)) {
      term2 = term2.substring(1, term2.length() - 1);
    } 
    term2 = StringUtils.quoted(StringUtils.quoted(term2, '%'), '\'');
    
    return term1 + " ILIKE " + term2;
  }

  @Override
  public String toString() {
    return this.getClass().getName();
  }

  @Override
  public String getForeignKeyDefinition(String tableName, String fieldName, 
      String targetTableName, String targetTableFieldName, String fixName) {
    String q = " ADD FOREIGN KEY (" + getQuotedName(fieldName) + ") " +
        "REFERENCES " + getQuotedName(targetTableName) +
        "(" + getQuotedName(targetTableFieldName) + ")";
    if (fixName.equals("prevent"))
      q += " ON DELETE RESTRICT";
    if (fixName.equals("delete"))
      q += " ON DELETE CASCADE";
    if (fixName.equals("clear"))
      q += " ON DELETE SET NULL";
    return q;
  }

  @Override
  public String getPrimaryKeyDefinition(String fieldName) {
    return " ADD PRIMARY KEY (" + getQuotedName(fieldName) + ")";
  }
  
  @Override
  public String alterColumnNotNullableSQL(String tableName, Column<?> column) {
    return "ALTER TABLE " + getQuotedName(tableName) +
    " ALTER COLUMN " + getQuotedName(column.getName()) +
    " SET NOT NULL";
  }

  @Override
  public String selectLimit(String querySelection, int limit) {
    return "SELECT " + querySelection + " LIMIT " + limit;
  }

  @Override
  public String booleanTrueExpression(Column<Boolean> booleanColumn) {
    return booleanColumn.fullQuotedName();
  }

  @Override
  public String getSqlDefaultValue(SQLType<?> sqlType) {
    if (sqlType instanceof BooleanPoemType) {
      return ("false");
    }
    if (sqlType instanceof DoublePoemType) {
      return ("0.0");
    }
    if (sqlType instanceof LongPoemType) {
      return ("0");
    }
    if (sqlType instanceof BinaryPoemType) {
      return "";
    }
    if (sqlType instanceof BigDecimalPoemType) {
      return new BigDecimal(0.0).toString();
    }
    if (sqlType instanceof DatePoemType) {
      return new Date(new java.util.Date().getTime()).toString();
    }
    if (sqlType instanceof TimestampPoemType) {
      return new Timestamp(System.currentTimeMillis()).toString();
    }
    if (sqlType instanceof TimePoemType) {
      return new Time(System.currentTimeMillis()).toString();
    }
    if (sqlType instanceof PasswordPoemType) {
      return "FIXME";
    }
    if (sqlType instanceof StringPoemType) {
      return "default";
    }
    //Set prevent as default fix
    if (sqlType instanceof IntegrityFixPoemType) {
      return StandardIntegrityFix.prevent.getIndex().toString();
    }

    // Defaults to User for ColumnPoemType
    // Primary for SearchabilityPoemType
    // This needs to be last, as types above extend IntegerPoemType
    if (sqlType instanceof IntegerPoemType) {
      return ("0");
    }
    throw new PoemBugPoemException("Unrecognised sqlType: " + sqlType);

  }

  /** TODO test on something which actually uses this */
  @Override
  public String alterColumnAddCommentSQL(Column<?> column, String comment) {
    // FIREBIRD, ORACLE, postgresql
    return "COMMENT ON COLUMN " 
        + getQuotedName(unreservedName(column.getTable().getName())) 
        +"." 
        + getQuotedName(unreservedName(column.getName()))
        + " IS '" 
        + comment 
        + "'";
  }

  /** TODO test on something which actually uses this */
  @Override
  public String alterTableAddCommentSQL(Table<?> table, String comment) {
    return "COMMENT ON TABLE " 
        + getQuotedName(table.getName()) 
        + " IS '" 
        + comment 
        + "'";
  }

}

