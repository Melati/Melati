package org.melati.example.contacts;

import org.melati.example.contacts.generated.*;
import org.melati.poem.*;

public class CategoryTable extends CategoryTableBase {

  public CategoryTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
