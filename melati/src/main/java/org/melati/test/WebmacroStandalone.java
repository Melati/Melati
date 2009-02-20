/*
 * $Source$
 * $Revision$
 *
 * Copyright (c) 1998, 1999, 2000 Semiotek Inc. All Rights Reserved.
 *
 * This software is the confidential intellectual property of
 * of Semiotek Inc.; it is copyrighted and licensed, not sold.
 * You may use it under the terms of the GNU General Public License,
 * version 2, as published by the Free Software Foundation. If you 
 * do not want to use the GPL, you may still use the software after
 * purchasing a proprietary developers license from Semiotek Inc.
 *
 * This software is provided "as is", with NO WARRANTY, not even the 
 * implied warranties of fitness to purpose, or merchantability. You
 * assume all risks and liabilities associated with its use.
 *
 * See the attached License.html file for details, or contact us
 * by e-mail at info@semiotek.com to get a copy.
 */

package org.melati.test;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.util.MelatiBugMelatiException;
import org.webmacro.FastWriter;
import org.webmacro.InitException;
import org.webmacro.ResourceException;
import org.webmacro.Template;
import org.webmacro.WM;
import org.webmacro.WebMacro;
import org.webmacro.servlet.WebContext;

/**
 * This example demonstrates using WebMacro in "standalone" mode. Instead of
 * subclassing from WMServlet you create and maintain your own WebMacro object
 * and you are free to subclass from another servlet. Also, this technique can
 * be used outside the servlet context.
 * <p>
 * The WebMacro master object is initialized when the servlet is initialized and
 * destroyed when the servlet is destroyed. There is some overhead involved in
 * creating the interface so you should prefer not to create one on every
 * request, although it is not too expensive.
 * <p>
 * This servlet can be compiled and installed as an ordinary servlet. You need
 * to ensure that your WebMacro.properties file is properly configured and
 * available on your CLASSPATH. When setting up WebMacro.properties make sure
 * that the TemplatePath is correctly set and that the template used by this
 * servlet, "standalone.wm", is available on that path.
 */
public class WebmacroStandalone extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * This is the core WebMacro interface which we use to create Contexts, load
   * Templates, and begin other WebMacro operations.
   */
  private WebMacro _wm = null;

  /**
   * The init() method will be called by your servlet runner whenever it wants
   * to instantiate a new copy of your servlet. We initialize WebMacro here so
   * as to avoid the expense of initializing it on every request.
   */
  public void init(ServletConfig sc) throws ServletException {
    try {
      if (_wm == null) {
        _wm = new WM();
      }
    } catch (InitException e) {
      throw new ServletException("Could not initialize WebMacro: " + e);
    }
  }

  /**
   * It's not strictly necessary to destroy the WebMacro object when you're done
   * with it--garbage collection will eventually get it--but it makes sure the
   * resources get freed. You really ought to do this, since it might be a lot
   * of resources.
   */
  public void destroy() {
    // It could of course be null already, but cheaper to re-null;
    // and you get better test coverage.
   _wm = null;
  }

  /**
   * We only implement the GET method in this servlet.
   * <p>
   * We create a WebContext object, populate it with some data, select a
   * template, and write the output of that template to the output stream. Note
   * that you need to pass the WebContext object to the template in order to
   * write it, you also need an output stream, in this case the
   * HttpServletResponse output stream is used.
   * <p>
   * If you were using WebMacro outside a servlet context you would not be able
   * to construct a WebContext object, since you would not have the
   * HttpServletRequest and HttpServletResponse objects which you need in order
   * to do that. WebContext is a subclass of Context, so outside of a servlet
   * just construct a TemplateContext object. You will obviously lose the
   * ability to talk about servlet specific things in your templates (such as
   * "Cookies") but otherwise it's the same.
   * <p>
   * There are a few exceptions you have to deal with. First, WebMacro may not
   * be able to locate the template you've requested in which case it'll throw a
   * NotFoundException. Second, the template will expect to find certain
   * information in the TemplateContext, and if you fail to provide that
   * information a ContextException will be thrown.
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String templateName = "org/melati/test/WebmacroStandalone.wm";

    WebContext context = new WebContext(_wm.getBroker(), req, resp);

    // fill up the context with our data
    context.put("Today", new Date());
    context.put("Number", new Long(23));

    // WebContext provides some utilities as well
    String other = context.getForm("other");
    if (other == null) {
      context.put("hello", "Hello again!"); // put this into the hash
    } else {
      context.put("hello", other); // else put this in
    }
    String templateNameFromForm = context.getForm("templateName");
    if (templateNameFromForm != null )
      templateName = templateNameFromForm;
    FastWriter fw = new FastWriter(_wm.getBroker(), resp.getOutputStream(),
        resp.getCharacterEncoding());
    // get the template we intend to execute
    Template template = null;
    try {
      template = _wm.getTemplate(templateName);
    } catch (ResourceException e) {

      fw.write("ERROR!  Could not locate template "
              + templateName
              + ", if you are not using a modified WebMacro.properties then it should be on the CLASSPATH.");
      e.printStackTrace();
      fw.close();
      return;
    }
    try {
      // write the template to the output, using our context
      template.write(fw.getOutputStream(), context);
    } catch (org.webmacro.ContextException e) {
       throw new MelatiBugMelatiException("You have misconfigured WebMacro.", e);
    } finally { 
      fw.close();
    }
  }

}
