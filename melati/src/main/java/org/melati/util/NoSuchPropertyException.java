package org.melati.util;

import java.util.*;

public class NoSuchPropertyException extends PropertyException {

  public NoSuchPropertyException(Properties properties, String propertyName) {
    super(properties, propertyName);
  }

  public String getMessage() {
    return "Property `" + propertyName + "' not found";
  }
}
