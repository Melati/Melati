package org.melati.doc.example;

import org.melati.doc.example.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.util.*;
import org.melati.poem.*;

public class ProductTable extends ProductTableBase {

  public ProductTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
