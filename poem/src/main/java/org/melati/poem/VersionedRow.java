package org.melati.poem;

import org.melati.util.*;

public interface VersionedRow extends VersionedObject {

  public static final Data nonexistent =
      new Data() {
        public Object clone() {
          return this;
        }
      };

  Table getTable();
  Integer getTroid();

  boolean valid();
}
