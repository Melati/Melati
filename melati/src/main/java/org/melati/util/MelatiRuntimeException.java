package org.melati.util;

public abstract class MelatiRuntimeException extends RuntimeException {

  public Exception subException;

  public MelatiRuntimeException(Exception subException) {
    this.subException = subException;
  }

  public MelatiRuntimeException() {
    this(null);
  }

  public String getMessage() {
    return this.getClass().getName() + "\n" + subException;
  }

  public void printStackTrace() {
    if (subException == null)
      super.printStackTrace();
    else {
      System.err.println(this);
      System.err.println("---");
      subException.printStackTrace();
    }
  }

  public void printStackTrace(java.io.PrintStream s) {
    if (subException == null)
      super.printStackTrace(s);
    else {
      s.println(this);
      s.println("---");
      subException.printStackTrace(s);
    }
  }

  public void printStackTrace(java.io.PrintWriter w) {
    if (subException == null)
      super.printStackTrace(w);
    else {
      w.println(this);
      w.println("---");
      subException.printStackTrace(w);
    }
  }
}
