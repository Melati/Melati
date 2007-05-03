package org.melati.poem.dbms.test.sql;

import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author timp
 * @since 10 Feb 2007
 *
 */
public class ThrowingSavepoint extends Thrower implements Savepoint {
  final static String className = ThrowingSavepoint.class.getName() + ".";
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
  
  Savepoint it = null;
  
  /**
   * Constructor.
   * @param savepoint to decorate
   */
  public ThrowingSavepoint(Savepoint savepoint) {
    this.it = savepoint;
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Savepoint#getSavepointId()
   */
  public int getSavepointId() throws SQLException {
    if (shouldThrow("getSavepointId"))
      throw new SQLException("Savepoint bombed");
    return it.getSavepointId();
  }

  /**
   * {@inheritDoc}
   * @see java.sql.Savepoint#getSavepointName()
   */
  public String getSavepointName() throws SQLException {
    if (shouldThrow("getSavepointName"))
      throw new SQLException("Savepoint bombed");
    return it.getSavepointName();
  }

}
