package org.melati.poem;

public class RowDisappearedPoemException extends PoemException {

  public Table table;
  public Integer troid;

  public RowDisappearedPoemException(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }

  public RowDisappearedPoemException(NoSuchRowPoemException e) {
    this(e.table, e.troid);
  }

  public RowDisappearedPoemException(Persistent persistent) {
    this(persistent.getTable(), persistent.troid());
  }

  public String getMessage() {
    return "The row " + table.getName() + "/" + troid + " has been deleted";
  }
}
