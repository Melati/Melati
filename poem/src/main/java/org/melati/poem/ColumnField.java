package org.melati.poem;

import org.melati.util.*;
import java.util.*;

public class ColumnField extends Field {

  private Column column;

  public ColumnField(Object ident, Column column) {
    super(ident);
    this.column = column;
  }

  public ColumnField(AccessPoemException accessException, Column column) {
    super(accessException);
    this.column = column;
  }

  public static ColumnField of(Persistent persistent, Column column) {
    try {
      return new ColumnField(column.getIdent(persistent), column);
    }
    catch (AccessPoemException accessException) {
      return new ColumnField(accessException, column);
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

  public boolean getIndexed() {
    return column.getIndexed();
  }

  public boolean getUserEditable() {
    return column.getUserEditable();
  }

  public boolean getUserCreateable() {
    return column.getUserCreateable();
  }

  public String getRenderInfo() {
    return column.getRenderInfo();
  }
}
