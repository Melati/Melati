package org.melati.poem;

import java.util.*;
import java.sql.*;

public class PoemDatabase extends PoemDatabaseBase {

  private static User william = null;

  public static void main(final String[] args) {
    try {
      DriverManager.registerDriver((Driver)Class.forName("postgresql.Driver").newInstance());
      final Database database = new PoemDatabase();
      database.connect("jdbc:postgresql:melatitest", "postgres", "*");
      database.dump();

      database.logSQL = true;

      database.inSession(
          AccessToken.root,
          new PoemTask() {
            public void run() {
              william = database.getUserTable().getUserObject(0);
            }
          });

      final Table t = database.getTable("foo");

      database.inSession(
          william,
          new PoemTask() {
            public void run() {
              try {
                t.getObject(3).setValue("bar", new java.util.Date().toString().substring(0, 20));
                t.getObject(3).setValue("baz", new java.util.Date().toString().substring(0, 20));
                Enumeration e = database.referencesTo(database.getTableInfoTable().getObject(1));
                while (e.hasMoreElements())
                  System.out.println(e.nextElement());
              }
              catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
              }
            }
          });
    }
    catch (Exception e) {
      try {
        e.printStackTrace();
      }
      catch (Exception ee) {
        System.err.println("caught exception: " + ee.getClass().getName());
        ee.printStackTrace();
      }
    }

//     database.dumpCacheAnalysis();
//     System.err.println("\n=== uncacheing contents");
//     database.uncacheContents();
//     database.dumpCacheAnalysis();
//     System.err.println("\n=== trimming");
//     database.trimCache(5);
//     database.dumpCacheAnalysis();
  }
}
