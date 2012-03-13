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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import org.melati.poem.generated.UserBase;

/**
 * A registered user.
 *
 * 
 * Melati POEM generated, programmer modified stub 
 * for a <code>Persistent</code> <code>User</code> object.
 * 
 * <p> 
 * Description: 
 *   A registered user of the database. 
 * </p>
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
 * @generator org.melati.poem.prepro.TableDef#generateMainJava 
 */
public class User extends UserBase implements AccessToken {


 /**
  * Constructor 
  * for a <code>Persistent</code> <code>User</code> object.
  * <p>
  * Description: 
  *   A registered user of the database. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public User() { }

  // programmer's domain-specific code here


  /**
   * Constructor.
   * 
   * @param login user's login name 
   * @param password user's password
   * @param name user's name
   */
  public User(String login, String password, String name) {
    setLogin_unsafe(login);
    setPassword_unsafe(password);
    setName_unsafe(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.AccessToken#givesCapability(org.melati.poem.Capability)
   */
  public boolean givesCapability(Capability capability) {
    return getDatabase().hasCapability(this, capability);
  }

  /**
   * Will throw a <TT>ReadPasswordAccessPoemException</TT> unless the access
   * token associated with the running thread is the <TT>User</TT> object
   * itself or provides the <TT>readPasswords</TT> capability.
   * 
   * FIXME It shouldn't be possible for anyone to getPassword
   *  
   * {@inheritDoc}
   * @see org.melati.poem.generated.UserBase#getPassword()
   */
  public String getPassword() throws AccessPoemException {
    // FIXME We need 2 sorts of object here 
    if (troid() != null) {
      AccessToken token = PoemThread.accessToken();
      if (token != this &&
          !token.givesCapability(getUserTable().canReadPasswords()))
        throw new ReadPasswordAccessPoemException(
              this, getUserTable().getPasswordColumn(), token,
              getUserTable().canReadPasswords());
    }

    return super.getPassword();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.generated.UserBase#setPassword(java.lang.String)
   */
  public void setPassword(String cooked) throws AccessPoemException {
    // FIXME We need 2 sorts of object here
    if (troid() != null) {
      AccessToken token = PoemThread.accessToken();
      if (token != this &&
          !token.givesCapability(getUserTable().canWritePasswords()))
        throw new WriteFieldAccessPoemException(
              this, getUserTable().getPasswordColumn(), token,
              getUserTable().canWritePasswords());
    }

    super.setPassword(cooked);
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return getLogin_unsafe() == null ? super.toString() : getLogin_unsafe();
  }

  /**
   * @return whether this User is the special, guest user
   */
  @SuppressWarnings("unchecked")
  public boolean isGuest() {
    return (this == ((UserTable<User>)getTable()).guestUser()) ? true: false;
  }

  /**
   * @return whether this User is an administrator
   */
  @SuppressWarnings("unchecked")
  public boolean isAdministrator() {
    return (this == ((UserTable<User>)getTable()).administratorUser()) ? true: false;
  }

}





