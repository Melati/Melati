package org.melati.poem;

public class DatabaseMixingPoemException extends BugPoemException {
  public Database db1, db2;

  public DatabaseMixingPoemException(Database db1, Database db2) {
    this.db1 = db1;
    this.db2 = db2;
  }

  public static void check(Database db1, Database db2)
      throws DatabaseMixingPoemException {
    if (db1 != db2)
      throw new DatabaseMixingPoemException(db1, db2);
  }
}
