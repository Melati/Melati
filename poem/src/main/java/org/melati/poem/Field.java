package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public abstract class Field implements FieldAttributes, Cloneable {

  private AccessPoemException accessException;
  private Object ident;

  protected Field(Object ident) {
    this.ident = ident;
    accessException = null;
  }

  protected Field(AccessPoemException accessException) {
    this.accessException = accessException;
    ident = null;
  }

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

  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
  }
}
