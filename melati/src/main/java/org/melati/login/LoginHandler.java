/*
 * $Source$
 * $Revision$
 * 
 *  Copyright (C) 2000 William Chesters
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
package org.melati.login;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.melati.servlet.TemplateServlet;
import org.melati.template.ServletTemplateContext;
import org.melati.Melati;
import org.melati.MelatiUtil;
import org.melati.poem.AccessPoemException;
import org.melati.poem.UserTable;
import org.melati.poem.User;
import org.melati.poem.PoemThread;
import org.melati.poem.Field;
import org.melati.util.HttpServletRequestParameters;
import org.melati.util.UTF8URLEncoder;
import org.melati.util.MD5Util;

/**
 * An object which sets up the login process.
 *
 */
public class LoginHandler {

  static int ONEYEARINSECONDS = 60 * 60 * 24 * 365;

  protected TemplateServlet servlet;

  public LoginHandler(TemplateServlet servlet) {
    this.servlet = servlet;
  }

  protected String loginTemplate(String name) {
    /*
    // Fails to find templates in jars!!
    return "org" + File.separatorChar + 
           "melati" + File.separatorChar + 
           "login" + File.separatorChar + 
           name;
    */
    return "org/melati/login/" + name;
    }

  protected String loginPageTemplate() {
    return loginTemplate("Login");
  }

  protected String usernameUnknownTemplate() {
    return loginTemplate("LoginFailure");
  }

  protected String passwordIncorrectTemplate() {
    return loginTemplate("LoginFailure");
  }

  protected String loginSuccessTemplate () {
    return loginTemplate("LoginSuccess");
  }

  /*
  protected Field loginField(Field standard) {
  return standard;
  }
   */

  public void setupContext(ServletTemplateContext context) {
    HttpSession session = context.getSession();

    AccessPoemException triggeringException = null;
    if (session != null) triggeringException = 
        (AccessPoemException)session.getAttribute(Login.TRIGGERING_EXCEPTION);

    if (triggeringException != null)
      context.put("triggeringException", triggeringException);

    String username = context.getForm("field_login");
    String password = context.getForm("field_password");
    UserTable users = PoemThread.database().getUserTable();

    context.put("login", new Field(username, users.getLoginColumn()));
    context.put("password", new Field(password, users.getPasswordColumn()));

    context.put("loginUnknown", Boolean.FALSE);
    context.put("passwordWrong", Boolean.FALSE);
  }

  public String loginSuccessfullyAs (Melati melati, 
                                  ServletTemplateContext templateContext, User user) {
    // Arrange for the original parameters from the request that triggered the
    // login to be overlaid on the next request that comes in if it's a match
    // (this allows POSTed fields to be recovered without converting the
    // request into a GET that the browser will repeat on reload with giving
    // any warning).
    
    // if we have asked that our password be remembered, set the cookies
    if (MelatiUtil.getFormNulled(templateContext,"rememberme") != null) {
      String ldb = melati.getPoemContext().getLogicalDatabase();
      melati.getResponse().addCookie(makeCookie(ldb, user.getLogin_unsafe()));
      melati.getResponse().addCookie(makeCookie(ldb+user.getLogin_unsafe(), 
                           MD5Util.encode(user.getPassword_unsafe())));
    }

    HttpSession session = templateContext.getSession();

    HttpServletRequestParameters triggeringParams =
        (HttpServletRequestParameters)session.getAttribute(
                                          Login.TRIGGERING_REQUEST_PARAMETERS);

    if (triggeringParams != null) {
      session.setAttribute(HttpSessionAccessHandler.OVERLAY_PARAMETERS,
                       triggeringParams);
      session.removeAttribute(Login.TRIGGERING_REQUEST_PARAMETERS);
      session.removeAttribute(Login.TRIGGERING_EXCEPTION);
      templateContext.put("continuationURL", 
                          triggeringParams.continuationURL());
    } else {
      if (MelatiUtil.getFormNulled(templateContext,"continuationURL") 
          != null) {
        templateContext.put("continuationURL",
                            templateContext.getForm("continuationURL"));
      }
    }

    session.setAttribute(HttpSessionAccessHandler.USER, user);

    return loginSuccessTemplate();
  }
  
  private Cookie makeCookie(String key, String value) {
    Cookie c =  new Cookie(UTF8URLEncoder.encode(key), UTF8URLEncoder.encode(value));

    c.setPath("/");
    c.setMaxAge(ONEYEARINSECONDS);
    c.setComment("This cookie is used to automatically log you back into " +
                 "this site when you return.");
    return c;
  }
    

  public String getLogin(ServletTemplateContext context) {
    return context.getForm("field_login");
  }

  public String doTemplateRequest(Melati melati, 
                                  ServletTemplateContext templateContext)
     throws Exception {

    setupContext(templateContext);

    String username = getLogin(templateContext);
    String password = templateContext.getForm("field_password");

    if (username == null)
      return loginPageTemplate();

    User user = (User)PoemThread.database().getUserTable().getLoginColumn().
                                                      firstWhereEq(username);
    if (user == null) {
      templateContext.put("loginUnknown", Boolean.TRUE);
      return usernameUnknownTemplate();
    }

    if (!user.getPassword_unsafe().equals(password)) {
      templateContext.put("passwordWrong", Boolean.TRUE);
      return passwordIncorrectTemplate();
    }

    return loginSuccessfullyAs(melati, templateContext, user);
  }
}
