package org.melati;

import org.melati.util.*;

public class DatabaseInitException extends MelatiException {

  public String name;

  public DatabaseInitException(String name, Exception problem) {
    super(problem);
    this.name = name;
  }

  public String getMessage() {
    return
        "Something went wrong trying to open the logical database `" +
        name + "'\n" + subException;
  }
}
