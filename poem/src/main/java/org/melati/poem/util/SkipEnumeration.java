package org.melati.util;

import java.util.*;

public interface SkipEnumeration extends Enumeration {
  void skip() throws NoSuchElementException;
}
