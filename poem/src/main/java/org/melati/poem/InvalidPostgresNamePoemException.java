package org.melati.poem;

public class InvalidPostgresNamePoemException
    extends InvalidNamePoemException {
  public InvalidPostgresNamePoemException(String name) {
    super(name);
  }
}
