package org.melati.login;

import javax.servlet.http.HttpSession;

import org.melati.servlet.TemplateServlet;
import org.melati.template.TemplateContext;
import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.poem.UserTable;
import org.melati.poem.User;
import org.melati.poem.PoemThread;
import org.melati.poem.Field;
import org.melati.util.HttpServletRequestParameters;

public class LoginHandler {

  protected TemplateServlet servlet;

  public LoginHandler(TemplateServlet servlet) {
    this.servlet = servlet;
  }

  protected String loginTemplate(String name) {
    return "login/" + name;
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

  public void setupContext(TemplateContext context) {
    HttpSession session = context.getSession();

    AccessPoemException triggeringException =
    (AccessPoemException)session.getValue(Login.TRIGGERING_EXCEPTION);

    if (triggeringException != null)
    context.put("triggeringException", triggeringException);

    String username = context.getForm("field_login");
    String password = context.getForm("field_password");
    UserTable users = PoemThread.database().getUserTable();

    context.put("login", new Field(username, users.getLoginColumn()));
    context.put("password",
    new Field(password, users.getPasswordColumn()));

    context.put("loginUnknown", Boolean.FALSE);
    context.put("passwordWrong", Boolean.FALSE);
  }

  public String loginSuccessfullyAs
  (TemplateContext templateContext, User user) {
    // Arrange for the original parameters from the request that triggered the
    // login to be overlaid on the next request that comes in if it's a match
    // (this allows POSTed fields to be recovered without converting the
    // request into a GET that the browser will repeat on reload with giving
    // any warning).

    HttpSession session = templateContext.getSession();

    HttpServletRequestParameters triggeringParams =
    (HttpServletRequestParameters)session.getValue(
    Login.TRIGGERING_REQUEST_PARAMETERS);

    if (triggeringParams != null) {
      session.putValue(HttpSessionAccessHandler.OVERLAY_PARAMETERS,
      triggeringParams);
      session.removeValue(Login.TRIGGERING_REQUEST_PARAMETERS);
      session.removeValue(Login.TRIGGERING_EXCEPTION);
      templateContext.put("continuationURL", triggeringParams.continuationURL());
    } else {
      if (templateContext.getForm("continuationURL") != null) {
        templateContext.put("continuationURL",
        templateContext.getForm("continuationURL"));
      }
    }

    session.putValue(HttpSessionAccessHandler.USER, user);

    return loginSuccessTemplate();
  }

  public String getLogin(TemplateContext context) {
    return context.getForm("field_login");
  }

  protected String doTemplateRequest(
  Melati melati, TemplateContext templateContext )
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

    return loginSuccessfullyAs(templateContext, user);
  }
}
