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
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
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
import java.io.*;
import java.text.*;
import org.melati.util.*;

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

  public Field withRaw(Object raw) {
    Field it = (Field)clone();
    it.raw = raw;
    return it;
  }

  public Field withNullable(boolean nullable) {
    return new Field(raw, new BaseFieldAttributes(attrs, nullable));
  }

  public Field withName(String name) {
    return new Field(raw, new BaseFieldAttributes(attrs, name));
  }

  public Enumeration getPossibilities() {
    final Field _this = this;
    return
        new MappedEnumeration(getType().possibleRaws()) {
          protected Object mapped(Object raw) {
            return _this.withRaw(raw);
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
    p.print(getName() + ": " + getCookedString(MelatiLocale.here,
					       DateFormat.MEDIUM));
  }

  public static Field basic(Object value, String name, PoemType type) {
    return
        new Field(value,
                  new BaseFieldAttributes(name, name, null, type, 20, 1, null,
                                          false, true, true));
  }

  public static Field string(String value, String name) {
    return basic(value, name, StringPoemType.nullable);
  }

  public static Field integer(Integer value, String name) {
    return basic(value, name, IntegerPoemType.nullable);
  }

  public static Field reference(Persistent value, String name) {
    return basic(value.troid(), name,
                 new ReferencePoemType(value.getTable(), true));
  }

  public static Field reference(Table table, String name) {
    return basic(null, name, new ReferencePoemType(table, true));
  }
}
