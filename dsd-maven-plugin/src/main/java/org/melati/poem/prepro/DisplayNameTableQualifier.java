package org.melati.poem.prepro;

import java.io.*;

public class DisplayNameTableQualifier extends TableQualifier {

  private String displayName;

  public DisplayNameTableQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != '"')
      throw new ParsingDSDException("<display name string>", tokens);
    displayName = tokens.sval;
    tokens.nextToken();
  }

  public void apply(TableDef field) {
    field.displayName = displayName;
  }
}
