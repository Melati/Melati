package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class FieldSet {

  private Hashtable table_columnMap;
  private Field[] fields;

  public FieldSet(Hashtable table_columnMap, Field[] fields) {
    this.table_columnMap = table_columnMap;
    this.fields = fields;
  }

  public Enumeration elements() {
    return new ArrayEnumeration(fields);
  }

  public Field get(String name) {
    Integer f = (Integer)table_columnMap.get(name);
    return f == null ? null : fields[f.intValue()];
  }
}
