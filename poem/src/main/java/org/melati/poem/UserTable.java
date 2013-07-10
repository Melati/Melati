/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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

import org.melati.poem.generated.UserTableBase;
import java.sql.ResultSet;

/**
 * The {@link Table} of registered {@link User}s.
 *
 * Every Melati DB has this table.
 * This table will contain at least a <code>_guest_</code>
 *  and <code>_administrator_</code> {@link User}.
 *
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>UserTable</code> object.
 * <p>
 * Description: 
 *   A registered user of the database. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>User</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> name </td><td> String </td><td> The user's real name </td></tr> 
 * <tr><td> login </td><td> String </td><td> The user's login name </td></tr> 
 * <tr><td> password </td><td> String </td><td> The user's password 
 * </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class UserTable<T extends User> extends UserTableBase<T> {


  // programmer's domain-specific code here
  protected User guestUser, administratorUser;
  // see below
  // private Capability canReadPasswords = new Capability("ReadPasswords");
  // private Capability canWritePasswords = new Capability("WritePasswords");


 /**
  * Constructor.
  * 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public UserTable(Database database, String name,
                   DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);

    guestUser = (User)newPersistent();
    guestUser.setLogin_unsafe("_guest_");
    guestUser.setPassword_unsafe("guest"); //Oracle bug means '' is null
    guestUser.setName_unsafe("Melati guest user");

    administratorUser = (User)newPersistent();
    administratorUser.setLogin_unsafe("_administrator_");
    administratorUser.setPassword_unsafe("FIXME");
    administratorUser.setName_unsafe("Melati database administrator");
  }

  /**
   * @return the special User guest
   */
  public User guestUser() {
    return guestUser;
  }

  /**
   * @return the special User administrator
   */
  public User administratorUser() {
    return administratorUser;
  }

  /**
   * Defaults to the db's administrator capability.
   * @return the Capability required to read a password
   */
  public Capability canReadPasswords() {
    return getDatabase().administerCapability();
    // return canReadPasswords;
  }

  /**
   * Defaults to the db's administraor capability.
   * @return the Capability required to write a password
   */
  public Capability canWritePasswords() {
    return getDatabase().administerCapability();
    // return canWritePasswords;
  }

  /**
   * Create guestUser and administratorUser.
   * {@inheritDoc}
   * @see org.melati.poem.Table#unifyWithDB(java.sql.ResultSet)
   */
  public synchronized void unifyWithDB(ResultSet colDescs, String troidColumnName)
      throws PoemException {
    super.unifyWithDB(colDescs, troidColumnName);
    guestUser = (User)getLoginColumn().ensure(guestUser);
    administratorUser = (User)getLoginColumn().ensure(administratorUser);
  }

  public void postInitialise() {
    super.postInitialise();
//     canReadPasswords =
//         (Capability)getDatabase().getCapabilityTable().getNameColumn().
//             ensure(canReadPasswords);
//     canWritePasswords =
//         (Capability)getDatabase().getCapabilityTable().getNameColumn().
//             ensure(canWritePasswords);
  }
}
