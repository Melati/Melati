package org.melati.admin;

import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import org.webmacro.broker.*;
import org.melati.*;

public class Status extends MelatiServlet {
  protected Template handle(WebContext context) throws Exception {
    return getTemplate("admin/Status.wm");
  }
}
