package org.melati.poem;

public class GroupCapabilityData extends GroupCapabilityDataBase {
  public GroupCapabilityData(Group group, Capability capability) {
    this.group = group.troid();
    this.capability = capability.troid();
  }

  public GroupCapabilityData() {}
}
