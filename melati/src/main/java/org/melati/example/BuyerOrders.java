package org.melati.doc.example;

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

public class BuyerOrders extends MelatiServlet {

  protected Template handle(WebContext context)
      throws PoemException, HandlerException {
    int start = 0;
    String startString = context.getForm("start");
    if (startString != null) {
      try {
        start = Math.max(0, Integer.parseInt(startString));
      }
      catch (NumberFormatException e) {
        throw new HandlerException("`start' param to `List' must be a number");
      }
    }

    ExampleDatabase db = (ExampleDatabase)PoemThread.database();
    context.put("buyers",
                db.getBuyerTable().selection(null, null, false, start, 20));

    try {
      return getTemplate("doc/example/BuyerOrders.wm");
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new HandlerException("Bollocks: " + e);
    }
  }
}
