package org.melati.poem;

public class MissingColumnInfoLogEvent extends PoemLogEvent {
  public Table table;
  public String columnName;

  public MissingColumnInfoLogEvent(Table table, String columnName) {
    this.table = table;
    this.columnName = columnName;
  }
}
