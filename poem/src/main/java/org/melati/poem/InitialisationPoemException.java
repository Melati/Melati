package org.melati.poem;

public class InitialisationPoemException extends PoemException {

  public Table table;

  public InitialisationPoemException(Table table, Exception e) {
    super(e);
    this.table = table;
  }

  public String getMessage() {
    return
        "You tried to create a record in the table " + table.getName() + "\n" +
        "but it failed post-initialisation tests.\n" +
        subException.getMessage();
  }
}
