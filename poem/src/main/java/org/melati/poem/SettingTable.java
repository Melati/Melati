package org.melati.poem;

import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.util.*;

public class SettingTable extends SettingTableBase {

  public SettingTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
