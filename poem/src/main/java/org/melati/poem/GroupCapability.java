package org.melati.poem;

public class GroupCapability extends GroupCapabilityBase {

  public GroupCapability() {
  }

  public GroupCapability(Group group, Capability capability) {
    this.group = group.troid();
    this.capability = capability.troid();
  }
}
