/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Samuel Goldstein
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
 *	   Samuel Goldstein <samuel@1969.ws>
 *     http://www.1969.ws
 *     13101 W. Washington Blvd Suite 248, Los Angeles, CA 90066 USA
 */

package org.melati.poem.prepro;

import java.io.StreamTokenizer;
import java.io.IOException;

/**
 * A <tt>FieldQualifier</TT> which enables the scale of 
 * BigDecimals to be specified.
 *
 * @todo Ensure that one field cannot have more than one scale qualifier
 */
public class ScaleFieldQualifier extends FieldQualifier {

  private int scale;

  public ScaleFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    tokens.nextToken();
    if (tokens.ttype != StreamTokenizer.TT_NUMBER || (int)tokens.nval < 0)
        throw new ParsingDSDException("<non-negative scale number>", tokens);
    scale = (int)tokens.nval;
    tokens.nextToken();
  }

  public void apply(FieldDef field) throws SizeApplicationException {
    // FIXME check for duplication
    if (field instanceof BigDecimalFieldDef)
       ((BigDecimalFieldDef)field).scale = scale;
    else
      throw new SizeApplicationException(field);
  }
}
