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
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.servlet.http.*;
import org.melati.admin.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.melati.templets.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;

public class Melati {

  private WebContext webContext;
  private Database database;
  private MelatiContext melatiContext;
  private MelatiLocale locale;
  private TempletLoader templetLoader;
  private String javascriptLibraryURL;
  private String staticURL;
  private Table table;
  private Persistent object;

  public Melati(WebContext webContext,
                Database database, MelatiContext melatiContext,
                MelatiLocale locale, TempletLoader templetLoader,
		String javascriptLibraryURL, String staticURL)
      throws PoemException {
    this.webContext = webContext;
    this.database = database;
    this.melatiContext = melatiContext;
    this.locale = locale;
    this.templetLoader = templetLoader;
    this.javascriptLibraryURL = javascriptLibraryURL;
    this.staticURL = staticURL;

    if (melatiContext.table != null) {
      table = database.getTable(melatiContext.table);
      if (melatiContext.troid != null)
        object = table.getObject(melatiContext.troid.intValue());
    }
  }

  public String getJavascriptLibraryURL() {
    return javascriptLibraryURL;
  }

  public String getStaticURL() {
    return staticURL;
  }
  
  public AdminUtils getAdminUtils() {
   return new AdminUtils(getWebContext().getRequest().getServletPath(),
                         getStaticURL() + "/admin",  
                         getLogicalDatabaseName());
  }    

  public HTMLMarkupLanguage getHTMLMarkupLanguage() {
    return new HTMLMarkupLanguage(getWebContext(),
                                  getTempletLoader(),
                                  getLocale());
  }

  public WMLMarkupLanguage getWMLMarkupLanguage() {
    return new WMLMarkupLanguage(getWebContext(),
                                 getTempletLoader(),
                                 getLocale());
  }

  public YMDDateAdaptor getYMDDateAdaptor() {
    return YMDDateAdaptor.it;
  }

  public YMDHMSTimestampAdaptor getYMDHMSTimestampAdaptor() {
    return YMDHMSTimestampAdaptor.it;
  }

  public SimpleDateAdaptor getSimpleDateAdaptor() {
    return SimpleDateAdaptor.it;
  }

  public JSDynamicTree getJSDynamicTree(Tree tree) {
    return new JSDynamicTree(tree);
  }

  public static class PassbackVariableExceptionHandler
      implements VariableExceptionHandler {
    public static final PassbackVariableExceptionHandler it =
        new PassbackVariableExceptionHandler();

    public Object handle(Variable variable, Object context, Exception problem) {
      Exception underlying =
	  problem instanceof VariableException ?
	    ((VariableException)problem).problem : problem;

      return underlying != null &&
	     underlying instanceof AccessPoemException ?
	       underlying : problem;
    }
  }

  public VariableExceptionHandler getPassbackVariableExceptionHandler() {
    return PassbackVariableExceptionHandler.it;
  }

  public User getUser() {
    // FIXME oops, POEM studiously assumes there isn't necessarily a user, only
    // an AccessToken

    try {
      return (User)PoemThread.accessToken();
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

  public Database getDatabase() {
    return database;
  }

  public String getLogicalDatabaseName() {
    return melatiContext.logicalDatabase;
  }

  public Table getTable() {
    return table;
  }

  public Persistent getObject() {
    return object;
  }

  public final WebContext getWebContext() {
    return webContext;
  }

  public final MelatiContext getContext() {
    return melatiContext;
  }

  public MelatiLocale getLocale() {
    return locale;
  }

  TempletLoader getTempletLoader() {
    return templetLoader;
  }

  public String getMethod() {
    return getContext().method;
  }

   protected String logoutPageServletClassName() {
    return "org.melati.login.Logout";
  }

  public String getLogoutURL() {
    StringBuffer url = new StringBuffer();
    HttpUtil.appendZoneURL(url, webContext.getRequest());
    url.append('/');
    url.append(logoutPageServletClassName());
    url.append('/');
    url.append(getLogicalDatabaseName());
    return url.toString();
  }

  public String getZoneURL() {
    return HttpUtil.zoneURL(webContext.getRequest());
  }

  public static String sameQueryWith(String qs, String field, String value) {

    String fenc = URLEncoder.encode(field);
    String fenceq = fenc + '=';
    String fev = fenceq + URLEncoder.encode(value);

    if (qs == null || qs.equals(""))
      return fev;

    int i;
    if (qs.startsWith(fenceq))
      i = 0;
    else {
      i = qs.indexOf('&' + fenceq);
      if (i == -1)
	return qs + '&' + fev;
      ++i;
    }

    int a = qs.indexOf('&', i);
    return qs.substring(0, i) + fev + (a == -1 ? "" : qs.substring(a));
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
    return sameURLWith(webContext.getRequest(), field, value);
  }

  public String sameURLWith(String field) {
    return sameURLWith(field, "1");
  }

  public String getSameURL() {
    String qs = webContext.getRequest().getQueryString();
    return webContext.getRequest().getRequestURI() +
               (qs == null ? "" : '?' + qs);
  }

  public static void extractFields(WebContext context, Persistent object) {
    for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      Object raw = extractField(context, "field_" + column.getName());

      if (raw instanceof String)
      {
        if (raw == null)
          return;

        if (raw.equals("") && column.getType().getNullable())
            column.setRaw(object, null);
        else
          column.setRawString(object, (String)raw);
      }
      else
        column.setRaw(object, raw);
    }
  }

  public static Object extractField(WebContext context,
                                    String fieldName)
                           throws TempletAdaptorConstructionMelatiException {

    String rawString = context.getForm(fieldName);

    String adaptorFieldName = fieldName + "-adaptor";
    String adaptorName = context.getForm(adaptorFieldName);
    if (adaptorName != null) {
      TempletAdaptor adaptor;
      try {
        // FIXME cache this instantiation
        adaptor = (TempletAdaptor)Class.forName(adaptorName).newInstance();
      } catch (Exception e) {
        throw new TempletAdaptorConstructionMelatiException(
                    adaptorFieldName, adaptorName, e);
      }
      return adaptor.rawFrom(context, fieldName);
    }
    return rawString;
  }
}
