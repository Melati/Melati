package org.melati.poem.test;

import org.melati.poem.Capability;
import org.melati.poem.test.generated.TestDatabaseBase;

/**
 * Melati POEM generated, programmer modifiable stub.
 */
public class TestDatabase extends TestDatabaseBase
                            implements TestDatabaseTables {
  // programmer's domain-specific code here

  public Capability getCanAdminister() {
    return administerCapability();
  }
  
}


