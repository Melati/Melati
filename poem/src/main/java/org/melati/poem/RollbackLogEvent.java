package org.melati.poem;

import org.melati.util.*;

public class RollbackLogEvent extends PoemLogEvent {

  public PoemTransaction transaction;

  public RollbackLogEvent(PoemTransaction transaction) {
    this.transaction = transaction;
  }

  public String toString() {
    return "Cancelled transaction " + transaction.index;
  }
}
