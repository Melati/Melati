package org.melati.example.contacts;

import org.melati.example.contacts.generated.*;
import org.melati.poem.*;

public class ContactTable extends ContactTableBase {

  public ContactTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
