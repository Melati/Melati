package org.melati.poem.csv;

public class NoPrimaryKeyInCSVTableException extends Exception {

  String tableName = null;
  String cvsValue = null;

  public NoPrimaryKeyInCSVTableException(String tableName, String cvsValue) {
    this.tableName = tableName;
    this.cvsValue = cvsValue;
  }

  public String getMessage() {
    return "A foreign key points to the value " + cvsValue +
           " in this table (" + tableName + ") but no record with that " +
           "value was found";
  }

}
