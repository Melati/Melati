package org.melati.doc.example;

import org.melati.poem.*;

public class NegativePriceException extends NormalPoemException {
  public String getMessage() {
    return "Your price must be positive";
  }
}
