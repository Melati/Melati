/*
 * $Source$
 * $Revision$
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

package org.melati.poem.prepro;

import java.io.*;
import java.util.*;

public class IndexedFieldQualifier extends FieldQualifier {

  protected boolean unique = false;
  private Vector withs = new Vector();

  public IndexedFieldQualifier(StreamTokenizer tokens) throws IOException {
    /*
    The difficulty in implementing (unique with) it is that it's
    not obvious how you are meant to extract information about
    multi-column indexes from the JDBC metadata.  Look at the
    DatabaseMetaData.getIndexInfo.

    if (tokens.ttype == StreamTokenizer.TT_WORD &&
        tokens.sval.equals("with"))
      while (tokens.nextToken() == StreamTokenizer.TT_WORD)
        withs.addElement(tokens.sval);
    */
  }

  public void apply(FieldDef field) throws IllegalityException {
    field.isIndexed = true;
  }
}
