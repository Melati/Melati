package org.melati.util;

import java.util.*;
import javax.servlet.http.*;

public class HttpServletRequestParameters {

  final Hashtable parameters;
  final String requestURL;
  final String queryString;
  final String method;
  final HttpSession session;

  public HttpServletRequestParameters(HttpServletRequest request) {
    parameters = new Hashtable();
    for (Enumeration p = request.getParameterNames();
         p.hasMoreElements();) {
      String name = (String)p.nextElement();
      parameters.put(name, request.getParameterValues(name));
    }

    requestURL = HttpUtils.getRequestURL(request).toString();
    queryString = request.getQueryString();
    method = request.getMethod();
    session = request.getSession(true);
  }

  public String continuationURL() {
    return
        requestURL +
        (method.equals("PUT") || queryString == null ? "" : "?" + queryString);
  }
}
