package org.melati.poem.prepro;

import java.io.*;

public class DisplayOrderPriorityFieldQualifier extends FieldQualifier {

  private int priority;

  public DisplayOrderPriorityFieldQualifier(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    DSD.expect(tokens, '=');
    tokens.nextToken();
    if (tokens.ttype != StreamTokenizer.TT_NUMBER || (int)tokens.nval < 0)
      throw new ParsingDSDException("<positive priority number>", tokens);
    priority = (int)tokens.nval;
    tokens.nextToken();
  }

  public void apply(FieldDef field) throws IllegalityException {
    field.displayOrderPriority = priority;
  }
}
