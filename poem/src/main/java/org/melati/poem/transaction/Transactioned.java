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

package org.melati.poem.transaction;

import org.melati.poem.PoemBugPoemException;

/**
 * An object which can have uncommitted state within a {@link Transaction}.
 */
public abstract class Transactioned {

  protected boolean valid = true;

  /** The transactions which have read us */
  private int seenMask = 0;

  /** The transaction which is writing to us */
  private Transaction touchedBy = null;
  private TransactionPool transactionPool = null;

  /**
   * Constructor.
   * @param transactionPool the TransactionPool
   */
  public Transactioned(TransactionPool transactionPool) {
    this.transactionPool = transactionPool;
  }

  /**
   * Constructor.
   */
  public Transactioned() {
    this(null);
  }

  /**
   * Load the transactioned object from its backing store.
   */
  protected abstract void load(Transaction transaction);

  /**
   * Whether this instance is up-to-date.
   * <p>
   * This is a hook to enable subtypes to define under what circumstances
   * an instance needs to be reloaded when it is marked as
   * invalid, however the two known subtypes just return 
   * the inherited valid flag. 
   */
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
    if (this.transactionPool != null && 
        this.transactionPool != transactionPool)
      throw new IllegalArgumentException();

    this.transactionPool = transactionPool;
  }

  /**
   * We don't synchronize this; under the one-thread-per-transaction 
   * parity it can't happen, and at worst it means loading twice sometimes.
   * @param transaction the transaction to check
   */
  private void ensureValid(Transaction transaction) {
    if (!valid) {
      if (transaction == null)
        transaction = touchedBy;

      // NOTE This could be simplified to if(!valid) 
      // but that would remove a useful extension hook.
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

  /**
   * Get a write lock on the given object if we do not already
   * have one.
   * <p>
   * This will block until no other transactions have
   * write locks on the object before claiming the next write
   * lock. Then it will block until none have read locks.
   * <p>
   * Finally it calls {@link #ensureValid(Transaction)}.
   */
  protected void writeLock(Transaction transaction) {

    if (transaction == null)
      throw new WriteCommittedException(this);

    // Block on other writers and readers until there aren't any

    for (;;) {
      Transaction blocker = null;
      synchronized (this) {
        if (touchedBy == transaction)
          // There's a writer, but it's us
          break;

        else if (touchedBy != null)
          // There's a writer, and it's not us
          blocker = touchedBy;

        else {
          int othersSeenMask = seenMask & transaction.negMask;
          if (othersSeenMask == 0) {
            // There are no readers besides us

            touchedBy = transaction;
            transaction.notifyTouched(this);
            break;
          }
          else {
            // There are other readers

            // We block not on the chronologically first reader but on the one
            // with the lowest index, i.e. essentially on an arbitrary
            // one---not perfect, but doing it any other way would be
            // expensive.

            int m = transactionPool().transactionsMax();
            int t, mask;
            for (t = 0, mask = 1;
                 t < m && (othersSeenMask & mask) == 0;
                 ++t, mask <<= 1)
              ;

            if (t == m)
              throw new PoemBugPoemException(
                  "Thought there was a blocking transaction, " +
                  "but didn't find it");

            blocker = transactionPool().transaction(t);
          }
        }
      }

      blocker.block(transaction);
    }

    ensureValid(transaction);
  }

  protected synchronized void commit(Transaction transaction) {
    if (touchedBy != transaction)
      throw new CrossTransactionCommitException(this);
    touchedBy = null;
  }

  protected synchronized void rollback(Transaction transaction) {
    if (touchedBy != transaction)
      throw new CrossTransactionCommitException(this);
    touchedBy = null;
    valid = false;
  }

  /**
   * Mark as invalid.
   */
  public synchronized void invalidate() {
    valid = false;
  }

  /**
   * Mark as valid.
   */
  public synchronized void markValid() {
    valid = true;
  }

  protected synchronized void unSee(Transaction transaction) {
    seenMask &= transaction.negMask;
  }
}
