/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import org.melati.poem.generated.*;
import java.util.*;
import org.melati.util.*;
import java.sql.*;

public class UserTable extends UserTableBase {

  protected User guestUser, administratorUser;
  // see below
  // private Capability canReadPasswords = new Capability("ReadPasswords");
  // private Capability canWritePasswords = new Capability("WritePasswords");

  public UserTable(Database database, String name) throws PoemException {
    this(database, name, DefinitionSource.dsd);
  }

  public UserTable(Database database, String name,
		   DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);

    guestUser = (User)newPersistent();
    guestUser.setLogin_unsafe("_guest_");
    guestUser.setPassword_unsafe("");
    guestUser.setName_unsafe("Melati guest user");

    administratorUser = (User)newPersistent();
    administratorUser.setLogin_unsafe("_administrator_");
    administratorUser.setPassword_unsafe("FIXME");
    administratorUser.setName_unsafe("Melati database administrator");
  }

  public User guestUser() {
    return guestUser;
  }

  public User administratorUser() {
    return administratorUser;
  }

  public Capability canReadPasswords() {
    return getDatabase().administerCapability();
    // return canReadPasswords;
  }

  public Capability canWritePasswords() {
    return getDatabase().administerCapability();
    // return canWritePasswords;
  }

  public synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);
    guestUser = (User)getLoginColumn().ensure(guestUser);
    administratorUser = (User)getLoginColumn().ensure(administratorUser);
  }

  protected void postInitialise() {
    super.postInitialise();
    if (getTableInfo().getDefaultcanwrite() == null)
      getTableInfo().setDefaultcanwrite(getDatabase().administerCapability());
    if (getTableInfo().getDefaultcandelete() == null)
      getTableInfo().setDefaultcandelete(getDatabase().administerCapability());
    if (getTableInfo().getCancreate() == null)
      getTableInfo().setCancreate(getDatabase().administerCapability());

    // see above

//     canReadPasswords =
//         (Capability)getDatabase().getCapabilityTable().getNameColumn().
//             ensure(canReadPasswords);
//     canWritePasswords =
//         (Capability)getDatabase().getCapabilityTable().getNameColumn().
//             ensure(canWritePasswords);
  }
}
