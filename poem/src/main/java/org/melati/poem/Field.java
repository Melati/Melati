package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class Field implements FieldAttributes, Cloneable {

  private AccessPoemException accessException;
  private Object ident;
  private FieldAttributes attrs;

  public Field(Object ident, FieldAttributes attrs) {
    this.ident = ident;
    this.attrs = attrs;
    accessException = null;
  }

  public Field(AccessPoemException accessException, FieldAttributes attrs) {
    this.accessException = accessException;
    this.attrs = attrs;
    ident = null;
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

  public final Object getIdent() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident;
  }

  public final Object getIdentString() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident == null ? "" : getType().stringOfIdent(ident);
  }

  public final Object getValue() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return getType().valueOfIdent(ident);
  }

  public final String getValueString() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident == null ? "" : getType().stringOfValue(getValue());
  }


  public int getWidth() {
    return getType().getWidth();
  }

  public int getHeight() {
    return getType().getHeight();
  }

  public Field withIdent(Object ident) {
    Field it = (Field)clone();
    it.ident = ident;
    return it;
  }

  public Enumeration getPossibilities() {
    final Field _this = this;
    return
        new MappedEnumeration(getType().possibleIdents()) {
          protected Object mapped(Object ident) {
            return _this.withIdent(ident);
          }
        };
  }

  public boolean sameIdentAs(Field other) throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident == null ? other.ident == null : ident.equals(other.ident);
  }

  public static Field of(Persistent persistent, Column column) {
    try {
      return new Field(column.getIdent(persistent), column);
    }
    catch (AccessPoemException accessException) {
      return new Field(accessException, column);
    }
  }
}
