package org.melati.poem;

public class InterruptedPoemException extends SeriousPoemException {
  public InterruptedPoemException(InterruptedException exception) {
    super(exception);
  }

  public String getMessage() {
    return "Received InterruptedException\n" + subException;
  }
}
