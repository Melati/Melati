package org.melati;

import java.util.*;
import java.io.*;
import java.sql.*;
import org.melati.util.*;
import org.melati.poem.*;

class LogicalDatabase {

  private LogicalDatabase() {}

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

  static Database named(String name) throws DatabaseInitException {
    synchronized (databases) {
      Database database = (Database)databases.get(name);
      if (database == null) {
        try {
          Properties defs = databaseDefs();
          String pref = "org.melati.LogicalDatabase." + name + ".";
          String url = PropertiesUtils.getOrDie(defs, pref + "url");
          String user = PropertiesUtils.getOrDie(defs, pref + "user");
          String pass = PropertiesUtils.getOrDie(defs, pref + "pass");
          String clazz = PropertiesUtils.getOrDie(defs, pref + "class");
          String driver = PropertiesUtils.getOrDie(defs, pref + "driver");

          try {
            DriverManager.getDriver(url);
          }
          catch (SQLException e) {
            DriverManager.registerDriver(
                (Driver)Class.forName(driver).newInstance());
          }

          database = (Database)Class.forName(clazz).newInstance();
          database.connect(url, user, pass);
        }
        catch (Exception e) {
          throw new DatabaseInitException(name, e);
        }

        databases.put(name, database);
      }
      return database;
    }
  }
}
