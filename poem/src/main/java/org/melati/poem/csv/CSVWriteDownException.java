/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 *  Copyright (C) 2004 Tim Pizey
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
 *     Tim Pizey <timp@paneris.org>
 *
 *
 */
package org.melati.poem.csv;

/**
 * Thrown when there is an error writing a CSV value to the database.
 * 
 * @author tim.pizey
 *
 */
public class CSVWriteDownException extends Exception {

  String tableName = null;
  int lineNo;
  Exception e;
  
  /**
   * Report an error on a line.
   * 
   * @param tableName name of table missing primary key
   * @param lineNo the line number of the CSV file
   * @param e the provoking exception
   */
  public CSVWriteDownException(String tableName, int lineNo, Exception e) {
    super();
    this.tableName = tableName;
    this.lineNo = lineNo;
    this.e = e;
  }
  

  /* (non-Javadoc)
   * @see java.lang.Throwable#getMessage()
   */
  public String getMessage() {
    return "Table " + tableName + " line " + lineNo + " :" +
    e.toString();
  }

}
