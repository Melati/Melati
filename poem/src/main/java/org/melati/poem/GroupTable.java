package org.melati.poem;

public class GroupTable extends GroupTableBase {

  static final GroupData administratorsData =
      new GroupData("Melati database administrators");

  private Group administratorsGroup = null;

  public GroupTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  Group administratorsGroup() {
    return administratorsGroup;
  }

  private Group ensureGroup(GroupData groupData) {
    Group group = (Group)getNameColumn().firstWhereEq(groupData.name);
    if (group == null)
      group = (Group)create(groupData);
    return group;
  }

  void postInitialise() {
    administratorsGroup = ensureGroup(administratorsData);
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
