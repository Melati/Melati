package org.melati.poem.prepro;

import java.io.*;

public class SizeFieldQualifier extends FieldQualifier {

  private int size;

  public SizeFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    tokens.nextToken();
    if (tokens.ttype == StreamTokenizer.TT_WORD &&
        tokens.sval.equals("unlimited"))
      size = -1;
    else {
      if (tokens.ttype != StreamTokenizer.TT_NUMBER || (int)tokens.nval <= 0)
        throw new ParsingDSDException("<positive size number>", tokens);
      size = (int)tokens.nval;
    }
    tokens.nextToken();
  }

  public void apply(FieldDef field) throws SizeApplicationException {
    // FIXME check for duplication
    if (!(field instanceof StringFieldDef))
      throw new SizeApplicationException(field);

    ((StringFieldDef)field).size = size;
  }
}
