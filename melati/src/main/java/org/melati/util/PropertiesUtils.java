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

  public static Object instanceOfNamedClass(
      Properties properties, String propertyName, Class base, Class defaulT)
         throws InstantiationPropertyException {
    String className = (String)properties.get(propertyName);
    if (className == null)
      try {
	return defaulT.newInstance();
      }
      catch (Exception e) {
	// FIXME grrrr
	throw new RuntimeException(e.toString());
      }
    else {
      try {
        Class clazz = Class.forName(className);
        if (!base.isAssignableFrom(clazz))
          throw new ClassCastException(clazz + " is not descended from " +
                                       base);
        return clazz.newInstance();
      }
      catch (Exception e) {
        throw new InstantiationPropertyException(properties, propertyName, e);
      }
    }
  }

  public static Object instanceOfNamedClass(
      Properties properties, String propertyName,
      String baseName, String defaultName)
          throws InstantiationPropertyException {
    Class base, defaulT;
    try {
      base = Class.forName(baseName);
      defaulT = Class.forName(defaultName);
    }
    catch (Exception e) {
      // FIXME grrrr
      throw new RuntimeException(e.toString());
    }


    return instanceOfNamedClass(properties, propertyName,
				// FIXME improve error-checking
				base, defaulT);
  }
}
