/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc@paneris.org>
 */
package org.melati.poem.csv;

import java.util.Vector;
import java.io.File;
import java.io.Writer;
import java.io.IOException;

import org.melati.poem.Database;
import org.melati.poem.PoemBugPoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.Table;

/**
 * A class to define a sequence of {@link CSVTable}s and process them by
 * parsing the files and writing the data to the database.
 * 
 * @author MylesC AT paneris.org
 *  
 */
public class CSVFilesProcessor {

  protected Vector<CSVTable> tables = new Vector<CSVTable>();
  Database db = null;

  /**
   * Constructor.
   * 
   * @param db the target database
   */
  public CSVFilesProcessor(Database db) {
    this.db = db;
  }

  /**
   * Convenience method.
   * 
   * @param tablename the name of a POEM table
   * @param file a CSV file, with first line containing field names
   * @return a new CSVTable
   */
  public CSVTable addTable(String tablename, File file) {
    return addTable(db.getTable(tablename), file);
  }

  /**
   * Add a table to this processor.
   * 
   * @param tab a POEM table
   * @param file a CSV file, with first line containing field names
   * @return a new CSVTable
   */
  public CSVTable addTable(Table<?> tab, File file) {
    if (!file.exists())
      throw new RuntimeException("File not found: " + file.getPath());
    CSVTable table = new CSVTable(tab, file);
    tables.addElement(table);
    return table;
  }

  /**
   * Load all the data from the files, empty the tables if necessary and then
   * write the new data into the tables.
   * <p>
   * Write a report of the progress to the Writer.
   * 
   * @param writeOnFly flag whether to write down to db when all files read in
   *          if set then it is the programmers responsibility to ensure that
   *          there are no references to yet to be created fields
   * @param emptyTables flag whether to remove remains from last run
   * @param recordDetails flag passed in to table.report
   * @param fieldDetails flag passed in to table.report
   * @param output to write report to
   * @throws IOException if file stuff goes wrong
   * @throws CSVParseException if csv file has an error
   * @throws NoPrimaryKeyInCSVTableException not thrown
   * @throws CSVWriteDownException thrown when a persistent cannot be created
   */
  public void process(boolean writeOnFly, boolean emptyTables,
      boolean recordDetails, boolean fieldDetails, Writer output)
      throws IOException, CSVParseException, NoPrimaryKeyInCSVTableException,
      CSVWriteDownException {

    output.write("Trying to get exclusive lock on the database\n");
    db.beginExclusiveLock();
    output.write("Got exclusive lock on the database!!!\n");

    // Delete all records from the tables, if necessary
    if (emptyTables) {
      for (int i = 0; i < tables.size(); i++) {
        CSVTable t = (CSVTable) tables.elementAt(i);
        t.emptyTable();
        output.write("Emptied table :" + t.getName() + "\n");
        System.err.println("Emptied table :" + t.getName());
      }
      PoemThread.writeDown();
    }


    // Load in data
    for (int i = 0; i < tables.size(); i++) {
      CSVTable t = ((CSVTable) tables.elementAt(i));
      t.load(writeOnFly);
      output.write("Loaded table :" + t.getName() + "\n");
      System.err.println("Loaded table :" + t.getName());
    }

    // We must have loaded in all the data before we
    // try writing records, otherwise Foreign Key lookups
    // defined in this set of CSVs won't work
    if (!writeOnFly) {
      writeData(output);
      output.write("Written records\n");
    }

    db.endExclusiveLock();

    output.write("Ended exclusive lock on the database!!!\n");
    output.write("***** REPORT ******\n");

    // Write a report about how many records are in each table
    for (int i = 0; i < tables.size(); i++)
      ((CSVTable) tables.elementAt(i)).report(recordDetails, fieldDetails,
          output);

  }
  /**
   * With write on the fly false.
   * <p>
   * Load all the data from the files, empty the tables if necessary and then
   * write the new data into the tables.
   * <p>
   * Write a report of the progress to the Writer.
   * 
   * @param emptyTables flag whether to remove remains from last run
   * @param recordDetails flag passed in to table.report
   * @param fieldDetails flag passed in to table.report
   * @param output to write report to
   * @throws IOException if file stuff goes wrong
   * @throws CSVParseException if csv file has an error
   * @throws NoPrimaryKeyInCSVTableException not thrown
   * @throws CSVWriteDownException thrown when a persistent cannot be created
   */
  public void process(boolean emptyTables, boolean recordDetails,
                      boolean fieldDetails, Writer output) 
      throws IOException,
      CSVParseException, NoPrimaryKeyInCSVTableException, CSVWriteDownException {
    process(false, emptyTables, recordDetails, fieldDetails, output);
  }

  /**
   * @param o output log to write to
   * @throws NoPrimaryKeyInCSVTableException
   * @throws CSVWriteDownException
   */
  protected void writeData(Writer o) throws NoPrimaryKeyInCSVTableException,
      CSVWriteDownException {
    for (int i = 0; i < tables.size(); i++) {
      CSVTable t = (CSVTable) tables.elementAt(i);
      try {
        o.write("Writing " + t.getName() + ".\n");
      } catch (IOException e) {
        throw new PoemBugPoemException("Problem writing log", e);
      }
      t.writeRecords();
    }
  }

}

