package org.melati.util;

import javax.servlet.http.*;

public class ReconstructedHttpServletRequestMismatchException
    extends MelatiException {

  public HttpServletRequestParameters stored;
  public HttpServletRequest newRequest;

  public ReconstructedHttpServletRequestMismatchException(
      HttpServletRequestParameters stored, HttpServletRequest newRequest) {
    this.stored = stored;
    this.newRequest = newRequest;
  }

  public String getMessage() {
    return
        "New HttpServletRequest " +
        HttpUtils.getRequestURL(newRequest).toString() + "/session " +
        newRequest.getSession(false) +
        " is incompatible with stored request " + stored.requestURL +
        "/session" + stored.session;
  }
}
