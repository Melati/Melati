package org.melati.poem;

import org.melati.util.*;

public class RollbackLogEvent extends PoemLogEvent {

  public PoemSession session;

  public RollbackLogEvent(PoemSession session) {
    this.session = session;
  }

  public String toString() {
    return "Cancelled session " + session.index();
  }
}
