package org.melati.poem;

import org.melati.poem.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.util.*;

public class GroupMembershipTable extends GroupMembershipTableBase {

  public GroupMembershipTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
