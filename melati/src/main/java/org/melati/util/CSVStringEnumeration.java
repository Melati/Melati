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
 *     William Chesters <williamc At paneris.org>
 */
package org.melati.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * A utility for tokenising a string made up of comma-separated
 * variables.  Unlike Tim's effort, it handles quoted variables as
 * well.
 *
 * <PRE>
 *   foo, bar om,,"baz, ,oof",xyz,   ->
 *     "foo", " bar om", "", "baz, , oof", "xyz", ""
 * </PRE>
 *
 * @author      williamc AT paneris.org
 */

public class CSVStringEnumeration implements Enumeration<String> {

  private String line = "";
  private boolean emptyLastField = false;
  int p = 0;

  /**
   * Look at a new string.
   */
  public void reset(String lineP) {
    this.line = lineP;
    p = 0;
  }

  /**
   * Are there any more tokens to come?
   * {@inheritDoc}
   * @see java.util.Enumeration#hasMoreElements()
   */
  public boolean hasMoreElements() {
    return emptyLastField || p < line.length();
  }

  /**
   * Return the next token as an <TT>Object</TT>.
   * {@inheritDoc}
   * @see java.util.Enumeration#nextElement()
   */
  public final String nextElement() {
    return nextToken();
  }

  /**
   * @return the next token as a <TT>String</TT>.
   */
  public String nextToken() {

    if (emptyLastField) {
      emptyLastField = false;
      return "";
    }

    if (p >= line.length()) throw new NoSuchElementException();

    if (line.charAt(p) == '"') {
      ++p;
      // we need to allow for quotes inside quoted fields, so now test for ",
      int q = line.indexOf("\",", p);
      // if it is not there, we are (hopefully) at the end of a line
      if (q == -1 && (line.indexOf('"', p) == line.length()-1)) 
        q = line.length()-1;
        
      if (q == -1) {
         p = line.length();
         throw new IllegalArgumentException("Unclosed quotes");
      }

      String it = line.substring(p, q);

      ++q;
      p = q+1;
      if (q < line.length()) {
        if (line.charAt(q) != ',') {
          p = line.length();
          throw new IllegalArgumentException("No comma after quotes");
        }
        else if (q == line.length() - 1)
          emptyLastField = true;
      }
      return it;
    } else {
      int q = line.indexOf(',', p);
      if (q == -1) {
        String it = line.substring(p);
        p = line.length();
        return it;
      } else {
          String it = line.substring(p, q);
          if (q == line.length() - 1)
            emptyLastField = true;
          p = q + 1;
          return it;
      }
    }
  }

}
