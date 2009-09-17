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
package org.melati.poem.util;

import java.lang.reflect.Method;

/**
 * @author timp
 * @since 15 Jun 2007
 * 
 */
public final class ClassUtils {
  
  private ClassUtils() {}
  
  /**
   * @param clazz The class to introspect
   * @param methodName The name of one argument methods to find
   * @return An array of methods with the given name and one argument
   */
  public static Method[] getOneArgumentMethods(Class<?> clazz, String methodName) {
    Method[] methods = clazz.getMethods();
    Method[] possibleMethods = new Method[methods.length];
    int possibleIndex = 0;
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equals(methodName)
              && methods[i].getParameterTypes().length == 1) {
        possibleMethods[possibleIndex++] = methods[i];
      }
    }
    return (Method[])ArrayUtils.section(possibleMethods, 0, possibleIndex);
  }

  /**
   * @param clazz The class to introspect
   * @param methodName The name of the no argument public method to return
   * @return An accessible Method with the given name and no arguments or null
   */
  public static Method getNoArgMethod(Class<?> clazz, String methodName) {
    Method method;
    try {
      method = clazz.getMethod(methodName, new Class[] {});
    } catch (Exception e) {
      method = null;
    }
    return method;
  }

}
