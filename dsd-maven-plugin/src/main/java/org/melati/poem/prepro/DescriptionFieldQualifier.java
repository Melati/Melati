package org.melati.poem.prepro;

import java.io.*;

public class DescriptionFieldQualifier extends FieldQualifier {

  private String description;

  public DescriptionFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != '"')
      throw new ParsingDSDException("<description string>", tokens);
    description = tokens.sval;
    tokens.nextToken();
  }

  public void apply(FieldDef field) {
    field.description = description;
  }
}
