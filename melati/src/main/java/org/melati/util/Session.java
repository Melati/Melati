package org.melati.util;

import java.sql.*;
import java.util.*;

public abstract class Session {
  private int index;
  private int mask;
  private int negMask;

  private int seenCapacityMin = 50;
  private int seenCapacityMax = 1000;
  private Vector seen = new Vector(seenCapacityMin);

  private int touchedCapacityMin = 50;
  private int touchedCapacityMax = 1000;
  private Vector touched = new Vector();

  public Session(int index) {
    if (index > 30)
      throw new SessionIndexTooLargeException();

    this.index = index;
    mask = 1 << index;
    negMask = ~mask;
  }

  public final int index() {
    return index;
  }

  final int mask() {
    return mask;
  }

  final int negMask() {
    return negMask;
  }

  final void notifyTouched(CachedVersionedObject vo) {
    touched.addElement(vo);
  }

  final void notifySeen(CachedVersionedObject vo) {
    seen.addElement(vo);
  }

  public void writeDown() {
    synchronized (touched) {
      for (Enumeration vo = touched.elements(); vo.hasMoreElements();)
        ((CachedVersionedObject)vo.nextElement()).writeDown(this);
    }
  }

  private void unSee() {
    synchronized (seen) {
      for (Enumeration vo = seen.elements(); vo.hasMoreElements();)
        ((CachedVersionedObject)vo.nextElement()).unSee(this);

      if (seen.size() > seenCapacityMax)
        seen = new Vector(seenCapacityMin);
      else
        seen.setSize(0);
    }
  }

  protected abstract void backingCommit();

  // This doesn't have to be synchronized.

  public void commit() {
    try {
      writeDown();
      backingCommit();

      for (Enumeration vo = touched.elements(); vo.hasMoreElements();)
        ((CachedVersionedObject)vo.nextElement()).commit(this);

      if (touched.size() > touchedCapacityMax)
        touched = new Vector(touchedCapacityMin);
      else
        touched.setSize(0);
    }
    finally {
      unSee();
    }
  }

  protected abstract void backingRollback();

  public void rollback() {
    try {
      backingRollback();
    }
    finally {
      unSee();
    }
  }
}
