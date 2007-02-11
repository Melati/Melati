/**
 * 
 */
package org.melati.poem.dbms.test;

import java.util.Hashtable;


/**
 * A class which can throw on demand.
 * 
 * @author timp
 * @since 10 Feb 2007
 *
 */
public abstract class Thrower {
  protected static Hashtable throwers;

  public static void startThrowing(String methodName) {
    throwers.put(methodName, Boolean.TRUE);
  }
  public static void stopThrowing(String methodName) {
    throwers.put(methodName, Boolean.FALSE);
  }
  protected static boolean shouldThrow(String methodName) { 
    if (throwers.get(methodName) == null || throwers.get(methodName) == Boolean.FALSE)
      return false;
    return true;
  }


}
