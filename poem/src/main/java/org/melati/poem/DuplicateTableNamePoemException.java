package org.melati.poem;

public class DuplicateTableNamePoemException extends NormalPoemException {
  public Database database;
  public String name;
  public DuplicateTableNamePoemException(Database database, String name) {
    this.database = database;
    this.name = name;
  }
}
