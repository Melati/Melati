package org.melati.poem;

public class GroupCapabilityTable extends GroupCapabilityTableBase {

  public GroupCapabilityTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyTouched(PoemSession session, Integer troid) {
    getDatabase().invalidateCapabilityCache(session);
    super.notifyTouched(session, troid);
  }

  void postInitialise() {
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
