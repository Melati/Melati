/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */
package org.melati.poem;

import org.melati.poem.generated.SettingTableBase;
import java.util.Hashtable;

/**
 * A {@link Table} which is used like a properties file.
 *
 * Every Melati DB has one.
 *
 * Extended from a Melati POEM generated stub. 
 * 
 * <p>
 * Description: 
 *   A configurable setting for the application. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Setting</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> The Table Row Object ID </td></tr> 
 * <tr><td> name </td><td> String </td><td> A code name for this setting 
 * </td></tr> 
 * <tr><td> value </td><td> String </td><td> The value of this setting 
 * </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */

public class SettingTable extends SettingTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public SettingTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  private static final Object nullEntry = new Object();
  private Hashtable cache = null;
  private long cacheSerial = 0L;

  /**
   * Get an object of the appropriate type by name.
   * 
   * Note this has not worked prior to 25/10/2006, 
   * a new cache was created every time.
   * Transactions are so fine grained that it is not clear that 
   * this cache serves any purpose. 
   * 
   * @param name the name field of this Setting object
   * @return its value, cast to the appropriate type.
   */
  public Object getCooked(String name) {
    if (cache == null || cacheSerial != serial(PoemThread.transaction())) {
      cacheSerial = serial(PoemThread.transaction());
      cache = new Hashtable();
    } else {
      Object value = cache.get(name);
      if (value == nullEntry)
        return null;
      else if (value != null) 
        return value;
    } 
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

  /**
   * Get the String representation of the setting.
   * @param name the name field of the Setting object
   * @return the value as a String or null
   */
  public String get(String name) {
    Object it = getCooked(name);
    return it == null ? null : it.toString();
  }

 /**
  * Thrown when a {@link Setting} requested has not been set. 
  */
  public static class UnsetException extends PoemException {
    private static final long serialVersionUID = 1L;

    /** The name of the requested setting. */
    public String name;

    /** Constructor. */
    public UnsetException(String name) {
      this.name = name;
    }

    /** @return The detail message. */
    public String getMessage() {
      return "The application's `" + name + "' parameter has not been set";
    }
  }

  /**
   * Get a set value.
   * 
   * @param name the Setting's name field
   * @return the Setting's value as an appropriate Object
   */
  public Object getOrDie(String name) {
    Object it = get(name);
    if (it == null)
      throw new UnsetException(name);
    return it;
  }

  /**
   * Make sure that a setting with this name exists, if not then create it.
   * Note that only the name is checked for existence, the other parameters 
   * are only used for creation, so enabling the setting to be manually changed. 
   * 
   * @param name the name of the Setting to ensure
   * @param typefactory what type to use
   * @param value the value of this setting
   * @param displayname a human readable name 
   * @param description the purpose of the setting
   * @return the existing or newly created Setting
   */
  public Setting ensure(String name, PoemTypeFactory typefactory, Object value,
                        String displayname, String description) {
    Setting setting = (Setting)getNameColumn().firstWhereEq(name);
    if (setting != null)
      return setting;
    else {
      setting = (Setting)newPersistent();
      setting.setName(name);
      setting.setDisplayname(displayname);
      setting.setDescription(description);

      setting.setUsereditable(true);

      setting.setWidth(20);
      setting.setHeight(1);
      setting.setPrecision(22);
      setting.setScale(2);

      setting.setNullable(true);
      setting.setSize(-1);
      setting.setTypefactory(typefactory);

      setting.setRaw(value);

      return (Setting)getNameColumn().ensure(setting);
    }
  }

  /**
   * Convenience method. 
   * 
   * @param name the name of the Setting to ensure
   * @param value the value of this setting
   * @param displayname a human readable name 
   * @param description the purpose of the setting
   * @return the existing or newly created Setting
   * @see #ensure(String, PoemTypeFactory, Object, String, String)
   */
  public Setting ensure(String name, String value,
                        String displayname, String description) {
    return ensure(name, PoemTypeFactory.STRING, value,
                  displayname, description);
  }

  /**
   * Convenience method. 
   * @param name the name of the Setting to ensure
   * @param value the value of this setting
   * @param displayname a human readable name 
   * @param description the purpose of the setting
   * @return the existing or newly created Setting
   * @see #ensure(String, PoemTypeFactory, Object, String, String)
   */
  public Setting ensure(String name, int value,
                        String displayname, String description) {
    return ensure(name, PoemTypeFactory.INTEGER, new Integer(value),
                  displayname, description);
  }
  
  /**
   * Convenience method. 
   * @param name the name of the Setting to ensure
   * @param value the value of this setting
   * @param displayname a human readable name 
   * @param description the purpose of the setting
   * @return the existing or newly created Setting
   * @see #ensure(String, PoemTypeFactory, Object, String, String)
   */
  public Setting ensure(String name, boolean value, 
                           String displayname, String description) { 
       return ensure(name, PoemTypeFactory.BOOLEAN, 
                     value ? Boolean.TRUE : Boolean.FALSE, 
                     displayname, description); 
     } 
}
