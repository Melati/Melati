package org.melati.poem.csv;

import org.melati.poem.*;

public class CSVColumn {

  String poemName = null;
  boolean isPrimaryKey = false;
  CSVTable foreignTable = null;

  public CSVColumn(String poemName) {
    this.poemName = poemName;
  }

  public CSVColumn(String poemName, CSVTable foreignTable) {
    this.poemName = poemName;
    this.foreignTable = foreignTable;
  }

  public CSVColumn(String poemName, boolean isPrimaryKey) {
    this.poemName = poemName;
    this.isPrimaryKey = isPrimaryKey;
  }

}
