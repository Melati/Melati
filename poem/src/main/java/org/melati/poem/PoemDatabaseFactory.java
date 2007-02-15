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
package org.melati.poem;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author timp
 * @since 2 Feb 2007
 *
 */
public final class PoemDatabaseFactory {

  private static final Hashtable databases = new Hashtable();
  /** The class name of this <code>LogicalDatabase</code>. */
  public static final String className = "org.melati.LogicalDatabase";


  /**
   * Disallow instantiation.
   */
  private PoemDatabaseFactory() {
  }
  

  /** 
   * Retrieve the databases which have completed initialisation.
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


  /** 
   * Retrieve the names of the databases which have 
   * completed initialisation.
   * Note that a databse which has not been used 
   * will not have been initialised.
   *
   * @return a <code>Vector</code> of the initialised database names
   */
  public static Vector getInitialisedDatabaseNames() {
    Vector dbs = new Vector();
    Enumeration e = null;
    synchronized (databases) {
      e = databases.keys();
      while (e.hasMoreElements()) {
        String key = (String)e.nextElement();
        Object dbOrPending = databases.get(key);
        if (dbOrPending != pending)
          dbs.addElement(key);
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
      throws DatabaseInitialisationPoemException {
    if (name == null)
      throw new NullPointerException();

    Object dbOrPending;

    synchronized (databases) {
      dbOrPending = databases.get(name);
    }
    if (dbOrPending == pending)
      throw new ConnectionPendingException(name);
    return (Database)dbOrPending;   
  }

  /**
   * Return a database from the cache or create it.
   * NOTE The first sucessful invocation will determine databases settings.  
   * @param name a short name of the db
   * @param url a JDBC url
   * @param user user authorised to access the databse through JDBC
   * @param password password for the user
   * @param clazz the name of the (POEM) database class
   * @param dbmsClass the name of the (POEM) dbms class
   * @param addConstraints whether to add constraints to the databases JDBC meta data
   * @param logSQL whether SQL statements should be logged
   * @param logCommits whether commits should be logged
   * @param maxTransactions the number of transactions (one less than the number of connections)
   * @return a new or existing database
   */
  public static Database getDatabase(String name, 
          String url, String user, String password, 
          String clazz, String dbmsClass, 
          boolean addConstraints, boolean logSQL, boolean logCommits,
          int maxTransactions) {
    
    Object dbOrPending;

    synchronized (databases) {
      dbOrPending = databases.get(name);
    }
    if (dbOrPending == pending)
      throw new ConnectionPendingException(name);
    if (dbOrPending != null)
      return (Database)dbOrPending;
    
    // Set an entry whilst we load
    databases.put(name, pending);

    Database database = null;
    try {

      try {
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

        // Set properties
        if (logSQL)
          database.setLogSQL(true);
        if (logCommits)
          database.setLogCommits(true);

        database.connect(dbmsClass, url, user, password, maxTransactions);

        if (addConstraints)
          database.addConstraints();
      }
      finally {
        // get it removed from the "initialising" state even if an Error, such
        // as no class found, occurs
        databases.remove(name);
      }

      databases.put(name, database);
    }
    catch (Exception e) {
      throw new DatabaseInitialisationPoemException(name, e);
    }
    return database;
  }

  /**
   * Enable a database to be reinitialised, used in tests.
   * @param name name of db to remove
   */
  public static void removeDatabase(String name) { 
    databases.remove(name);
  }
}
