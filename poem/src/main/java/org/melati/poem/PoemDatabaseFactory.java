/**
 * 
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

        database.connect(dbmsClass, url, user, password, maxTransactions);

        // Set properties
        if (logSQL)
          database.setLogSQL(true);
        if (logCommits)
          database.setLogCommits(true);
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
