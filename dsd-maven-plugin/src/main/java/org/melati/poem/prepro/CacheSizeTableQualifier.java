package org.melati.poem.prepro;

import java.io.*;

public class CacheSizeTableQualifier extends TableQualifier {

  public static final int DEFAULT = -1, UNLIMITED = -2;

  private int size;

  public CacheSizeTableQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    tokens.nextToken();
    if (tokens.ttype == StreamTokenizer.TT_WORD &&
        tokens.sval.equals("unlimited"))
      size = CacheSizeTableQualifier.UNLIMITED;
    else {
      if (tokens.ttype != StreamTokenizer.TT_NUMBER || (int)tokens.nval <= 0)
        throw new ParsingDSDException("<positive size number>", tokens);
      size = (int)tokens.nval;
    }

    tokens.nextToken();
  }

  public void apply(TableDef table) {
    table.cacheSize = size;
  }
}
