package org.melati.poem;

public class ColumnInfo extends ColumnInfoBase {
  
  protected void assertCanRead(Data data, AccessToken token) {}

  private Column column;

  void setColumn(Column column) {
    this.column = column;
  }

  public void setName(String name) {
    String current = getName();
    if (current != null && !current.equals(name))
      throw new ColumnRenamePoemException(name);
    super.setName(name);
  }

  public void setPrimarydisplay(Boolean value) {
    super.setPrimarydisplay(value);
    if (value.booleanValue()) {
      Column column = this.column;
      if (column != null)
        column.getTable().setDisplayColumn(column);
    }
  }

  public String displayString() throws AccessPoemException {
    return getTableinfo().getDisplayname() + " . " + getDisplayname();
  }
}
