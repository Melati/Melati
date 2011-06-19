package org.melati.courtiouspoem.poem;

import org.melati.courtiouspoem.poem.generated.CourtiouspoemDatabaseBase;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub.
 */
public class CourtiouspoemDatabase extends CourtiouspoemDatabaseBase
                            implements CourtiouspoemDatabaseTables {

  // programmer's domain-specific code here
  
  @Override
  public void disconnect() throws PoemException {
    beginStructuralModification();
    try { 
      modifyStructure("DROP TABLE " + getTableCategoryTable().quotedName());
      modifyStructure("DROP TABLE " + getGroupMembershipTable().quotedName());
      modifyStructure("DROP TABLE " + getGroupCapabilityTable().quotedName());
      modifyStructure("DROP TABLE " + getGroupTable().quotedName());
      modifyStructure("DROP TABLE " + getCapabilityTable().quotedName());
      modifyStructure("DROP TABLE " + getColumnInfoTable().quotedName());
      modifyStructure("DROP TABLE " + getTableInfoTable().quotedName());
      modifyStructure("DROP TABLE " + getUserTable().quotedName());
      modifyStructure("DROP TABLE " + getSettingTable().quotedName());
    }
    finally {
      endStructuralModification();
    }
    super.disconnect();
  }
  
}


