package org.melati.poem;

import java.util.*;
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

  public int getWidth() {
    return getType().getWidth();
  }

  public int getHeight() {
    return getType().getHeight();
  }

  public Field withRaw(Object raw) {
    Field it = (Field)clone();
    it.raw = raw;
    return it;
  }

  public Field withNullable(boolean nullable) {
    return new Field(raw, new BaseFieldAttributes(attrs, nullable));
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

  public static Field of(Persistent persistent, Column column) {
    try {
      return new Field(column.getRaw(persistent), column);
    }
    catch (AccessPoemException accessException) {
      return new Field(accessException, column);
    }
  }
}
