package org.melati.util;

public class CrossTransactionCommitException extends MelatiRuntimeException {
  public String getMessage() {
    return "An attempt was made to commit or roll back changes to an object " +
           "from a different transaction than the one that made the changes";
  }
}
