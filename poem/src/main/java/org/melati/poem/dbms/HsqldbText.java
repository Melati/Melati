/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.poem.dbms;

import org.melati.poem.Table;

/**
 * Use CSV tables as backing store.
 * 
 * NOTE 2010/10/24 I could not get this working due to problems with prepaed statements. 
 *
 * @author timp
 * @since 23 Sep 2008
 *
 */
public class HsqldbText extends Hsqldb {
  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.Dbms#canDropColumns()
   */
  @Override
  public boolean canDropColumns(){
    return false;
  }

  /** 
   * The default is to keep everything in memory,
   * this allows for the db to be written to the disk.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.dbms.Dbms#createTableSql()
   * @see org.melati.poem.dbms.AnsiStandard#createTableSql()
   */
  public String createTableTypeQualifierSql(Table<?> table) {
    String tableType;
    if (table == null || table.getDbmsTableType() == null)
      tableType = "TEXT ";
    else
      tableType = table.getDbmsTableType() + " "; 
      
    return tableType;
  }
  /** 
   * {@inheritDoc}
   * @see org.melati.poem.dbms.AnsiStandard#tableInitialisationSql(org.melati.poem.Table)
   */
  @Override
  public String tableInitialisationSql(Table<?> table) {
    String tableInitialisationSql = null;
    if (table.getDbmsTableType() == null) 
      tableInitialisationSql =  "SET TABLE " + getQuotedName(table.getName()) + 
             " SOURCE " + getQuotedName(table.getName() + ".csv;ignore_first=true;all_quoted=true");
    else
      if (table.getDbmsTableType().equals("TEXT"))
        tableInitialisationSql =  "SET TABLE " + getQuotedName(table.getName()) + 
               " SOURCE " + getQuotedName(table.getName() + ".csv;ignore_first=true;all_quoted=true");
    System.err.println("Returning tableInitialisationSql:"+ tableInitialisationSql);
    return tableInitialisationSql;
  }
  
  
}
