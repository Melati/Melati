package org.melati.poem;

public class TableCategoryTable extends TableCategoryTableBase {

  public TableCategoryTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  TableCategory ensure(String name) {
    return (TableCategory)getNameColumn().ensure(new TableCategory(name));
  }
}
