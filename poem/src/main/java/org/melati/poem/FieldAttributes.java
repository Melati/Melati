package org.melati.poem;

import java.util.*;

public interface FieldAttributes {
  String getName();
  String getDisplayName();
  String getDescription();
  PoemType getType();
  boolean getIndexed();
  boolean getUserEditable();
  boolean getUserCreateable();
  String getRenderInfo();
}
