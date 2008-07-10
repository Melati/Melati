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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.util.Enumeration;
import java.io.PrintStream;
import java.text.DateFormat;
import org.melati.poem.util.LimitedEnumeration;
import org.melati.poem.util.MappedEnumeration;

/**
 * A Field.
 * A field is a value (the raw) with its metadata 
 * (a set of attributes) and possibly an access violation 
 * if the current user is not allowed to access it. 
 * 
 */
public class Field implements FieldAttributes, Cloneable {

  private AccessPoemException accessException;
  private Object raw;
  private FieldAttributes attrs;

  /**
   * Constructor.
   * 
   * @param raw the object value, integer for reference types
   * @param attrs the metadata attributes to set
   */
  public Field(Object raw, FieldAttributes attrs) {
    this.raw = raw;
    this.attrs = attrs;
    accessException = null;
  }      

  /**
   * Constructor for a Field with an access violation.
   * 
   * @param accessException the access violation
   * @param attrs the metadata attributes to set
   */
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

  /**
   * {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new UnexpectedExceptionPoemException(e, "Object no longer supports clone.");
    }
  }

  // 
  // -----------------
  //  FieldAttributes
  // -----------------
  // 

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getName()
   */
  public String getName() {
    return attrs.getName();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getDisplayName()
   */
  public String getDisplayName() {
    return attrs.getDisplayName();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getDescription()
   */
  public String getDescription() {
    return attrs.getDescription();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getType()
   */
  public PoemType getType() {
    return attrs.getType();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getIndexed()
   */
  public boolean getIndexed() {
    return attrs.getIndexed();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getUserEditable()
   */
  public boolean getUserEditable() {
    return attrs.getUserEditable();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getUserCreateable()
   */
  public boolean getUserCreateable() {
    return attrs.getUserCreateable();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getWidth()
   */
  public int getWidth() {
    return attrs.getWidth();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getHeight()
   */
  public int getHeight() {
    return attrs.getHeight();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.FieldAttributes#getRenderInfo()
   */
  public String getRenderInfo() {
    return attrs.getRenderInfo();
  }

  // 
  // -------
  //  Field
  // -------
  // 

  /**
   * Get the value of the Field.
   * 
   * @return the Object value, Integer for reference types
   * @throws AccessPoemException
   */
  public final Object getRaw() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw;
  }

  /**
   * Get the value as a String.
   * 
   * @return the String representation of this Field.
   * @throws AccessPoemException if the current AccessToken does not permit reading
   */
  public final Object getRawString() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw == null ? "" : getType().stringOfRaw(raw);
  }

  /**
   * @return the object represented by the raw
   * @throws AccessPoemException if the current AccessToken does not permit reading
   */
  public final Object getCooked() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return getType().cookedOfRaw(raw);
  }

  /**
   * @return cooked value as a String with defaulted Locale and DateFormat
   * @throws AccessPoemException
   */
  public final String getCookedString()
      throws AccessPoemException {
    return getCookedString(PoemLocale.HERE,java.text.DateFormat.SHORT); 
  }
  /**
   * @param locale
   * @param style
   * @return a String representation of the Object represented by the raw
   * @throws AccessPoemException if the current AccessToken does not permit reading
   */
  public final String getCookedString(PoemLocale locale, int style)
  throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw == null ? "" :
                         getType().stringOfCooked(getCooked(), locale, style);
  }

  /**
   * Clone this Field with a new value but same metadata.
   * 
   * @param rawP new value to set
   * @return a clone with the raw value set to new value
   */
  public Field withRaw(Object rawP) {
    Field it = (Field)clone();
    it.raw = rawP;
    return it;
  }

  /**
   * Clone with a different nullability.
   * 
   * @param nullable the new nullability
   * @return a new Field with a new, presumably different, nullability
   */
  public Field withNullable(boolean nullable) {
    return new Field(raw, new BaseFieldAttributes(attrs, nullable));
  }

  /**
   * Clone with a new name.
   * 
   * @param name the new name
   * @return a new Field with a new name
   */
  public Field withName(String name) {
    return new Field(raw, new BaseFieldAttributes(attrs, name));
  }

  /**
   * Clone with a new description.
   * 
   * @param description the new description
   * @return a new Field with a new description
   */
  public Field withDescription(String description) {
    return new Field(raw, new BaseFieldAttributes(
                                        attrs, attrs.getName(), description));
  }

  /**
   * Might be a bit big for some Reference types.
   * Returns <code>null</code> for String or Integer Types.
   * 
   * @return All possible values or null.
   */
  public Enumeration getPossibilities() {
    final Field _this = this;
    Enumeration en = getType().possibleRaws();
    return
        en == null ? null :
          new MappedEnumeration(en) {
            protected Object mapped(Object rawP) {
              return _this.withRaw(rawP);
            }
          };
  }

  /**
   * Return a limited enumeration of possibilities.
   * 
   * A bit of a hack?
   * @return the first 100 possibilities or null
   */
  public Enumeration getFirst1000Possibilities() {
    Enumeration en = getPossibilities();
    return en == null ? null : new LimitedEnumeration(en, 1000);
  }

  /**
   * Compare raws.
   * 
   * @param other another field to check
   * @return whether the other field has the same raw value as this one
   * @throws AccessPoemException if it is already set
   */
  public boolean sameRawAs(Field other) throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return raw == null ? other.raw == null : raw.equals(other.raw);
  }

  /**
   * Dump to a PrintStream.
   * 
   * @param p the PRintStream to write to
   */
  public void dump(PrintStream p) {
    p.print(toString());
  }
  
  /**
   * Dump to a string.
   * 
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return getName() + ": " + getCookedString(PoemLocale.HERE,
                                               DateFormat.MEDIUM);
  }

  /**
   * A convenience method to create a Field.
   * 
   * @param value the Object to set the value to 
   * @param name the name of the new Field, also used as description
   * @param type the PoemType of the Field
   * @return a newly created Field
   */
  public static Field basic(Object value, String name, PoemType type) {
    return
        new Field(value,
                  new BaseFieldAttributes(name, name, null, type, 20, 1, null,
                                          false, true, true));
  }

  /**
   * A convenience method to create nullable String Field.
   * 
   * @param value the String to set the value to 
   * @param name the name of the new Field, also used as description
   * @return a newly created nullable Field of type StringPoemType
   */
  public static Field string(String value, String name) {
    return basic(value, name, StringPoemType.nullableInstance);
  }

  /**
   * A convenience method to create nullable Integer Field.
   * 
   * @param value the Integer to set the value to 
   * @param name the name of the new Field, also used as description
   * @return a newly created nullable Field of type IntegerPoemType
   */
  public static Field integer(Integer value, String name) {
    return basic(value, name, IntegerPoemType.nullableInstance);
  }

  /**
   * A convenience method to create a populated, nullable, Reference Field.
   * 
   * @param value the Persistent to set the value to 
   * @param name the name of the new Field, also used as description
   * @return a newly created nullable Field of type ReferencePoemType
   */
  public static Field reference(Persistent value, String name) {
    return basic(value.troid(), name,
                 new ReferencePoemType(value.getTable(), true));
  }

  /**
   * A convenience method to create new unpopulated, nullable Reference Field.
   * 
   * @param table the Table to refer to  
   * @param name the name of the new Field, also used as description
   * @return a newly created nullable Field of type ReferencePoemType
   */
  public static Field reference(Table table, String name) {
    return basic(null, name, new ReferencePoemType(table, true));
  }
}
