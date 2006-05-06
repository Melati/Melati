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

package org.melati;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Properties;

import org.melati.poem.Database;
import org.melati.util.ConnectionPendingException;
import org.melati.util.DatabaseInitException;
import org.melati.util.PropertiesUtils;

/**
 * An object which knows how to connect to a database.
 */
public final class LogicalDatabase {

  private LogicalDatabase() {}

  /** The class name of this <code>LogicalDatabase</code>. */
  public static final String className =
      new LogicalDatabase().getClass().getName();

  private static final String defaultPropertiesName =
      "org.melati.LogicalDatabase.properties";

  /** Properties, named for this class. */
  public static Properties databaseDefs = null;

  private static synchronized Properties databaseDefs() throws IOException {
    if (databaseDefs == null)
      databaseDefs =
          PropertiesUtils.fromResource(new LogicalDatabase().getClass(),
                                       defaultPropertiesName);
    return databaseDefs;
  }

  private static final Hashtable databases = new Hashtable();

  /** 
   * Retrieve the the databases which have completed initialisation.
   *
   * @return a <code>Vector</code> of the initialised databases
   */
  public static Vector initialisedDatabases() {
    Vector dbs = new Vector();
    Enumeration e = null;
    synchronized (databases) {
      e = databases.keys();
      while (e.hasMoreElements()) {
        Object dbOrPending = databases.get(e.nextElement());
        if (dbOrPending != pending)
          dbs.addElement(dbOrPending);
      }
    }
    return dbs;
  }


  private static final Object pending = new Object();

  /** 
   * Retrieve a database by name.
   *
   * @param name the name of the database
   * @throws DatabaseInitException if any Exception is trapped
   * @return a <code>Database</code> with the name specified
   */
  public static Database getDatabase(String name) 
      throws DatabaseInitException {
    if (name == null)
      throw new NullPointerException();

    Object dbOrPending;

    synchronized (databases) {
      dbOrPending = databases.get(name);
      if (dbOrPending == pending)
        throw new ConnectionPendingException(name);
      else if (dbOrPending == null)
        databases.put(name, pending);
    }

    if (dbOrPending != null)
      return (Database)dbOrPending;

    try {
      Database database;

      try {
        Properties defs = databaseDefs();
        String pref = className + "." + name + ".";
        String url = PropertiesUtils.getOrDie(defs, pref + "url");
        String user = PropertiesUtils.getOrDie(defs, pref + "user");
        String pass = PropertiesUtils.getOrDie(defs, pref + "pass");
        String clazz = PropertiesUtils.getOrDie(defs, pref + "class");
        String dbmsclass = PropertiesUtils.getOrDie(defs, pref + "dbmsclass");
        // max transactions default to 8 if not set
        int maxTrans = PropertiesUtils.
                           getOrDefault_int(defs, pref + "maxtransactions", 8);

        //Object databaseObject=Class.forName(clazz).newInstance();

        Object databaseObject = null;

        try {
          databaseObject = Thread.currentThread().getContextClassLoader().
                               loadClass(clazz).newInstance();
        }
        catch (Exception e) {
          databaseObject = Class.forName(clazz).newInstance();
        }

        if (!(databaseObject instanceof Database)) 
          throw new ClassCastException(
              "The .class=" + clazz + " entry named a class of type " +
              databaseObject.getClass() + ", " +
              "which is not an org.melati.poem.Database");

        database = (Database)databaseObject;

        // Changed to use dbmsclass not driver, it will throw an exception 
        // if that is not correct

        database.connect(dbmsclass, url, user, pass, maxTrans);
      }
      finally {
        // get it removed from the "initialising" state even if an Error, such
        // as no class found, occurs
        databases.remove(name);
      }

      databases.put(name, database);
      return database;
    }
    catch (Exception e) {
      throw new DatabaseInitException(defaultPropertiesName, name, e);
    }
  }

  /**
   * Set the databaseDefs.
   */
  public static void setDatabaseDefs(Properties databaseDefsIn) {
    databaseDefs = databaseDefsIn;
  }

  /**
   * @return Returns the defaultPropertiesName.
   */
  public static String getDefaultPropertiesName() {
    return defaultPropertiesName;
  }
}







