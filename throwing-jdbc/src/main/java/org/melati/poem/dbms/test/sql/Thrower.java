/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
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

  protected Thrower() {}
  
  /**
   * Tell named method to start throwing exceptions.
   * @param i Interface class object
   * @param methodName name in class.methodName format
   */
  public static void startThrowing(Class i, String methodName) {
    String fullName = i.getName() + "." + methodName;
    throwers.put(fullName, new Integer(1));
  }
  /**
   * Tell named method to start throwing exceptions.
   * @param i Interface class object
   * @param methodName name in class.methodName format
   */
  public static void startThrowingAfter(Class i, String methodName, int goes) {
    String fullName = i.getName() + "." + methodName;
    throwers.put(fullName, new Integer(1 + goes));
  }
  /**
   * Tell named method to stop throwing exceptions.
   * @param i Interface class object
   * @param methodName name in class.methodName format
   */
  public static void stopThrowing(Class i, String methodName) {
    String fullName = i.getName() + "." + methodName;
    throwers.put(fullName, new Integer(0));
  }
  /**
   * Check whether method should throw, called once for every method invocation.
   * @param i Interface class object
   * @param methodName name in class.methodName format
   * @return whether method named should throw exception
   */
  public static boolean shouldThrow(Class i, String methodName) {
    String fullName = i.getName() + "." + methodName;
    if (throwers.get(fullName) == null) 
      throwers.put(fullName, new Integer(0));
    int toGo = ((Integer)throwers.get(fullName)).intValue(); 
    if (toGo == 0)  
      return false;
    else { 
      toGo = toGo - 1;
      throwers.put(fullName, new Integer(toGo));
      return toGo == 0 ? true : false;
    }
  }

}
