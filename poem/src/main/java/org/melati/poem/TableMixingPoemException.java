package org.melati.poem;

public class TableMixingPoemException extends BugPoemException {
  public Table table1, table2;

  public TableMixingPoemException(Table table1, Table table2) {
    this.table1 = table1;
    this.table2 = table2;
  }

  public static void check(Table table1, Table table2)
      throws TableMixingPoemException {
    if (table1 != table2)
      throw new TableMixingPoemException(table1, table2);
  }
}
