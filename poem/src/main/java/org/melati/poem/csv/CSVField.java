package org.melati.poem.csv;

import org.melati.poem.*;

public class CSVField {

  CSVColumn column = null;
  String value = null;

  public CSVField(CSVColumn column, String value) {
    this.column = column;
    this.value = value;
  }

}
