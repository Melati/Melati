/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
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
 *     Tim Joyce <timj@paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.MelatiContext;
import org.melati.Melati;
import org.melati.login.AccessHandler;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemTask;
import org.melati.poem.AccessToken;
import org.melati.DatabaseInitException;

/**
 * Base class to use Poem with Servlets.
 * Simply extend this class, override the doPoemRequest method
 * if you are going to use a template engine, please take a look at TemplateServlet
 */

public abstract class PoemServlet extends ConfigServlet
{
  /**
   * Process the request.
   */
  protected void doConfiguredRequest(final MelatiContext melatiContextIn)
  throws ServletException, IOException {

    /* in a POEM session, it is often useful to use the path info to indicate actions on POEM objects
    here is a default implementation for parsing the pathinfo.

    Pathinfo length        Action
    0                      no logicalDatabase is set, and you will not have a POEM session (unless you override setLogicalDatabase()
    1                      the first argument is considered to be the 'Method' available using melatiContext.getMethod()
    2                      1st - LogicalDatabaseName, 2nd - Method
    3                      1st - LogicalDatabaseName, 2nd - Poem Table, 3rd - Method
    4                      1st - LogicalDatabaseName, 2nd - Poem Table, 3rd - Object troid, 4th - Method
     */

    String logicalDatabase = melatiContextIn.getLogicalDatabaseName();
    String method = melatiContextIn.getMethod();
    String tableName = melatiContextIn.getTableName();
    Integer troid = melatiContextIn.getTroid();
    
    String[] parts = melatiContextIn.getPathInfoParts();
    if (parts.length > 1) {
      logicalDatabase = parts[0];
      if (parts.length == 2) method = parts[1];
      if (parts.length == 3) {
        tableName = parts[1];
        method = parts[2];
      }
      if (parts.length == 4) {
        tableName = parts[1];
        method = parts[3];
        try {
          troid = new Integer(parts[2]);
        } catch (NumberFormatException e) {
          error(melatiContextIn.getResponse(),new PathInfoException(melatiContextIn.getRequest().getPathInfo(),e));
          return;
        }
      }
    }

    melatiContextIn.setMethod(getMethod(melatiContextIn, method));
    final String finalTableName = tableName;
    final Integer finalTroid = troid;
    // set up the melati context with stuff from the pathinfo (or from overridden methods)
    try {
      melatiContextIn.setDatabase(getLogicalDatabase(melatiContextIn, logicalDatabase));
    } catch (DatabaseInitException e) {
      error(melatiContextIn.getResponse(),e);
      return;
    }

    // Set up a POEM session and call the application code

    // dearie me, what a lot of hoops to jump through
    // at the end of the day Java is terribly poorly suited to this kind of
    // lambda idiom :(

    final PoemServlet _this = this;

    melatiContextIn.getDatabase().inSession(
    AccessToken.root, new PoemTask() {
      public void run() {
        try {
          MelatiContext melatiContext =
          melati.getAccessHandler().establishUser(melatiContextIn);
          melatiContext.setTable(getTable(melatiContext, finalTableName));
          melatiContext.setTroid(getTroid(melatiContext, finalTroid));
          try {
            _this.doPoemRequest(melatiContext);
          }
          catch (Exception e) {
            _handleException(melatiContext,e);
          }
        } catch (Exception e) {
          try {
            // we have to log this here, otherwise we loose the stacktrace
            error(melatiContextIn.getResponse(),e);
            throw new TrappedException(e.toString());
          } catch (IOException f) {
            throw new TrappedException(f.toString());
          }
        }
      }
    }
    );
  }

  // default method to handle an exception withut a template engine
  protected void handleException(MelatiContext melatiContext, Exception exception)
  throws Exception {

    if (exception instanceof AccessPoemException) {
      melati.getAccessHandler().handleAccessException(melatiContext,(AccessPoemException)exception);
    }
    else throw exception;
  }

  protected final void _handleException(MelatiContext melatiContext, Exception exception) throws Exception {
    try {
      handleException(melatiContext, exception);
    }
    catch (Exception e) {
      PoemThread.rollback();
      throw e;
    }
  }

  // override this to set your logical database to something other than is provided in pathinfo
  public String getLogicalDatabase(MelatiContext melatiContextIn, String logicalDatabase) {
    return logicalDatabase;
  }

  // override this to set your table name to something other than is provided in pathinfo
  public String getTable(MelatiContext melatiContextIn, String table) {
    return table;
  }

  // override this to always set your triod to something other than is provided in pathinfo
  public Integer getTroid(MelatiContext melatiContextIn, Integer troid) {
    return troid;
  }

  /**
   * Override the method to build up your output
   * @param melatiContext
   * @return String
   */
  protected abstract void doPoemRequest(MelatiContext melatiContext) throws Exception;

}






