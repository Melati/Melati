package org.melati.poem;

public class GroupMembershipTable extends GroupMembershipTableBase {

  public GroupMembershipTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyUpdate(Session session, Data data) {
    getDatabase().getUserTable().invalidateCapabilityCaches(session);
    super.notifyUpdate(session, data);
  }
}
