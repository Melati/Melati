package org.melati.poem;

import org.melati.util.*;

class NonexistentVersionedRow extends Data {
  private NonexistentVersionedRow() {}

  public Object clone() {
    return this;
  }

  static final NonexistentVersionedRow it = new NonexistentVersionedRow();
}

public interface VersionedRow extends VersionedObject {

  public static final Data nonexistent = NonexistentVersionedRow.it;

  Table getTable();
  Integer getTroid();

  boolean valid();
}
