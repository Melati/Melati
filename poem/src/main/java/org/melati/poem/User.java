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
import java.sql.*;
import org.melati.util.*;

/**
 * FIXME it shouldn't be possible for anyone to getPassword
 */

public class User extends UserBase implements AccessToken {

  public User() {
  }

  public User(String login, String password, String name) {
    setLogin_unsafe(login);
    setPassword_unsafe(password);
    setName_unsafe(name);
  }

  public boolean givesCapability(Capability capability) {
    return getDatabase().hasCapability(this, capability);
  }

  /**
   * Will throw a <TT>ReadPasswordAccessPoemException</TT> unless the access
   * token associated with the running thread is the <TT>User</TT> object
   * itself or provides the <TT>readPasswords</TT> capability.
   */

  public String getPassword() throws AccessPoemException {
    // FIXME need 2 sorts of obj
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

  public void setPassword(String cooked) throws AccessPoemException {
    // FIXME need 2 sorts of obj
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

  public String toString() {
    return getLogin_unsafe() == null ? super.toString() : getLogin_unsafe();
  }

  public boolean isGuest() {
    return (this == ((UserTable)getTable()).guestUser()) ? true: false;
  }
}
