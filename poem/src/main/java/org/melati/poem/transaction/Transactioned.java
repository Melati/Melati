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
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use WebMacro on non-GPL terms,
 * contact me!
 */

package org.melati.util;

public abstract class Transactioned {

  private boolean valid = true;
  private int seenMask = 0;
  private Transaction touchedBy = null;
  private TransactionPool transactionPool = null;

  public Transactioned(TransactionPool transactionPool) {
    this.transactionPool = transactionPool;
  }

  public Transactioned() {
    this(null);
  }

  protected abstract void load(Transaction transaction);
  protected abstract boolean upToDate(Transaction transaction);
  protected abstract void writeDown(Transaction transaction);

  protected synchronized void reset() {
    valid = true;
    seenMask = 0;
    touchedBy = null;
  }

  protected final TransactionPool transactionPool() {
    return transactionPool;
  }

  protected synchronized void setTransactionPool(
      TransactionPool transactionPool) {
    if (transactionPool == null)
      throw new NullPointerException();
    if (this.transactionPool != null && this.transactionPool != transactionPool)
      throw new IllegalArgumentException();

    this.transactionPool = transactionPool;
  }

  // We don't synchronize this; under the one-thread-per-transaction parity it
  // can't happen, and at worst it means loading twice sometimes

  private void ensureValid(Transaction transaction) {
    if (!valid) {
      if (transaction == null)
	transaction = touchedBy;

      if (!upToDate(transaction))
	load(transaction);

      valid = true;
    }
  }

  protected void readLock(Transaction transaction) {

    if (transaction != null) {
      // Block on writers until there aren't any

      for (;;) {
	Transaction blocker;
	synchronized (this) {
	  if (touchedBy != null && touchedBy != transaction)
	    blocker = touchedBy;
	  else {
	    if ((seenMask & transaction.mask) == 0) {
	      seenMask |= transaction.mask;
	      transaction.notifySeen(this);
	    }

	    break;
	  }
	}

	blocker.block(transaction);
      }
    }

    ensureValid(transaction);
  }

  protected void writeLock(Transaction transaction) {

    // System.err.println(this + ".writeLock(" + transaction + ")");

    if (transaction == null)
      throw new WriteCommittedException(this);

    // Block on other writers and readers until there aren't any

    for (;;) {
      Transaction blocker = null;
      synchronized (this) {
	if (touchedBy == transaction) {
	  // System.err.println("already " + this + ".touchedBy == " + transaction);
	  break;
	}
	else if (touchedBy != null)
	  blocker = touchedBy;
	else {
	  int othersSeenMask = seenMask & transaction.negMask;
	  if (othersSeenMask == 0) {
	    touchedBy = transaction;
	    // System.err.println("calling " + transaction + ".notifyTouched(" + this + ")");
	    transaction.notifyTouched(this);
	    break;
	  }
          else {
	    // System.err.println("othersSeenMask is " + othersSeenMask);
	    int m = transactionPool().transactionsMax();
	    int t, mask;
	    for (t = 0, mask = 1;
		 t < m && (othersSeenMask & mask) == 0;
		 ++t, mask <<= 1)
	      ;

	    if (t == m)
	      break;
	    blocker = transactionPool().transaction(t);
	    // System.err.println("it's " + (othersSeenMask & mask) + "; " + "transaction(" + t + ") -> " + blocker);
	  }
	}
      }

      // System.err.println(this + " blocking " + transaction + " on " + blocker);

      blocker.block(transaction);
    }

    ensureValid(transaction);
  }

  protected synchronized void commit(Transaction transaction) {
    if (touchedBy != transaction)
      throw new CrossTransactionCommitException();
    touchedBy = null;
  }

  protected synchronized void rollback(Transaction transaction) {
    if (touchedBy != transaction)
      throw new CrossTransactionCommitException();
    touchedBy = null;
    valid = false;
  }

  public synchronized void invalidate() {
    valid = false;
  }

  public synchronized void markValid() {
    valid = true;
  }

  protected synchronized void unSee(Transaction transaction) {
    seenMask &= transaction.negMask;
  }
}
