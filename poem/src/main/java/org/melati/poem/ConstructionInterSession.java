package org.melati.poem;

public class ConstructionInterSession implements InterSession {
  private Table table;
  private Integer troid;
  private Data data;
  private Session session;

  ConstructionInterSession(Table table, Integer troid, Data data,
                           Session session) {
    this.table = table;
    this.troid = troid;
    this.data = data;
    this.session = session;
  }

  public Table getTable() {
    return table;
  }

  public Integer getTroid() {
    return troid;
  }

  private Data data(Session session) {
    if (session != this.session)
      throw new WrongSessionPoemException(table, troid);
    return data;
  }

  public Data dataForReading(Session session) {
    return data(session);
  }

  public Data dataForWriting(Session session) {
    return data(session);
  }

  public void delete(Session session) {
    throw new IllegalArgumentException();
  }

  public boolean valid() {
    return true;
  }
}
