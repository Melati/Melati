package org.melati;

import java.util.*;
import java.io.*;
import org.melati.*;
import org.melati.poem.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public abstract class MelatiServlet extends WMServlet {

/*
  private final Hashtable handlers = new Hashtable();

  private Handler handler(String name) throws HandlerException {
    synchronized (handlers) {
      Handler handler = (Handler)handlers.get(name);
      if (handler == null) {
        try {
          handler = (Handler)Class.forName(name).newInstance();
        }
        catch (Exception e) {
          throw new HandlerException(e.toString());
        }
        handler.init();
        handlers.put(name, handler);
      }
      return handler;
    }
  }

  protected void stop() {
    synchronized (handlers) {
      for (Enumeration h = handlers.elements(); h.hasMoreElements();)
        ((Handler)h.nextElement()).destroy();
    }
  } 
*/

  protected abstract Template template(WebContext context) throws HandlerException;

  protected final Template handle(WebContext context) throws HandlerException {
    context.put("melati", new Melati(context));
/*
    String servletPath = context.getRequest().getServletPath();
    int slash = servletPath.lastIndexOf('/');
    String handlerClassName =
        slash == -1 ? servletPath : servletPath.substring(slash + 1);
    return handler(handlerClassName).accept(context);
*/
    return template(context);
  }

  private void reallyService(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    super.service(request, response);
  }

  public void service(final ServletRequest request,
                      final ServletResponse response)
      throws ServletException, IOException {

    String pathInfo = ((HttpServletRequest)request).getPathInfo();
    String subPathInfo = null;
    String logicalDatabaseName = null;
    if (pathInfo != null) {
      int slash = pathInfo.indexOf('/');
      if (slash != -1) {
        int slash2 = pathInfo.indexOf('/', slash + 1);
        logicalDatabaseName =
            slash2 == -1 ? pathInfo.substring(slash + 1) :
                           pathInfo.substring(slash + 1, slash2);
        subPathInfo = slash2 == -1 ? null : pathInfo.substring(slash2 + 1);
      }
    }

    // dearie me, what a lot of hoops to jump through
    // at the end of the day Java is terribly poorly suited to this kind of
    // lambda idiom :(

    final MelatiServlet _this = this;

    Database database;
    try {
      database = LogicalDatabase.named(logicalDatabaseName);
    }
    catch (DatabaseInitException e) {
      e.printStackTrace();
      throw new ServletException(e.toString());
    }

    final String[] problem = new String[1];

    try {
      database.inSession(
          AccessToken.root,
          new PoemTask() {
            public void run() {
              try {
                _this.reallyService(request, response);
              }
              catch (Exception e) {
               // FIXME oops we have to do this in-session!
                problem[0] = e.toString();
                e.printStackTrace();
              }
            }
          });
    }
    catch (Exception e) {
      throw new ServletException(e.toString());
    }

    if (problem[0] != null)
      throw new ServletException(problem[0]);
  }
}
