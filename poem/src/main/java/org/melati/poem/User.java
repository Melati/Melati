package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

/**
 * FIXME it shouldn't be possible for anyone to getPassword
 */

public class User extends UserBase implements AccessToken {

  public User() {
  }

  public User(String login, String password, String name) {
    this.login = login;
    this.password = password;
    this.name = name;
  }

  public boolean givesCapability(Capability capability) {
    return getDatabase().hasCapability(this, capability);
  }

  public String toString() {
    return getLogin();
  }
}
