package org.melati.poem;

public class GroupTable extends GroupTableBase {

  public GroupTable(Database database, String name) throws PoemException {
    super(database, name);
  }
  // programmer's domain-specific code here

  void postInitialise() {
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
