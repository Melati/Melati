package org.melati.poem;

public class ColumnInfo extends ColumnInfoBase {
  protected void assertCanRead(Data data, AccessToken token) {}

  public void setName(String name) {
    String current = getName();
    if (current != null && current != name)
      throw new ColumnRenamePoemException(name);
    super.setName(name);
  }

  public String displayString() throws AccessPoemException {
    return getTableinfo().getDisplayname() + " . " + getDisplayname();
  }
}
