package org.melati;

import java.util.*;
import java.io.*;
import java.sql.*;
import org.melati.util.*;
import org.melati.poem.*;

public class LogicalDatabase {

  private LogicalDatabase() {}

  public static final String className =
      new LogicalDatabase().getClass().getName();

  private static final String databaseDefsName =
      "org.melati.LogicalDatabase.properties";

  private static Properties databaseDefs = null;

  private static synchronized Properties databaseDefs() throws IOException {
    if (databaseDefs == null)
      databaseDefs =
          PropertiesUtils.fromResource(new LogicalDatabase().getClass(),
                                       databaseDefsName);
    return databaseDefs;
  }

  private static final Hashtable databases = new Hashtable();

  public static Database named(String name) throws DatabaseInitException {
    if (name == null)
      name = "default";
    
    synchronized (databases) {
      Database database = (Database)databases.get(name);
      if (database == null) {
        try {
          Properties defs = databaseDefs();
          String pref = className + "." + name + ".";
          String url = PropertiesUtils.getOrDie(defs, pref + "url");
          String user = PropertiesUtils.getOrDie(defs, pref + "user");
          String pass = PropertiesUtils.getOrDie(defs, pref + "pass");
          String clazz = PropertiesUtils.getOrDie(defs, pref + "class");
          String driverName = PropertiesUtils.getOrDie(defs, pref + "driver");

	  Object driverObject = Class.forName(driverName).newInstance();

	  if (!(driverObject instanceof Driver))
	    throw new ClassCastException(
                "The .driver=" + driverName + " entry named a class of type " +
                driverObject.getClass() + ", which is not a java.sql.Driver");

	  Driver driver = (Driver)driverObject;

	  Object databaseObject = Class.forName(clazz).newInstance();

	  if (!(databaseObject instanceof Database))
	    throw new ClassCastException(
                "The .class=" + driverName + " entry named a class of type " +
                databaseObject.getClass() + ", " +
                "which is not an org.melati.poem.Database");

          database = (Database)databaseObject;
          database.connect(driver, url, user, pass);
        }
        catch (Exception e) {
          throw new DatabaseInitException(databaseDefsName, name, e);
        }

        databases.put(name, database);
      }
      return database;
    }
  }
}
