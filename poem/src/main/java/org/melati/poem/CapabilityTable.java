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

import org.melati.poem.generated.CapabilityTableBase;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@link Table} of {@link Capability}s.
 *
 * Every Melati DB has this table.
 * This table will contain at least <code>_administer_</code>. 
 *
 */
public class CapabilityTable extends CapabilityTableBase {

  private static final Object nullEntry = new Object();

  public CapabilityTable(Database database, String name,
                         DefinitionSource definitionSource)
      throws PoemException {
    super(database, name, definitionSource);
  }

  private Capability administer;

  Capability administer() {
    return administer;
  }

  public synchronized void unifyWithDB(ResultSet colDescs)
      throws SQLException, PoemException {
    super.unifyWithDB(colDescs);

    Capability standard = (Capability)newPersistent();
    standard.setName_unsafe("_administer_");
    administer = (Capability)getNameColumn().ensure(standard);

    if (getTableInfo().getDefaultcanwrite() == null)
      getTableInfo().setDefaultcanwrite(administer);
    if (getTableInfo().getDefaultcandelete() == null)
      getTableInfo().setDefaultcandelete(administer);
    if (getTableInfo().getCancreate() == null)
      getTableInfo().setCancreate(administer);
  }
  
  public Capability get(String name) {
      return (Capability) getNameColumn().firstWhereEq(name);
  }

  public Capability ensure(String name) {
    Capability capability = get(name);
    if (capability != null)
      return capability;
    else {
      capability = (Capability) newPersistent();
      capability.setName(name);
      return (Capability)getNameColumn().ensure(capability);
    }
  }

}
