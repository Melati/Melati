package org.melati.poem;

import org.melati.poem.generated.*;
import java.util.*;
import org.melati.util.*;

public class TableCategoryTable extends TableCategoryTableBase {

  public TableCategoryTable(
    Database database, String name,    DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  TableCategory ensure(String name) {
    TableCategory it = (TableCategory)newPersistent();
    it.setName(name);
    return (TableCategory)getNameColumn().ensure(it);
  }
  
  protected void postInitialise() {
    super.postInitialise();
    if (getTableInfo().getDefaultcanwrite() == null)
      getTableInfo().setDefaultcanwrite(getDatabase().administerCapability());
    if (getTableInfo().getCancreate() == null)
      getTableInfo().setCancreate(getDatabase().administerCapability());
  }
}
