package org.melati.poem;

public class GroupTable extends GroupTableBase {

  private Group administratorsGroup =
      new Group("Melati database administrators");

  public GroupTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  Group administratorsGroup() {
    return administratorsGroup;
  }

  void postInitialise() {
    super.postInitialise();
    getNameColumn().ensure(administratorsGroup);
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
