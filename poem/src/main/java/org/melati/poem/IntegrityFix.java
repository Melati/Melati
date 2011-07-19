/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 William Chesters
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

import java.util.Enumeration;
import java.util.Map;

/**
 * Class representing what to do about references to a {@link Persistent}
 * which is about to be deleted.
 *
 * Three canned {@link IntegrityFix} policies are provided: {@link
 * StandardIntegrityFix#delete}, which simply deletes all referring objects,
 * {@link StandardIntegrityFix#clear}, which <TT>NULL</TT>s out all
 * references, and {@link StandardIntegrityFix#prevent}, which prevents
 * deletion if there are any references which would be left dangling.
 *
 * @author WilliamC At paneris.org
 *
 */

public interface IntegrityFix {

  /**
   * Do something about references from a given column to a
   * {@link Persistent} which is about to be deleted.  Called via {@link
   * Persistent#delete(Map)}, this gives the application programmer 
   * fine-grained control over how referential integrity is maintained.
   *
   *
   * @param referee   The object which is about to be deleted.

   * @param column    A column which refers to <TT>referee</TT>'s table:
   *                  <TT>column.getType()</TT> is a
   *                  <TT>ReferencePoemType</TT> with <TT>targetTable()</TT>
   *                  <TT>==</TT> <TT>referee.getTable()</TT>.
   *
   * @param refs      All the <TT>Persistent</TT>s of which <TT>column</TT>
   *                  actually points to <TT>referee</TT>.
   *
   * @param referenceFixOfColumn The column-to-<TT>IntegrityFix</TT>
   *         mapping passed into {@link Persistent#delete(Map)}.
   *
   * @return Either a list of the <TT>Persistent</TT>s from <TT>refs</TT> which
   *         constitute reasons why the <TT>referee</TT> can't be deleted 
   *         or the <TT>EmptyEnumeration</TT> if there are no references or 
   *         all <TT>refs</TT> have been cleared or deleted.
   */

  Enumeration<Persistent> referencesTo(Persistent referee, Column<?> column,
                           Enumeration<Persistent> refs, Map<Column<?>, IntegrityFix> referenceFixOfColumn);
  
  /**
   * Integrity fixes are set in the DSD by name. 
   * @return the name
   */
  String getName();
}
