package org.melati.poem;

public class CommitLogEvent extends PoemLogEvent {

  public Session session;

  public CommitLogEvent(Session session) {
    this.session = session;
  }

  public String toString() {
    return "Committed session " + session.index();
  }
}
