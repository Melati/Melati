package org.melati.poem;

import java.util.*;

public class TableProjection {
  private Column column;
  private Object ident;

  public TableProjection(Column column, Object ident) {
    this.column = column;
    this.ident = ident;
  }

  public Table getTable() {
    return column.getTable();
  }

  Enumeration elements(boolean resolved) {
    return column.selectionWhereEq(ident, resolved);
  }
}
