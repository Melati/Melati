package org.melati.poem.test;

import org.melati.poem.Capability;
import org.melati.poem.test.generated.EverythingDatabaseBase;

/**
 * Melati POEM generated, programmer modifiable stub.
 */
public class EverythingDatabase extends EverythingDatabaseBase
                            implements EverythingDatabaseTables {
  // programmer's domain-specific code here

  public Capability getCanAdminister() {
    return administerCapability();
  }
}


