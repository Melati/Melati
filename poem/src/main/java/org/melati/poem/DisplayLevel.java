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

import java.util.Hashtable;

public class DisplayLevel {

  public final Integer index;
  public final String name;

  private DisplayLevel(int index, String name) {
    this.index = new Integer(index);
    this.name = name;
  }

  /**
   * Display level of the column used to refer concisely to a record
   * on the UI.
   *
   * @see Table#displayColumn()
   */
  public static final DisplayLevel primary;

  /**
   * Display level of columns included in a summary of records in
   * a set.
   * <p>
   * This is the default display level for a column.
   *
   * @see Table#getSummaryDisplayColumns()
   * @see Column#defaultDisplayLevel()
   * @see Persistent#getSummaryDisplayFields()
   */
  public static final DisplayLevel summary;

  /**
   * Display level of columns included in display focusing on a
   * single record, but without detail.
   *
   * @see Table#getRecordDisplayColumns()
   * @see Persistent#getRecordDisplayFields()
   */
  public static final DisplayLevel record;

  /**
   * Display level of columns included in a detailed display
   * of a single record.
   *
   * @see Table#getRecordDisplayColumns()
   * @see Persistent#getDetailDisplayFields()
   */
  public static final DisplayLevel detail;

  /**
   * Display level of columns hidden from users.
   */
  public static final DisplayLevel never;

  private static int n = 0;

  private static final DisplayLevel[] displayLevels =
    { primary = new DisplayLevel(n++, "primary"),
      summary = new DisplayLevel(n++, "summary"),
      record = new DisplayLevel(n++, "record"),
      detail = new DisplayLevel(n++, "detail"),
      never = new DisplayLevel(n++, "never") };

  private static final Hashtable levelOfName = new Hashtable();

  static {
    for (int i = 0; i < displayLevels.length; ++i)
      levelOfName.put(displayLevels[i].name, displayLevels[i]);
  }

  public static DisplayLevel forIndex(int index) {
    return displayLevels[index];
  }

  public static int count() {
    return displayLevels.length;
  }

  public static class NameUnrecognisedException extends PoemException {
    public String name;

    public NameUnrecognisedException(String name) {
      this.name = name;
    }

    public String getMessage() {
      return "No display level found which goes by the name `" + name + "'";
    }
  }

  public static DisplayLevel named(String name) {
    DisplayLevel it = (DisplayLevel)levelOfName.get(name);
    if (it == null)
      throw new NameUnrecognisedException(name);
    return it;
  }
}
