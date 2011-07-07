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
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, UK
 */

package org.melati.poem.prepro;

import java.util.Hashtable;

/**
 * A store of naming information for a <code>Table</code>.
 */
public class TableNamingStore {

  /**
   * Constructor.
   */
  public TableNamingStore() {
    super();
 }

  Hashtable<String,TableNamingInfo> tablesByShortName = new Hashtable<String,TableNamingInfo>();
  Hashtable<String,TableNamingInfo> tablesByFQName = new Hashtable<String,TableNamingInfo>();
  boolean debug = false;

  /**
   * Add a table to the naming store. 
   * 
   * @param packageName fully qualified java package name
   * @param projectNameIn short name of project
   * @param name        the name of this table
   * @param superclass  not null if table extends another
   * @throws HiddenTableException if this table has the same name as 
   *                    one already encountered but does not extend it 
   * @return a newly created <code>TableNamingInfo</code> 
   */
  public TableNamingInfo add(String packageName, String projectNameIn, String name, 
                             String superclass)
      throws HiddenTableException {

    TableNamingInfo info = new TableNamingInfo(packageName, projectNameIn, name);

    // superclass could be FQ (e.g. 'org.melati.poem.User') 
    // or not (e.g. 'User')
    // We make sure it ends up FQ
    if (superclass != null) {
      if (superclass.indexOf('.') == -1) {
        TableNamingInfo sup = (TableNamingInfo)tablesByShortName.
                                                   get(superclass);
        if (sup != null) {
          superclass = sup.tableFQName;
        } else {
          superclass = packageName + "." + superclass;
        }
      }

      TableNamingInfo sup = (TableNamingInfo)tablesByFQName.get(superclass);
      if (sup != null) {
        info.superclass = sup;
      } else {
        String pack = superclass.substring(0, superclass.lastIndexOf("."));
        String nam = superclass.substring(superclass.lastIndexOf(".")+1);
        info.superclass = add(pack, projectNameIn, nam, null);
      }
    }

    tablesByFQName.put(info.tableFQName, info);
    Object old = tablesByShortName.put(info.tableShortName, info);
    if (old != null) {
      if (old != info.superclass) {
        throw new HiddenTableException(name, 
                                       ((TableNamingInfo)old).tableFQName);
      }
      ((TableNamingInfo)old).hidden = true;
      info.hidesOther = true;
    }

    return info;
  }

}
