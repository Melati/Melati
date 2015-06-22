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
 *     William Chesters <williamc AT paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import org.melati.poem.generated.CapabilityTableBase;
import java.sql.ResultSet;

/**
 * The {@link Table} of {@link Capability}s.
 *
 * Every Melati DB has this table.
 * This table will contain at least <code>_administer_</code>. 
 *
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>CapabilityTable</code> object.
 * <p>
 * Description: 
 *   A capability which users can be required to possess before accessing 
 *   data. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>Capability</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> name </td><td> String </td><td> A human-readable name for the 
 * capability </td></tr> 
 * </table> 
 * 
 * See  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class CapabilityTable<T extends Capability> extends CapabilityTableBase<T> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public CapabilityTable(Database database, String name,
                         DefinitionSource definitionSource)
      throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  /** Ensured capability - all db have one. */ 
  private Capability administer;

  /** @return the Capability required to administer a database **/ 
  Capability administer() {
    return administer;
  }

 /**
  * Ensure that the <tt>_administer_</tt> {@link Capability} 
  * exists and apply it to this table, also create <tt>canRead</tt>,
  * <tt>canWrite</tt>, <tt>candelete</tt> and <tt>canSelect</tt> 
  * as they are referrred to in column definition.
  *
  * @param colDescs the {@link Column} descriptions
  * @see org.melati.poem.Table#defineColumn(Column)
  */
  public synchronized void unifyWithDB(ResultSet colDescs, String troidColumnName)
      throws PoemException {
    super.unifyWithDB(colDescs, troidColumnName);

    administer = ensure("_administer_");
    
    ensure("canRead");
    ensure("canWrite");
    ensure("canDelete");
    ensure("canSelect");

  }
  
 /**
  * Retrieve a {@link Capability} by name.
  *
  * @param name the name of the {@link Capability} to return
  * @return the existing {@link Capability}
  */
  public Capability get(String name) {
      return (Capability) getNameColumn().firstWhereEq(name);
  }

 /**
  * Make sure that a record exists.
  *
  * @param name the name of the {@link Capability} to ensure
  * @return the existing or newly created {@link Capability}
  */
  public Capability ensure(String name) {
    Capability capability = get(name);
    if (capability != null)
      return capability;
    else {
      capability = (Capability)newPersistent();
      capability.setName(name);
      return (Capability)getNameColumn().ensure(capability);
    }
  }

}
