package org.melati.poem;

public class NoSuchTablePoemException extends NormalPoemException {
  public Database database;
  public String name;
  public NoSuchTablePoemException(Database database, String name) {
    this.database = database;
    this.name = name;
  }
}
