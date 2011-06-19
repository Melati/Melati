package org.melati.courteouspoem.poem;

import org.melati.courteouspoem.poem.generated.CourteouspoemDatabaseBase;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub.
 */
public class CourteouspoemDatabase extends CourteouspoemDatabaseBase
                            implements CourteouspoemDatabaseTables {

  // programmer's domain-specific code here
  
  @Override
  public void disconnect() throws PoemException {
    beginStructuralModification();
    try { 
      modifyStructure("DROP TABLE " + getGroupMembershipTable().quotedName());
      modifyStructure("DROP TABLE " + getGroupCapabilityTable().quotedName());
      modifyStructure("DROP TABLE " + getGroupTable().quotedName());
      modifyStructure("DROP TABLE " + getColumnInfoTable().quotedName());
      modifyStructure("DROP TABLE " + getTableInfoTable().quotedName());
      modifyStructure("DROP TABLE " + getCapabilityTable().quotedName());
      modifyStructure("DROP TABLE " + getTableCategoryTable().quotedName());
      modifyStructure("DROP TABLE " + getUserTable().quotedName());
      modifyStructure("DROP TABLE " + getSettingTable().quotedName());
    }
    finally {
      endStructuralModification();
    }
    super.disconnect();
  }
  
}


