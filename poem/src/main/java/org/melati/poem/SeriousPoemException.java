package org.melati.poem;

import org.melati.util.*;

public class SeriousPoemException extends MelatiRuntimeException {

  public SeriousPoemException(Exception subException) {
    super(subException);
  }

  public SeriousPoemException() {
    this(null);
  }
}
