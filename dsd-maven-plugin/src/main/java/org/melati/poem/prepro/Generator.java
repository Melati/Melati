package org.melati.poem.prepro;

import java.io.*;

public interface Generator {
  public void process(Writer w) throws IOException;
}
