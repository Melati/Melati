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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati;

import java.io.CharArrayWriter;
import java.io.Writer;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.melati.admin.AdminUtils;
import org.melati.poem.Database;
import org.melati.poem.Table;
import org.melati.poem.User;
import org.melati.poem.Persistent;
import org.melati.poem.PoemThread;
import org.melati.poem.NotInSessionPoemException;
import org.melati.poem.NoAccessTokenPoemException;
import org.melati.servlet.Flusher;
import org.melati.servlet.MelatiContext;
import org.melati.template.TemplateContext;
import org.melati.template.HTMLMarkupLanguage;
import org.melati.template.WMLMarkupLanguage;
import org.melati.template.TemplateEngine;
import org.melati.util.HttpUtil;
import org.melati.util.ThrowingPrintWriter;
import org.melati.util.UnexpectedExceptionException;
import org.melati.util.DatabaseInitException;
import org.melati.util.StringUtils;

import org.webmacro.engine.VariableExceptionHandler;


public class Melati {

  private MelatiConfig config;
  private MelatiContext context;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private Database database = null;
  private Table table = null;
  private Persistent object = null;
  private TemplateEngine templateEngine;
  private TemplateContext templateContext;
  // most of the time we buffer the output
  private boolean buffered = true;
  // check to see if we have got the writer
  private boolean gotwriter = false;
  // if we don't buffer, we can allow the output to be interrupted
  private boolean stopping = true;
  // the flusher send output to the client ever two seconds
  private Flusher flusher;
  // what the servlet's output is buffered into
  private CharArrayWriter output = new CharArrayWriter (2000);

  public Melati (MelatiConfig config, HttpServletRequest request,
  HttpServletResponse response) {

    this.request = request;
    this.response = response;
    this.config = config;
  }

  public HttpServletRequest getRequest () {
    return request;
  }

  public void setRequest (HttpServletRequest request) {
    this.request = request;
  }

  public HttpServletResponse getResponse () {
    return response;
  }
  
  public void setContext(MelatiContext context) throws DatabaseInitException {
    this.context = context;
    if (context.logicalDatabase != null) 
      database = LogicalDatabase.getDatabase(context.logicalDatabase);
  }

  public void loadTableAndObject() {
    if (context.table != null && database != null) 
      table = database.getTable(context.table);
    if (context.troid != null && table != null) 
      object = table.getObject(context.troid.intValue());
  }
  
  public MelatiContext getContext() {
    return context;
  }

  // get the database
  public Database getDatabase () {
    return database;
  }

  // get the table
  public Table getTable () {
    return table;
  }

  // get the object
  public Persistent getObject () {
    return object;
  }

  public String getMethod () {
    return context.method;
  }

  public void setTemplateEngine (TemplateEngine te) {
    templateEngine = te;
  }

  public TemplateEngine getTemplateEngine () {
    return templateEngine;
  }


  public void setTemplateContext (TemplateContext tc) {
    templateContext = tc;
  }

  public TemplateContext getTemplateContext () {
    return templateContext;
  }

  public MelatiConfig getConfig () {
    return config;
  }

  /* get the pathinf, split into bits
   */
  public String[] getPathInfoParts () {
    String pathInfo = request.getPathInfo ();
    if (pathInfo == null || pathInfo.length () < 1) return new String[0];
    pathInfo = pathInfo.substring (1);
//    if (pathInfo.endsWith ("/")) 
//      pathInfo = pathInfo.substring(0,pathInfo.length()-1);
    return StringUtils.split (pathInfo, '/');
  }


  public HttpSession getSession () {
    return getRequest ().getSession (true);
  }

  // get the admin utils object
  public AdminUtils getAdminUtils () {
    return new AdminUtils (getRequest ().getServletPath (),
    config.getStaticURL () + "/admin",
    context.logicalDatabase);
  }

  public String getLogoutURL () {
    StringBuffer url = new StringBuffer ();
    HttpUtil.appendZoneURL (url, getRequest ());
    url.append ('/');
    url.append (config.logoutPageServletClassName ());
    url.append ('/');
    url.append (context.logicalDatabase);
    return url.toString ();
  }

  public String getZoneURL () {
    return HttpUtil.zoneURL (getRequest ());
  }

  // location of javascript for this site
  public String getJavascriptLibraryURL () {
    return config.getJavascriptLibraryURL ();
  }

  public HTMLMarkupLanguage getHTMLMarkupLanguage () {
    return new HTMLMarkupLanguage (this,
    config.getTempletLoader (),
    config.getLocale ());
  }

  public WMLMarkupLanguage getWMLMarkupLanguage () {
    return new WMLMarkupLanguage (this,
    config.getTempletLoader (),
    config.getLocale ());
  }

  public String sameURLWith (String field, String value) {
    return MelatiUtil.sameURLWith (getRequest (), field, value);
  }

  public String sameURLWith (String field) {
    return sameURLWith (field, "1");
  }

  public String getSameURL () {
    String qs = getRequest ().getQueryString ();
    return getRequest ().getRequestURI () + (qs == null ? "" : '?' + qs);
  }

  // turn off buffering
  // the stop paramter allow you flush the output and stop when cancelled
  public void setBufferingOff (boolean stop) {
    buffered = false;
    stopping = stop;
  }

  public boolean gotWriter () {
    return gotwriter;
  }

  // gets a writer
  // if we are buffering and stopping, this writer will be a ThrowingPrintWriter
  public Writer getWriter () throws IOException {
    gotwriter = true;
    if (buffered) {
      return output;
    } else {
      if (stopping) {
        Writer out = new ThrowingPrintWriter (getResponse ().getWriter (),
        "servlet response stream");
        flusher = new Flusher (out);
        flusher.start ();
        return out;
      } else {
        return getResponse ().getWriter ();
      }
    }
  }

  // writes the buffered output to the servlet writer
  // we also need to stop the flusher if it has started
  public void write () throws IOException {
    // only write stuff if we have previously got a writer
    if (gotwriter) {
      if (buffered) output.writeTo (getResponse ().getWriter ());
      if (flusher != null) flusher.setStopTask (true);
    }
  }

  public VariableExceptionHandler getPassbackVariableExceptionHandler () {
    return PassbackVariableExceptionHandler.it;
  }

  // get the current user for this session (if he is there)
  public User getUser () {
    // FIXME oops, POEM studiously assumes there isn't necessarily a user, only
    // an AccessToken
    try {
      return (User)PoemThread.accessToken ();
    }
    catch (NotInSessionPoemException e) {
      return null;
    }
    catch (NoAccessTokenPoemException e) {
      return null;
    }
    catch (ClassCastException e) {
      return null;
    }
  }

}
