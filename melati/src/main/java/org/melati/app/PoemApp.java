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
 *     Tim Pizey <timp@paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;


import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemTask;
import org.melati.poem.AccessToken;
import org.melati.poem.NoMoreTransactionsException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiException;
import org.melati.util.UnexpectedExceptionException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiSimpleWriter;

/**
 * Base class to use Poem as an application.
 *
 * <p>
 * Simply extend this class and override the doPoemRequest method.
 * If you are going to use a template engine look at {@link TemplateApp}.
 * </p>
 *
 * <UL>
 * <LI>
 * The command line arguments are expected in the following order:
 * 
 * <BLOCKQUOTE><TT>
 *     db
 * <BR>db meth
 * <BR>db table method
 * <BR>db table troid  method
 * <BR>db table troid method other
 * </TT></BLOCKQUOTE>
 *
 * these components are broken out of the command line arguments and passed to
 * your application code in the {@link Melati} parameter.
 *
 * <TABLE>
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
 *   <TR>
 *     <TD><TT><I>other</I></TT></TD>
 *     <TD>
 *       Any other information you wish to pass in. 
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
 * properties file <TT>org.melati.MelatiServlet.properties</TT>
 * exists and contains a setting
 * <TT>org.melati.MelatiServlet.accessHandler=<I>foo</I></TT>, then
 * <TT><I>foo</I></TT> is taken to be the name of a class implementing the
 * <TT>AccessHandler</TT> interface.  
 * FIXME - this needs to be thoughtout for non-http access.
 * </UL>
 *
 *
 * @see org.melati.poem.Database#guestAccessToken
 * @see org.melati.poem.PoemThread#commit
 * @see org.melati.poem.PoemThread#rollback
 * @see #poemContext
 * @see org.melati.login.AccessHandler
 * @see org.melati.login.HttpSessionAccessHandler
 * @see org.melati.login.Login
 * @see org.melati.login.HttpBasicAuthenticationAccessHandler
 * @todo work out non-http access control
 */

public abstract class PoemApp implements PoemTask {

  protected static MelatiConfig melatiConfig;

  /**
   * Inititialise Melati.
   *
   * @throws MelatiException is anything goes wrong
   */
  public void init() throws MelatiException {
  }

  /**
   * Process the request.
   *
   * @param request the incoming <code>HttpServletRequest</code>
   * @param response the outgoing <code>HttpServletResponse</code>
   * @throws IOException if anything goes wrong with the file system
   */
  public void run() {
    try {
      melatiConfig = melatiConfig();
      MelatiWriter out = new MelatiSimpleWriter(new OutputStreamWriter(System.out));
      final Melati melati = melatiConfig.getMelati(out);
      PoemContext poemContext = poemContext(melati);
      melati.setPoemContext(poemContext);
      // Set up a POEM session and call the application code

      // Do something outside of the PoemSession
      melati.getConfig().getAccessHandler().buildRequest(melati);
      //prePoemSession(melati);


      melati.getDatabase().inSession (
        AccessToken.root, new PoemTask() {
          public void run () {
            melati.getConfig().getAccessHandler().establishUser(melati);
            melati.loadTableAndObject();
            try {
              try {
                doPoemRequest(melati);
              } catch (Exception e) {
                _handleException (melati, e);
              }
            } catch (Exception e) {
              throw new UnexpectedExceptionException(e);
          }
          }

          public String toString() {
            return "PoemApp";
          }
        }
      );
      // send the output to the client
      melati.write();
    }
    catch (Exception e) {
      // log it
      e.printStackTrace(System.err);
    }
  }

  /**
   * Print the <code>ConnectionPendingException</code>  directly to the client.
   *
   * This is called if a request is made whilst the system is 
   * still being initialised.
   *  
   * Which makes no sense for a command line application!
   *
   * @param out the <code>PrintWriter</code> to print to 
   * @param e   the {@link Exception} to report
   */
  public void writeConnectionPendingException(PrintWriter out, Exception e) {
    out.println("Problem in org.melati.app.ConfigApp");
    out.println("Sorry");
    out.println("The database is starting up.");
    out.println("This takes a few seconds. ");
    e.printStackTrace(out);
  }    

  /** 
   * This method <b>SHOULD</b> be overidden.
   * @return the System Administrators name.
   */
  public String getSysAdminName () {
    return "nobody";
  }

  /** 
   * This method <b>SHOULD</b> be overidden.
   * @return the System Administrators email address.
   */
  public String getSysAdminEmail () {
    return "nobody@nobody.com";
  }

  
  /** 
   * To override any setting from MelatiServlet.properties,
   * simply override this method and return a vaild MelatiConfig.
   *
   * eg to use a different AccessHandler from the default:
   *
   * <PRE>
   *   protected MelatiConfig melatiConfig() throws MelatiException {
   *     MelatiConfig config = super.melatiConfig();
   *     config.setAccessHandler(new YourAccessHandler());
   *     return config;
   *   }
   * </PRE>
   *
   * @throws MelatiException if anything goes wrong with Melati
   */
  protected MelatiConfig melatiConfig() throws MelatiException {
    return new MelatiConfig();
  }
  /**
   * Overriden in TemplateServlet.
   *
   * @param melati org.melati.Melati  
   *               A source of information about the Melati database
   *               context (database, table, object) and utility objects
   *               such as error handlers.
   */

  protected void prePoemSession(Melati melati) throws Exception {
  }


 /**
  * Default method to handle an exception without a template engine.
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
    else if (exception instanceof NoMoreTransactionsException) {
      exception.printStackTrace(System.err);
      dbBusyMessage(melati);
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

  protected static void dbBusyMessage(Melati melati) throws IOException {
    MelatiWriter mw =  melati.getWriter();
    mw.reset();
    PrintWriter out = mw.getPrintWriter();
    out.println("Server Busy");
    out.println("Please try again in a short while"); 
    melati.write();
  }

  protected static PoemContext poemContext(Melati melati) 
      throws InvalidArgumentsException {

    String[] args = new String[0];//melati.getArguments();
    
    PoemContext it = new PoemContext();

    // set it to something in order to provoke meaningful error
    it.setLogicalDatabase("");
    if (args.length > 0) {
      it.setLogicalDatabase(args[0]);
      if (args.length == 2) it.setMethod(args[1]);
      if (args.length == 3) {
        it.setTable(args[1]);
        it.setMethod(args[2]);
      }
      if (args.length == 4) {
        it.setTable(args[1]);
        try {
          it.setTroid(new Integer (args[2]));
        }
        catch (NumberFormatException e) {
          throw new InvalidArgumentsException (args,e);
        }
        it.setMethod(args[3]);
      }
      if (args.length > 4 ) {
        throw new InvalidArgumentsException(args);
      }
    }
    return it;
  }

  /**
   * This is provided for convenience, so you don't have to specify the 
   * logicaldatabase in the arguments.  This is useful when
   * writing appications where you are only accessing a single database.
   *
   * Simply override {@link #poemContext(Melati melati)} thus:
   *
   * <PRE>
   * protected PoemContext poemContext(Melati melati) 
   *     throws InvalidArgumentsException {
   *   return poemContextWithLDB(melati,"<your logical database name>");
   * }
   * </PRE>
   *
   */
  protected PoemContext poemContextWithLDB(Melati melati, 
                                            String logicalDatabase) 
      throws InvalidArgumentsException {

    String[] args = new String[0];// melati.getArguments();
    
    PoemContext it = new PoemContext();

    // set it to something in order to provoke meaningful error
    it.setLogicalDatabase(logicalDatabase);
    if (args.length > 0) {
      if (args.length == 1) it.setMethod(args[0]);
      if (args.length == 2) {
        it.setTable(args[0]);
        it.setMethod(args[1]);
      }
      if (args.length == 3) {
        it.setTable(args[0]);
        try {
          it.setTroid(new Integer (args[1]));
        }
        catch (NumberFormatException e) {
          throw new InvalidArgumentsException (args,e);
        }
        it.setMethod(args[2]);
      }
      if (args.length > 3) {
        throw new InvalidArgumentsException(args);
      }
    }
    return it;
  }

  
   
  /**
   * Override this method to build up your own output.
   *
   * @param melati 
   */
  protected abstract void doPoemRequest(Melati melati) throws Exception;

}
