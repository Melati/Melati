package org.melati.poem;

import java.util.*;

public class PoemThread {

  private PoemThread() {}

  private static Vector sessionTokens = new Vector();
  private static Vector freeSessionTokenIndices = new Vector();

  public static final int threadsMax = 100; // must be < Char.MAX_VALUE = 64k

  static Integer allocatedSessionToken(AccessToken accessToken,
                                       Session session) {
    synchronized (freeSessionTokenIndices) {
      Integer index;
      if (freeSessionTokenIndices.size() == 0) {
        int i = sessionTokens.size();
        if (i >= threadsMax)
          throw new TooManyThreadsPoemException();
        sessionTokens.setSize(i + 1);
        index = new Integer(i);
      }
      else {
        index = (Integer)freeSessionTokenIndices.lastElement();
        freeSessionTokenIndices.setSize(freeSessionTokenIndices.size() - 1);
      }

      SessionToken token = new SessionToken(
          Thread.currentThread(), session, accessToken);
      sessionTokens.setElementAt(token, index.intValue());
      Thread.currentThread().setName("" + (char)index.intValue());

      return index;
    }
  }

  static void inSession(Runnable task, AccessToken accessToken,
                        Session session) {
    Integer token = allocatedSessionToken(accessToken, session);
    try {
      task.run();
    }
    finally {
      synchronized (freeSessionTokenIndices) {
        ((SessionToken)sessionTokens.elementAt(token.intValue())).invalidate();
        sessionTokens.setElementAt(null, token.intValue());
        freeSessionTokenIndices.addElement(token);
      }
    }
  }

  static SessionToken sessionToken() {
    try {
      SessionToken context =
          (SessionToken)sessionTokens.elementAt(Thread.currentThread().
                                                getName().charAt(0));
      if (context.thread == Thread.currentThread())
        return context;
      else
        return null;
    }
    catch (Exception e) {
      return null;
    }
  }

  public static Session session() {
    SessionToken context = sessionToken();
    return context == null ? null : context.session;
  }

  public static boolean inSession() {
    return sessionToken() != null;   // FIXME really, what does this mean?
  }

  public static AccessToken accessToken() throws NoAccessTokenPoemException {
    SessionToken context = sessionToken();
    if (context == null || context.accessToken == null)
      throw new NoAccessTokenPoemException();
    return context.accessToken;
  }

  static AccessToken setAccessToken(AccessToken token) {
    SessionToken context = sessionToken();
    if (context == null)
      throw new NotInSessionPoemException();
    AccessToken old = context.accessToken;
    context.accessToken = token;
    return old;
  }

  public static Database database() throws NotInSessionPoemException {
    Session session = session();
    if (session == null)
      throw new NoAccessTokenPoemException();
    return session.getDatabase();
  }
}
