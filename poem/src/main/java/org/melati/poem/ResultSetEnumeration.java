package org.melati.poem;

import java.util.*;
import java.sql.*;

abstract class ResultSetEnumeration implements Enumeration {
  private final ResultSet resultSet;
  private int more = -1;

  public ResultSetEnumeration(ResultSet resultSet) {
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

  protected abstract Object mapped(ResultSet resultSet)
      throws SQLException, NoSuchRowPoemException;

  public synchronized Object nextElement() throws NoSuchElementException {
    try {
      if (more == -1)
        more = resultSet.next() ? 1 : 0;

      if (more == 0)
        throw new NoSuchElementException();

      try {
        return mapped(resultSet);
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
