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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.util.Hashtable;
import java.util.Enumeration;
import org.melati.poem.util.ArrayEnumeration;

/**
 * A set of {@link Field}s accessible in sequence or looked up
 * using an encoding of the table name and column name.
 */
public class FieldSet {

  private Hashtable<String, Integer> table_columnMap;
  private Field[] fields;

  /**
   * Constructor.
   * @param table_columnMap a Hashtable keyed on tablename underscore fieldname containing indices to fields
   * @param fields an array of fields
   */
  public FieldSet(Hashtable<String, Integer> table_columnMap, Field[] fields) {
    this.table_columnMap = table_columnMap;
    this.fields = fields;
  }

  /**
   * @return an Enumeration of the fields
   */
  public Enumeration<Field> elements() {
    return new ArrayEnumeration<Field>(fields);
  }

  /**
   * Return a specified field.
   *
   * @param name The table name and column name separated by "_".
   * @return a Field with given name or null
   * @see TailoredResultSetEnumeration#mapped(java.sql.ResultSet)
   */
  public Field get(String name) {
    Integer f = (Integer)table_columnMap.get(name);
    return f == null ? null : fields[f.intValue()];
  }
  
  /**
   * Used to debug tests.
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    if (fields != null) {
      String result = "";
        for (int i = 0; i< fields.length; i++) {
          result += fields[i].getName();
          result += "=\"";
          result += fields[i].getCooked();
          result += "\"";
          result += "\n";
        }
      return result;
    } else {
      return null;
    }
    
  }
}
