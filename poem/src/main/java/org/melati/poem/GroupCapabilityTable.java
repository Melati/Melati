package org.melati.poem;

public class GroupCapabilityTable extends GroupCapabilityTableBase {

  public GroupCapabilityTable(Database database, String name)
      throws PoemException {
    super(database, name);
  }

  void postInitialise() {
    super.postInitialise();

    Database d = getDatabase();
    GroupCapabilityData admin =
        new GroupCapabilityData(d.getGroupTable().administratorsGroup(),
                                d.administerCapability());
    if (!exists(admin))
      create(admin);

    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
