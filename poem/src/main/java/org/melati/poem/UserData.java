package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class UserData extends UserDataBase {
  Hashtable capabilities = null;

  UserData(String login, String password, String name) {
    this.login = login;
    this.password = password;
    this.name = name;
  }

  public UserData() {
  }

  public Object clone() {
    UserData other = (UserData)super.clone();
    other.capabilities = (Hashtable)capabilities.clone();
    return other;
  }
}
