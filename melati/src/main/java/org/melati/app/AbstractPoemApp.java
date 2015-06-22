/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2005 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.app;


import java.io.IOException;

import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemTask;
import org.melati.poem.AccessToken;
import org.melati.poem.util.ArrayUtils;
import org.melati.util.ConfigException;
import org.melati.util.MelatiException;
import org.melati.util.UnexpectedExceptionException;

/**
 * Base class to use Poem as an application.
 *
 * <p>
 * Simply extend this class and override the {@link #doPoemRequest} method.
 * If you are going to use a template engine look at {@link AbstractTemplateApp}.
 * </p>
 *
 * <UL>
 * <LI>
 * The command line arguments are expected in the following order:
 * <BLOCKQUOTE><TT>
 * <BR>db
 * <BR>db method
 * <BR>db table method
 * <BR>db table troid  method
 * </TT></BLOCKQUOTE>
 *
 * these components are broken out of the command line arguments and passed to
 * your application code in the {@link Melati} parameter.
 *
 * <TABLE>
 *   <caption>Arguments</caption>
 *   <TR>
 *     <TD><TT><I>db</I></TT></TD>
 *     <TD>
 *       The first argument is taken to be the `logical name'
 *       of the POEM database to which the servlet should connect.  It
 *       is mapped onto JDBC connection details via the config file
 *       <TT>org.melati.LogicalDatabase.properties</TT>, of which there is an
 *       example in the source tree.  This is automatically made available in
 *       templates as <TT>$melati.Database</TT>.
 *     </TD>
 *   <TR>
 *     <TD><TT><I>table</I></TT></TD>
 *     <TD>
 *       The name of a table to work on:
 *       perhaps it is meant to list its contents.  This is automatically
 *       made available in templates as <TT>$melati.Table</TT>.
 *     </TD>
 *   </TR>
 *   <TR>
 *     <TD><TT><I>troid</I></TT></TD>
 *     <TD>
 *       The POEM `troid' (table row identifier, or row-unique integer) of a
 *       row within a <TT><I>table</I></TT>.
 *       This is automatically made
 *       available in templates as <TT>$melati.Object</TT>.
 *     </TD>
 *   </TR>
 *   <TR>
 *     <TD><TT><I>method</I></TT></TD>
 *     <TD>
 *       A freeform string telling your servlet what it is meant to do.  This
 *       is automatically made available in templates as
 *       <TT>$melati.Method</TT>.
 *     </TD>
 *   </TR>
 * </TABLE>
 *
 * <LI>
 * You can change the way these things are determined by overriding
 * {@link #poemContext}.
 * 
 * <LI>
 * Any POEM database operations you perform will be done with the access
 * rights of the POEM <TT>User</TT> associated with the POEM session.  If
 * there is no established session, the current user will be set to
 * the default `guest' user.  If this method terminates with an
 * <TT>AccessPoemException</TT>, indicating that you have attempted something
 * which you aren't entitled to do, the user will be prompted to log in, and
 * the original request will be retried.  The precise mechanism used for
 * login is <A HREF=#loginmechanism>configurable</A>.
 *
 * <LI>
 * No changes made to the database by other concurrently executing threads
 * will be visible to you (in the sense that once you have seen a particular
 * version of a record, you will always subsequently see the same one), and
 * your own changes will not be made permanent until this method completes
 * successfully or you perform an explicit <TT>PoemThread.commit()</TT>.  If
 * it terminates with an exception or you issue a
 * <TT>PoemThread.rollback()</TT>, your changes will be lost.
 *
 * <LI>
 * <A NAME=loginmechanism></A>It is possible to configure how your
 * <TT>PoemApp</TT>-derived applications implement user login. If the
 * properties file <TT>org.melati.MelatiApp.properties</TT>
 * exists and contains a setting
 * <TT>org.melati.MelatiApp.accessHandler=<I>foo</I></TT>, then
 * <TT><I>foo</I></TT> is taken to be the name of a class implementing the
 * <TT>AccessHandler</TT> interface.  
 * </UL>
 * 
 * If you do not need access handling then set your accessHandler to 
 * <tt>org.melati.login.OpenAccessHandler</tt>.
 * If you do need access handling then set your accessHandler to 
 * <tt>org.melati.login.CommandLineAccessHandler</tt>.
 * However this is not extremely secure, as the user could potentially 
 * change this seting to <tt>OpenAccessHandler</tt> as they are on the same machine.
 * 
 * You can specify the username and password to use by adding command line parameters:
 * <pre>
 * <tt>-username user -password password</tt>
 * </pre>
 *
 *
 * @see org.melati.poem.Database#guestAccessToken
 * @see org.melati.poem.PoemThread#commit
 * @see org.melati.poem.PoemThread#rollback
 * @see #poemContext
 * @see org.melati.login.AccessHandler
 * @see org.melati.login.HttpSessionAccessHandler
 * @see org.melati.login.Login
 * @see org.melati.login.OpenAccessHandler
 * @see org.melati.login.CommandLineAccessHandler
 */

public abstract class AbstractPoemApp extends AbstractConfigApp implements  App {

  private static Boolean taskPerformedOrLoggedInAndTaskAttempted = Boolean.FALSE;

  /**
   * Initialise.
   * 
   * @param args the command line arguments
   * @return a configured Melati
   * {@inheritDoc}
   * @see org.melati.app.AbstractConfigApp#init(java.lang.String[])
   */
  public Melati init(String[] args)  throws MelatiException {
    Melati m = super.init(args);
    if (m.getDatabase() == null) {
      try {
        super.term(m);
      } catch (IOException e) {
        e = null;
      }
      throw new ConfigException("No database configured");
    }
    return m;
  }

  /**
   * Clean up at end of run.
   * 
   * @param melati the melati 
   * @throws IOException 
   */
  public void term(Melati melati) throws IOException {
    super.term(melati);
    if (melati.getDatabase() != null)
      PoemDatabaseFactory.disconnectDatabase(melati.getDatabase().getName());
  }
  
  /**
   * A place holder for things you might want to do before 
   * setting up a <code>PoemSession</code>.
   *
   * @param melati the current Melati
   * @throws Exception if anything goes wrong
   */
  protected void prePoemSession(Melati melati) throws Exception {
    Melati foolEclipse = melati;
    melati = foolEclipse;
  }

  protected void doConfiguredRequest(final Melati melati) {
    // Do something outside of the PoemSession
    try {
      melati.getConfig().getAccessHandler().buildRequest(melati);
      prePoemSession(melati);
    } catch (Exception e) {
      throw new UnexpectedExceptionException(e);
    }
    
    // Login loop
    // If not logged-in when required then an exception is thrown.
    // The exception is handled and the task revisited
    // The flag is reset to allow this to be run again.
    synchronized(taskPerformedOrLoggedInAndTaskAttempted) {
      taskPerformedOrLoggedInAndTaskAttempted = Boolean.FALSE;
      //int goes = 0;
      while (taskPerformedOrLoggedInAndTaskAttempted.equals(Boolean.FALSE)) { 
        //goes ++;
        //if (goes > 2)
        //  throw new MelatiBugMelatiException("Problem with login loop logic, goes = " + goes);
        melati.getDatabase().inSession (
          AccessToken.root, new PoemTask() {
            public void run () {
              melati.getConfig().getAccessHandler().establishUser(melati);
              melati.loadTableAndObject();
              try {
                try {
                  doPoemRequest(melati);
                  taskPerformedOrLoggedInAndTaskAttempted = Boolean.TRUE;
                } catch (Exception e) {
                  _handleException (melati, e);
                }
              } catch (Exception e) {
                taskPerformedOrLoggedInAndTaskAttempted = Boolean.TRUE;
                try {
                  term(melati);
                } catch (IOException e1) {
                  e1 = null;
                }
                throw new UnhandledExceptionException(e);
              }
            }

            // Not sure there is any point in this
            // Cannot find a way of accessing it
            //public String toString() {
            //  return "PoemApp";
            //}
          }
        );
      }
    }
  }
 
 /**
  * Default method to handle an exception.
  *
  * @param melati the Melati
  * @param exception the exception to handle
  */
  protected static void handleException(Melati melati, Exception exception)
      throws Exception {

    if (exception instanceof AccessPoemException) {
      melati.getConfig().getAccessHandler()
        .handleAccessException(melati,(AccessPoemException)exception);

    }
    else
      throw exception;
  }

  protected final void _handleException(Melati melati, Exception exception) 
       throws Exception {
    try {
      handleException(melati, exception);
    }
    catch (Exception e) {
      PoemThread.rollback();
      throw e;
    }
  }


  protected PoemContext poemContext(Melati melati) 
      throws InvalidArgumentsException {
    String[] args = melati.getArguments();
    
    PoemContext pc = new PoemContext();
    if (args.length > 0) {
      pc.setLogicalDatabase(args[0]);
      setTableTroidMethod(pc, (String[])ArrayUtils.section(args,  1,  args.length));
    }

    return pc;
  }

  protected void setTableTroidMethod(PoemContext pc, String[] args){
    if (args.length == 1) { 
      pc.setMethod(args[0]);
    }
    if (args.length == 2) {
      pc.setTable(args[0]);
      try {
        pc.setTroid(new Integer (args[1]));
      }
      catch (NumberFormatException e) {
        pc.setMethod(args[1]);
      }
    }
    if (args.length >= 3) {
      pc.setTable(args[0]);
      try {
        pc.setTroid(new Integer (args[1]));
      }
      catch (NumberFormatException e) {
        throw new UnexpectedExceptionException(new InvalidArgumentsException(args, e));
      }
      pc.setMethod(args[2]);
    }
  }
  
   
  /**
   * This is provided for convenience, so you don't have to specify the 
   * logical database in the arguments.  This is useful when
   * writing applications where you are only accessing a single database.
   *
   * Simply override {@link #poemContext(Melati melati)} thus:
   *
   * <PRE>
   * protected PoemContext poemContext(Melati melati) 
   *     throws InvalidArgumentsException {
   *   return poemContextWithLDB(melati,"&lt;your logical database name&gt;");
   * }
   * </PRE>
   *
   */
  protected PoemContext poemContextWithLDB(Melati melati, 
                                           String logicalDatabase) 
      throws InvalidArgumentsException {
    PoemContext pc = new PoemContext();
    pc.setLogicalDatabase(logicalDatabase);
    setTableTroidMethod(pc, melati.getArguments());
    return pc;
  }

  /**
   * Override this method to do your own thing.
   *
   * @param melati a {@link Melati} containing POEM and other configuration data
   */
  protected abstract void doPoemRequest(Melati melati) throws Exception;

}
