package org.melati.poem;

import java.util.*;
import org.melati.util.*;
import java.sql.*;

public class CapabilityTable extends CapabilityTableBase {

  static final CapabilityData administerData =
      new CapabilityData("_administer_");

  public CapabilityTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  private Capability administer;

  Capability administer() {
    return administer;
  }

  synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);

    administer =
        (Capability)getNameColumn().firstWhereEq(administerData.name);
    if (administer == null)
      administer = (Capability)create(administerData);

    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(administer);
    if (info.getCancreate() == null)
      info.setCancreate(administer);
  }
}
