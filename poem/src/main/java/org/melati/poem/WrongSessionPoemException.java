package org.melati.poem;

public class WrongSessionPoemException extends AppBugPoemException {
  public SessionToken objectSession;
  public SessionToken threadSession;
  public Table table;

  public WrongSessionPoemException(
      SessionToken objectSession, SessionToken threadSession, Table table) {
    this.objectSession = objectSession;
    this.threadSession = threadSession;
    this.table = table;
  }

  public String getMessage() {
    return
        "A thread accessed a Poem object obtained in a different session.\n" +
        "Thread session: " + threadSession + "\n" +
        "Object session: " + objectSession + "\n" +
        "The object was from the table `" + table.getName() + "'";
  }
}
