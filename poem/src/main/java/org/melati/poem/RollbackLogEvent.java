package org.melati.poem;

public class RollbackLogEvent extends PoemLogEvent {

  public Session session;

  public RollbackLogEvent(Session session) {
    this.session = session;
  }

  public String toString() {
    return "Cancelled session " + session.index();
  }
}
