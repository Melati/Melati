package org.melati.util;

import java.io.*;

public class FtellPrintWriter extends PrintWriter {
  protected long position = 0;

  public FtellPrintWriter(Writer writer) {
    super(writer);
  }

  public void write(int c) {
    super.write(c);
    ++position;
  }

  public void write(char buf[], int off, int len) {
    super.write(buf, off, len);
    position += len;
  }

  public void write(String buf, int off, int len) {
    super.write(buf, off, len);
    position += len;
  }

  public long ftell() {
    return position;
  }
}
