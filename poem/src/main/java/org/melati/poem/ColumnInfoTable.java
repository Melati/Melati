package org.melati.poem;

public class ColumnInfoTable extends ColumnInfoTableBase {

  public ColumnInfoTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  void postInitialise() {
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
