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

  static void inSession(PoemTask task, AccessToken accessToken,
                        Session session) throws PoemException {
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

  static SessionToken _sessionToken() {
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

  static SessionToken sessionToken() throws NotInSessionPoemException {
    SessionToken it = _sessionToken();
    if (it == null)
      throw new NotInSessionPoemException();
    return it;
  }

  public static Session session() {
    return sessionToken().session;
  }

  public static boolean inSession() {
    return _sessionToken() != null;
  }

  public static AccessToken accessToken() throws NoAccessTokenPoemException {
    AccessToken it = sessionToken().accessToken;
    if (it == null)
      throw new NoAccessTokenPoemException();
    return it;
  }

  static AccessToken setAccessToken(AccessToken token) {
    SessionToken context = sessionToken();
    AccessToken old = context.accessToken;
    context.accessToken = token;
    return old;
  }

  public static Database database() throws NotInSessionPoemException {
    return session().getDatabase();
  }

  public static void commit() {
    session().commit();
  }

  public static void rollback() {
    session().rollback();
  }
}
