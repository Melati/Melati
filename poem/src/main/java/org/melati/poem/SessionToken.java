package org.melati.poem;

class SessionToken {
  Thread thread;
  Session session;
  AccessToken accessToken;

  SessionToken(Thread thread, Session session, AccessToken accessToken) {
    this.thread = thread;
    this.session = session;
    this.accessToken = accessToken;
  }
}
