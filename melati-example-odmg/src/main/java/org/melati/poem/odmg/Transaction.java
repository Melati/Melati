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

/**
 * An ODMG Transaction.
 */

final class Transaction implements org.odmg.Transaction {

  private Database _db = null;

  static Transaction getNewTransaction(org.odmg.Database db) { 
    return new Transaction(db); 
  }
  private Transaction(org.odmg.Database db) {
    _db = (Database)db;
  }

  //private org.melati.util.Transaction _tx = null;
  private Object _tx = null;

  public void begin() 
    throws org.odmg.ODMGRuntimeException {
    if (_tx != null) throw new org.odmg.TransactionInProgressException();

    try { 
      _tx = new Object();
      // any issues with always using 1?
      _db.getPoemDatabase().beginSession(org.melati.poem.AccessToken.root);  
    } catch (org.odmg.ODMGRuntimeException exc) { 
      throw new org.odmg.ODMGRuntimeException(exc.getMessage());
    }
  }

  public void commit() { 
    if (_tx == null) throw new org.odmg.TransactionNotInProgressException();

    try { 
         _db.getPoemDatabase().endSession();
         _tx = null;
    } catch (org.odmg.ODMGRuntimeException exc) { 
      throw new org.odmg.ODMGRuntimeException(exc.getMessage());
    }
  }
  
  public void join() {
    throw new org.odmg.NotImplementedException(); 
  }

  public void abort() {
    throw new org.odmg.NotImplementedException(); 
  }

  public void checkpoint() {
    throw new org.odmg.NotImplementedException(); 
  }

  public void leave() {
    throw new org.odmg.NotImplementedException(); 
  }

  public void lock(Object obj, int code) { 
    throw new org.odmg.NotImplementedException(); 
  }

  public boolean tryLock(Object obj, int code) {
    throw new org.odmg.NotImplementedException(); 
  }

  public boolean isOpen() {
    throw new org.odmg.NotImplementedException(); 
  }
}
