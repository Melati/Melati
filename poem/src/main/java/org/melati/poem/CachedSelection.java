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
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import org.melati.util.*;
import java.sql.*;
import java.util.*;

public class CachedSelection extends CachedQuery {

  private String whereClause;
  private String orderByClause;
  private String tableDefaultOrderByClause = null;

  public CachedSelection(final Table table,
                           final String whereClause,
                           final String orderByClause,
                           final Table otherTables[]) {
    super(table,
          table.selectionSQL(whereClause, orderByClause, false),
          otherTables);
    this.whereClause = whereClause;
    this.orderByClause = orderByClause;
  }

  public CachedSelection(final Table table,
                           final String whereClause,
                           final String orderByClause) {
    this(table,whereClause,orderByClause,null);
  }

  protected PreparedStatementFactory statements() {
    if (orderByClause == null) {
      String defaultOrderByClause = table.defaultOrderByClause();
      if (defaultOrderByClause != tableDefaultOrderByClause) {
        statements = null;
        tableDefaultOrderByClause = defaultOrderByClause;
      }
    }

    if (statements == null)
      statements = new PreparedStatementFactory(
                       table.getDatabase(),
                       table.selectionSQL(whereClause, orderByClause, false));

    return statements;
  }
  
  final protected Object extract(ResultSet rs) throws SQLException {
    return new Integer(rs.getInt(1));
  }

  public Enumeration troids() {
    compute();
    return rows.elements();
  }

  public Enumeration objects() {
    return
        new MappedEnumeration(troids()) {
          public Object mapped(Object troid) {
            return table.getObject((Integer)troid);
          }
        };
  }

  public Persistent firstObject() {
    return nth(0);
  }

  public Persistent nth(int n) {
    compute();
    Vector rows = this.rows;
    return rows.size() <= n ?
               null :
               table.getObject((Integer)rows.elementAt(n));
  }

  public int count() {
    compute();
    return this.rows.size();
  }
}
