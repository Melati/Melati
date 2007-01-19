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

import org.melati.util.ToTidyList;

/**
 * A Session Token.
 * 
 * A Session has an AccessToken, a Time started, a Task, 
 * a Transaction within which the task is running and a Thread which runs the task.
 */
public class SessionToken {
  Thread thread;
  long started;
  PoemTransaction transaction;
  PoemTask task;
  AccessToken accessToken;
  private ToTidyList toTidy = null;

  /**
   * @param thread
   * @param transaction
   * @param accessToken
   * @param task
   */
  SessionToken(Thread thread, PoemTransaction transaction,
               AccessToken accessToken, PoemTask task) {
    this.thread = thread;
    this.started = System.currentTimeMillis();
    this.transaction = transaction;
    this.task        = task;
    this.accessToken = accessToken;
  }

  /**
   * Close this session and its toTidy list.
   */
  void close() {
    thread = null;
    started = 0L;
    transaction = null;
    task = null;
    accessToken = null;
    if (toTidy != null)
      toTidy.close();
  }

  /**
   * @return the list of objects to close when we close
   */
  public synchronized ToTidyList toTidy() {
    if (toTidy == null)
      toTidy = new ToTidyList();
    return toTidy;
  }

  /**
   * @return Returns the time, as a long, started.
   */
  public long getStarted() {
    return started;
  }

  /**
   * @return Returns the task.
   */
  public PoemTask getTask() {
    return task;
  }
 
  /**
   * @return Returns the thread.
   */

  public Thread getThread() {
    return thread;
  }

  /**
   * @return Returns the transaction.
   */
  public PoemTransaction getTransaction() {
    return transaction;
  }
}
