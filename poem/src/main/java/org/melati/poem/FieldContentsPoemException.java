package org.melati.poem;

public class FieldContentsPoemException extends PoemException {

  public Column column;

  public FieldContentsPoemException(Column column, Exception problem) {
    super(problem);
    this.column = column;
  }

  public String getMessage() {
    return
        "The field " + column + " had an illegal value\n" +
        subException.getMessage();
  }
}
