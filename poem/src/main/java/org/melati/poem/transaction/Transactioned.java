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

    System.err.println(this + ".writeLock(" + transaction + ")");

    if (transaction == null)
      throw new WriteCommittedException(this);

    // Block on other writers and readers until there aren't any

    for (;;) {
      Transaction blocker = null;
      synchronized (this) {
	if (touchedBy == transaction) {
	  System.err.println("already " + this + ".touchedBy == " + transaction);
	  break;
	}
	else if (touchedBy != null)
	  blocker = touchedBy;
	else {
	  int othersSeenMask = seenMask & transaction.negMask;
	  if (othersSeenMask == 0) {
	    touchedBy = transaction;
	    System.err.println("calling " + transaction + ".notifyTouched(" + this + ")");
	    transaction.notifyTouched(this);
	    break;
	  }
          else {
	    System.err.println("othersSeenMask is " + othersSeenMask);
	    int m = transactionPool().transactionsMax();
	    int t, mask;
	    for (t = 0, mask = 1;
		 t < m && (othersSeenMask & mask) == 0;
		 ++t, mask <<= 1)
	      ;

	    if (t == m)
	      break;
	    blocker = transactionPool().transaction(t);
	    System.err.println("it's " + (othersSeenMask & mask) + "; " + "transaction(" + t + ") -> " + blocker);
	  }
	}
      }

      System.err.println(this + " blocking " + transaction + " on " + blocker);

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
