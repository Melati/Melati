package org.melati.poem;

import java.util.*;
import org.melati.util.*;
import java.sql.*;

public class UserTable extends UserTableBase {

  private User guestUser =
      new User("_guest_", "", "Melati guest user");

  private User administratorUser =
      new User("_administrator_", "FIXME", "Melati database administrator");

  public UserTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  User guestUser() {
    return guestUser;
  }

  public User administratorUser() {
    return administratorUser;
  }

  synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);
    guestUser = (User)getLoginColumn().ensure(guestUser);
    administratorUser = (User)getLoginColumn().ensure(administratorUser);
  }

  void postInitialise() {
    super.postInitialise();
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
