package org.melati.admin;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.melati.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import org.webmacro.broker.*;

/**
 * FIXME getting a bit big, wants breaking up
 */

public class Display extends MelatiServlet {

  protected Template handle(WebContext context, Melati melati)
      throws PoemException, HandlerException {

    try {
        melati.getObject().assertCanRead();
        context.put("object", melati.getObject());
        return getTemplate(context.getForm("template"));
    }
    catch (PoemException e) {
      // we want to let these through untouched, since MelatiServlet handles
      // AccessPoemException specially ...
      throw e;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new HandlerException("Bollocks: " + e);
    }

  }
}
