package org.melati.poem.prepro;

import java.io.*;

public class CategoryTableQualifier extends TableQualifier {

  private String category;

  public CategoryTableQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != '"')
      throw new ParsingDSDException("<category string>", tokens);
    category = tokens.sval;
    tokens.nextToken();
  }

  public void apply(TableDef table) {
    table.category = category;
  }
}
