package org.melati.util;

import java.io.*;

public final class ThrowingPrintWriter extends PrintWriter {
  private PrintWriter pw;
  public final String description;

  public static class SuperUseException extends BugException {
    public SuperUseException() {
      super("ThrowingPrintWriter tried to use super.out");
    }
  }

  public ThrowingPrintWriter(PrintWriter pw, String description) {
    super(pw);
    this.pw = pw;
    this.description = description;
  }

  public class TroubleException extends MelatiRuntimeException {
    public String getMessage() {
      return "An exception condition occurred writing to " +
             (description == null ? "a PrintWriter" : description);
    }
  }

  public void throwOnTrouble() {
    if (pw.checkError())
      throw new TroubleException();
  }

  public void flush() {
    pw.flush();
    throwOnTrouble();
  }

  public void close() {
    pw.close();
    throwOnTrouble();
  }

  public boolean checkError() {
    return pw.checkError();
  }

  public void write(int c) {
    pw.write(c);
    throwOnTrouble();
  }

  public void write(char buf[], int off, int len) {
    pw.write(buf, off, len);
    throwOnTrouble();
  }

  public void write(String buf, int off, int len) {
    pw.write(buf, off, len);
    throwOnTrouble();
  }

  public void println() {
    pw.println();
    throwOnTrouble();
  }
}
