/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import org.melati.poem.generated.*;

public class TableInfo extends TableInfoBase {

  private Table _actualTable = null;

  private Table actualTable() {
    if (_actualTable == null && troid() != null)
      _actualTable = getDatabase().tableWithTableInfoID(troid().intValue());
    return _actualTable;
  }

  protected void assertCanRead(AccessToken token) {}

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
