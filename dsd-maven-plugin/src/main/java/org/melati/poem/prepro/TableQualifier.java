package org.melati.poem.prepro;

import java.io.*;

public abstract class TableQualifier {

  public abstract void apply(TableDef table) throws IllegalityException;

  public static TableQualifier from(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<table qualifier>", tokens);
    TableQualifier it;
    String kind = tokens.sval;
    tokens.nextToken();
    if (kind.equals("displayname"))
      it = new DisplayNameTableQualifier(tokens);
    else if (kind.equals("description"))
      it = new DescriptionTableQualifier(tokens);
    else if (kind.equals("cachelimit"))
      it = new CacheSizeTableQualifier(tokens);
    else if (kind.equals("seqcached"))
      it = new SeqCachedTableQualifier(tokens);
    else
      throw new ParsingDSDException("<table qualifier>", kind, tokens);
    return it;
  }
}
