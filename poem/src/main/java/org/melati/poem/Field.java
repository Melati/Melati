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

import java.util.Enumeration;
import java.io.PrintStream;
import java.text.DateFormat;
import org.melati.util.LimitedEnumeration;
import org.melati.util.MappedEnumeration;
import org.melati.util.MelatiLocale;

/**
 * A Field.
 */
public class Field implements FieldAttributes, Cloneable {

  private AccessPoemException accessException;
  private Object raw;
  private FieldAttributes attrs;

  public Field(Object raw, FieldAttributes attrs) {
    this.raw = raw;
    this.attrs = attrs;
    accessException = null;
  }      

  public Field(AccessPoemException accessException, FieldAttributes attrs) {
    this.accessException = accessException;
    this.attrs = attrs;
    raw = null;
  }

  // 
  // -----------
  //  Cloneable
  // -----------
  // 

  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
  }

  // 
  // -----------------
  //  FieldAttributes
  // -----------------
  // 

  public String getName() {
    return attrs.getName();
  }

  public String getDisplayName() {
    return attrs.getDisplayName();
  }

  public String getDescription() {
    return attrs.getDescription();
  }

  public PoemType getType() {
    return attrs.getType();
  }

  public boolean getIndexed() {
    return attrs.getIndexed();
  }

  public boolean getUserEditable() {
    return attrs.getUserEditable();
  }

  public boolean getUserCreateable() {
    return attrs.getUserCreateable();
  }

  public int getWidth() {
    return attrs.getWidth();
  }

  public int getHeight() {
    return attrs.getHeight();
  }

  public String getRenderInfo() {
    return attrs.getRenderInfo();
  }

  // 
  // -------
  //  Field
  // -------
  // 

  public final Object getRaw() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw;
  }

  public final Object getRawString() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw == null ? "" : getType().stringOfRaw(raw);
  }

  public final Object getCooked() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return getType().cookedOfRaw(raw);
  }

  public final String getCookedString(MelatiLocale locale, int style)
      throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw == null ? "" :
                         getType().stringOfCooked(getCooked(), locale, style);
  }

  public Field withRaw(Object rawP) {
    Field it = (Field)clone();
    it.raw = rawP;
    return it;
  }

  public Field withNullable(boolean nullable) {
    return new Field(raw, new BaseFieldAttributes(attrs, nullable));
  }

  public Field withName(String name) {
    return new Field(raw, new BaseFieldAttributes(attrs, name));
  }

  public Field withDescription(String description) {
    return new Field(raw, new BaseFieldAttributes(
                                        attrs, attrs.getName(), description));
  }

  public Enumeration getPossibilities() {
    final Field _this = this;
    return
        new MappedEnumeration(getType().possibleRaws()) {
          protected Object mapped(Object rawP) {
            return _this.withRaw(rawP);
          }
        };
  }

  /**
   * A bit of a hack?
   */

  public Enumeration getFirst1000Possibilities() {
    return new LimitedEnumeration(getPossibilities(), 1000);
  }

  public boolean sameRawAs(Field other) throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw == null ? other.raw == null : raw.equals(other.raw);
  }

  public void dump(PrintStream p) {
    p.print(getName() + ": " + getCookedString(MelatiLocale.HERE,
                                               DateFormat.MEDIUM));
  }

  public static Field basic(Object value, String name, PoemType type) {
    return
        new Field(value,
                  new BaseFieldAttributes(name, name, null, type, 20, 1, null,
                                          false, true, true));
  }

  public static Field string(String value, String name) {
    return basic(value, name, StringPoemType.nullableInstance);
  }

  public static Field integer(Integer value, String name) {
    return basic(value, name, IntegerPoemType.nullableInstance);
  }

  public static Field reference(Persistent value, String name) {
    return basic(value.troid(), name,
                 new ReferencePoemType(value.getTable(), true));
  }

  public static Field reference(Table table, String name) {
    return basic(null, name, new ReferencePoemType(table, true));
  }
}
