package org.melati.poem.prepro;

import java.io.*;

public class DescriptionTableQualifier extends TableQualifier {

  private String description;

  public DescriptionTableQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != '"')
      throw new ParsingDSDException("<description string>", tokens);
    description = tokens.sval;
    tokens.nextToken();
  }

  public void apply(TableDef table) {
    table.description = description;
  }
}
