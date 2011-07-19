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

import java.util.Hashtable;

/**
 * The quality of being searchable:
 *  <tt>yes</tt>, <tt>no</tt> or <tt>primary</tt>.
 */
public final class Searchability {

  /** The Id of the Searchability. */
  public final Integer index;
  /** Its name. */
  private final String name;

  /** Constructor. */
  private Searchability(int index, String name) {
    this.index = new Integer(index);
    this.name = name;
  }

  /**
   * The column is the only one in the table for which a
   * special widget is provided to enter a search value.
   * <p>
   * There is at most one such column per table.
   * The user is able to select matching rows without
   * popping up a search criteria dialogue window.
   */
  public static final Searchability primary;

  /**
   * The column is searchable so that the user is able
   * to enter search criteria for it included along
   * with others when searching for rows.
   * <p>
   * Currently the search criteria for a column consist
   * of at most one non-null value.
   */
  public static final Searchability yes;

  /**
   * The column is not searchable so the user will not
   * be given the opportunity to enter search criteria
   * for it.
   */
  public static final Searchability no;

  private static int n = 0;

  private static final Searchability[] searchabilities =
    { primary = new Searchability(n++, "primary"),
      yes = new Searchability(n++, "yes"),
      no = new Searchability(n++, "no") };

  private static final Hashtable<String, Searchability> searchabilityOfName = new Hashtable<String, Searchability>();

  static {
    for (int i = 0; i < searchabilities.length; ++i)
      searchabilityOfName.put(searchabilities[i].name, searchabilities[i]);
  }

  /**
   * @param index key
   * @return the selected Searchability
   */
  public static Searchability forIndex(int index) {
    return searchabilities[index];
  }

  /**
   * @return the number of Searchabilities
   */
  public static int count() {
    return searchabilities.length;
  }

 /**
  * Thrown when an invalid {@link Searchability} level is specified, 
  * by a typing mistake in the DSD for example.
  */
  public static class NameUnrecognisedException
      extends PoemException {
    private static final long serialVersionUID = 1L;

    /** The name of the requested Searchability. */
    public String name;

    /** Constructor. */
    public NameUnrecognisedException(String name) {
      this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getMessage() {
      return
        "No searchability level found which goes by the name `" + name + "'";
    }
  }

  /**
   * Throws NameUnrecognisedException if not found.
   *  
   * @param name String name of Searchability
   * @return the Searchability 
   */
  public static Searchability named(String name) {
    Searchability it = (Searchability)searchabilityOfName.get(name);
    if (it == null)
      throw new NameUnrecognisedException(name);
    return it;
  }
  
  /** 
   * @return the name and index
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name + "/" + index;
  }

  /**
   * @return the index
   */
  public Integer getIndex() {
    return index;
  }

  /**
   * @return the Name
   */
  public String getName() {
    return name;
  }

}
