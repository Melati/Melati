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
    throwers.put(methodName, new Integer(1));
  }
  /**
   * Tell named method to start throwing exceptions.
   * @param methodName name in class.methodName format
   */
  public static void startThrowingAfter(String methodName, int goes) {
    throwers.put(methodName, new Integer(1 + goes));
  }
  /**
   * Tell named method to start throwing exceptions.
   * @param methodName name in class.methodName format
   */
  public static void stopThrowing(String methodName) {
    throwers.put(methodName, new Integer(0));
  }
  /**
   * @param methodName name in class.methodName format
   * @return whether method named should throw exception
   */
  public static boolean shouldThrow(String methodName) { 
    if (throwers.get(methodName) == null) 
      throwers.put(methodName, new Integer(0));
    int toGo = ((Integer)throwers.get(methodName)).intValue(); 
    if (toGo == 0)  
      return false;
    else { 
      toGo = toGo - 1;
      throwers.put(methodName, new Integer(toGo));
      return toGo == 0 ? true : false;
    }
  }


}
