package org.melati.admin;

import org.melati.util.*;

public class PathInfoMethodRefException extends MelatiException {
  public String pathInfo;

  public PathInfoMethodRefException(String pathInfo) {
    this.pathInfo = pathInfo;
  }

  public String getMessage() {
    return pathInfo == null ?
        "No path info given" : "Path info `" + pathInfo + "' has wrong form";
  }
}
