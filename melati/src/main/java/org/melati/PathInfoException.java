package org.melati;

import org.melati.util.*;

public class PathInfoException extends MelatiException {
  public String pathInfo;

  public PathInfoException(String pathInfo) {
    this.pathInfo = pathInfo;
  }

  public String getMessage() {
    return pathInfo == null ?
        "No path info given" : "Path info `" + pathInfo + "' has wrong form";
  }
}
