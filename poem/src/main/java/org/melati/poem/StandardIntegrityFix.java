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

import java.util.Enumeration;
import java.util.Map;
import java.util.Hashtable;
import org.melati.util.EmptyEnumeration;

/**
 * A class which defines the three standard integrity fixes of 
 * <tt>delete</tt>, <tt>clear</tt> and <tt>fix</tt>.
 *
 * @todo More javadoc please.
 */
public abstract class StandardIntegrityFix implements IntegrityFix {

  public final Integer index;
  public final String name;

  /* private -- makes Sun compiler barf ... 
     An abstract class must be extended so cannot have a private constructor.
   */ 
  StandardIntegrityFix(int index, String name) {
    this.index = new Integer(index);
    this.name = name;
  }

  public static final StandardIntegrityFix delete, clear, prevent;

  /**
   * Each {@link StandardIntegrityFix} differs from another by the 
   * way they implement {@link #referencesTo}.
   *
   * <b>delete</b> deletes all references to this {@link Persistent}. <br/>
   * <b>clear</b> sets the references to null. <br/>
   * <b>prevent</b> returns the references, which prevokes an exception
   * 
   * @see Persistent#delete(Map)
   * @param referee the {@link Persistent} we are deleting
   * @param column the {@link Column} of the reference to {@link Persistent} 
   *        we are deleting, used in <b>clear</b>
   * @param refs an {@link Enumeration} of the objects which contain 
   *        a reference to the {@link Persistent} we are deleting,
   *        each is deleted, cleared or returned respectively
   * @param referenceFixOfColumn a {@link Map} keyed on {@link Column} 
   *        giving the associated {@link IntegrityFix}, passed in to 
   *        the {@link Persistent} referer to delete itself
   *@return an {@link Enumeration} of the remaining referers    
   */
   public abstract Enumeration referencesTo(Persistent referee,
                                   Column column,
                                   Enumeration refs,
                                   Map referenceFixOfColumn);
  
  private static final StandardIntegrityFix[] fixes = { 
    delete = new StandardIntegrityFix(0, "delete") {
      public Enumeration referencesTo(Persistent referee, Column column,
                                      Enumeration refs,
                                      Map referenceFixOfColumn) {
        while (refs.hasMoreElements()) {
          try {
            Persistent p = (Persistent)refs.nextElement();
            p.delete(referenceFixOfColumn);
          }
          catch (RowDisappearedPoemException e) {
            // This is possible if the table has a (currently non-standard)
            // integrity fix that deletes subsequent rows. ResultSetEnumeration
            // allows us to carry on in that case.
            if (! (refs instanceof ResultSetEnumeration)) {
              throw e;
            }
          }
        }
        return EmptyEnumeration.it;
      }
    },
    clear = new StandardIntegrityFix(1, "clear") {
      public Enumeration referencesTo(Persistent referrer, Column column,
                                      Enumeration refs,
                                      Map referenceFixOfColumn) {
        while (refs.hasMoreElements())
          column.setRaw((Persistent)refs.nextElement(), null);
        return EmptyEnumeration.it;
      }
    },
    prevent = new StandardIntegrityFix(2, "prevent") {
      public Enumeration referencesTo(Persistent referrer, Column column,
                                      Enumeration refs,
                                      Map referenceFixOfColumn) {
        return refs;
      }
    }
  };

  public static StandardIntegrityFix forIndex(int i) {
    return fixes[i];
  }

  public static int count() {
    return fixes.length;
  }

  private static final Hashtable fixOfName = new Hashtable();

  static {
    for (int i = 0; i < fixes.length; ++i)
      fixOfName.put(fixes[i].name, fixes[i]);
  }

 /**
  * Thrown when an invalid {@link StandardIntegrityFix} is specified, 
  * by a typing mistake in the DSD for example.
  */
  public static class NameUnrecognisedException extends PoemException {
    private static final long serialVersionUID = 1L;

    public String name;

    public NameUnrecognisedException(String name) {
      this.name = name;
    }

    public String getMessage() {
      return "No integrity fix found which goes by the name `" + name + "'";
    }
  }

  public static StandardIntegrityFix named(String name) {
    StandardIntegrityFix it = (StandardIntegrityFix)fixOfName.get(name);
    if (it == null)
      throw new NameUnrecognisedException(name);
    return it;
  }
}
