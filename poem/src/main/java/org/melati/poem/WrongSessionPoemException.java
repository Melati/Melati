package org.melati.poem;

public class WrongSessionPoemException extends AppBugPoemException {
  public Table table;
  public Integer troid;

  public WrongSessionPoemException(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }

  public String getMessage() {
    return
        "An object being constructed was accessed in a different transaction\n" +
        "Object is table `" + table.getName() + "', troid " + troid;
  }
}
