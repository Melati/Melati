/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
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

package org.melati.util;

import java.util.*;
import org.melati.poem.*;

public abstract class Transaction {

  public final int index;
  public final int mask;
  public final int negMask;

  private Transaction blockedOn = null;

  // The transactions that are directly waiting on us

  private Vector blockees = new Vector();

  // The transitive closure of the transactions we are waiting on

  private int blockedOnMask;

  private int seenCapacityMin = 50;
  private int seenCapacityMax = 1000;
  private Vector seen = new Vector(seenCapacityMin);

  private int touchedCapacityMin = 50;
  private int touchedCapacityMax = 1000;
  private Vector touched = new Vector();

  public Transaction(int index) {
    if (index > 30)
      throw new TransactionIndexTooLargeException();

    this.index = index;
    mask = 1 << index;
    negMask = ~mask;
  }

  protected abstract void backingCommit();
  protected abstract void backingRollback();

  synchronized void block(Transaction blockee) {
    // System.err.println("*** " + this + ".block(" + blockee + ")");

    blockees.addElement(blockee);
    blockee.blockedOn = this;
    blockee.propagateBlockage();
    try {
      wait();
    }
    catch (InterruptedException e) {
      throw new UnexpectedExceptionPoemException(e);
    }
    finally {
      blockees.removeElement(blockee);
      blockee.blockedOn = null;
      blockee.propagateBlockage();
    }
  }

  private synchronized void propagateBlockage() {
    if (blockedOn == null)
      blockedOnMask = mask;
    else {
      if ((blockedOn.blockedOnMask & mask) != 0)
	throw new WouldDeadlockException();
      blockedOnMask = blockedOn.blockedOnMask | mask;
    }

    for (int i = blockees.size() - 1; i >= 0; --i)
      ((Transaction)blockees.elementAt(i)).propagateBlockage();
  }

  final void notifyTouched(Transactioned persistent) {
    touched.addElement(persistent);
  }

  final void notifySeen(Transactioned persistent) {
    seen.addElement(persistent);
  }


  public void writeDown() {
    // System.err.println(this + ".writeDown()");
    synchronized (touched) {
      for (Enumeration p = touched.elements(); p.hasMoreElements();)
        ((Transactioned)p.nextElement()).writeDown(this);
    }
  }

  private void unSee() {
    synchronized (seen) {
      for (Enumeration p = seen.elements(); p.hasMoreElements();)
        ((Transactioned)p.nextElement()).unSee(this);

      if (seen.size() > seenCapacityMax)
        seen = new Vector(seenCapacityMin);
      else
        seen.setSize(0);
    }
  }

  // This doesn't have to be synchronized.

  private void finish(boolean commit) {
    try {
      if (commit) {
	writeDown();
	backingCommit();
      }
      else
	backingRollback();

      for (Enumeration p = touched.elements(); p.hasMoreElements();) {
	Transactioned persistent = (Transactioned)p.nextElement();
	if (commit)
	  persistent.commit(this);
	else
	  persistent.rollback(this);
      }

      if (touched.size() > touchedCapacityMax)
        touched = new Vector(touchedCapacityMin);
      else
        touched.setSize(0);
    }
    finally {
      unSee();

      // notifyAll will wake too many threads if some of them are writers, but
      // this is really the best we can do without using heavy Lock-ish objects

      synchronized (this) {
	// System.err.println(this + ".notifyAll() after commit = " + commit);
	notifyAll();
      }
    }
  }

  public void commit() {
    try {
      finish(true);
    }
    catch (RuntimeException e) {
      try {
	finish(false);
      }
      catch (Exception ignore) {
      }

      throw e;
    }
  }

  public void rollback() {
    finish(false);
  }

  public String toString() {
    return "transaction" + index;
  }
}
