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

package org.melati.poem.prepro;

import java.io.StreamTokenizer;
import java.io.IOException;

/**
 * Abstract class from which all <tt>FieldQualifier</TT>s are derived.
 */
public abstract class FieldQualifier {

 /**
  * Update the model.
  *
  * @param field the {@link FieldDef} to update
  *
  * @throws IllegalityException if there is a semantic contractiction
  */
  public abstract void apply(FieldDef field) throws IllegalityException;

 /**
  * Creates the appropriate type of <code>FieldQualifier</code> 
  * from the input stream.
  *
  * @param tokens the <code>StreamTokenizer</code> to get tokens from
  *
  * @throws ParsingDSDException if an unexpected token is encountered
  * @throws IOException if something goes wrong with the file system
  * @return a new <code>FieldQualifier</code> of the appropriate type
  */
  public static FieldQualifier from(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field qualifier>", tokens);
    FieldQualifier it;
    String kind = tokens.sval;
    tokens.nextToken();
    if (kind.equals("indexed"))
      it = new IndexedFieldQualifier();
    else if (kind.equals("unique"))
      it = new UniqueFieldQualifier();
    else if (kind.equals("primary"))
      it = new TroidFieldQualifier();
    else if (kind.equals("nullable"))
      it = new NullableFieldQualifier();
    else if (kind.equals("size"))
      it = new SizeFieldQualifier(tokens);
    else if (kind.equals("deleted"))
      it = new DeletedFieldQualifier();
    else if (kind.equals("displayorderpriority"))
      it = new DisplayOrderPriorityFieldQualifier(tokens);
    else if (kind.equals("sortdescending"))
      it = new SortDescendingFieldQualifier();
    else if (kind.equals("uneditable"))
      it = new UneditableFieldQualifier();
    else if (kind.equals("uncreateable"))
      it = new UncreateableFieldQualifier();
    else if (kind.equals("displayname"))
      it = new DisplayNameFieldQualifier(tokens);
    else if (kind.equals("displayorder"))
      it = new DisplayOrderFieldQualifier(tokens);
    else if (kind.equals("description"))
      it = new DescriptionFieldQualifier(tokens);
    else if (kind.equals("displaylevel"))
      it = new DisplayLevelFieldQualifier(tokens);
    else if (kind.equals("searchability"))
      it = new SearchabilityFieldQualifier(tokens);
    else if (kind.equals("compareonly"))
      it = new CompareOnlyFieldQualifier();
    else if (kind.equals("width"))
      it = new WidthFieldQualifier(tokens);
    else if (kind.equals("height"))
      it = new HeightFieldQualifier(tokens);
    else if (kind.equals("renderinfo"))
      it = new RenderinfoFieldQualifier(tokens);
    else if (kind.equals("integrityfix"))
      it = new IntegrityfixFieldQualifier(tokens);
    else if (kind.equals("precision"))
      it = new PrecisionFieldQualifier(tokens);
    else if (kind.equals("scale"))
      it = new ScaleFieldQualifier(tokens);

    else
      throw new ParsingDSDException("<field qualifier>", tokens);
    return it;
  }
}
