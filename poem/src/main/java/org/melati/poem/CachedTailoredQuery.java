package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

public class CachedTailoredQuery extends PreparedTailoredQuery {

  private static class SelectionVersion extends VersionVector {
    private static long lastSerial = 0L;

    long tableSerials[];
    long serial;

    SelectionVersion(Table[] tables, Session session, Enumeration results) {
      tableSerials = new long[tables.length];
      for (int t = 0; t < tables.length; ++t)
	tableSerials[t] = tables[t].serial(session);

      while (results.hasMoreElements())
	addElement(results.nextElement());

      serial = lastSerial++;
    }
  }

  private PoemFloatingVersionedObject cache;

  public CachedTailoredQuery(Column[] selectedColumns, Table[] otherTables,
			     String whereClause, String orderByClause) {
    super(selectedColumns, otherTables, whereClause, orderByClause);
    cache =
        new PoemFloatingVersionedObject(database) {
          protected Version backingVersion(Session session) {
	    return new SelectionVersion(tables, session, super_selection());
          }

 	  public boolean upToDate(Session session, Version current) {
	    long[] tableSerials = ((SelectionVersion)current).tableSerials;
	    for (int t = 0; t < tables.length; ++t)
	      if (tableSerials[t] != tables[t].serial(session))
		return false;

	    return true;
 	  }
        };
  }

  private Enumeration super_selection() {
    return super.selection();
  }

  public Enumeration selection() {
    return ((Vector)cache.versionForReading(PoemThread.session())).elements();
  }

  public long serial(Session session) {
    return ((SelectionVersion)cache.versionForReading(session)).serial;
  }
}
