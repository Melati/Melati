package org.melati.poem;

public class GroupCapabilityTable extends GroupCapabilityTableBase {

  public GroupCapabilityTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  protected void notifyTouched(PoemSession session, Integer troid) {
    getDatabase().invalidateCapabilityCache(session);
    super.notifyTouched(session, troid);
  }
}
