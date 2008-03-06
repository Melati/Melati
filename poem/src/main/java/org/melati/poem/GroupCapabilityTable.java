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

import java.util.Enumeration;

import org.melati.poem.generated.GroupCapabilityTableBase;

/**
 * The {@link Table} of {@link GroupCapability}s.
 *
 * Every Melati DB has this table.
 * This table will contain at least the tuple
 * <code>Melati database administrators</code>:<code>_administer_</code>. 
 *
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>GroupCapabilityTable</code> object.
 * <p>
 * Description: 
 *   A record that users belonging to a given group possess a given 
 *   capability. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>GroupCapability</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> group </td><td> Group </td><td> The user-group which has the 
 * capability </td></tr> 
 * <tr><td> capability </td><td> Capability </td><td> The capability 
 * </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */

public class GroupCapabilityTable extends GroupCapabilityTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public GroupCapabilityTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  protected void postInitialise() {
    super.postInitialise();

    Database d = getDatabase();
    GroupCapability admin = (GroupCapability)newPersistent();
    admin.setGroup(d.getGroupTable().administratorsGroup());
    admin.setCapability(d.administerCapability());

    if (!exists(admin))
      create(admin);

  }
  
  /**
   * Make sure that a record exists.
   *
   * @return the existing or newly created {@link GroupCapability}
   */
  public GroupCapability ensure(Group group, Capability capability) {
     GroupCapability p = (GroupCapability)newPersistent();
     p.setGroup(group);
     p.setCapability(capability);
     Enumeration them = selection(p);
     if (them.hasMoreElements()) 
       return (GroupCapability)them.nextElement();
     else {
       p.makePersistent();
       return p;
     }
   }

}
