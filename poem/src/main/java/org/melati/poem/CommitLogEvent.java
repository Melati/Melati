package org.melati.poem;

import org.melati.util.*;

public class CommitLogEvent extends PoemLogEvent {

  public PoemSession session;

  public CommitLogEvent(PoemSession session) {
    this.session = session;
  }

  public String toString() {
    return "Committed session " + session.index();
  }
}
