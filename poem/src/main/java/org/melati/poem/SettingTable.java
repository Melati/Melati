package org.melati.poem;

import java.util.*;
import java.sql.Date;
import org.melati.util.*;

public class SettingTable extends SettingTableBase {

  private static final Object nullEntry = new Object();

  public SettingTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  private Hashtable cache = null;
  private long cacheSerial = 0L;

  public String get(String name) {
    if (cache == null || cacheSerial != serial(PoemThread.transaction()))
      cache = new Hashtable();
    Object value = cache.get(name);
    if (value == nullEntry)
      return null;
    else if (value != null)
      return (String)value;
    else {
      Setting prop =
          (Setting)getNameColumn().firstWhereEq(name);
      if (prop == null) {
	cache.put(name, nullEntry);
	return null;
      }
      else {
	String propValue = prop.getValue();
	cache.put(name, propValue == null ? nullEntry : propValue);
	return propValue;
      }
    }
  }

  public static class UnsetException extends PoemException {
    public String name;

    public UnsetException(String name) {
      this.name = name;
    }

    public String getMessage() {
      return "The application's `" + name + "' parameter has not been set";
      // FIXME include an URL
    }
  }

  public String getOrDie(String name) {
    String it = get(name);
    if (it == null)
      throw new UnsetException(name);
    return it;
  }
}
