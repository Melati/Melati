/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.util;

import java.util.Properties;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PropertiesUtils {
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

  public static Object instanceOfNamedClass(
      Properties properties, String propertyName, Class base, Class defaulT)
         throws InstantiationPropertyException {
    String className = (String)properties.get(propertyName);
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
