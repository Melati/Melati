package org.melati.poem;

public class GroupMembership extends GroupMembershipBase {

  public GroupMembership() {
  }

  public GroupMembership(User user, Group group) {
    this.user = user.troid();
    this.group = group.troid();
  }

  public GroupMembership(Integer user, Integer group) {
    this.user = user;
    this.group = group;
  }
}
