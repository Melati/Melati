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

public class TableInfo extends TableInfoBase {

  private Table _actualTable = null;

  public Table actualTable() {
    if (_actualTable == null && troid() != null)
      _actualTable = getDatabase().tableWithTableInfoID(troid().intValue());
    return _actualTable;
  }

  public void assertCanRead(AccessToken token) {}

  public TableInfo() {
  }

  public TableInfo(String name, String displayName, int displayOrder,
                   String description, Integer cacheLimit,
                   boolean rememberAllTroids, TableCategory category) {
    setName_unsafe(name);
    setDisplayname_unsafe(displayName);
    setDisplayorder_unsafe( new Integer(displayOrder) );
    setDescription_unsafe(description);
    setCachelimit_unsafe(cacheLimit);
    setSeqcached_unsafe( rememberAllTroids ? Boolean.TRUE : Boolean.FALSE);
    setCategory_unsafe(category.troid());
  }

  public void setName(String name) {
    String current = getName();
    if (current != null && !current.equals(name))
      throw new TableRenamePoemException(name);
    super.setName(name);
  }

  public String displayString() throws AccessPoemException {
    return getDisplayname();
  }

  public void setSeqcached(Boolean b) throws AccessPoemException {
    super.setSeqcached(b);
    actualTable().rememberAllTroids(b.booleanValue());
  }

  public void setCachelimit(Integer limit) throws AccessPoemException {
    super.setCachelimit(limit);
    actualTable().setCacheLimit(limit);
  }
}
