package org.melati.poem.dbms.test.sql;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Hashtable;

public class ThrowingSavepoint extends Thrower implements Savepoint {
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
  Savepoint s = null;
  
  public ThrowingSavepoint(Savepoint savepoint) {
    this.s = savepoint;
  }

  public int getSavepointId() throws SQLException {
    if (shouldThrow("getSavepointId"))
      throw new SQLException("Savepoint bombed");
    return s.getSavepointId();
  }

  public String getSavepointName() throws SQLException {
    if (shouldThrow("getSavepointName"))
      throw new SQLException("Savepoint bombed");
    return s.getSavepointName();
  }

}
