package org.melati.poem;

public class GroupMembershipTable extends GroupMembershipTableBase {

  public GroupMembershipTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyTouched(Session session, Integer troid, Data data) {
    getDatabase().getUserTable().invalidateCapabilityCaches(session);
    super.notifyTouched(session, troid, data);
  }
}
