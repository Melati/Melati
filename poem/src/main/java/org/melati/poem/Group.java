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

import org.melati.poem.generated.GroupBase;

/**
 * An authorisation grouping that a {@link User} can belong to.
 *
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>Group</code> object.
 * 
 * <p> 
 * Description: 
 *   A group to which registered users of the database can belong. 
 * </p>
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>Group</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> name </td><td> String </td><td> A human-readable name for the 
 * group </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateMainJava 
 */
public class Group extends GroupBase {

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>Group</code> object.
  * <p>
  * Description: 
  *   A group to which registered users of the database can belong. 
  * </p>
  * 
  * See org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public Group() {}

  // programmer's domain-specific code here

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>Group</code> object.
  * <p>
  * Description: 
  *   A group to which registered users of the database can belong. 
  * </p>
  * 
  * @param name the name of the {@link Group} to create
  */
  public Group(String name) {
    this.name = name;
  }

 /**
  * Allow this object to be read by anyone.
  * 
  * @param token any {@link AccessToken}
  */
  public void assertCanRead(AccessToken token) {}
  
  public GroupMembership ensureMember(User u) { 
    GroupMembershipTable<GroupMembership> gmt = getDatabase().getGroupMembershipTable();
    return gmt.ensure(this, u);
  }
}
