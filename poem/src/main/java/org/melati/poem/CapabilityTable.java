package org.melati.poem;

import java.util.*;
import org.melati.util.*;
import java.sql.*;

public class CapabilityTable extends CapabilityTableBase {

  public CapabilityTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  private Capability administer = new Capability("_administer_");

  Capability administer() {
    return administer;
  }

  synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);

    administer = (Capability)getNameColumn().ensure(administer);

    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(administer);
    if (info.getCancreate() == null)
      info.setCancreate(administer);
  }
}
