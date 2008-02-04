package org.melati.poem.dbms.test.sql;

import java.sql.ParameterMetaData;
import java.sql.SQLException;

/**
 * @author timp
 * @since 22 Feb 2007
 *
 */
public class ThrowingParameterMetaData extends Thrower implements
        ParameterMetaData {
  
  final static String className = ThrowingParameterMetaData.class.getName() + ".";
  public static void startThrowing(String methodName) {
    Thrower.startThrowing(className  +  methodName);
  }
  public static void startThrowingAfter(String methodName, int goes) {
    Thrower.startThrowingAfter(className  +  methodName, goes);
  }
  public static void stopThrowing(String methodName) {
    Thrower.stopThrowing(className  +  methodName);
  }
  public static boolean shouldThrow(String methodName) { 
    return Thrower.shouldThrow(className  +  methodName);
  }

  ParameterMetaData it = null;
  
  /**
   * Constructor.
   * @param parameterMetaData the ParameterMetaData to decorate
   */
  public ThrowingParameterMetaData(ParameterMetaData parameterMetaData) {
    this.it = parameterMetaData;
  }


  public String getParameterClassName(int param) throws SQLException {
    if (shouldThrow("getParameterClassName"))
      throw new SQLException("CallableStatement bombed");
    return it.getParameterClassName(param);
  }

  public int getParameterCount() throws SQLException {
    if (shouldThrow("getParameterCount"))
      throw new SQLException("CallableStatement bombed");
    return it.getParameterCount();
  }

  public int getParameterMode(int param) throws SQLException {
    if (shouldThrow("getParameterMode"))
      throw new SQLException("CallableStatement bombed");
    return it.getParameterMode(param);
  }

  public int getParameterType(int param) throws SQLException {
    if (shouldThrow("getParameterType"))
      throw new SQLException("CallableStatement bombed");
    return it.getParameterType(param);
  }

  public String getParameterTypeName(int param) throws SQLException {
    if (shouldThrow("getParameterTypeName"))
      throw new SQLException("CallableStatement bombed");
    return it.getParameterTypeName(param);
  }

  public int getPrecision(int param) throws SQLException {
    if (shouldThrow("getPrecision"))
      throw new SQLException("CallableStatement bombed");
    return it.getPrecision(param);
  }

  public int getScale(int param) throws SQLException {
    if (shouldThrow("getScale"))
      throw new SQLException("CallableStatement bombed");
    return it.getScale(param);
  }

  public int isNullable(int param) throws SQLException {
    if (shouldThrow("isNullable"))
      throw new SQLException("CallableStatement bombed");
    return it.isNullable(param);
  }

  public boolean isSigned(int param) throws SQLException {
    if (shouldThrow("isSigned"))
      throw new SQLException("CallableStatement bombed");
    return it.isSigned(param);
  }

  /**
   * JDK 1.6
   * @param c
   */
  public boolean isWrapperFor(Class c) {
    Class c2 = c;
    c = c2;
    return false;
  }
}
