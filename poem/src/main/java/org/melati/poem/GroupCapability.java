package org.melati.poem;

import org.melati.poem.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.util.*;

public class GroupCapability extends GroupCapabilityBase {
  public GroupCapability() {}

  public GroupCapability(Group group, Capability capability) {
    this.group = group.troid();
    this.capability = capability.troid();
  }
}
