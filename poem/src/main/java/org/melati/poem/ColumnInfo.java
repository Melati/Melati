package org.melati.poem;

public class ColumnInfo extends ColumnInfoBase {
  
  protected void assertCanRead(Data data, AccessToken token) {}

  private Column _column = null;

  private Column column() {
    if (_column == null)
      _column = getTable().columnWithColumnInfoID(troid().intValue());
    return _column;
  }

  void setColumn(Column column) {
    _column = column;
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
      Column column = column();
      if (column != null) {
        Table table = column.getTable();
        Column previous = table.displayColumn();
        if (previous != null)
          previous.setPrimaryDisplay(false);
        table.setDisplayColumn(column);
      }
    }
  }

  public void setDisplayorderpriority(Integer value) {
    super.setDisplayorderpriority(value);
    Column column = column();
    if (column != null)
      column.getTable().invalidateDisplayColumns();
  }

  public String displayString() throws AccessPoemException {
    return getTableinfo().getDisplayname() + " . " + getDisplayname();
  }
}
