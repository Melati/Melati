package org.melati.poem;

public class SQLLogEvent extends PoemLogEvent {
  public String sql;

  public SQLLogEvent(String sql) {
    this.sql = sql;
  }

  public String toString() {
    return "Executed SQL: " + sql.trim();
  }
}
