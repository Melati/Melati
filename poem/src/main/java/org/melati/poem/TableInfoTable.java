package org.melati.poem;

public class TableInfoTable extends TableInfoTableBase {

  public TableInfoTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  void postInitialise() {
    super.postInitialise();
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
