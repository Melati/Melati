package org.melati.poem;

public class GroupMembershipTable extends GroupMembershipTableBase {

  public GroupMembershipTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyTouched(PoemSession session, Integer troid) {
    getDatabase().invalidateCapabilityCache(session);
    super.notifyTouched(session, troid);
  }
}
