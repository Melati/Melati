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
 * A <tt>FieldQualifier</tt> which defines the type of 
 * <tt>IntegrityFix</tt> to use with a reference field.
 *
 */
public class IntegrityfixFieldQualifier extends FieldQualifier {

  private String integrityfix;

 /**
  * Constructor.
  *
  * @param tokens the <code>StreamTokenizer</code> to get tokens from
  *
  * @throws ParsingDSDException if an unexpected token is encountered
  * @throws IOException if something goes wrong with the file system
  */
  public IntegrityfixFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    tokens.nextToken();
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<integrity fix name>", tokens);
    integrityfix = validIntegrityFixNamed(tokens.sval);

    tokens.nextToken();
  }

 /**
  * Check fix name.
  * @param sval fix name
  * @return validated fix name
  */
  private String validIntegrityFixNamed(String sval) {
    if (sval.equals("delete") ||  
        sval.equals("clear")  ||
        sval.equals("prevent"))
      return sval;
    else 
      throw new ParsingDSDException("<integrity fix name>: one of delete, clear or prevent", sval);
  }

 /**
  * Thrown when an attempt is made to apply an <code>IntegrityFix</code>
  * to a field which is not a <code>ReferencePoemType</code>.
  */
  public static class ApplicationException extends IllegalityException {
    private static final long serialVersionUID = 1L;

    ApplicationException(FieldDef field) {
      this.field = field;
      this.lineNumber = field.lineNumber;
      this.message = "The column " + field + " cannot have an `integrityfix' " +
      "because that only applies to references";
    }
  }

 /**
  * Update the model.
  *
  * @param field the {@link FieldDef} to update
  *
  * @throws IllegalityException if there is a semantic contractiction
  */
  public void apply(FieldDef field) throws IllegalityException {
    if (field instanceof ReferenceFieldDef)
      ((ReferenceFieldDef)field).setIntegrityFix(integrityfix);
    else if (field instanceof StringKeyReferenceFieldDef)
      ((StringKeyReferenceFieldDef)field).setIntegrityFix(integrityfix);
    else
      throw new ApplicationException(field);
  }
}
