package org.melati.poem;

import java.util.*;
import java.sql.Date;
import org.melati.util.*;

public class SystemPropertiesTable extends SystemPropertiesTableBase {

  private static final Object nullEntry = new Object();

  public SystemPropertiesTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

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
      SystemProperties prop =
          (SystemProperties)getNameColumn().firstWhereEq(name);
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
}
