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

import org.melati.poem.generated.*;

public class ColumnInfo extends ColumnInfoBase {

  public void assertCanRead(AccessToken token) {}

  private Column _column = null;

  private Column column() {
    if (_column == null && troid() != null)
      _column = getDatabase().columnWithColumnInfoID(troid().intValue());
    return _column;
  }

  void setColumn(Column column) {
    _column = column;
  }

  public void setName(String name) {
    String current = getName();
    if (current != null && !current.equals(name))
      throw new ColumnRenamePoemException(name);
    super.setName(name);
  }

  public void setTableinfoTroid(Integer raw) throws AccessPoemException {
    Integer ti = super.getTableinfoTroid();
    if (ti != null && !ti.equals(raw))
      throw new IllegalArgumentException();
    super.setTableinfoTroid(raw);
  }

  public void setDisplaylevelIndex(Integer index) {
    super.setDisplaylevelIndex(index);
    if (index.equals(DisplayLevel.primary.index)) {
      Column column = column();
      if (column != null) {
        Table table = column.getTable();
        Column previous = table.displayColumn();
        if (previous != null && previous != column) {
          previous.setDisplayLevel(DisplayLevel.summary);
          table.setDisplayColumn(column);
        }
      }
    }
  }
}
