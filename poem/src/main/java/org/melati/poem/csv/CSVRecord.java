/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc@paneris.org>
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

package org.melati.poem.csv;

import java.util.*;
import org.melati.poem.*;

public class CSVRecord extends Vector {

  /* The value of the primary key of this record, from the csv file */
  String primaryKeyValue = null;

  /* The table this record wants to be written to */
  Table table = null;

  /* The Poem Persistent corresponding to writing this record to the db */
  Persistent poemRecord = null;


  /**
   * Constructor
   */

  public CSVRecord(Table table) {
    super();
    this.table = table;
  }


  /**
   * Add a field to this record
   */

  public synchronized void addField(CSVField field) {
    if (field.column.isPrimaryKey)
      primaryKeyValue = field.value;
    addElement(field);
  }


  /**
   * Write the data in this record into a new Persistent
   */

  private void createPersistent() throws NoPrimaryKeyInCSVTableException {
    if (poemRecord != null)
      return;
    Persistent newObj = table.newPersistent();

    for(int j = 0; j < size(); j++) {
      CSVColumn col = ((CSVField)elementAt(j)).column;
      String cvsValue = ((CSVField)elementAt(j)).value;

      if (col.foreignTable == null) {
        if (col.poemName != null)
          newObj.setRawString(col.poemName, cvsValue);
      }
      // Lookup up value in the foreign Table
      else {
        Persistent lookup = col.foreignTable.getRecordWithID(cvsValue);
        newObj.setCooked(col.poemName, lookup);
      }
    }
    newObj.makePersistent();
    poemRecord = newObj;
  }


  /**
   * Retreive the Persistent corresponding to this CSVRecord, if there
   * is one
   */

  Persistent getPersistent() throws NoPrimaryKeyInCSVTableException {
    if (poemRecord != null)
      return poemRecord;
    createPersistent();
    return poemRecord;
  }


  /**
   * Make sure this record is written to the database
   */
  void makePersistent() throws NoPrimaryKeyInCSVTableException {
    getPersistent();
  }

}


