package org.melati.poem;

public class GroupMembership extends Persistent {
  public void setUser(User user) {
    Integer groupTroid = getGroupTroid();
    getUser().notifyLeaveGroup(groupTroid);
    user.notifyJoinGroup(groupTroid);
    super.setUser(user);
  }
}
