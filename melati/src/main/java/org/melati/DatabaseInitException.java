package org.melati;

import org.melati.util.*;

public class DatabaseInitException extends MelatiException {

  public String databaseDefsName;
  public String name;

  public DatabaseInitException(String databaseDefsName, String name, Exception problem) {
    super(problem);
    this.databaseDefsName = databaseDefsName;
    this.name = name;
  }

  public String getMessage() {
    return
        "Something went wrong trying to open the logical database `" +
        name + "' using the config file `" + databaseDefsName + "'\n" + subException;
  }
}
