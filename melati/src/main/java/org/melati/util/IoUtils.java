package org.melati.util;

import java.io.*;

public class IoUtils {

  // not sure this is really optimal ...

  public static byte[] slurp(InputStream i, int estimate, int limit)
      throws IOException {
    byte[] b = new byte[estimate];
    int p = 0;

    for (;;) {
      int g = i.read(b, p, Math.min(b.length, limit) - p);
      if (g == -1) break;
      p += g;
      if (p >= limit) break;
      if (p >= b.length) {
        byte[] c = new byte[2 * b.length];
        System.arraycopy(b, 0, c, 0, p);
        b = c;
      }
    }

    i.close();

    System.err.println("slurped " + p + " bytes");

    if (p == b.length)
      return b;
    else {
      byte[] c = new byte[p];
      System.arraycopy(b, 0, c, 0, p);
      return c;
    }
  }

  public static byte[] slurp(InputStream i, int estimate) throws IOException {
    return slurp(i, estimate, Integer.MAX_VALUE);
  }

  /**
   * FIXME warn about potential inefficiency
   */

  public static byte[] slurpOutputOf_bytes(String[] command,
                                           int estimate, int limit)
      throws IOException {
    Process proc = Runtime.getRuntime().exec(command);

    byte[] output = IoUtils.slurp(proc.getInputStream(), estimate, limit);

    byte[] errors = IoUtils.slurp(proc.getErrorStream(), estimate, limit);

    try {
      if (proc.waitFor() != 0)
        throw new ProcessFailedException(command[0] + " failed",
                                         new String(errors));

      return output;
    }
    catch (InterruptedException e) {
      throw new IOException("interrupted while waiting for " +
                            command[0] + " to complete");
    }
  }

  /**
   * FIXME warn about potential inefficiency
   */

  public static String slurpOutputOf(String[] command, int estimate, int limit)
      throws IOException {
    return new String(slurpOutputOf_bytes(command, estimate, limit));
  }

  public static void copy(InputStream i, int buf, OutputStream o)
      throws IOException {
    byte b[] = new byte[buf];
    for (;;) {
      int g = i.read(b);
      if (g == -1) break;
      o.write(b, 0, g);
    }
  }
}
