/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
package org.melati.app;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * Goal which runs a Melati command.
 * 
 * @goal run
 * @phase compile
 */
public class MelatiMojo extends AbstractMojo {

  /**
   * Location of the output file.
   * 
   * @parameter expression="${project.build.directory}"
   * @required
   */
  private File outputDirectory;

  /**
   * Application name.
   * 
   * @parameter expression="TemplateAppExample"
   * @required
   */
  private String appName;

  /**
   * Database name.
   * 
   * @parameter expression="melatitest"
   * @required
   */
  private String db;

  /**
   * Database table.
   * 
   * @parameter expression="user"
   * @required
   */
  private String table;

  /**
   * Table row id (troid).
   * 
   * @parameter expression="0"
   * @required
   */
  private String troid;

  /**
   * Method.
   * 
   * @parameter expression="Main"
   * @required
   */
  private String method;

  
  public void execute()
      throws MojoExecutionException {
    File f = outputDirectory;

    if (!f.exists()) {
      f.mkdirs();
    }
    App app;
    try {
      app = (App)instanceOfNamedClass(appName, "org.melati.app.App");
      app.run(new String[] {db,  table, troid, method});
    } catch (InstantiationException e) {
      throw new MojoExecutionException("Could not load main class. Terminating", e);
    }
  }
  /**
   * Instantiate an interface.
   * 
   * @param className the name of the Class
   * @param interfaceName the name of the interface Class
   * @return a new object
   * @throws InstantiationException 
   *   if the named class does not descend from the interface
   */
  public static Object instanceOfNamedClass(String className, String interfaceName)
      throws InstantiationException {
    try {
      Class clazz = Class.forName(className);
      Class interfaceRequired = Class.forName(interfaceName);
      if (!interfaceRequired.isAssignableFrom(clazz))
        throw new ClassCastException(
            clazz + " is not descended from " + interfaceName);
      return clazz.newInstance();
    }
    catch (Exception e) {
      throw new 
          InstantiationException(
              "Error instantiating " + className + ": " + e.toString());
    }
  }

}
