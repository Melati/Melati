package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

public class CachedTailoredQuery extends PreparedTailoredQuery {

  private Vector results = null;
  private long[] tableSerials;

  public CachedTailoredQuery(Column[] selectedColumns, Table[] otherTables,
			     String whereClause, String orderByClause) {
    super(selectedColumns, otherTables, whereClause, orderByClause);
    tableSerials = new long[tables.length];
  }

  public Enumeration selection() {
    PoemTransaction transaction = PoemThread.transaction();

    Vector results = this.results;

    for (int t = 0; t < tables.length; ++t) {
      long currentSerial = tables[t].serial(transaction);
      if (tableSerials[t] != currentSerial) {
	results = null;
	tableSerials[t] = currentSerial;
      }
    }

    if (results == null)
      results = this.results = EnumUtils.vectorOf(super.selection());

    return results.elements();
  }
}
