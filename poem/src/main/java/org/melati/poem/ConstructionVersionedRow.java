package org.melati.poem;

import org.melati.util.*;

public class ConstructionVersionedRow implements VersionedRow {
  private Table table;
  private Integer troid;
  private Version version;
  private PoemSession session;

  ConstructionVersionedRow(Table table, Integer troid, Version version,
                           PoemSession session) {
    this.table = table;
    this.troid = troid;
    this.version = version;
    this.session = session;
  }

  public Table getTable() {
    return table;
  }

  public Integer getTroid() {
    return troid;
  }

  private Version version(Session session) {
    if (session != this.session)
      throw new WrongSessionPoemException(table, troid);
    return version;
  }

  public Version versionForReading(Session session) {
    return version(session);
  }

  public Version versionForWriting(Session session) {
    return version(session);
  }

  public void delete(Session session) {
    throw new IllegalArgumentException();
  }

  public boolean valid() {
    return true;
  }
}
