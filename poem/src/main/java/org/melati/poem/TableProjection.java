package org.melati.poem;

import java.util.*;

public class TableProjection {
  private Column column;
  private Object raw;

  public TableProjection(Column column, Object raw) {
    this.column = column;
    this.raw = raw;
  }

  public Table getTable() {
    return column.getTable();
  }

  Enumeration elements(boolean resolved) {
    return column.selectionWhereEq(raw, resolved);
  }
}
