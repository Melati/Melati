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

import org.melati.poem.generated.TableInfoBase;

/**
 * All the data defining a {@link Table}; actually a {@link Persistent} 
 * from the {@link TableInfoTable}.
 * 
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>TableInfo</code> object.
 * 
 * <p> 
 * Description: 
 *   Configuration information about a table in the database. 
 * </p>
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>TableInfo</code>
 * </caption>
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
 * @generator org.melati.poem.prepro.TableDef#generateMainJava 
 */

public class TableInfo extends TableInfoBase {

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>TableInfo</code> object.
  * <p>
  * Description: 
  *   Configuration information about a table in the database. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public TableInfo() { }

  // programmer's domain-specific code here

  private Table<?> _actualTable = null;

  /**
   * Constructor creates a {@link TableInfo} from a {@link Table}.
   * Note This must be overridden if you extend TableInfo in your DSD.
   * 
   * @param table from which to get metadata
   */
  public TableInfo(Table<?> table) {
    setName_unsafe(table.getName());
    setDisplayname_unsafe(table.defaultDisplayName());
    setDisplayorder_unsafe(new Integer(table.defaultDisplayOrder()));
    setDescription_unsafe(table.defaultDescription());
    setCachelimit_unsafe(table.defaultCacheLimit());
    setSeqcached_unsafe(table.defaultRememberAllTroids() ? 
                            Boolean.TRUE : Boolean.FALSE);
    setCategory_unsafe(table.getDatabase().getTableCategoryTable().
                           ensure(table.defaultCategory()).troid());
  }
  
  /**
   * Get the {@link Table} this is about.
   * 
   * @return The table this object represents.
   */
  public Table<?> actualTable() {
    if (_actualTable == null && troid() != null)
      _actualTable = getDatabase().tableWithTableInfoID(troid().intValue());
    return _actualTable;
  }

 /**
  * Allow this object to be read by anyone.
  * 
  * @param token any {@link AccessToken}
  */
  public void assertCanRead(AccessToken token) {}

  /**
   * Overridden to disallow table renaming.
   * 
   * @see org.melati.poem.generated.TableInfoBase#setName(java.lang.String)
   */
  public void setName(String name) {
    String current = getName();
    if (current != null && !current.equals(name))
      throw new TableRenamePoemException(name);
    super.setName(name);
  }

  /**
   * Set here and in table we represent. 
   * 
   * @see org.melati.poem.generated.TableInfoBase#setSeqcached(java.lang.Boolean)
   */
  public void setSeqcached(Boolean b) throws AccessPoemException {
    super.setSeqcached(b);
    if (actualTable() != null) 
      actualTable().rememberAllTroids(b.booleanValue());
  }

  /**
   * Set here and in table we represent. 
   * 
   * @see org.melati.poem.generated.TableInfoBase#setCachelimit(java.lang.Integer)
   */
  public void setCachelimit(Integer limit) throws AccessPoemException {
    super.setCachelimit(limit);
    if (actualTable() != null) 
      actualTable().setCacheLimit(limit);
  }

}
