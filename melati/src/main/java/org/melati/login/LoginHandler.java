package org.melati.login;

import javax.servlet.http.HttpSession;

import org.melati.servlet.TemplateServlet;
import org.melati.template.TemplateContext;
import org.melati.MelatiContext;
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

  protected TemplateContext loginTemplate(TemplateContext templateContext, String name) {
    templateContext.setTemplateName("login/" + name);
    return templateContext;
  }

  protected TemplateContext loginPageTemplate(TemplateContext templateContext) {
    return loginTemplate(templateContext, "Login.wm");
  }

  protected TemplateContext usernameUnknownTemplate(TemplateContext templateContext) {
    return loginTemplate(templateContext, "LoginFailure.wm");
  }

  protected TemplateContext passwordIncorrectTemplate(TemplateContext templateContext) {
    return loginTemplate(templateContext, "LoginFailure.wm");
  }

  protected TemplateContext loginSuccessTemplate(TemplateContext templateContext) {
    return loginTemplate(templateContext, "LoginSuccess.wm");
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

  public TemplateContext loginSuccessfullyAs(TemplateContext templateContext, User user) {
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
        templateContext.put("continuationURL",templateContext.getForm("continuationURL"));
      }
    }

    session.putValue(HttpSessionAccessHandler.USER, user);

    return loginSuccessTemplate(templateContext);
  }

  public String getLogin(TemplateContext context) {
    return context.getForm("field_login");
  }

  protected TemplateContext doTemplateRequest(
  MelatiContext melatiContext, TemplateContext templateContext )
  throws Exception {

    setupContext(templateContext);

    String username = getLogin(templateContext);
    String password = templateContext.getForm("field_password");

    if (username == null)
    return loginPageTemplate(templateContext);

    User user = (User)PoemThread.database().getUserTable().getLoginColumn().
    firstWhereEq(username);
    if (user == null) {
      templateContext.put("loginUnknown", Boolean.TRUE);
      return usernameUnknownTemplate(templateContext);
    }

    if (!user.getPassword_unsafe().equals(password)) {
      templateContext.put("passwordWrong", Boolean.TRUE);
      return passwordIncorrectTemplate(templateContext);
    }

    return loginSuccessfullyAs(templateContext, user);
  }
}
