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
 *
 */

package org.melati;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.login.AccessHandler;
import org.melati.poem.User;
import org.melati.poem.PoemThread;
import org.melati.poem.Persistent;
import org.melati.poem.Column;
import org.melati.poem.NotInSessionPoemException;
import org.melati.poem.NoAccessTokenPoemException;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateContext;
import org.melati.template.TempletAdaptor;
import org.melati.template.YMDDateAdaptor;
import org.melati.template.SimpleDateAdaptor;
import org.melati.template.TempletLoader;
import org.melati.util.JSDynamicTree;
import org.melati.util.MelatiException;
import org.melati.util.MelatiLocale;
import org.melati.util.Tree;

/*
<p>A melati is the main entry point for using Melati. </p>
<p>Parameters are loaded when it is 1st requested</p>
 */

public class Melati {

  private MelatiConfig config = null;
  private TemplateEngine templateEngine = null;

  // allows creation of a melati with default config params
  public Melati() throws MelatiException {
    this(new MelatiConfig());
  }

  // allows creation of a melati with a configuration
  public Melati(MelatiConfig config) throws MelatiException {
    this.config = config;
  }

  // creates a melati context
  public MelatiContext getContext(HttpServletRequest request,
  HttpServletResponse response) throws MelatiException {
    return new MelatiContext(this, request, response);
  }

  // the template engine in use
  public TemplateEngine getTemplateEngine() {
    return config.getTemplateEngine();
  }

  // get the adaptor for rendering dates as drop-downs
  public YMDDateAdaptor getYMDDateAdaptor() {
    return YMDDateAdaptor.it;
  }

  // get the adaptor for rendering dates as normal
  public SimpleDateAdaptor getSimpleDateAdaptor() {
    return SimpleDateAdaptor.it;
  }

  // get a tree object
  public JSDynamicTree getJSDynamicTree(Tree tree) {
    return new JSDynamicTree(tree);
  }

  public AccessHandler getAccessHandler() {
    return config.getAccessHandler();
  }

  // get the current user for this session (if he is there)
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

  public MelatiLocale getLocale() {
    return config.getMelatiLocale();
  }

  public TempletLoader getTempletLoader() {
    return config.getTempletLoader();
  }

  // location of javascript for this site
  public String getJavascriptLibraryURL() {
    return config.getJavascriptLibraryURL();
  }

  // location of static content for this site
  public String getStaticURL() {
    return config.getStaticURL();
  }

  protected String logoutPageServletClassName() {
    return "org.melati.login.Logout";
  }

  public static void extractFields(TemplateContext context, Persistent object) {
    for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String formFieldName = "field_" + column.getName();
      String rawString = context.getForm(formFieldName);

      String adaptorFieldName = formFieldName + "-adaptor";
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
        column.setRaw(object, adaptor.rawFrom(context, formFieldName));
      }
      else {
        if (rawString != null) {
          if (rawString.equals("")) {
            if (column.getType().getNullable())
            column.setRaw(object, null);
            else
            column.setRawString(object, "");
          }
          else
          column.setRawString(object, rawString);
        }
      }
    }
  }

}
