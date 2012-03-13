/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Chris Kimpton
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
 *     Chris Kimpton (kimtoc@techie.com)
 *
 */
package org.melati.poem.odmg;

import java.util.Map;
import java.util.HashMap;

import org.melati.LogicalDatabase;
import org.melati.util.DatabaseInitException;

/** POEM implementation of the ODMG Database API. **/
final class Database implements org.odmg.Database {

  /** Hide this from general use. **/
  private Database() {}

  /** Provide a package factory method 
      so we could return a different type if needed. **/
  static Database getNewDatabase() { 
    return new Database(); 
  }

  private org.melati.poem.Database _poemDB = null;
  private String _logicalDB = null;

  org.melati.poem.Database getPoemDatabase() 
    throws org.odmg.ODMGRuntimeException { 
    if (_poemDB == null) throw new org.odmg.DatabaseClosedException(
          "org.melati.poem.odmg.Database::getPoemDatabase - POEM DB not set");
    return _poemDB; 
  }

 /** Opens a connection to the db
  * @param openParameters the Poem logical db name
  */
  public void open(String openParameters, int openType) 
    throws org.odmg.ODMGException {
    _logicalDB = openParameters;
    try {
      _poemDB = LogicalDatabase.getDatabase(_logicalDB);
      _cachedTables = new HashMap<String,PoemTableAsDCollection<?>>();
    } catch (DatabaseInitException err) {
      err.printStackTrace();
      throw new org.odmg.ODMGException(err.getMessage());
    }
  }

  public void close() { 
    _poemDB.disconnect();
    _poemDB = null;
    _cachedTables = null;
    ODMGFactory.resetDb();
  }

  private Map<String,PoemTableAsDCollection<?>> _cachedTables = null;

 /** 
  * Retrieves a collection wrapper for the selected table.
  *
  * @param objectIdentifier the name of the table for which a collection 
  *                         is required
  **/
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Object lookup(String objectIdentifier) 
    throws org.odmg.ObjectNameNotFoundException { 
    if (_poemDB == null) throw new org.odmg.DatabaseClosedException();
    PoemTableAsDCollection<?> theObject = _cachedTables.get(objectIdentifier);
    if (theObject == null) {
      theObject = new PoemTableAsDCollection(_poemDB.
                                               getTable(objectIdentifier));
      _cachedTables.put(objectIdentifier,theObject);
    }
    return theObject;
  }

  /** Not supported with the Poem / ODMG wrapper **/
  public void makePersistent(Object objectToBePersistent) { 
    throw new org.odmg.NotImplementedException(); 
  }
  /** Not supported with the Poem / ODMG wrapper **/
  public void deletePersistent(Object persistedObject) { 
    throw new org.odmg.NotImplementedException(); 
  }
  /** Not supported with the Poem / ODMG wrapper **/
  public void bind(Object objectToBePersistent, String objectIdentifier) { 
    throw new org.odmg.NotImplementedException(); 
  }
  /** Not supported with the Poem / ODMG wrapper **/
  public void unbind(String objectIdentifier) { 
    throw new org.odmg.NotImplementedException(); 
  }
   
}

