package org.melati.poem;

public class GroupMembershipData extends GroupMembershipDataBase {
  public GroupMembershipData() {}

  public GroupMembershipData(User user, Group group) {
    this.user = user.troid();
    this.group = group.troid();
  }
}
