package org.melati.poem.prepro;

import java.io.*;

public class HeightFieldQualifier extends FieldQualifier {

  private int height;

  public HeightFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != StreamTokenizer.TT_NUMBER ||
        (int)tokens.nval <= 0)
      throw new ParsingDSDException("<positive height number>", tokens);
    height = (int)tokens.nval;
    tokens.nextToken();
  }

  public void apply(FieldDef field) {
    field.height = height;
  }
}
