package org.melati.poem;

import org.melati.util.*;
import java.util.*;

public final class Field {
  private final Object ident;
  private final Column column;

  public Field(Object ident, Column column) {
    this.ident = ident;
    this.column = column;
  }

  public Field(Persistent persistent,
               Column column) throws AccessPoemException {
    this(column.getIdent(persistent), column);
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

  public Object getIdent() {
    return ident;
  }

  public Object getIdentString() {
    return ident == null ? "" : getType().stringOfIdent(ident);
  }

  public Object getValue() {
    return getType().valueOfIdent(ident);
  }

  public String getValueString() {
    return ident == null ? "" : getType().stringOfValue(getValue());
  }

  public int getWidth() {
    return getType().getWidth();
  }

  public int getHeight() {
    return getType().getHeight();
  }

  public Enumeration getPossibilities() {
    return
        new MappedEnumeration(getType().possibleIdents()) {
          protected Object mapped(Object ident) {
            return new Field(ident, column);
          }
        };
  }

  public boolean sameIdentAs(Field other) {
    return ident == null ? other.ident == null : ident.equals(other.ident);
  }
}
