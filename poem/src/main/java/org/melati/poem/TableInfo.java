package org.melati.poem;

public class TableInfo extends TableInfoBase {
  protected void assertCanRead(AccessToken token) {}

  public TableInfo() {
  }

  public TableInfo(String name, String displayName, int displayOrder,
		   String description, Integer cacheLimit,
		   boolean rememberAllTroids) {
    this.name = name;
    this.displayname = displayName;
    this.displayorder = new Integer(displayOrder);
    this.description = description;
    this.cachelimit = cacheLimit;
    this.seqcached = rememberAllTroids ? Boolean.TRUE : Boolean.FALSE;
  }

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

  public void setCachelimit(Integer limit) throws AccessPoemException {
    super.setCachelimit(limit);
    getTable().setCacheLimit(limit);
  }
}
