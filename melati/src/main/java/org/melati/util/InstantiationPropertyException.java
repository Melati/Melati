package org.melati.util;

import java.util.*;

public class InstantiationPropertyException extends PropertyException {

  public InstantiationPropertyException(
      Properties properties, String propertyName, Exception problem) {
    super(properties, propertyName, problem);
  }

  public String getMessage() {
    return "Unable to instantiate an object from property " +
           "`" + propertyName + "'\n" +
           subException.getMessage();
  }
}
