package org.melati.poem;

import java.util.*;
import java.sql.*;

public class PoemDatabase extends PoemDatabaseBase {

  public static void main(final String[] args) throws Exception {
    DriverManager.registerDriver((Driver)Class.forName("postgresql.Driver").newInstance());
    final Database database = new PoemDatabase();
    database.connect("jdbc:postgresql:williamc", "williamc", "*");
    // database.dump();

    User william = database.getUserTable().getUserObject(new Integer(0));
    final Table t = database.getTable("foo");

    database.logSQL = true;

    database.inSession(
        william,
        new Runnable() {
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
            }
          }
        });

    database.inCommittedSession(
        william,
        new Runnable() {
          public void run() {
            try {
              t.getObject(4).deleteAndCommit();
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        });


//     database.dumpCacheAnalysis();
//     System.err.println("\n=== uncacheing contents");
//     database.uncacheContents();
//     database.dumpCacheAnalysis();
//     System.err.println("\n=== trimming");
//     database.trimCache(5);
//     database.dumpCacheAnalysis();
  }
}
