package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class UserTable extends UserTableBase {
  public UserTable(Database database, String name) throws PoemException {
    super(database, name);
  }

  static final Procedure capabilityCacheInvalidator =
      new Procedure() {
        public void apply(Object data) {
          ((UserData)data).capabilities = null;
        }
      };

  private boolean capabilityCachesExist = false;

  void notifyCapabilityCacheExists() {
    capabilityCachesExist = true;
  }

  void invalidateCapabilityCaches(Session session) {
    synchronized (capabilityCacheInvalidator) {
      if (capabilityCachesExist) {
        cacheIterate(null, capabilityCacheInvalidator);
        if (session != null)
          cacheIterate(session, capabilityCacheInvalidator);
        capabilityCachesExist = false;
      }
    }
  }
}
