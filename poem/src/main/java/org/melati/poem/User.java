package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

// the simplest way to invalidate the user/capability cache when
// things change is to erase them ALL whenever anything significant
// changes---which is bound to be rare
// but for that we need the operation "zap all currently cached Users"

public class User extends UserBase implements AccessToken {
  public boolean givesCapability(Capability capability) {
    return getDatabase().hasCapability(this, capability);
  }
}
