package org.melati.admin;

import org.webmacro.servlet.HandlerException;

public class InvalidPathInfoException extends HandlerException {
  public String pathInfo;

  public InvalidPathInfoException(String pathInfo) {
    super("Invalid path info `" + pathInfo + "'");
    this.pathInfo = pathInfo;
  }
}
