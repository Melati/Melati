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

package org.melati.util;

public class BitmapSet implements Cloneable {
  private int[] flags;

  private BitmapSet(BitmapSet other) {
    flags = (int[])other.flags.clone();
  }

  public BitmapSet(int capacity) {
    flags = new int[(capacity + 31) / 32];
  }

  public synchronized void set(int n) {
    int i = n / 32;

    if (flags.length <= i) {
      int[] newFlags = new int[Math.max(i * 2, flags.length * 2)];
      System.arraycopy(flags, 0, newFlags, 0, flags.length);
      flags = newFlags;
    }

    flags[i] |= 1 << i % 32;
  }

  public boolean get(int n) {
    int[] flags = this.flags;
    int i = n / 32;
    return flags.length <= i ? false : (flags[i] & (1 << i % 32)) != 0;
  }

  public Object clone() {
    return new BitmapSet(this);
  }
}
