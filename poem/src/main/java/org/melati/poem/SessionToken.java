package org.melati.poem;

import org.melati.util.*;

class SessionToken {
  Thread thread;
  PoemTransaction transaction;
  AccessToken accessToken;

  SessionToken(Thread thread, PoemTransaction transaction, AccessToken accessToken) {
    this.thread = thread;
    this.transaction = transaction;
    this.accessToken = accessToken;
  }

  void invalidate() {
    thread = null;
    transaction = null;
    accessToken = null;
  }
}
