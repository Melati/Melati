package org.melati.poem.odmg.playing;

import org.melati.poem.odmg.playing.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.poem.*;

public class ChildTable extends ChildTableBase {

  public ChildTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
