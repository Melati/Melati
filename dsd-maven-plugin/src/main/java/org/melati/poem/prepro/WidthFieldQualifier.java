package org.melati.poem.prepro;

import java.io.*;

public class WidthFieldQualifier extends FieldQualifier {

  private int width;

  public WidthFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    if (tokens.nextToken() != StreamTokenizer.TT_NUMBER ||
        (int)tokens.nval <= 0)
      throw new ParsingDSDException("<positive width number>", tokens);
    width = (int)tokens.nval;
    tokens.nextToken();
  }

  public void apply(FieldDef field) {
    field.width = width;
  }
}
