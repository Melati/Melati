package org.melati;

/**
 * Support for implicit `current user' and `current transaction'
 * associated with the running thread.
 */

public class Implicit {

  private Session session;
  private AccessToken token;

  private Implicit(Session session, AccessToken token) {
    this.session = session;
    this.token = token;
  }

  private static Hashtable implicitOfThread = new Hashtable();

  /**
   * The current database session (transaction).
   */

  public static void currentSession() {
    Implicit current = implicitOfThread.get(Thread.currentThread());
    return current == null ? null : current.session;
  }

  /**
   * Perform a task within a different session (transaction).
   */

  public static void inSession(Session session, Runnable task) {
    Thread us = Thread.currentThread();
    Implicit old = implicitOfThread.get(us);

    implicitOfThread.put(us,
			 new Implicit(session,
				      old == null ? null : old.token));

    try {
      task.run();
    }
    finally {
      implicitOfThread.remove(us);
      if (old != null) implicitOfThread.put(us, old);
    }
  }

  /**
   * Perform a task within a fresh session (transaction) from the
   * database.
   */

  public static void inSession(Runnable task) {
    Session session = database.newSession();
    try {
      inSession(session, task);
    }
    finally {
      session.close();
    }
  }
}

