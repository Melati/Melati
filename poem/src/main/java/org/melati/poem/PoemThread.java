/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class PoemThread {

  private PoemThread() {}

  private static Vector sessionTokens = new Vector();
  private static Vector freeSessionTokenIndices = new Vector();

  public static final int threadsMax = 100; // must be < Char.MAX_VALUE = 64k

  static Integer allocatedSessionToken(AccessToken accessToken,
                                       PoemTransaction transaction) {
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
          Thread.currentThread(), transaction, accessToken);
      sessionTokens.setElementAt(token, index.intValue());

      return index;
    }
  }

  static void inSession(PoemTask task, AccessToken accessToken,
                        PoemTransaction transaction) throws PoemException {
    Integer token = allocatedSessionToken(accessToken, transaction);
    String oldname = Thread.currentThread().getName();
    Thread.currentThread().setName("" + (char)token.intValue());
    try {
      task.run();
    }
    finally {
      Thread.currentThread().setName(oldname);
      synchronized (freeSessionTokenIndices) {
        ((SessionToken)sessionTokens.elementAt(token.intValue())).close();
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

  public static ToTidyList toTidy() throws NotInSessionPoemException {
    return sessionToken().toTidy();
  }

  public static PoemTransaction transaction() {
    return sessionToken().transaction;
  }

  public static boolean inSession() {
    return _sessionToken() != null;
  }

  /**
   * The access token under which your thread is running.
   */

  public static AccessToken accessToken()
      throws NotInSessionPoemException, NoAccessTokenPoemException {
    AccessToken it = sessionToken().accessToken;
    if (it == null)
      throw new NoAccessTokenPoemException();
    return it;
  }

  /**
   * Change the access token under which your thread is operating.  You can't
   * do this unless the current token is <TT>root</TT>.
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

  public static void withAccessToken(AccessToken token, PoemTask task) {
    SessionToken context = sessionToken();
    AccessToken old = context.accessToken;
    context.accessToken = token;
    try {
      task.run();
    }
    finally {
      context.accessToken = old;
    }
  }

  public static void assertHasCapability(Capability capability)
     throws NotInSessionPoemException, NoAccessTokenPoemException,
            AccessPoemException {
    AccessToken token = accessToken();
    if (!token.givesCapability(capability))
      throw new AccessPoemException(token, capability);
  }

  public static Database database() throws NotInSessionPoemException {
    return transaction().getDatabase();
  }

  public static void writeDown() {
    transaction().writeDown();
  }

  public static void commit() {
    transaction().commit();
  }

  public static void rollback() {
    transaction().rollback();
  }
}
