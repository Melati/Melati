package org.melati.poem;

import java.util.*;
import java.io.*;
import org.melati.poem.csv.*;


/**
 * A class to define a sequence of CSVTables and then process
 * them (i.e. parse the files and write the data into the database)
 */
public class CSVFilesProcessor {

  Vector tables = new Vector();
  Database db = null;

  public CSVFilesProcessor(Database db) {
    this.db = db;
  }

  public CSVTable addTable(String tablename, File file) {
    return addTable(db.getTable(tablename), file);
  }

  public CSVTable addTable(Table tab, File file) {
    CSVTable table = new CSVTable(tab, file);
    tables.addElement(table);
    return table;
  }

  /**
   * Load all the data from the files, empty the tables if
   * necessary and then write the new data into the tables
   *
   * Write a report of the progress to the Writer
   */
  public void process(boolean emptyTables,
                      boolean recordDetails,
                      boolean fieldDetails,
                      Writer output)
                            throws IOException, CSVParseException,
                                     NoPrimaryKeyInCSVTableException {

    // Load in data
    for(int i = 0; i < tables.size(); i++)
      ((CSVTable)tables.elementAt(i)).load();

    output.write("Loaded files\n");
    output.write("Trying to get exclusive lock on the database\n");

    db.beginExclusiveLock();

    output.write("Got exclusive lock on the database!!!\n");

    // Delete all records from the tables, if necessary
    if (emptyTables) {
      for(int i = 0; i < tables.size(); i++)
        ((CSVTable)tables.elementAt(i)).emptyTable();
      PoemThread.writeDown();
    }

    output.write("Emptied all tables\n");

    System.err.println("Emptied all tables");

    // We must have loaded in all the data before we
    // try writing records, otherwise Foreign Key lookups
    // won't work
    for(int i = 0; i < tables.size(); i++)
      ((CSVTable)tables.elementAt(i)).writeRecords();

    output.write("Written records\n");

    db.endExclusiveLock();

    output.write("Ended exclusive lock on the database!!!\n");
    output.write("***** REPORT ******\n");

    // Write a report about how many records are in each table
    for(int i = 0; i < tables.size(); i++)
      ((CSVTable)tables.elementAt(i)).
            report(recordDetails, fieldDetails, output);
  
  }

}
