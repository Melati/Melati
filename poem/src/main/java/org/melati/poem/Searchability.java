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
import org.melati.util.*;

public class Searchability {

  public final Integer index;
  public final String name;

  private Searchability(int index, String name) {
    this.index = new Integer(index);
    this.name = name;
  }

  public static final Searchability
      primary, yes, no;

  private static int n = 0;

  private static final Searchability[] searchabilities =
    { primary = new Searchability(n++, "primary"),
      yes = new Searchability(n++, "yes"),
      no = new Searchability(n++, "no") };

  private static final Hashtable searchabilityOfName = new Hashtable();

  static {
    for (int i = 0; i < searchabilities.length; ++i)
      searchabilityOfName.put(searchabilities[i].name, searchabilities[i]);
  }

  public static Searchability forIndex(int index) {
    return searchabilities[index];
  }

  public static int count() {
    return searchabilities.length;
  }

  public static class NameUnrecognisedException
      extends MelatiRuntimeException {
    public String name;

    public NameUnrecognisedException(String name) {
      this.name = name;
    }

    public String getMessage() {
      return
	  "No searchability level found which goes by the name `" + name + "'";
    }
  }

  public static Searchability named(String name) {
    Searchability it = (Searchability)searchabilityOfName.get(name);
    if (it == null)
      throw new NameUnrecognisedException(name);
    return it;
  }
}
