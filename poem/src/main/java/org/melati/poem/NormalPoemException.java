package org.melati.poem;

public abstract class NormalPoemException extends PoemException {
  public NormalPoemException(Exception subException) {
    super(subException);
  }

  public NormalPoemException() {
    super();
  }
}
