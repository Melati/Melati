package org.melati.poem;

public class DuplicateTroidPoemException extends DBConsistencyPoemException {
  public Table table;
  public Integer troid;

  public DuplicateTroidPoemException(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }
}
