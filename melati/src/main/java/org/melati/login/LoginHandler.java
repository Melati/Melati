package org.melati.login;

import java.util.*;
import java.io.*;
import org.melati.util.*;
import org.melati.*;
import org.melati.poem.*;
import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginHandler {

  protected MelatiServlet servlet;

  public LoginHandler(MelatiServlet servlet) {
    this.servlet = servlet;
  }

  protected Template getTemplate(String name) throws WebMacroException {
    return servlet.getTemplate(name);
  }

  protected Template loginTemplate(String name) throws WebMacroException {
    return getTemplate("login/" + name);
  }

  protected Template loginPageTemplate() throws WebMacroException {
    return loginTemplate("Login.wm");
  }

  protected Template usernameUnknownTemplate() throws WebMacroException {
    return loginTemplate("LoginFailure.wm");
  }

  protected Template passwordIncorrectTemplate() throws WebMacroException {
    return loginTemplate("LoginFailure.wm");
  }

  protected Template loginSuccessTemplate() throws WebMacroException {
    return loginTemplate("LoginSuccess.wm");
  }

  protected Field loginField(Field standard) {
    return standard;
  }

  public void setupContext(WebContext context) {
    HttpSession session = context.getSession();

    AccessPoemException triggeringException =
        (AccessPoemException)session.getValue(Login.TRIGGERING_EXCEPTION);

    if (triggeringException != null)
      context.put("triggeringException", triggeringException);

    String username = context.getForm("field_login");
    String password = context.getForm("field_password");
    UserTable users = PoemThread.database().getUserTable();
    
    context.put("login",
                loginField(new Field(username, users.getLoginColumn())));
    context.put("password",
                new Field(password, users.getPasswordColumn()));

    context.put("loginUnknown", Boolean.FALSE);
    context.put("passwordWrong", Boolean.FALSE);
  }

  public Template loginSuccessfullyAs(WebContext context, User user)
      throws WebMacroException {
    // Arrange for the original parameters from the request that triggered the
    // login to be overlaid on the next request that comes in if it's a match
    // (this allows POSTed fields to be recovered without converting the
    // request into a GET that the browser will repeat on reload with giving
    // any warning).

    HttpSession session = context.getSession();

    HttpServletRequestParameters triggeringParams =
        (HttpServletRequestParameters)session.getValue(
            Login.TRIGGERING_REQUEST_PARAMETERS);

    if (triggeringParams != null) {
      session.putValue(HttpSessionAccessHandler.OVERLAY_PARAMETERS,
                       triggeringParams);
      session.removeValue(Login.TRIGGERING_REQUEST_PARAMETERS);
      session.removeValue(Login.TRIGGERING_EXCEPTION);
      context.put("continuationURL", triggeringParams.continuationURL());
    } else {
      if (context.getForm("continuationURL") != null) {
        context.put("continuationURL",context.getForm("continuationURL"));
      }
    }

    session.putValue(HttpSessionAccessHandler.USER, user);

    return loginSuccessTemplate();
  }

  public String getLogin(WebContext context) {
    return context.getForm("field_login");
  }

  public Template handle(WebContext context)
      throws PoemException, WebMacroException {

    setupContext(context);

    String username = getLogin(context);
    String password = context.getForm("field_password");

    if (username == null)
      return loginPageTemplate();

    User user = (User)PoemThread.database().getUserTable().getLoginColumn().
                    firstWhereEq(username);
    if (user == null) {
      context.put("loginUnknown", Boolean.TRUE);
      return usernameUnknownTemplate();
    }

    if (!user.getPassword_unsafe().equals(password)) {
      context.put("passwordWrong", Boolean.TRUE);
      return passwordIncorrectTemplate();
    }

    return loginSuccessfullyAs(context, user);
  }
}
