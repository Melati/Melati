package org.melati.util;

public class MelatiBugMelatiException extends MelatiRuntimeException {

  public String bug;

  public MelatiBugMelatiException(String bug, Exception e) {
    super(e);
    this.bug = bug;
  }

  public MelatiBugMelatiException(String bug) {
    this.bug = bug;
  }

  public MelatiBugMelatiException() {
    this(null);
  }

  public String getMessage() {
    return "An apparent bug in Melati: " + bug;
  }
}
