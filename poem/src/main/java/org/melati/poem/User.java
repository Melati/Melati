package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

// the simplest way to invalidate the user/capability cache when
// things change is to erase them ALL whenever anything significant
// changes---which is bound to be rare
// but for that we need the operation "zap all currently cached Users"

public class User extends UserBase implements AccessToken {

  private boolean dbGivesCapability(Capability capability) {
    String sql = 
        "SELECT count(*) FROM groupmembership " +
        "WHERE \"user\" = " + troid() + " AND " +
        "EXISTS (" +
          "SELECT \"group\", capability FROM groupcapability " +
          "WHERE groupcapability.\"group\" = groupmembership.\"group\" AND " +
                "capability = " + capability.troid() + ")";

    try {
      ResultSet rs = getDatabase().sqlQuery(sql);
      rs.next();
      return rs.getInt(1) > 0;
    }
    catch (SQLPoemException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e, sql);
    }
  }

  public boolean givesCapability(Capability capability) {
    UserData data = dataForReading();
    synchronized (getUserTable().capabilityCacheInvalidator) {
      Hashtable capabilities = data.capabilities;
      if (capabilities == null) {
        data.capabilities = capabilities = new Hashtable();
        getUserTable().notifyCapabilityCacheExists();
      }
      Boolean known = (Boolean)capabilities.get(capability.troid());
      if (known != null)
        return known.booleanValue();
      else {
        boolean does = dbGivesCapability(capability);
        capabilities.put(capability.troid(), does ? Boolean.TRUE : Boolean.FALSE);
        return does;
      }
    }
  }
}
