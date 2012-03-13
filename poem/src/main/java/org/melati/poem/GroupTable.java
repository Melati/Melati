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

import org.melati.poem.generated.GroupTableBase;

/**
 * The {@link Table} of {@link Group}s.
 *
 * Every Melati DB has this table.
 * This table will contain at least 
 * <code>Melati database administrators</code>. 
 *
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>GroupTable</code> object.
 * <p>
 * Description: 
 *   A group to which registered users of the database can belong. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Group</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> name </td><td> String </td><td> A human-readable name for the 
 * group </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class GroupTable<T extends Group> extends GroupTableBase<T> {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public GroupTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  private Group administratorsGroup;

  /**
   * @return the Group to which administrators belong
   */
  public Group administratorsGroup() {
    return administratorsGroup;
  }

  public void postInitialise() {
    super.postInitialise();

    administratorsGroup = ensure("Melati database administrators");

  }
  
 /**
  * Make sure that a record exists.
  *
  * @param name the name of the {@link Group} to ensure
  * @return the existing or newly created {@link Group}
  */
  public Group ensure(String name) {
    Group group = (Group)getNameColumn().firstWhereEq(name);
    if (group != null)
      return group;
    else {
      group = (Group)newPersistent();
      group.setName(name);
      return (Group)getNameColumn().ensure(group);
    }
  }

}
