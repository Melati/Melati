package org.melati.util;

import java.io.IOException;

public class ProcessFailedException extends IOException {
  public String stderr;
  public ProcessFailedException(String message, String stderr) {
    super(message);
    this.stderr = stderr;
  }
}
