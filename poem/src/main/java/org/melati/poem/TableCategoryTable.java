package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class TableCategoryTable extends TableCategoryTableBase {

  public TableCategoryTable(
    Database database, String name,    DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  TableCategory ensure(String name) {
    return (TableCategory)getNameColumn().ensure(new TableCategory(name));
  }
}
