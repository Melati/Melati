package org.melati.poem;

public class TableInfo extends TableInfoBase {
  protected void assertCanRead(Data data, AccessToken token) {}

  public void setName(String name) {
    String current = getName();
    if (current != null && !current.equals(name))
      throw new TableRenamePoemException(name);
    super.setName(name);
  }

  public String displayString() throws AccessPoemException {
    return getDisplayname();
  }

  public void setSeqcached(Boolean b) throws AccessPoemException {
    super.setSeqcached(b);
    getTable().rememberAllTroids(b.booleanValue());
  }
}
