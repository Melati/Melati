package org.melati.poem;

import java.util.*;

public class DeletionIntegrityPoemException extends PoemException {
  public Persistent object;
  public Enumeration references;

  public DeletionIntegrityPoemException(Persistent object,
                                        Enumeration references) {
    this.object = object;
    this.references = references;
  }

  public String toString() {
    return "You can't delete " + object + " since there are references to it";
  }
}
