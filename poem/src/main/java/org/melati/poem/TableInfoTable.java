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

import org.melati.poem.generated.TableInfoTableBase;

/**
 * A {@link Table} which holds information about all {@link Table}s in 
 * the Database.
 *
 * If a database does not contain a table called <tt>tableinfo</tt> 
 * it will be created during the unification phase of startup.
 * see Database#unifyWithDB
 * 
 * Note that when overriding you need to override defaultTableInfoFor.
 * 
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>TableInfoTable</code> object.
 * <p>
 * Description: 
 *   Configuration information about a table in the database. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>TableInfo</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> name </td><td> String </td><td> A code-name for the table 
 * </td></tr> 
 * <tr><td> displayname </td><td> String </td><td> A user-friendly name for 
 * the table </td></tr> 
 * <tr><td> description </td><td> String </td><td> A brief description of the 
 * table's function </td></tr> 
 * <tr><td> displayorder </td><td> Integer </td><td> A rank determining where 
 * the table appears in the list of all tables </td></tr> 
 * <tr><td> defaultcanread </td><td> Capability </td><td> The capability 
 * required, by default, for reading the table's records </td></tr> 
 * <tr><td> defaultcanwrite </td><td> Capability </td><td> The capability 
 * required, by default, for updating the table's records </td></tr> 
 * <tr><td> defaultcandelete </td><td> Capability </td><td> The capability 
 * required, by default, for deleting the table's records </td></tr> 
 * <tr><td> cancreate </td><td> Capability </td><td> The capability required, 
 * by default, for creating records in the table </td></tr> 
 * <tr><td> cachelimit </td><td> Integer </td><td> The maximum number of 
 * records from the table to keep in the cache </td></tr> 
 * <tr><td> seqcached </td><td> Boolean </td><td> Whether the display 
 * sequence for the table's records is cached </td></tr> 
 * <tr><td> category </td><td> TableCategory </td><td> Which category the 
 * table falls into </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */

public class TableInfoTable extends TableInfoTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public TableInfoTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here


  /**
   * Create a {@link TableInfo} of the correct type for our DB.
   * NOTE you should override this if you extend TableInfo in your own db.
   * 
   * @param table the Table to get metadata from.
   * @return a new TableInfo Persistent
   */
  protected TableInfo defaultTableInfoFor(Table table) {
    return new TableInfo((JdbcTable)table);
  }
  
}
