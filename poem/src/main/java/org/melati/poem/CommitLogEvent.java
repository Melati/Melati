package org.melati.poem;

import org.melati.util.*;

public class CommitLogEvent extends PoemLogEvent {

  public PoemTransaction transaction;

  public CommitLogEvent(PoemTransaction transaction) {
    this.transaction = transaction;
  }

  public String toString() {
    return "Committed transaction " + transaction.index;
  }
}
