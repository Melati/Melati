package org.melati.util;

import javax.servlet.http.*;

public class HttpUtil {
  public static void appendZoneURL(
      StringBuffer url, HttpServletRequest request) {
    url.append(request.getScheme());
    url.append("://");
    url.append(request.getServerName());
    if (request.getScheme().equals("http") && request.getServerPort() != 80 ||
        request.getScheme().equals("https") && request.getServerPort() != 443) {
      url.append(':');
      url.append(request.getServerPort());
    }

    String servlet = request.getServletPath();
    if (servlet != null)
      url.append(servlet.substring(0, servlet.lastIndexOf('/')));
  }

  public static String zoneURL(HttpServletRequest request) {
    StringBuffer url = new StringBuffer();
    appendZoneURL(url, request);
    return url.toString();
  }
}
