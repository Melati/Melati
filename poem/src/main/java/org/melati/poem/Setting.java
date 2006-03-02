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

import org.melati.poem.generated.SettingBase;

/**
 * A setting, analageous to a Property.
 *
 * Melati POEM generated, modified definition of  
 * a <code>Persistent</code> <code>Setting</code> object.
 * 
 * <p> 
 * Description: 
 *   A configurable setting for the application. 
 * </p>
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
 * @generator org.melati.poem.prepro.TableDef#generateMainJava 
 * @author WilliamC@paneris.org
 */

public class Setting extends SettingBase {
 /**
  * Constructor 
  * for a <code>Persistent</code> <code>Setting</code> object.
  * <p>
  * Description: 
  *   A configurable setting for the application. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public Setting() { }

  // programmer's domain-specific code here

  private FieldAttributes valueAttributes = null;
  private Object raw = null;
  private Object cooked = null;

  public Setting(Integer typefactory, String name, String value,
                 String displayname, String description) {
    setTypefactory_unsafe(typefactory);
    setName_unsafe(name);
    setValue_unsafe(value);
    setDisplayname_unsafe(displayname);
    setDescription_unsafe(description);
  }

 /**
  * Thrown when a {@link Setting} value fails validation.
  */
  public static class SettingValidationException extends PoemException {
    private static final long serialVersionUID = 1L;

    public String name;

    public SettingValidationException(String name, Exception problem) {
      super(problem);
      this.name = name;
    }

    public String getMessage() {
      return "A problem arose updating the value of the `" + name +
      "' setting:\n" + subException.getMessage();
    }
  }

  public void setValue(String value) {
    Object rawLocal;
    try {
      rawLocal = getType().rawOfString(value);
    } catch (Exception e) {
      throw new SettingValidationException(getName_unsafe(), e);
    }

    super.setValue(value);
    this.raw = rawLocal;
    cooked = null;
  }

  public void setRaw(Object raw) {
    String string;
    try {
      string = getType().stringOfRaw(raw);
    } catch (Exception e) {
      throw new SettingValidationException(getName_unsafe(), e);
    }

    super.setValue(string);
    this.raw = raw;
    cooked = null;
  }

  public Object getRaw() {
    if (raw == null)
      try {
        raw = getType().rawOfString(getValue());
      } catch (Exception e) {
        throw new SettingValidationException(getName_unsafe(), e);
      }

    return raw;
  }

  public Object getCooked() {
    if (cooked == null)
      cooked = getType().cookedOfRaw(getRaw());
    return cooked;
  }

 /**
  * Thrown when a {@link Setting}'s type does not match the type required. 
  */
  public static class 
      SettingTypeMismatchException extends AppBugPoemException {
    private static final long serialVersionUID = 1L;

    public String name;
    public PoemTypeFactory type;
    public String reqType;

    public SettingTypeMismatchException(String name, PoemTypeFactory type,
                                        String reqType) {
      this.name = name;
      this.type = type;
      this.reqType = reqType;
    }

    public String getMessage() {
      return "The setting `" + name + "' has type `" + type + "' but " +
      "the application asked for a value of type " + reqType;
    }
  }

  public Integer getIntegerCooked() {
    Object cookedLocal = getCooked();
    if (cookedLocal == null)
      return null;
    else if (cookedLocal instanceof Integer)
      return (Integer)cookedLocal;
    else
      throw new SettingTypeMismatchException(getName_unsafe(),
                                             getTypefactory(), "Integer");
  }

  public String getStringCooked() {
    Object cookedLocal = getCooked();
    if (cookedLocal == null)
      return null;
    else if (cookedLocal instanceof String)
      return (String)cookedLocal;
    else
      throw new SettingTypeMismatchException(getName_unsafe(),
                                             getTypefactory(), "String");
  }

  public Boolean getBooleanCooked() {
    Object cookedLocal = getCooked();
    if (cookedLocal == null)
      return null;
    else if (cookedLocal instanceof Boolean)
      return (Boolean)cookedLocal;
    else
      throw new SettingTypeMismatchException(getName_unsafe(),
                                             getTypefactory(), "Boolean");
  }

  private FieldAttributes valueAttributes() {
    if (valueAttributes == null)
      valueAttributes =
          fieldAttributesRenamedAs(getSettingTable().getValueColumn());

    return valueAttributes;
  }

  public Field getValueField() {
    try {
      return new Field(getRaw(), valueAttributes());
    } catch (AccessPoemException accessException) {
      return new Field(accessException, valueAttributes());
    }
  }
}
