package org.melati.poem;

import java.util.*;
import java.sql.*;

public class PoemDatabase extends PoemDatabaseBase {

  private static User william = null;

  public static void main(final String[] args) {
    try {
      // pull in a driver, but not at code level to avoid compilation problems
      // with JDBC1.1

      Driver driver = (Driver)Class.forName("org.melati.poem.postgresql.jdbc2.Driver").newInstance();

      final Database database = new PoemDatabase();
      database.connect(driver, "jdbc:postgresql:melatitest", "postgres", "*");

      database.logSQL = true;

      // final Table t = database.getTable("foo");

      database.inSession(
          database.getUserTable().administratorUser(),
          new PoemTask() {
            public void run() {
              try {
		Column[] columns = {
		    database.getUserTable().getNameColumn(),
		    database.getGroupTable().getNameColumn(),
		};
		Table[] tables = { database.getGroupMembershipTable() };

		CachedTailoredQuery q =
		    new CachedTailoredQuery(
			columns, tables,
			"\"user\" = \"user\".id AND \"group\" = \"group\".id",
			null);

		for (int i = 0; i < 4; ++i) {
		  if (i == 2)
		    database.getGroupMembershipTable().create(
                        new GroupMembershipData(new Integer(0),
						new Integer(1)));

		  for (Enumeration ms = q.selection(); ms.hasMoreElements();) {
		    FieldSet fs = (FieldSet)ms.nextElement();
		    System.out.println(fs.get("user_name").getValueString() +
				       " is a member of " +
				       fs.get("group_name").getValueString());
		  }
		}

				  
//                 t.getObject(3).setValue("bar", new java.util.Date().toString().substring(0, 20));
//                 t.getObject(3).setValue("baz", new java.util.Date().toString().substring(0, 20));

//                 // Enumeration e = database.referencesTo(database.getTableInfoTable().getObject(1));
//                 // while (e.hasMoreElements())
//                 //   System.out.println(e.nextElement());

//                 TableInfoData tid = new TableInfoData();
//                 tid.name = "newtable";
//                 tid.displayname = "A random new table";
//                 tid.description = "Just a gash table to test the addTable thing";
//                 TableInfo ti = (TableInfo)database.getTableInfoTable().create(tid);
//                 database.addTableAndCommit(ti, "id");

//                 Table newtable = database.getTable("newtable");

//                 ColumnInfoData cid = new ColumnInfoData();
//                 cid.name = "newcolumn";
//                 cid.displayname = "A random new column";
//                 cid.description = "Just a column to test the addColumn thing";
//                 cid.tableinfo = newtable.tableInfoID();
//                 cid.usereditable = Boolean.TRUE;
//                 cid.displayable = Boolean.TRUE;
//                 cid.primarydisplay = Boolean.FALSE;
//                 cid.type = PoemTypeFactory.STRING.code;
//                 cid.nullable = Boolean.TRUE;
//                 cid.size = new Integer(20);
//                 cid.width = new Integer(1);
//                 cid.height = new Integer(1);
//                 newtable.addColumnAndCommit((ColumnInfo)database.getColumnInfoTable().create(cid));
              }
              catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
              }
            }
          });

      //      database.dump();
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
