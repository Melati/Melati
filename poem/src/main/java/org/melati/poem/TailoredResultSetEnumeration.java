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

import java.util.*;
import java.sql.*;

public class TailoredResultSetEnumeration extends ResultSetEnumeration {

  protected TailoredQuery query;

  public TailoredResultSetEnumeration(TailoredQuery query,
                                      ResultSet resultSet) {
    super(resultSet);
    this.query = query;
  }

  void checkTableAccess(ResultSet them) {
    AccessToken token = PoemThread.accessToken();

    for (int t = 0; t < query.canReadTables.length; ++t) {
      Capability canRead = query.canReadTables[t].getDefaultCanRead();
      if (canRead != null && !token.givesCapability(canRead))
	throw new AccessPoemException(token, canRead);
    }
  }

  Object column(ResultSet them, int c) {
    Column column = query.columns[c];
    Object raw = column.getSQLType().getRaw(them, c + 1);

    if (query.isCanReadColumn[c]) {
      Capability canRead = (Capability)column.getType().cookedOfRaw(raw);
      if (canRead == null)
	canRead = column.getTable().getDefaultCanRead();
      if (canRead != null) {
	AccessToken token = PoemThread.accessToken();
	if (!token.givesCapability(canRead))
	  throw new AccessPoemException(token, canRead);
      }
    }

    return raw;
  }

  protected Object mapped(ResultSet them)
      throws SQLException, NoSuchRowPoemException {
    checkTableAccess(them);

    Field[] fields = new Field[query.columns.length];
    try {
      for (int c = 0; c < fields.length; ++c)
        fields[c] = new Field(column(them, c), query.columns[c]);
    }
    catch (AccessPoemException accessProblem) {
      // Blank out the whole FieldSet.  We have to do this because we don't
      // know how fields have been used in the WHERE clause without
      // interpreting it.  Walls have ears and all that.

      for (int c = 0; c < fields.length; ++c)
        fields[c] = new Field(accessProblem, query.columns[c]);
    }

    return new FieldSet(query.table_columnMap, fields);
  }
}
