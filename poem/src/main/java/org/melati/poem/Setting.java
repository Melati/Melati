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

import java.util.*;
import java.sql.Date;
import org.melati.util.*;

public class Setting extends SettingBase {
  public Setting() {}

  private PoemType poemType = null;
  private FieldAttributes valueAttributes = null;
  private Object raw = null;
  private Object cooked = null;

  public Setting(Integer typefactory, String name, String rawstring,
		 String displayname, String description) {
    this.typefactory = typefactory;
    this.name = name;
    this.rawstring = rawstring;
    this.displayname = displayname;
    this.description = description;
  }

  public PoemType getType() {
    if (poemType == null)
      poemType = getTypefactory().typeOf(getDatabase(),
					 PoemTypeFactory.Parameter.generic);
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

  public void setRawstring(String rawstring) {
    Object raw;
    try {
      raw = getType().rawOfString(rawstring);
    }
    catch (Exception e) {
      throw new SettingValidationException(name, e);
    }

    super.setRawstring(rawstring);
    this.raw = raw;
    cooked = null;
  }

  public void setRaw(Object raw) {
    String string;
    try {
      string = getType().stringOfRaw(raw);
    }
    catch (Exception e) {
      throw new SettingValidationException(name, e);
    }

    super.setRawstring(string);
    this.raw = raw;
    cooked = null;
  }

  public Object getRaw() {
    if (raw == null)
      try {
	raw = getType().rawOfString(getRawstring());
      }
      catch (Exception e) {
	throw new SettingValidationException(name, e);
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

  public int getIntValue() {
    Object value = getCooked();
    if (value instanceof Integer)
      return ((Integer)value).intValue();
    else
      throw new SettingTypeMismatchException(name, getTypefactory(), "int");
  }

  public String getStringValue() {
    Object value = getCooked();
    if (value instanceof String)
      return (String)value;
    else
      throw new SettingTypeMismatchException(name, getTypefactory(), "String");
  }

  public String getValue() {
    return getStringValue();
  }
}
