package org.melati.poem;

import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.util.*;

public class ValueInfo extends ValueInfoBase {
  public ValueInfo() {}

  public PoemTypeFactory.Parameter toTypeParameter() {
    return
        new PoemTypeFactory.Parameter() {
          public boolean getNullable() {
	    return nullable.booleanValue();
	  }

	  public int getSize() {
	    return size.intValue();
	  }
        };
  }
}
