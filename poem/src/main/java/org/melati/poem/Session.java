package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public class Session {
  private Database database;
  private Connection connection;
  private int index;

  private int seenCapacityMin = 50;
  private int seenCapacityMax = 1000;
  private Vector seen = new Vector(seenCapacityMin);

  private int changedCapacityMin = 50;
  private int changedCapacityMax = 1000;
  private Vector touched = new Vector();

  Session(Database database, Connection connection, int index) {
    if (index > 30)
      throw new SessionIndexTooLargePoemException();

    this.database = database;
    this.connection = connection;
    this.index = index;
  }

  final int getIndex() {
    return index;
  }

  final Connection getConnection() {
    return connection;
  }

  final void notifyTouched(Persistent g) {
    touched.addElement(g);
  }

  final void notifySeen(Persistent g) {
    seen.addElement(g);
  }

  public void writeDown() {
    synchronized (touched) {
      for (Enumeration g = touched.elements(); g.hasMoreElements();) {
        Persistent generic = (Persistent)g.nextElement();
        generic.getTable().writeDown(this, generic.getFields());
      }

      if (touched.size() > changedCapacityMax)
        touched = new Vector(changedCapacityMin);
      else
        touched.setSize(0);
    }
  }

  private void unSee() {
    synchronized (seen) {
      for (Enumeration g = seen.elements(); g.hasMoreElements();)
        ((Persistent)g.nextElement()).unSee();

      if (seen.size() > seenCapacityMax)
        seen = new Vector(seenCapacityMin);
      else
        seen.setSize(0);
    }
  }

  // This doesn't have to be synchronized.

  public void commit() {
    try {
      writeDown();
      try {
        connection.commit();
      }
      catch (SQLException e) {
        throw new CommitFailedPoemException(e);
      }

      for (Enumeration g = touched.elements(); g.hasMoreElements();)
        ((Persistent)g.nextElement()).commit();
    }
    finally {
      unSee();
    }
  }

  public void rollback() {
    try {
      connection.rollback();
    }
    catch (SQLException e) {
      throw new RollbackFailedPoemException(e);
    }
    finally {
      unSee();
    }
  }

  public void close() {
    database.notifyClosed(this);
  }
}
