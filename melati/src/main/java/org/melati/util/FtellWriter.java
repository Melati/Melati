package org.melati.util;

import java.io.*;

public class FtellWriter extends FilterWriter {
  protected long position = 0;

  public FtellWriter(Writer writer) {
    super(writer);
  }

  public void write(int c) throws IOException {
    out.write(c);
    ++position;
  }

  public void write(char buf[], int off, int len) throws IOException {
    out.write(buf, off, len);
    position += len;
  }

  public void write(String buf, int off, int len) throws IOException {
    out.write(buf, off, len);
    position += len;
  }

  public long ftell() {
    return position;
  }
}
