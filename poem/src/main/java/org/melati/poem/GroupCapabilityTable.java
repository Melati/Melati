package org.melati.poem;

public class GroupCapabilityTable extends GroupCapabilityTableBase {

  public GroupCapabilityTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyTouched(Session session, Integer troid, Data data) {
    getDatabase().getUserTable().invalidateCapabilityCaches(session);
    super.notifyTouched(session, troid, data);
  }
}
