package org.melati.doc.example;

import java.util.*;
import java.sql.Date;
import org.melati.util.*;
import org.melati.poem.*;

public class OrderTable extends OrderTableBase {

  public OrderTable(
    Database database, String name,    DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
