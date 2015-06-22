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
 *     Myles Chippendale <mylesc At paneris.org>
 */
package org.melati.poem.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * A utility for tokenising a file made up of comma-separated
 * variables.  We allow for fields having returns in them.
 *
 * <PRE>
 *   foo, bar om,,"baz, ,oof",xyz,   -&gt;
 *     "foo", " bar om", "", "baz, , oof", "xyz", ""
 *
 *   foo, "bar
 *   bar
 *   bar", baz -&gt;
 *   "foo", "bar\u0015bar\u0015bar", "baz"
 * </PRE>
 *
 * Each record (which is usually a line, unless some fields have
 * a line break in them) is accessed one at a time by calling
 * <code>nextRecord()</code>. Within each record
 * <code>recordHasMoreFields()</code> and <code>nextField()</code>
 * can be used like an Enumeration to iterate through the fields.
 *
 * @author  mylesc, based heavily on 
 *          orginal CSVStringEnumeration williamc
 */

public class CSVFileParser {

  private BufferedReader reader = null;

  int lineNo = 0;      // The first line will be line '1'
  private String line = "";
  private boolean emptyLastField = false;
  int p = 0;

  /**
   * Constructor.
   * @param reader file reader
   */
  public CSVFileParser(BufferedReader reader) {
    this.reader = reader;
  }

  /**
   * @return whether there is another line 
   */
  public boolean nextRecord() throws IOException {
    return nextLine();
  }

  private boolean nextLine() throws IOException {
    // Not confident about this
    // but we need to return false if we have reached end and closed the file
    if (!reader.ready()) return false;
    line = reader.readLine();
    // This should be false anyway if we're called from nextToken()
    emptyLastField = false;
    p = 0;
    if (line == null) {
      reader.close();
      return false;
    }
    lineNo++;
    return true;
  }

  /**
   * Return the line number.
   * 
   * @return the current lineNo
   */
  public int getLineNo() {
    return lineNo;
  }
  
  /**
   * Are there any more tokens to come?
   * @return whether there are more fields
   */
  public boolean recordHasMoreFields() {
    return emptyLastField || p < line.length();
  }

  /**
   * @return the next token as a String
   */
  public String nextField() throws IOException {
    return nextToken(false);
  }

  /**
   * @return the next token as a String
   */
  private String nextToken(boolean inUnclosedQuotes) throws IOException {

    if (emptyLastField) {
      emptyLastField = false;
      return "";
    }

    if (p >= line.length()) throw new NoSuchElementException();

    if (inUnclosedQuotes || (line.charAt(p) == '"' && (++p>0))) {

      // we need to allow for quotes inside quoted fields, so now test for ",
      int q = line.indexOf("\",", p);
      // if it is not there, we are (hopefully) at the end of a line
      if (q == -1 && (line.indexOf('"', p) == line.length()-1)) 
        q = line.length()-1;
        
      // If we don't find the end quote try reading in more lines
      // since fields can have \n in them
      if (q == -1) {
        String sofar = line.substring(p, line.length());
        if (!nextLine())
          throw new IllegalArgumentException("Unclosed quotes on line "
                                             + lineNo);
        return sofar + "\n" + nextToken(true);
      }

      String it = line.substring(p, q);

      ++q;
      p = q+1;
      if (q < line.length()) {
        if (line.charAt(q) != ',') {
          p = line.length();
          throw new IllegalArgumentException("No comma after quotes on line "
                                            + lineNo);
        }
        else if (q == line.length() - 1)
          emptyLastField = true;
      }
      return it;
    } else {
      int q = line.indexOf(',', p);
      if (q == -1) {
         String it = line.substring(p);
         p = line.length();
         return it;
      } else {
         String it = line.substring(p, q);
         if (q == line.length() - 1)
            emptyLastField = true;
            p = q + 1;
            return it;
      }
    }
  }

  /**
   * Test harness.
   * @param args arguments
   * @throws Exception if anything fails
   */
  public static void main(String[] args) throws Exception {

    System.out.println("***** Reading file " + args[0]);

    BufferedReader reader = new BufferedReader(
                                 new FileReader(new File(args[0])));
    CSVFileParser toks = new CSVFileParser(reader);

    int recordCount = 0;
    while(toks.nextRecord()) {
      System.out.println("*** Record " + ++recordCount);
      int i = 0;
      while (toks.recordHasMoreFields()) {
        System.out.println("Field " + ++i + ":" + toks.nextField());
      }
    }
  }


}
