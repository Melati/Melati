package org.melati.poem;

public class RowDisappearedPoemException extends SeriousPoemException {

  public Table table;
  public Integer troid;

  public RowDisappearedPoemException(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }

  public RowDisappearedPoemException(NoSuchRowPoemException e) {
    this(e.table, e.troid);
  }
}
