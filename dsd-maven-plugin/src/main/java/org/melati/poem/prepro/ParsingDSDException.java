package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public class ParsingDSDException extends Exception {

  public String expected, got;

  public ParsingDSDException(String expected, String got) {
    this.expected = expected;
    this.got = "\"" + got + "\"";
  }

  public ParsingDSDException(String expected, StreamTokenizer got) {
    this(expected, got.toString());
  }

  public ParsingDSDException(String expected, String got,
                             StreamTokenizer context) {
    this.expected = expected;
    this.got = "\"" + got + "\" near " + context.toString();
  }

  public String getMessage() {
    return "Expected \"" + expected + "\" but got " + got + "\n";
  }
}
