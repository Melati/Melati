package org.melati.util;

import java.util.*;

public class NoSuchPropertyException extends Exception {
  public Properties properties;
  public String propertyName;

  public NoSuchPropertyException(Properties properties, String propertyName) {
    this.properties = properties;
    this.propertyName = propertyName;
  }

  public String getMessage() {
    return "Property `" + propertyName + "' not found";
  }
}
