package org.melati.poem.csv;

public class CSVPrimaryKeyColumnAlreadySetException extends Exception {

  String tableName = null;

  public CSVPrimaryKeyColumnAlreadySetException(String tableName) {
    this.tableName = tableName;
  }

  public String getMessage() {
    return "Tried to add a Primary Key column to table " + tableName +
           " but one had already been set";
  }

}
