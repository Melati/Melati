package org.melati.poem;

public class NoSuchColumnPoemException extends NormalPoemException {
  public Table table;
  public String name;
  public NoSuchColumnPoemException(Table table, String name) {
    this.table = table;
    this.name = name;
  }
}
