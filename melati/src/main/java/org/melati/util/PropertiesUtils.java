/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.util;

import java.util.Properties;
import java.io.*;

public class PropertiesUtils {

  public static Properties fromFile(File path) throws IOException {
    InputStream data = new FileInputStream(path);
    Properties them = new Properties();
    try {
      them.load(data);
    }
    catch (IOException e) {
      throw new IOException("Corrupt properties file `" + path + "': " +
                            e.getMessage());
    }

    return them;
  }

  public static Properties fromResource(Class clazz, String name)
      throws IOException {
    InputStream is = clazz.getResourceAsStream(name);

    if (is == null)
      throw new FileNotFoundException(name + ": is it in CLASSPATH?");

    Properties them = new Properties();
    try {
      them.load(is);
    }
    catch (IOException e) {
      throw new IOException("Corrupt properties file `" + name + "': " +
                            e.getMessage());
    }

    return them;
  }

  public static String getOrDie(Properties properties, String propertyName)
      throws NoSuchPropertyException {
    String value = properties.getProperty(propertyName);
    if (value == null)
      throw new NoSuchPropertyException(properties, propertyName);
    return value;
  }

  public static String getOrDefault(Properties properties, String propertyName, String def)
      throws NoSuchPropertyException {
    String value = properties.getProperty(propertyName);
    if (value == null) return def;
    return value;
  }

  public static int getOrDie_int(Properties properties, String propertyName)
      throws NoSuchPropertyException, FormatPropertyException {
    String string = getOrDie(properties, propertyName);
    try {
      return Integer.parseInt(string);
    }
    catch (NumberFormatException e) {
      throw new FormatPropertyException(properties, propertyName, string,
					"an integer", e);
    }
  }

  public static int getOrDefault_int(Properties properties, String propertyName, int def)
      throws NoSuchPropertyException, FormatPropertyException {
    String string = getOrDefault(properties, propertyName, ""+def);
    try {
      return Integer.parseInt(string);
    }
    catch (NumberFormatException e) {
      throw new FormatPropertyException(properties, propertyName, string,
					"an integer", e);
    }
  }

  public static Object instanceOfNamedClass(
      Properties properties, String propertyName, Class base, Class defaulT)
         throws InstantiationPropertyException {
      String className =  (String)properties.get(propertyName);
    if (className == null)
      try {
	return defaulT.newInstance();
      }
      catch (Exception e) {
	// FIXME grrrr
	throw new RuntimeException(e.toString());
      }
    else {
      try {
        Class clazz = Class.forName(className);
        if (!base.isAssignableFrom(clazz))
          throw new ClassCastException(clazz + " is not descended from " +
                                       base);
        return clazz.newInstance();
      }
      catch (Exception e) {
        throw new InstantiationPropertyException(properties, propertyName, e);
      }
    }
  }

  public static Object instanceOfNamedClass(
      Properties properties, String propertyName,
      String baseName, String defaultName)
          throws InstantiationPropertyException {
    Class base, defaulT;
    try {
      base = Class.forName(baseName);
      defaulT = Class.forName(defaultName);
    }
    catch (Exception e) {
      // FIXME grrrr
      throw new RuntimeException(e.toString());
    }


    return instanceOfNamedClass(properties, propertyName,
				// FIXME improve error-checking
				base, defaulT);
  }
}
