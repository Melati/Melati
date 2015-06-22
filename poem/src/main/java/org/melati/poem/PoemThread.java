/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;
import org.melati.poem.transaction.ToTidyList;

/**
 * A Poem Thread.
 * 
 */
public final class PoemThread {

  private PoemThread() {
  }

  private static Vector<SessionToken> sessionTokens = new Vector<SessionToken>();

  private static Vector<Integer> freeSessionTokenIndices = new Vector<Integer>();

  /**
   * The maximum number of Threads. Must be &lt; Char.MAX_VALUE = 64k
   */
  public static final int threadsMax = 100;

  static Integer allocatedSessionToken(AccessToken accessToken,
          PoemTransaction transaction, PoemTask task) {
    synchronized (freeSessionTokenIndices) {
      Integer index;
      if (freeSessionTokenIndices.size() == 0) {
        int i = sessionTokens.size();
        if (i >= threadsMax)
          throw new TooManyThreadsPoemException();
        sessionTokens.setSize(i + 1);
        index = new Integer(i);
      } else {
        index = (Integer)freeSessionTokenIndices.lastElement();
        freeSessionTokenIndices.setSize(freeSessionTokenIndices.size() - 1);
      }

      SessionToken token = new SessionToken(Thread.currentThread(),
              transaction, accessToken, task);
      sessionTokens.setElementAt(token, index.intValue());

      return index;
    }
  }

  /** Keep track of the old thread names. */
  private static Map<Integer, String> threadOldNames = new HashMap<Integer, String>();

  /**
   * Do the processing to start a db session.
   * 
   * @param accessToken
   *          The session's token
   * @param transaction
   *          The PoemTransaction to run in
   * @param task
   *          The PoemTask to run
   * @throws PoemException
   *           if we are already in a Session
   */
  static void beginSession(AccessToken accessToken,
          PoemTransaction transaction, PoemTask task) throws PoemException {
    if (inSession())
      throw new AlreadyInSessionPoemException();
    Integer token = allocatedSessionToken(accessToken, transaction, task);
    String oldname = Thread.currentThread().getName();
    Thread.currentThread().setName("" + (char)token.intValue());
    // Save the old thread name for later use
    threadOldNames.put(token, oldname);
  }

  static void beginSession(AccessToken accessToken, PoemTransaction transaction)
          throws PoemException {
    beginSession(accessToken, transaction, null);
  }

  /**
   * End a db session.
   * 
   * @throws PoemException
   *           if we are not in a Session
   */
  static void endSession() throws PoemException {
    char tokenChar = Thread.currentThread().getName().charAt(0);
    Integer token = new Integer(tokenChar);
    String oldname = (String)threadOldNames.get(token);
    if (oldname == null)
      throw new NotInSessionPoemException(Thread.currentThread().getName()
              + " has null old name");

    Thread.currentThread().setName(oldname);
      synchronized (freeSessionTokenIndices) {
        ((SessionToken)sessionTokens.elementAt(token.intValue())).close();
        sessionTokens.setElementAt(null, token.intValue());
        freeSessionTokenIndices.addElement(token);
      }
    }

  /**
   * Perform the specified task in the current thread session.
   * 
   * @throws PoemException
   *           if there is a problem starting or ending the session or if there
   *           is a problem running the task.
   */
  static void inSession(PoemTask task, AccessToken accessToken,
          PoemTransaction transaction) throws PoemException {
    beginSession(accessToken, transaction, task);
    try {
      task.run();
    } finally {
      endSession();
    }
  }

  /**
   * Retrieve the open sessions.
   * 
   * @return a Vector of open {@link SessionToken}s
   */
  public static Vector<SessionToken> openSessions() {
    Vector<SessionToken> open = new Vector<SessionToken>();
    Enumeration<SessionToken> e = null;
    // synchronized(sessionTokens) {
    e = sessionTokens.elements();
    // }
    while (e.hasMoreElements()) {
      SessionToken token = e.nextElement();
      if (token != null)
        open.addElement(token);
    }
    return open;
  }

  static SessionToken _sessionToken() {
    // If we are not in a PoemThread then the name is likely
    // to be "main" or "Thread-1", "Thread-2" etc
    if (Thread.currentThread().getName().length() != 1)
      return null;
    SessionToken context = (SessionToken)sessionTokens.elementAt(Thread
            .currentThread().getName().charAt(0));
    if (context.thread == Thread.currentThread())
      return context;
    else
      return null;
  }

  /**
   * @return the current SessionToken
   * @throws NotInSessionPoemException if there is no current SessionToken
   */
  public static SessionToken sessionToken() throws NotInSessionPoemException {
    SessionToken it = _sessionToken();
    if (it == null)
      throw new NotInSessionPoemException();
    return it;
  }

  /**
   * Retrieve the {@link ToTidyList} for this session.
   * 
   * @return the {@link ToTidyList} for this {@link PoemThread}.
   */
  public static ToTidyList toTidy() throws NotInSessionPoemException {
    return sessionToken().toTidy();
  }

  /**
   * Retrieve the {@link PoemTransaction} for this PoemThread.
   * 
   * @return the {@link PoemTransaction} for this {@link PoemThread}.
   */
  public static PoemTransaction transaction() {
    return sessionToken().transaction;
  }

  /**
   * Whether we are currently in a session.
   * 
   * @return whether we are currently in a session
   */
  public static boolean inSession() {
    return _sessionToken() != null;
  }

  /**
   * @return the access token under which your thread is running.
   * @throws NotInSessionPoemException
   *           if we are not in a session
   * @throws NoAccessTokenPoemException
   *           if we do not have an AccessToken
   */
  public static AccessToken accessToken() throws NotInSessionPoemException,
          NoAccessTokenPoemException {
    AccessToken it = sessionToken().accessToken;
    if (it == null)
      throw new NoAccessTokenPoemException();
    return it;
  }

  /**
   * Change the access token under which your thread is operating. You can't do
   * this unless the current token is <TT>root</TT>.
   * 
   * @see AccessToken#root
   */
  public static void setAccessToken(AccessToken token)
          throws NonRootSetAccessTokenPoemException {
    SessionToken context = sessionToken();
    AccessToken old = context.accessToken;
    if (old != AccessToken.root)
      throw new NonRootSetAccessTokenPoemException(old);
    context.accessToken = token;
  }

  /**
   * Run a {@link PoemTask} under a specified {@link AccessToken}, typically
   * <tt>Root</tt>.
   * 
   * @param token
   *          the token to run with
   * @param task
   *          the task to run
   */
  public static void withAccessToken(AccessToken token, PoemTask task) {
    SessionToken context = sessionToken();
    AccessToken old = context.accessToken;
    context.accessToken = token;
    try {
      task.run();
    } finally {
      context.accessToken = old;
    }
  }

  /**
   * Check that we have the given {@link Capability}, throw an
   * {@link AccessPoemException} if we don't.
   * 
   * @param capability
   *          to check
   */
  public static void assertHasCapability(Capability capability)
          throws NotInSessionPoemException, NoAccessTokenPoemException,
          AccessPoemException {
    AccessToken token = accessToken();
    if (!token.givesCapability(capability))
      throw new AccessPoemException(token, capability);
  }

  /**
   * Retrieve the {@link Database} associated with this thread.
   * 
   * @return the {@link Database} associated with this thread.
   */
  public static Database database() throws NotInSessionPoemException {
    return transaction().getDatabase();
  }

  /**
   * Write to the underlying DBMS.
   * 
   */
  public static void writeDown() {
    transaction().writeDown();
  }

  /**
   * Commit to the underlying DBMS.
   * 
   */
  public static void commit() {
    SessionToken token = sessionToken();
    try {
      token.transaction.commit();
    } finally {
      token.toTidy().close();
    }
  }

  /**
   * Rollback the underlying DBMS.
   * 
   */
  public static void rollback() {
    SessionToken token = sessionToken();
    try {
      token.transaction.rollback();
    } finally {
      token.toTidy().close();
    }
  }
}
