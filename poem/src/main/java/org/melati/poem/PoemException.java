package org.melati.poem;

import org.melati.util.*;

public abstract class PoemException extends MelatiRuntimeException {

  public PoemException(Exception subException) {
    super(subException);
  }

  public PoemException() {
    this(null);
  }
}
