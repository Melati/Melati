package org.melati.poem;

public class GroupMembershipData extends GroupMembershipDataBase {
  public GroupMembershipData() {}

  public GroupMembershipData(Integer userTroid, Integer groupTroid) {
    this.user = userTroid;
    this.group = groupTroid;
  }

  public GroupMembershipData(User user, Group group) {
    this(user.troid(), group.troid());
  }
}
