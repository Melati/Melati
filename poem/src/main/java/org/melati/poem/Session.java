package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public class Session {
  private Database database;
  private Connection connection;
  private int index;
  private int mask;
  private int negMask;

  private int seenCapacityMin = 50;
  private int seenCapacityMax = 1000;
  private Vector seen = new Vector(seenCapacityMin);

  private int touchedCapacityMin = 50;
  private int touchedCapacityMax = 1000;
  private Vector touched = new Vector();

  Session(Database database, Connection connection, int index) {
    if (index > 30)
      throw new SessionIndexTooLargePoemException();

    this.database = database;
    this.connection = connection;
    this.index = index;
    mask = 1 << index;
    negMask = ~mask;
  }

  final Database getDatabase() {
    return database;
  }

  final int index() {
    return index;
  }

  final int mask() {
    return mask;
  }

  final int negMask() {
    return negMask;
  }

  final Connection getConnection() {
    return connection;
  }

  final void notifyTouched(CacheInterSession is) {
    touched.addElement(is);
  }

  final void notifySeen(CacheInterSession is) {
    seen.addElement(is);
  }

  public void writeDown() {
    synchronized (touched) {
      for (Enumeration is = touched.elements(); is.hasMoreElements();)
        ((CacheInterSession)is.nextElement()).writeDown(this);
    }
  }

  private void unSee() {
    synchronized (seen) {
      for (Enumeration is = seen.elements(); is.hasMoreElements();)
        ((CacheInterSession)is.nextElement()).unSee(this);

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
        database.log(new CommitLogEvent(this));
      }
      catch (SQLException e) {
        throw new CommitFailedPoemException(e);
      }

      for (Enumeration is = touched.elements(); is.hasMoreElements();)
        ((CacheInterSession)is.nextElement()).commit(this);

      if (touched.size() > touchedCapacityMax)
        touched = new Vector(touchedCapacityMin);
      else
        touched.setSize(0);
    }
    finally {
      unSee();
    }
  }

  public void rollback() {
    try {
      connection.rollback();
      database.log(new RollbackLogEvent(this));
    }
    catch (SQLException e) {
      throw new RollbackFailedPoemException(e);
    }
    finally {
      unSee();
    }
  }

  public void close(boolean commit) {
    if (commit)
      commit();
    else
      rollback();
    database.notifyClosed(this);
  }
}
