package org.melati.poem;

import org.melati.util.*;

class SessionToken {
  Thread thread;
  PoemSession session;
  AccessToken accessToken;

  SessionToken(Thread thread, PoemSession session, AccessToken accessToken) {
    this.thread = thread;
    this.session = session;
    this.accessToken = accessToken;
  }

  void invalidate() {
    thread = null;
    session = null;
    accessToken = null;
  }
}
