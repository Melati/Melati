package org.melati.poem;

public class InvalidColumnInfoTypecodePoemException
    extends SeriousPoemException {
  public ColumnInfo columnInfo;
  public InvalidColumnInfoTypecodePoemException(ColumnInfo columnInfo) {
    this.columnInfo = columnInfo;
  }

  public String getMessage() {
    return
        "The info for column " + columnInfo.name +
        " had an invalid typecode " + columnInfo.type;
  }
}
