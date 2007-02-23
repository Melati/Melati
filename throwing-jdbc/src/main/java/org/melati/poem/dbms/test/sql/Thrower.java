/**
 * 
 */
package org.melati.poem.dbms.test.sql;

import java.util.Hashtable;


/**
 * A class which can throw on demand.
 * 
 * @author timp
 * @since 10 Feb 2007
 *
 */
public abstract class Thrower {
  static Hashtable throwers = new Hashtable();

  /**
   * Tell named method to start throwing exceptions.
   * @param methodName name in class.methodName format
   */
  public static void startThrowing(String methodName) {
    throwers.put(methodName, Boolean.TRUE);
  }
  /**
   * Tell named method to start throwing exceptions.
   * @param methodName name in class.methodName format
   */
  public static void stopThrowing(String methodName) {
    throwers.put(methodName, Boolean.FALSE);
  }
  /**
   * @param methodName name in class.methodName format
   * @return whether method named should throw exception
   */
  public static boolean shouldThrow(String methodName) { 
    if (throwers.get(methodName) == null || throwers.get(methodName) == Boolean.FALSE)
      return false;
    return true;
  }


}
