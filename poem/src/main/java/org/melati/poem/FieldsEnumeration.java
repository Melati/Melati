package org.melati.poem;

import java.util.*;

class FieldsEnumeration implements Enumeration {

  private Enumeration columns;
  private Persistent persistent;

  FieldsEnumeration(Persistent persistent) {
    this.persistent = persistent;
    // FIXME ordering ... structure changes probably OK since must be in a session
    columns = persistent.getTable().columns();
  }

  public boolean hasMoreElements() {
    return columns.hasMoreElements();
  }

  public Object nextElement() {
    return new Field(persistent, (Column)columns.nextElement());
  }
}
