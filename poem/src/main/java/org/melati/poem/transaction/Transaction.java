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
