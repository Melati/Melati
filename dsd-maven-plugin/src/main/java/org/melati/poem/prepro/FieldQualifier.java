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
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public abstract class FieldQualifier {

  public abstract void apply(FieldDef field) throws IllegalityException;

  public static FieldQualifier from(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field qualifier>", tokens);
    FieldQualifier it;
    String kind = tokens.sval;
    tokens.nextToken();
    if (kind.equals("indexed"))
      it = new IndexedFieldQualifier(tokens);
    else if (kind.equals("unique"))
      it = new UniqueFieldQualifier(tokens);
    else if (kind.equals("primary"))
      it = new TroidFieldQualifier(tokens);
    else if (kind.equals("nullable"))
      it = new NullableFieldQualifier(tokens);
    else if (kind.equals("size"))
      it = new SizeFieldQualifier(tokens);
    else if (kind.equals("deleted"))
      it = new DeletedFieldQualifier(tokens);
    else if (kind.equals("displayorderpriority"))
      it = new DisplayOrderPriorityFieldQualifier(tokens);
    else if (kind.equals("sortdescending"))
      it = new SortDescendingFieldQualifier(tokens);
    else if (kind.equals("uneditable"))
      it = new UneditableFieldQualifier(tokens);
    else if (kind.equals("uncreateable"))
      it = new UncreateableFieldQualifier(tokens);
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
      it = new CompareOnlyFieldQualifier(tokens);
    else if (kind.equals("width"))
      it = new WidthFieldQualifier(tokens);
    else if (kind.equals("height"))
      it = new HeightFieldQualifier(tokens);
    else if (kind.equals("renderinfo"))
      it = new RenderinfoFieldQualifier(tokens);
    else
      throw new ParsingDSDException("<field qualifier>", tokens);
    return it;
  }
}
