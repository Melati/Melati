package org.melati.poem;

import java.util.*;
import org.melati.util.*;
import java.sql.*;

public class UserTable extends UserTableBase {

  static final UserData guestData =
      new UserData("_guest_", "", "Melati guest user");

  private User guestUser = null;

  static final UserData administratorData =
      new UserData("_administrator_", "FIXME",
                   "Melati database administrator");

  private User administratorUser = null;

  public UserTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  User guestUser() {
    return guestUser;
  }

  User administratorUser() {
    return administratorUser;
  }

  private User ensureUser(UserData userData) {
    User user = (User)getLoginColumn().firstWhereEq(userData.login);
    if (user == null)
      user = (User)create(userData);
    return user;
  }

  synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);
    guestUser = ensureUser(guestData);
    administratorUser = ensureUser(administratorData);
  }

  void postInitialise() {
    if (info.getDefaultcanwrite() == null)
      info.setDefaultcanwrite(getDatabase().administerCapability());
    if (info.getCancreate() == null)
      info.setCancreate(getDatabase().administerCapability());
  }
}
