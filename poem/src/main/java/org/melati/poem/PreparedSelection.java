package org.melati.poem;

import org.melati.util.*;
import java.sql.*;
import java.util.*;

public class PreparedSelection {

  private static class SelectionVersion extends VersionVector {
    long tableSerial;
    SelectionVersion(long tableSerial) {
      this.tableSerial = tableSerial;
    }
  }

  private PoemFloatingVersionedObject cache;
  private final Table table;

  public PreparedSelection(final Table table,
                           final String whereClause,
                           final String orderByClause) {
    this.table = table;
    final Database database = table.getDatabase();

    // HACK we use 0 to mean "committed session", i + 1 to mean "noncommitted
    // session i"

    final CachedIndexFactory statements =
        new CachedIndexFactory() {
          protected Object reallyGet(int index) {
            String sql = table.selectionSQL(whereClause, orderByClause, false);
            try {
	      Connection c =
		  index == 0 ? database.getCommittedConnection()
		             : database.session(index - 1).getConnection();
              return c.prepareStatement(sql);
            }
            catch (SQLException e) {
              throw new SQLPoemException(e);
            }
          }
        };

    cache =
        new PoemFloatingVersionedObject(database) {
          protected Version backingVersion(Session session) {
            try {
	      PreparedStatement statement =
		  (PreparedStatement)statements.get(session == null ?
						      0 :
						      session.index() + 1);

              SelectionVersion store =
		  new SelectionVersion(table.serial(session));

              ResultSet them = statement.executeQuery();

              while (them.next())
                store.addElement(new Integer(them.getInt(1)));

              return store;
            }
            catch (SQLException e) {
              throw new SQLPoemException(e);
            }
          }

 	  public boolean upToDate(Session session, Version current) {
 	    return ((SelectionVersion)current).tableSerial ==
	               table.serial(session);
 	  }
        };
  }

  public Table getTable() {
    return table;
  }

  public Enumeration troids() {
    return ((Vector)cache.versionForReading(PoemThread.session())).elements();
  }

  public Enumeration objects() {
    return
        new MappedEnumeration(troids()) {
          public Object mapped(Object troid) {
            return table.getObject((Integer)troid);
          }
        };
  }

  public long serial(Session session) {
    return ((SelectionVersion)cache.versionForReading(session)).tableSerial;
  }
}
