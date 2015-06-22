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

import org.melati.poem.generated.ValueInfoTableBase;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ValueInfoTable</code> object.
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>ValueInfo</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> displayname </td><td> String </td><td> A user-friendly name for 
 * the field </td></tr> 
 * <tr><td> description </td><td> String </td><td> A brief description of the 
 * field's function </td></tr> 
 * <tr><td> usereditable </td><td> Boolean </td><td> Whether it makes sense 
 * for the user to update the field's value </td></tr> 
 * <tr><td> typefactory </td><td> PoemTypeFactory </td><td> The field's 
 * Melati type </td></tr> 
 * <tr><td> nullable </td><td> Boolean </td><td> Whether the field can be 
 * empty </td></tr> 
 * <tr><td> size </td><td> Integer </td><td> For character fields, the 
 * maximum number of characters that can be stored, (-1 for unlimited) 
 * </td></tr> 
 * <tr><td> width </td><td> Integer </td><td> A sensible width for text boxes 
 * used for entering the field, where appropriate </td></tr> 
 * <tr><td> height </td><td> Integer </td><td> A sensible height for text 
 * boxes used for entering the field, where appropriate </td></tr> 
 * <tr><td> precision </td><td> Integer </td><td> Precision (total number of 
 * digits) for fixed-point numbers </td></tr> 
 * <tr><td> scale </td><td> Integer </td><td> Scale (number of digits after 
 * the decimal) for fixed-point numbers </td></tr> 
 * <tr><td> renderinfo </td><td> String </td><td> The name of the Melati 
 * templet (if not the default) to use for input controls for the field 
 * </td></tr> 
 * <tr><td> rangelow_string </td><td> String </td><td> The low end of the 
 * range of permissible values for the field </td></tr> 
 * <tr><td> rangelimit_string </td><td> String </td><td> The (exclusive) 
 * limit of the range of permissible values for the field </td></tr> 
 * </table> 
 * 
 * See  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class ValueInfoTable<T extends ValueInfo> extends ValueInfoTableBase<T> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ValueInfoTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
