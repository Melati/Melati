package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public class PoemTransaction extends Transaction {
  private Database database;
  private Connection connection;

  PoemTransaction(Database database, Connection connection, int index) {
    super(index);
    this.database = database;
    this.connection = connection;
    try {
      connection.setAutoCommit(false);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  final Database getDatabase() {
    return database;
  }

  final Connection getConnection() {
    return connection;
  }

  protected void backingCommit() {
    try {
      connection.commit();
      database.log(new CommitLogEvent(this));
    }
    catch (SQLException e) {
      throw new CommitFailedPoemException(e);
    }
  }

  protected void backingRollback() {
    try {
      connection.rollback();
      database.log(new RollbackLogEvent(this));
    }
    catch (SQLException e) {
      throw new RollbackFailedPoemException(e);
    }
  }

  public void close(boolean commit) {
    System.err.println(this + ".close(" + commit + ")");
    try {
      if (commit)
        commit();
      else
        rollback();
    }
    finally {
      database.notifyClosed(this);
    }
  }
}
