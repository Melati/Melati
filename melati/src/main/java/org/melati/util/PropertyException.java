package org.melati.util;

import java.util.*;

public class PropertyException extends MelatiException {
  public Properties properties;
  public String propertyName;

  public PropertyException(Properties properties, String propertyName,
                           Exception problem) {
    super(problem);
    this.properties = properties;
    this.propertyName = propertyName;
  }

  public PropertyException(Properties properties, String propertyName) {
    this(properties, propertyName, null);
  }

  public String getMessage() {
    return "A problem arose with property `" + propertyName + "'";
  }
}
