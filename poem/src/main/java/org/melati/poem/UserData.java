package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class UserData extends UserDataBase {
  Hashtable capabilities = null;

  public Object clone() {
    UserData other = (UserData)super.clone();
    other.capabilities = (Hashtable)capabilities.clone();
    return other;
  }
}
