package org.melati.poem;

import org.melati.util.*;
import java.util.*;

public final class Field {

  private AccessPoemException accessException;
  private Object ident;
  private Column column;

  public Field(Object ident, Column column) {
    this.ident = ident;
    this.column = column;
  }

  public Field(Persistent persistent, Column column) {
    this.column = column;
    try {
      ident = column.getIdent(persistent);
      accessException = null;
    }
    catch (AccessPoemException accessException) {
      ident = null;
      this.accessException = accessException;
    }
  }

  public String getName() {
    return column.getName();
  }

  public String getDisplayName() {
    return column.getDisplayName();
  }

  public String getDescription() {
    return column.getDescription();
  }

  public PoemType getType() {
    return column.getType();
  }

  public boolean getNullable() {
    return getType().isNullable();
  }

  public boolean getUserEditable() {
    return column.isUserEditable();
  }

  public boolean getUserCreateable() {
    return column.isUserCreateable();
  }

  public String getRenderInfo() {
    return column.getRenderInfo();
  }

  public Object getIdent() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident;
  }

  public Object getIdentString() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident == null ? "" : getType().stringOfIdent(ident);
  }

  public Object getValue() throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return getType().valueOfIdent(ident);
  }

  public String getValueString() throws AccessPoemException {
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

  public Enumeration getPossibilities() {
    final Column column = this.column;
    return
        new MappedEnumeration(getType().possibleIdents()) {
          protected Object mapped(Object ident) {
            return new Field(ident, column);
          }
        };
  }

  public boolean sameIdentAs(Field other) throws AccessPoemException {
    if (accessException != null)
      throw accessException;
    return ident == null ? other.ident == null : ident.equals(other.ident);
  }
}
