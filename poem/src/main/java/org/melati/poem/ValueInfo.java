package org.melati.poem;

import org.melati.poem.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.util.*;

public class ValueInfo extends ValueInfoBase {
  public ValueInfo() {}

  public PoemTypeFactory.Parameter toTypeParameter() {
    final Boolean nullable = getNullable_unsafe();
    final Integer size = getSize_unsafe();
    return
    new PoemTypeFactory.Parameter() {
      public boolean getNullable() {
        return nullable == null || nullable.booleanValue();
      }

      public int getSize() {
        return size == null ? -1 : size.intValue();
      }
    };
  }
}
