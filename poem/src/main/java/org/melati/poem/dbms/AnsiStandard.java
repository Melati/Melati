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
 *     International House, 174 Three Bridges Road, Crawley, West Sussex RH10 1LE, UK
 *
 */

package org.melati.poem.dbms;

import java.util.*;
import java.sql.*;
import org.melati.poem.*;
import org.melati.util.*;

public class AnsiStandard implements Dbms {
  private boolean driverLoaded = false;
  private String driverClassName = null;
  private Driver driver = null;

  protected synchronized void setDriverClassName(String name) {
    driverClassName = name;
  }
  protected synchronized String getDriverClassName() {
    return driverClassName;
  }

  protected synchronized void setDriverLoaded(boolean loaded) {
    driverLoaded = loaded;
  }
  protected synchronized boolean getDriverLoaded() {
    return driverLoaded;
  }

  protected synchronized void loadDriver() {
    Class driverClass;
    try {
      driverClass = Class.forName (getDriverClassName());
      setDriverLoaded(true);
    } catch (java.lang.ClassNotFoundException e) {
      // A call to Class.forName() forces us to consider this exception :-)...
      setDriverLoaded(false);
      return;
    }

    try {
      driver = (Driver)driverClass.newInstance();
    } catch (java.lang.Exception e) {
      // ... otherwise, "something went wrong" and I don't here care what
      // or have the wherewithal to do anything about it :)
      throw new UnexpectedExceptionPoemException(e);
    }
  }

  public Connection getConnection(String url, String user, String password) throws ConnectionFailurePoemException {
    synchronized (driverClassName) {
      if ( !getDriverLoaded() ) {
        if (getDriverClassName() == null) {
          throw new ConnectionFailurePoemException(new SQLException("No Driver Classname set in dbms specific class"));
        }
        loadDriver();
      }
      if ( !getDriverLoaded() ) {
        throw new ConnectionFailurePoemException(new SQLException("The Driver class " + getDriverClassName() + " failed to load"));
      }
    }


    if (driver != null) {
      Properties info = new Properties();
      if (user != null) info.put("user", user);
      if (password != null) info.put("password", password);
      
      try {
        return driver.connect(url, info);
      } catch (SQLException e) {
        throw new ConnectionFailurePoemException(e);
      }
    }
    

    try {
      return DriverManager.getConnection (url, user, password);
    } catch (java.sql.SQLException e) {
      throw new ConnectionFailurePoemException(e);
    }
  }

  public String getQuotedName(String name) {
    StringBuffer b = new StringBuffer();
    StringUtils.appendQuoted(b, name, '"');
    return b.toString();
  }

  public String getSqlDefinition(String sqlTypeName) throws SQLException {
    return sqlTypeName;
  }

  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0)
      throw new SQLException(
          "unlimited length not supported in AnsiStandard STRINGs");

    return "VARCHAR(" + size + ")";
  }

  public String getBinarySqlDefinition(int size) throws SQLException {
    if (size < 0)
      throw new SQLException(
          "unlimited length not supported in AnsiStandard BINARYs");

    return "LONGVARBINARY(" + size + ")";
  }

  public PoemType canRepresent(PoemType storage, PoemType type) {
    return storage.canRepresent(type);
  }

  private SQLPoemType unsupported(String sqlTypeName, ResultSet md)
      throws UnsupportedTypePoemException {
    UnsupportedTypePoemException e;
    try {
      e = new UnsupportedTypePoemException(
              md.getString("TABLE_NAME"), md.getString("COLUMN_NAME"),
              md.getShort("DATA_TYPE"), sqlTypeName,
              md.getString("TYPE_NAME"));
    }
    catch (SQLException ee) {
      throw new UnsupportedTypePoemException(sqlTypeName);
    }

    throw e;
  }

  /**
   * The simplest POEM type corresponding to a JDBC description from the
   * database.  FIXME this is meant to be customised per-database, and needs to
   * be delegated to a <TT>DatabasePecularities</TT> class.
   */

  public SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
      throws SQLException {
    int typeCode = md.getShort("DATA_TYPE");
    boolean nullable =
        md.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
    int width = md.getInt("COLUMN_SIZE");

    switch (typeCode) {
      case Types.BIT            : return new BooleanPoemType(nullable);
      case Types.TINYINT        : return unsupported("TINYINT", md);
      case Types.SMALLINT       : return unsupported("SMALLINT", md);
      case Types.INTEGER        : return new IntegerPoemType(nullable);
      case Types.BIGINT         : return unsupported("BIGINT", md);

      case Types.FLOAT          : return unsupported("FLOAT", md);
      case Types.REAL           : return new DoublePoemType(nullable);
      case Types.DOUBLE         : return new DoublePoemType(nullable);

      case Types.NUMERIC        : return unsupported("NUMERIC", md);
      case Types.DECIMAL        : return unsupported("DECIMAL", md);

      case Types.CHAR           : return unsupported("CHAR", md);
      case Types.VARCHAR        : return new StringPoemType(nullable, width);
      case Types.LONGVARCHAR    : return new StringPoemType(nullable, width);

      case Types.DATE           : return new DatePoemType(nullable);
      case Types.TIME           : return unsupported("TIME", md);
      case Types.TIMESTAMP      : return new TimestampPoemType(nullable);

      case Types.BINARY         : return unsupported("BINARY", md);
      case Types.VARBINARY      : return new BinaryPoemType(nullable, width);
      case Types.LONGVARBINARY  : return new BinaryPoemType(nullable, width);

      case Types.NULL           : return unsupported("NULL", md);

      case Types.OTHER          : return unsupported("OTHER", md);

      default: return unsupported("<code not in Types.java!>", md);
    }
  }
}
