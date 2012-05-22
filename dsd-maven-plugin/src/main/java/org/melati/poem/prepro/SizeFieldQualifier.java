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

package org.melati.poem.prepro;

import java.io.StreamTokenizer;
import java.io.IOException;

/**
 * A <tt>FieldQualifier</TT> which enables the size of 
 * strings to be specified.
 */
public class SizeFieldQualifier extends FieldQualifier {

  private int size;

 /**
  * Constructor.
  *
  * @param tokens the <code>StreamTokenizer</code> to get tokens from
  *
  * @throws ParsingDSDException if an unexpected token is encountered
  * @throws IOException if something goes wrong with the file system
  */
  public SizeFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    tokens.nextToken();
    if (tokens.ttype == StreamTokenizer.TT_WORD &&
        tokens.sval.equals("unlimited"))
      size = -1;
    else {
      if (tokens.ttype != StreamTokenizer.TT_NUMBER || (int)tokens.nval <= 0)
        throw new ParsingDSDException("<positive size number>", tokens);
      size = (int)tokens.nval;
    }
    tokens.nextToken();
  }

 /**
  * Update the model.
  *
  * @param field the {@link FieldDef} to update
  *
  * @throws SizeApplicationException if the field is a non-sizable field
  */
  public void apply(FieldDef field) throws SizeApplicationException {
    if (field instanceof StringFieldDef)
     ((StringFieldDef)field).setSize(size);
    else if (field instanceof BinaryFieldDef)
      ((BinaryFieldDef)field).setSize(size);
    else if (field instanceof StringKeyReferenceFieldDef)
      ((StringKeyReferenceFieldDef)field).setSize(size);
    else
      throw new SizeApplicationException(field);
  }
}
