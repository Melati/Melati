package org.melati.poem;

public class InvalidOperationOnFloatingPersistentPoemException
    extends AppBugPoemException {

  public Persistent floating;

  public InvalidOperationOnFloatingPersistentPoemException(Persistent floating) {
    this.floating = floating;
  }

  public String getMessage() {
    return "The application performed an operation on an unattached Persistent object" +
           "`" + floating + "' " +
           "which is only possible on attached ones";
  }
}
