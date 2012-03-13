/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 *  Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc At paneris.org>
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.melati.poem.Persistent;
import org.melati.poem.Table;

/**
 * A representation of a CSV file as a POEM Table.
 */
public class CSVTable {

  protected Table table = null;
  protected File data = null;
  protected Hashtable<String,CSVColumn> columns = new Hashtable<String,CSVColumn>();
  protected Vector<CSVColumn> columnsInUploadOrder = new Vector<CSVColumn>();
  protected CSVColumn primaryKey = null;
  protected Vector<CSVRecord> records = new Vector<CSVRecord>();
  protected BufferedReader reader = null;
  protected CSVFileParser parser = null;
  
  /** The line number of the CSV file. */
  private int lineNo;
  
  /** The record number of the CSV file. */
  private int recordNo;


 /**
  * Constructor.
  * 
  * @param table POEM table to load data into
  * @param data CSV file to read from
  */
  public CSVTable(Table table, File data) {
    this.table = table;
    this.data = data;
    try {
      reader = new BufferedReader(new FileReader(this.data));
      parser = new CSVFileParser(this.reader);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Process the first line to define columns. 
   * The first line contains the field names - this needs to be
   * validated against expected values, and the order of
   * the fields established.
   * 
   * @throws IOException
   */
  public void define() throws IOException  {
      parser.nextRecord();
      lineNo = 1;
      recordNo = 0;

      while (parser.recordHasMoreFields()) {
        String key = parser.nextField();
        CSVColumn col = columns.get(key);
        if (col == null)
          throw new CSVParseException(
            "I don't know what to do with the column in " + data.getPath() +
            " called " + key); 
        columnsInUploadOrder.addElement(col);
      }
  }  

 /**
  * Add column definitions to this table.
  */
  public void addColumn(String csvName, String poemName) {
    columns.put(csvName, new CSVColumn(poemName));
  }

 /**
  * Add column definitions, perhaps Primary Keys, to this table.
  */
  public void addColumn(String csvName, String poemName, boolean isPrimaryKey)
      throws CSVPrimaryKeyColumnAlreadySetException {
    if (isPrimaryKey && primaryKey != null)
      throw new CSVPrimaryKeyColumnAlreadySetException(table.getName());
    CSVColumn col = new CSVColumn(poemName, isPrimaryKey);
    columns.put(csvName, col);
    if (isPrimaryKey)
      primaryKey = col;
  }

   /**
    * Add column definitions for foreign keys to this table.
    */
    public void addColumn(String csvName,  
                          String foreignPoemName, CSVTable foreignTable) {
      columns.put(csvName, new CSVColumn(foreignPoemName, foreignTable));
    }


 /**
  * Parse the CSV data file and store the data for saving later.
  * 
  * @param writeOnFly whether to commit each line to db as we go
  * @throws IOException if there is a file system problem
  * @throws CSVParseException if the is a malformed field in the CSV
  * @throws CSVWriteDownException
  * @throws NoPrimaryKeyInCSVTableException
  */
  public void load(boolean writeOnFly) 
      throws IOException, CSVParseException, NoPrimaryKeyInCSVTableException, 
             CSVWriteDownException {

    try {
      define();
      // Suck in all the data
      CSVRecord record;
      while(null != (record = parseRecord())) {
        record.setLineNo(lineNo++);
        record.setRecordNo(recordNo++);
        if (writeOnFly)
          record.makePersistent();
        else 
          records.addElement(record);
      }

    }
    catch (IllegalArgumentException e) {
       throw new CSVParseException("Failed to load field in " +
                                    data.getPath() + 
                                    " line " + lineNo +
                                    ": " + e.toString());
    }
    catch (NoSuchElementException f) {
       throw new CSVParseException("Failed to read column header in " +
                                    data.getPath() + 
                                    " line " + lineNo +
                                    ": " + f.toString());
    }
    finally {
      reader.close();
    }
  }


 /**
  * Reads the file until is has seen an object's-worth of
  * field values (ie until it sees an EOF or a line starting
  * with '$') which it returns in a hashtable (null if there are
  * no field values).
   * @return a new CSVRecord
   * @throws IOException if there is a problem with the file system
   * @throws CSVParseException if there is a problem parsing the input
   */
  public CSVRecord parseRecord() throws IOException, CSVParseException {
    if (!parser.nextRecord())
      return null;

    int i = 0;
    String value = null;
    try {
      CSVRecord record = new CSVRecord(table);
      for (; i < columnsInUploadOrder.size(); i++) {
        value = parser.nextField();
        CSVColumn col = (CSVColumn)columnsInUploadOrder.elementAt(i);
        record.addField(new CSVField(col, value));
      }
      record.setLineNo(parser.getLineNo());
      return record;
    }
    catch (IllegalArgumentException e) {
       throw new CSVParseException("Failed to read data field no. " + 
                                    (i+1) + 
                                    " in " +
                                    data + 
                                    " line " + lineNo +
                                    ": " + e.toString());
    }
    catch (NoSuchElementException f) {
      String message = "Problem with data field no. " + (i+1) +
      " of " + columnsInUploadOrder.size() +
      " in " + data +
      " line " + lineNo;
      
      if (value == null) {
        message += " (Check last line of file) : " + 
                    f.toString();
      } else {
        message += ", Value:" + value + ": " + f.toString();
      }
      throw new CSVParseException(message);
    }
  }


 /**
  * Delete all Persistents from the Poem table.
  */
  public void emptyTable() {
    Enumeration<Persistent> rows = table.selection();
    while(rows.hasMoreElements()) {
      Persistent p = (Persistent)rows.nextElement();
      p.delete();
    }
  }

 /**
  * Write the records to the database, 
  * called if we are not writing each record to db as we go.
  */
  public void writeRecords() 
      throws NoPrimaryKeyInCSVTableException,  CSVWriteDownException {
    for (int i = 0; i < records.size(); i++) {
      ((CSVRecord)records.elementAt(i)).makePersistent();
    }
  }
  

  /**
   * Lookup the Persistent corresponding to the CSV record
   * with the given value for the CSV table's primary key.
   * @throws NoPrimaryKeyInCSVTableException
   * @throws CSVWriteDownException
   */
  protected Persistent getRecordWithID(String csvValue)
      throws NoPrimaryKeyInCSVTableException, CSVWriteDownException {
    if (primaryKey == null)
      throw new NoPrimaryKeyInCSVTableException(table.getName(), csvValue);

    for (int i = 0; i < records.size(); i++) {
      CSVRecord record = (CSVRecord)records.elementAt(i);
      if (record.primaryKeyValue != null &&
          record.primaryKeyValue.equals(csvValue))
        return record.getPersistent();
    }
    return null;
  }

 /**
  * Return a string reporting on the data added to this table.
  */
  public void report(boolean recordDetails, boolean fieldDetails,
                       Writer output) throws IOException {

    output.write("*** TABLE: " + table.getName().toUpperCase() + " **\n\n");
    output.write("** I have read " + records.size() + " records of " +
                   columnsInUploadOrder.size() + " fields\n");

    if (recordDetails) {
      for (int i = 0; i < records.size(); i++) {
        CSVRecord record = (CSVRecord)records.elementAt(i);
        output.write("   Record: CSV primary key = " +
                        record.primaryKeyValue);

        if (record.poemRecord == null)
          output.write(", No Poem Persistent written\n");
        else
          output.write(", Poem Troid = " + record.poemRecord.getTroid() + "\n");

        if (fieldDetails) {
          for (int j = 0; j < record.getFields().size(); j++) {
            CSVField field = (CSVField)record.getFields().elementAt(j);
            output.write(field.column + "=\"" + field.value);
            if (j < record.getFields().size()-1)
              output.write("\",");
            else
              output.write("\"\n");
          }
        }
      }
    }
    output.write("** Currently " + table.count(null) +
                  " Persistents in this table\n\n");
  }

  /**
   * Used in debugging to display name of table being emptied.
   * 
   * @return the POEM Table's name
   */
  public String getName() {
    return table.getName();
  }
}

