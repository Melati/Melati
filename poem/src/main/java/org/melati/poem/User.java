package org.melati.poem;

public class User extends UserBase {

  public Enumeration getMemberGroups() {
    Vector groups = super.getMemberGroups();
    if (groups == null) {
      groups = 'SELECT';
      super.setMemberGroups(groups);
    }

    return groups.elements();
  }

  public void notifyJoinGroup(Integer groupTroid) {
    super.setMemberGroups(null);
  }

  public void notifyLeaveGroup(Integer groupTroid) {
    super.setMemberGroups(null);
  }
}
