package org.melati.poem;

import java.util.*;
import java.sql.*;

class ResultSetEnumeration implements Enumeration {
  private final Table table;
  private final ResultSet resultSet;
  private int more = -1;
  private final boolean resolve;

  public ResultSetEnumeration(Table table, ResultSet resultSet,
                              boolean resolve) {
    this.table = table;
    this.resultSet = resultSet;
    this.resolve = resolve;
  }

  public synchronized boolean hasMoreElements() {
    try {
      if (more == -1)
        more = resultSet.next() ? 1 : 0;
      return more == 1;
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  public synchronized Object nextElement() throws NoSuchElementException {
    try {
      if (more == -1)
        more = resultSet.next() ? 1 : 0;

      if (more == 0)
        throw new NoSuchElementException();

      try {
        Integer troid = new Integer(resultSet.getInt(1));
        return resolve ? (Object)table.getObject(troid) : (Object)troid;
      }
      finally {
        more = resultSet.next() ? 1 : 0;
      }
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
    catch (NoSuchRowPoemException e) {
      throw new RowDisappearedPoemException(e);
    }
  }
}
