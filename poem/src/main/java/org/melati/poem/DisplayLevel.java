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
 * An attribute of a {@link Column} which indicates what level of 
 * reporting of a {@link Table} it should be included in.
 *
 */
public final class DisplayLevel {

  /** The numeric Id of the Level. */
  public final Integer index;
  /** The name of the level. */
  private final String name;

  /** Constructor. */
  private DisplayLevel(int index, String name) {
    this.index = new Integer(index);
    this.name = name;
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the index.
   */
  public Integer getIndex() {
    return index;
  }


  /**
   * Display level of a {@link Column} used as the name of the 
   * whole record.
   *
   * @see Table#displayColumn()
   */
  public static final DisplayLevel primary;

  /**
   * Display level of {@link Column}s to be included in a summary of 
   * records in a set.
   * <p>
   * This is the default display level for a {@link Column}.
   *
   * @see Table#getSummaryDisplayColumns()
   * @see Column#defaultDisplayLevel()
   * @see Persistent#getSummaryDisplayFields()
   */
  public static final DisplayLevel summary;

  /**
   * Display level of {@link Column}s included in display focusing on a
   * single record, but without detail.
   *
   * @see Table#getRecordDisplayColumns()
   * @see Persistent#getRecordDisplayFields()
   */
  public static final DisplayLevel record;

  /**
   * Display level of {@link Column}s included in a detailed display
   * of a single record.
   *
   * @see Table#getRecordDisplayColumns()
   * @see Persistent#getDetailDisplayFields()
   */
  public static final DisplayLevel detail;

  /**
   * Display level of {@link Column}s hidden from users.
   */
  public static final DisplayLevel never;

  private static int n = 0;

  private static final DisplayLevel[] displayLevels =
    { primary = new DisplayLevel(n++, "primary"),
      summary = new DisplayLevel(n++, "summary"),
      record = new DisplayLevel(n++, "record"),
      detail = new DisplayLevel(n++, "detail"),
      never = new DisplayLevel(n++, "never") };

  private static final Hashtable<String, DisplayLevel> levelOfName = new Hashtable<String, DisplayLevel>();

  static {
    for (int i = 0; i < displayLevels.length; ++i)
      levelOfName.put(displayLevels[i].name, displayLevels[i]);
  }

  /**
   * Get by numeric id. 
   * 
   * @param index the numeric Id of the level
   * @return the level corresponding to the index
   */
  public static DisplayLevel forIndex(int index) {
    return displayLevels[index];
  }

  /**
   * @return the number of levels. 
   */
  public static int count() {
    return displayLevels.length;
  }

 /**
  * Thrown when a <code>DisplayLevel</code> which doesn't exist is referenced, 
  * by misspelling for example.
  */
  public static class NameUnrecognisedException extends PoemException {
    private static final long serialVersionUID = 1L;

    /** The name we did not recognise. */
    public String name;

    /** Constructor. */
    public NameUnrecognisedException(String name) {
      this.name = name;
    }

    /** @return The detail message. */
    public String getMessage() {
      return "No display level found which goes by the name `" + name + "'";
    }
  }

  /**
   * Get by name. 
   * @param name The name of the required level
   * @return the named level
   */
  public static DisplayLevel named(String name) {
    DisplayLevel it = (DisplayLevel)levelOfName.get(name);
    if (it == null)
      throw new NameUnrecognisedException(name);
    return it;
  }
  
  /** 
   * @return the name and index.
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name + "/" + index;
  }
}
