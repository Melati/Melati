package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class UserTable extends UserTableBase {
  public UserTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  static final Function capabilityCacheInvalidator =
      new Function() {
        public Object apply(Object data) {
          ((UserData)data).capabilities = null;
          return null;
        }
      };

  private boolean capabilityCachesExist = false;

  void notifyCapabilityCacheExists() {
    capabilityCachesExist = true;
  }

  void invalidateCapabilityCaches(Session session) {
    synchronized (capabilityCacheInvalidator) {
      if (capabilityCachesExist) {
        cacheIterate(session, capabilityCacheInvalidator);
        if (session != null)
          cacheIterate(null, capabilityCacheInvalidator);
        capabilityCachesExist = false;
      }
    }
  }
}
