package org.melati.util;

import java.util.Properties;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PropertiesUtils {
  public static Properties fromResource(Class clazz, String name)
      throws IOException {
    InputStream is = clazz.getResourceAsStream(name);

    if (is == null)
      throw new FileNotFoundException(name + ": is it in CLASSPATH?");

    Properties them = new Properties();
    try {
      them.load(is);
    }
    catch (IOException e) {
      throw new IOException("Corrupt properties file `" + name + "': " +
                            e.getMessage());
    }

    return them;
  }

  public static String getOrDie(Properties properties, String propertyName)
      throws NoSuchPropertyException {
    String value = properties.getProperty(propertyName);
    if (value == null)
      throw new NoSuchPropertyException(properties, propertyName);
    return value;
  }
}
