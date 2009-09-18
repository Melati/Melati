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
import org.melati.poem.util.MappedEnumeration;

/**
 * A cached instance of an SQL <code>select</code> query.
 */
public class CachedSelection extends CachedQuery {

  /**
   * Constructor.
   * @param table the primary Table we are selecting rows from
   * @param whereClause SQL snippet
   * @param orderByClause SQL snippet
   * @param otherTables an Array of Tables which are also referenced in query
   */
  public CachedSelection(final Table table,
                         final String whereClause,
                         final String orderByClause,
                         final Table otherTables[]) {
    super(table, null, otherTables);
    

    String fromClause = table.quotedName();
    if (otherTables != null) {
      for (int i = 0; i<otherTables.length; i++) {
        fromClause += ", " + otherTables[i].quotedName();
      }
    }
    setQuery(table.selectionSQL(fromClause, whereClause, orderByClause, false, true));
    
  }

  /**
   * Constructor for a single Table query.
   * @param table the primary Table we are selecting rows from
   * @param whereClause SQL snippet
   * @param orderByClause SQL snippet
   */
  public CachedSelection(final Table table,
                         final String whereClause,
                         final String orderByClause) {
    this(table,whereClause,orderByClause,null);
  }

  /**
   * @return an Enumeration of Table Row Object Ids
   */
  public Enumeration<Integer> troids() {
    compute();
    return rows.elements();
  }

  /**
   * @return an Enumeration of Table Row ReferencePoemType objects
   */
  public Enumeration objects() {
    return
        new MappedEnumeration(troids()) {
          public Object mapped(Object troid) {
            return table.getObject((Integer)troid);
          }
        };
  }

  /**
   * @return the first, often only, result
   */
  public Persistent firstObject() {
    return nth(0);
  }

  /**
   * @param n index into ResultSet
   * @return the row corresponding to the index
   */
  public Persistent nth(int n) {
    compute();
    return rows.size() <= n ?
               null :
               table.getObject((Integer)rows.elementAt(n));
  }

  /**
   * @return the number of results
   */
  public int count() {
    compute();
    return this.rows.size();
  }
}
