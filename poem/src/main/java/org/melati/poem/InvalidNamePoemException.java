package org.melati.poem;

import java.sql.*;

public class InvalidNamePoemException extends NormalPoemException {
  public String name;
  public InvalidNamePoemException(String name) {
    this.name = name;
  }
}
