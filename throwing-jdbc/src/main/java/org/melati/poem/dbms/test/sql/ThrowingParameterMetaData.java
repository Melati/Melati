package org.melati.poem.dbms.test.sql;

import java.sql.ParameterMetaData;
import java.sql.SQLException;
import java.util.Hashtable;

public class ThrowingParameterMetaData extends Thrower implements
        ParameterMetaData {
  static Hashtable throwers = new Hashtable();
  public static void startThrowing(String methodName) {
    throwers.put(methodName, Boolean.TRUE);
  }
  public static void stopThrowing(String methodName) {
    throwers.put(methodName, Boolean.FALSE);
  }
  public static boolean shouldThrow(String methodName) { 
    if (throwers.get(methodName) == null || throwers.get(methodName) == Boolean.FALSE)
      return false;
    return true;
  }
  ParameterMetaData p = null;
  
  public ThrowingParameterMetaData(ParameterMetaData parameterMetaData) {
    this.p = parameterMetaData;
  }


  public String getParameterClassName(int param) throws SQLException {
    if (shouldThrow("getParameterClassName"))
      throw new SQLException("CallableStatement bombed");
    return p.getParameterClassName(param);
  }

  public int getParameterCount() throws SQLException {
    if (shouldThrow("getParameterCount"))
      throw new SQLException("CallableStatement bombed");
    return p.getParameterCount();
  }

  public int getParameterMode(int param) throws SQLException {
    if (shouldThrow("getParameterMode"))
      throw new SQLException("CallableStatement bombed");
    return p.getParameterMode(param);
  }

  public int getParameterType(int param) throws SQLException {
    if (shouldThrow("getParameterType"))
      throw new SQLException("CallableStatement bombed");
    return p.getParameterType(param);
  }

  public String getParameterTypeName(int param) throws SQLException {
    if (shouldThrow("getParameterTypeName"))
      throw new SQLException("CallableStatement bombed");
    return p.getParameterTypeName(param);
  }

  public int getPrecision(int param) throws SQLException {
    if (shouldThrow("getPrecision"))
      throw new SQLException("CallableStatement bombed");
    return p.getPrecision(param);
  }

  public int getScale(int param) throws SQLException {
    if (shouldThrow("getScale"))
      throw new SQLException("CallableStatement bombed");
    return p.getScale(param);
  }

  public int isNullable(int param) throws SQLException {
    if (shouldThrow("isNullable"))
      throw new SQLException("CallableStatement bombed");
    return p.isNullable(param);
  }

  public boolean isSigned(int param) throws SQLException {
    if (shouldThrow("isSigned"))
      throw new SQLException("CallableStatement bombed");
    return p.isSigned(param);
  }

}
