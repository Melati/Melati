package org.melati.poem;

import java.util.*;
import java.sql.*;

class ResultSetEnumeration implements Enumeration {
  private Table table;
  private ResultSet resultSet;
  private int more = -1;

  public ResultSetEnumeration(Table table, ResultSet resultSet) {
    this.table = table;
    this.resultSet = resultSet;
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
        return table.getObject(new Integer(resultSet.getInt(1)));
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
