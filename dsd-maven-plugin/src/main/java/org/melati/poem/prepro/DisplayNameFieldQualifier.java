package org.melati.poem.prepro;

import java.io.*;

public class DisplayNameFieldQualifier extends FieldQualifier {

  private String displayName;

  public DisplayNameFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != '"')
      throw new ParsingDSDException("<display name string>", tokens);
    displayName = tokens.sval;
    tokens.nextToken();
  }

  public void apply(FieldDef field) {
    field.displayName = displayName;
  }
}
