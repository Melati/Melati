package org.melati.poem;

import org.melati.util.*;
import java.util.*;

public class ColumnField extends Field {

  public ColumnField(Object ident, Column column) {
    super(ident, column);
  }

  public ColumnField(AccessPoemException accessException, Column column) {
    super(accessException, column);
  }

  public static ColumnField of(Persistent persistent, Column column) {
    try {
      return new ColumnField(column.getIdent(persistent), column);
    }
    catch (AccessPoemException accessException) {
      return new ColumnField(accessException, column);
    }
  }
}
