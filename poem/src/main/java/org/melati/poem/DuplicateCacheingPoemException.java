package org.melati.poem;

public class DuplicateCacheingPoemException
    extends DBConsistencyPoemException {
  public Table table;
  public Integer troid;

  public DuplicateCacheingPoemException(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }
}
