package org.melati;

import org.webmacro.servlet.HandlerException;

public class InvalidUsageException extends HandlerException {
  public InvalidUsageException(MelatiServlet servlet, MelatiContext context) {
    super("The servlet " + servlet.getClass().getName() + " was invoked " +
	  "with invalid parameters " + context);
  }
}
