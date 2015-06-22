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

import org.melati.poem.generated.ColumnInfoTableBase;

/**
 * A {@link Table} which holds information about all {@link Column}s in 
 * a {@link Database}.
 * If a database does not contain a table called <tt>columninfo</tt> 
 * it will be created.
 *
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ColumnInfoTable</code> object.
 * <p>
 * Description: 
 *   Configuration information about a column in the database. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>ColumnInfo</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> tableinfo </td><td> TableInfo </td><td> The table to which the 
 * field belongs </td></tr> 
 * <tr><td> name </td><td> String </td><td> A code-name for the field 
 * </td></tr> 
 * <tr><td> displayorder </td><td> Integer </td><td> A rank determining where 
 * the field appears in lists </td></tr> 
 * <tr><td> usercreateable </td><td> Boolean </td><td> Whether it makes sense 
 * for the user to initialise the field's value </td></tr> 
 * <tr><td> displaylevel </td><td> DisplayLevel </td><td> A category 
 * determining what granularity of report the field appears in </td></tr> 
 * <tr><td> searchability </td><td> Searchability </td><td> A category 
 * determining what level of searching this field supports </td></tr> 
 * <tr><td> displayorderpriority </td><td> Integer </td><td> If present, the 
 * level at which lists of records are sorted by the field </td></tr> 
 * <tr><td> sortdescending </td><td> Boolean </td><td> Whether when sorting 
 * by this column, the sort order should be reversed </td></tr> 
 * <tr><td> indexed </td><td> Boolean </td><td> Whether the field is indexed 
 * (ignored if the field is marked `unique') </td></tr> 
 * <tr><td> unique </td><td> Boolean </td><td> Whether the field is unique 
 * (implies that it's `indexed') </td></tr> 
 * <tr><td> integrityfix </td><td> StandardIntegrityFix </td><td> How 
 * referential integrity is maintained, what to do when the object referred 
 * to is deleted </td></tr> 
 * </table> 
 * 
 * See  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class ColumnInfoTable<T extends ColumnInfo> extends ColumnInfoTableBase<T> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ColumnInfoTable(Database database, String name,
                         DefinitionSource definitionSource)
      throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

}
