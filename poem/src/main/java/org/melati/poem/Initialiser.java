package org.melati.poem;

public interface Initialiser {
  void init(Persistent g) throws AccessPoemException, ValidationPoemException;
}
