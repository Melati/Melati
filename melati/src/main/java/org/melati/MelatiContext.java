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
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.melati.admin.AdminUtils;
import org.melati.poem.Database;
import org.melati.poem.Table;
import org.melati.poem.Persistent;
import org.melati.servlet.Flusher;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.util.StringUtils;
import org.melati.util.HttpUtil;
import org.melati.util.ThrowingPrintWriter;
import org.melati.util.UnexpectedExceptionException;

import org.webmacro.engine.VariableExceptionHandler;


public class MelatiContext implements Cloneable {

  private String logicalDatabaseName;
  private String tableName;
  private Integer troid;
  private String method;
  private Melati melati;
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
  private CharArrayWriter output = new CharArrayWriter(2000);

  public MelatiContext(Melati melati, HttpServletRequest request,
    HttpServletResponse response) {

    this.request = request;
    this.response = response;
    this.melati = melati;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public String getLogicalDatabaseName() {
    return logicalDatabaseName;
  }

  public void setDatabase(String ldb) throws DatabaseInitException {
    logicalDatabaseName = ldb;
    database = LogicalDatabase.getDatabase(logicalDatabaseName);
  }

  public void setTable(String t) {
    tableName = t;
    if (t != null && database != null) table = database.getTable(t);
  }

  public void setTroid(Integer t) {
    troid = t;
    if (t != null && table != null) object = table.getObject(troid.intValue());
  }

  public Integer getTroid() {
    return troid;
  }

  public void setTemplateEngine(TemplateEngine te) {
    templateEngine = te;
  }

  public TemplateEngine getTemplateEngine() {
    return templateEngine;
  }


  public void setTemplateContext(TemplateContext tc) {
    templateContext = tc;
  }

  public TemplateContext getTemplateContext() {
    return templateContext;
  }

  // get the database
  public Database getDatabase() {
    return database;
  }

  // get the table
  public Table getTable() {
    return table;
  }

  // get the table name
  public String getTableName() {
    return tableName;
  }

  // get the object
  public Persistent getObject() {
    return object;
  }

  public String setMethod(String m) {
    return method = m;
  }

  public String getMethod() {
    return method;
  }

  public Melati getMelati() {
    return melati;
  }

  public HttpSession getSession() {
    return getRequest().getSession(true);
  }

  /* get the pathinf, split into bits
  */
  public String[] getPathInfoParts() {
    String pathInfo = request.getPathInfo();
    if (pathInfo == null || pathInfo.length() < 1) return new String[0];
    pathInfo = pathInfo.substring(1);
    if (pathInfo.endsWith("/")) pathInfo = pathInfo.substring(0,pathInfo.length()-1);
    return StringUtils.split(pathInfo, '/');
  }

  public String toString() {
    return "logicalDatabase = " + getLogicalDatabaseName() + ", " +
           "table = " + table + ", " +
           "troid = " + troid + ", " +
           "method = " + method;
  }

  // get the admin utils object
  public AdminUtils getAdminUtils() {
    return new AdminUtils(getRequest().getServletPath(),
               melati.getStaticURL() + "/admin",
               getLogicalDatabaseName());
  }

  public String getLogoutURL() {
    StringBuffer url = new StringBuffer();
    HttpUtil.appendZoneURL(url, getRequest());
    url.append('/');
    url.append(melati.logoutPageServletClassName());
    url.append('/');
    url.append(getLogicalDatabaseName());
    return url.toString();
  }

  public String getZoneURL() {
    return HttpUtil.zoneURL(getRequest());
  }

  // location of javascript for this site
  public String getJavascriptLibraryURL() {
    return melati.getJavascriptLibraryURL();
  }

  public HTMLMarkupLanguage getHTMLMarkupLanguage() {
    return new HTMLMarkupLanguage(this,
                                  melati.getTempletLoader(),
                                  melati.getLocale());
  }

  public WMLMarkupLanguage getWMLMarkupLanguage() {
    return new WMLMarkupLanguage(this,
                                 melati.getTempletLoader(),
                                 melati.getLocale());
  }
  
  public static String sameURLWith(String uri, String query,
                                   String field, String value) {
    return uri + "?" + sameQueryWith(query, field, value);
  }

  public static String sameURLWith(HttpServletRequest request,
                                   String field, String value) {
    return sameURLWith(request.getRequestURI(), request.getQueryString(),
                       field, value);
  }

  public String sameURLWith(String field, String value) {
    return sameURLWith(getRequest(), field, value);
  }

  public String sameURLWith(String field) {
    return sameURLWith(field, "1");
  }

  public String getSameURL() {
    String qs = getRequest().getQueryString();
    return getRequest().getRequestURI() + (qs == null ? "" : '?' + qs);
  }

  public static String sameQueryWith(String qs, String field, String value) {
    String fenc = URLEncoder.encode(field);
    String fenceq = fenc + '=';
    String fev = fenceq + URLEncoder.encode(value);

    if (qs == null || qs.equals("")) return fev;

    int i;
    if (qs.startsWith(fenceq)) i = 0;
    else {
      i = qs.indexOf('&' + fenceq);
      if (i == -1) return qs + '&' + fev;
      ++i;
    }

    int a = qs.indexOf('&', i);
    return qs.substring(0, i) + fev + (a == -1 ? "" : qs.substring(a));
  }

  // turn off buffering
  // the stop paramter allow you flush the output and stop when cancelled
  public void setBufferingOff(boolean stop) {
    buffered = false;
    stopping = stop;
  }

  public boolean gotWriter() {
    return gotwriter;
  }
  
  // gets a writer
  // if we are buffering and stopping, this writer will be a ThrowingPrintWriter
  public Writer getWriter() throws IOException {
    gotwriter = true;
    if (buffered) {
      return output;
    } else {
      if (stopping) {
        Writer out = new ThrowingPrintWriter(getResponse().getWriter(),
                                             "servlet response stream");
        flusher = new Flusher(out);
        flusher.start();
        return out;
      } else {
        return getResponse().getWriter();
      }
    }
  }

  // writes the buffered output to the servlet writer
  // we also need to stop the flusher if it has started
  public void write() throws IOException {
    // only write stuff if we have previously got a writer
    if (gotwriter) {
      if (buffered) output.writeTo(getResponse().getWriter());
      if (flusher != null) flusher.setStopTask(true);
    }
  }

  /**
   * Create a new MelatiContext like this one, only with new values
   * for request and response
   */
  final public MelatiContext newInstance(final HttpServletRequest req,
                                         final HttpServletResponse resp) {
    MelatiContext mc = (MelatiContext) clone();
    mc.request = req;
    mc.response = resp;
    mc.melati = melati;
    return mc;
  }

  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new UnexpectedExceptionException(e);
    }
  }

  public VariableExceptionHandler getPassbackVariableExceptionHandler() {
    return PassbackVariableExceptionHandler.it;
  }

}
