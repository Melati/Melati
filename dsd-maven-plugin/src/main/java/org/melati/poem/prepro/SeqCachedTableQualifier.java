package org.melati.poem.prepro;

import java.io.*;

public class SeqCachedTableQualifier extends TableQualifier {

  public SeqCachedTableQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
  }

  public void apply(TableDef table) {
    table.seqCached = true;
  }
}
