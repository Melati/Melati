package org.melati.poem;

public class DoubleCreatePoemException extends AppBugPoemException {
  public Persistent persistent;

  public DoubleCreatePoemException(Persistent persistent) {
    this.persistent = persistent;
  }

  public String getMessage() {
    return "The application tried to create an already-existing persistent object " +
           "`" + persistent + "'";
  }
}
