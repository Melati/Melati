package org.melati.util;

public class WriteCommittedException extends MelatiRuntimeException {
  public Transactioned transactioned;

  public WriteCommittedException(Transactioned transactioned) {
    this.transactioned = transactioned;
  }

  public String getMessage() {
    return "An attempt was made to write to the object " + transactioned + " " +
           "in the committed transaction";
  }
}
