/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import org.melati.poem.generated.*;
import java.util.*;
import java.sql.Date;
import org.melati.util.*;

public class Setting extends SettingBase {
  public Setting() {}

  private PoemType poemType = null;
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

  public PoemType getType() {
    if (poemType == null) {
      PoemTypeFactory f = getTypefactory();
      if (f == null)
        return new StringPoemType(true, -1);
      poemType = getTypefactory().typeOf(getDatabase(), toTypeParameter());
    }
    return poemType;
  }

  public static class SettingValidationException extends PoemException {
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
    Object raw;
    try {
      raw = getType().rawOfString(value);
    } catch (Exception e) {
      throw new SettingValidationException(getName_unsafe(), e);
    }

    super.setValue(value);
    this.raw = raw;
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

  public static class SettingTypeMismatchException extends AppBugPoemException {
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
    Object cooked = getCooked();
    if (cooked == null)
      return null;
    else if (cooked instanceof Integer)
      return(Integer)cooked;
    else
      throw new SettingTypeMismatchException(getName_unsafe(), getTypefactory(), "Integer");
  }

  public String getStringCooked() {
    Object cooked = getCooked();
    if (cooked == null)
      return null;
    else if (cooked instanceof String)
      return(String)cooked;
    else
      throw new SettingTypeMismatchException(getName_unsafe(), getTypefactory(), "String");
  }

  private FieldAttributes valueAttributes() {
    if (valueAttributes == null) {
      Column c = getSettingTable().getValueColumn();
      valueAttributes =
      new BaseFieldAttributes(
                             c.getName(), c.getDisplayName(), c.getDescription(), getType(),
                             getWidth_unsafe() == null ? 12 : getWidth_unsafe().intValue(),
                             getHeight_unsafe() == null ? 1 : getHeight_unsafe().intValue(),
                             getRenderinfo_unsafe());
    }

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
