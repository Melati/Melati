package org.melati.poem.prepro;

import java.io.*;

public class TargetExistsDSDException extends IOException {
  public File target;

  public TargetExistsDSDException(File target) {
    this.target = target;
  }

  public String getMessage() {
    return
        "The target file `" + target + "' exists " +
        "and was apparently not auto-generated";
  }
}
