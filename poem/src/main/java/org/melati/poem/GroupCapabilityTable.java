package org.melati.poem;

public class GroupCapabilityTable extends GroupCapabilityTableBase {

  public GroupCapabilityTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyUpdate(Session session, Data data) {
    getDatabase().getUserTable().invalidateCapabilityCaches(session);
    super.notifyUpdate(session, data);
  }
}
