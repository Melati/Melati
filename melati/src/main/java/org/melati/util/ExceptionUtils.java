package org.melati.util;

import java.io.StringWriter;
import java.io.PrintWriter;

public class ExceptionUtils {
  public static String stackTrace(Throwable e) {
    StringWriter buf = new StringWriter();
    e.printStackTrace(new PrintWriter(buf));
    return buf.toString();
  }
}
