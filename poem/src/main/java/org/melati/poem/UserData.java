package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class UserData extends UserDataBase {
  UserData(String login, String password, String name) {
    this.login = login;
    this.password = password;
    this.name = name;
  }

  public UserData() {
  }
}
