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

  public Object getCooked(String name) {
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
	Object propValue = prop.getCooked();
	cache.put(name, propValue == null ? nullEntry : propValue);
	return propValue;
      }
    }
  }

  public String get(String name) {
    Object it = getCooked(name);
    return it == null ? null : it.toString();
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

  public Object getOrDie(String name) {
    Object it = get(name);
    if (it == null)
      throw new UnsetException(name);
    return it;
  }

  public Setting ensure(String name, PoemTypeFactory typefactory, Object value,
			String displayname, String description) {
    Setting setting = (Setting)getNameColumn().firstWhereEq(name);
    if (setting != null)
      return setting;
    else {
      setting = (Setting)newPersistent();
      setting.setName(name);
      setting.setTypefactory(typefactory);
      setting.setRaw(value);
      setting.setDisplayname(displayname);
      setting.setDescription(description);
      return (Setting)getNameColumn().ensure(setting);
    }
  }

  public Setting ensure(String name, String value,
			String displayname, String description) {
    return ensure(name, PoemTypeFactory.STRING, value,
		  displayname, description);
  }

  public Setting ensure(String name, int value,
			String displayname, String description) {
    return ensure(name, PoemTypeFactory.INTEGER, new Integer(value),
		  displayname, description);
  }
}
