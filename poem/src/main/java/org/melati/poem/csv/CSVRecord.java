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
