package org.melati.poem;

import java.util.*;
import org.melati.util.*;
import java.sql.*;

public class UserTable extends UserTableBase {

  static final UserData guestData =
      new UserData("_guest_", "", "Melati guest user");

  private User guestUser;

  public UserTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  User guestUser() {
    return guestUser;
  }

  synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);

    guestUser = (User)getLoginColumn().firstWhereEq(guestData.login);
    if (guestUser == null)
      guestUser = (User)create(guestData);
  }
}
