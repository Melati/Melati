package org.melati.poem.prepro;

import java.util.*;
import java.io.*;

public abstract class FieldQualifier {

  public abstract void apply(FieldDef field) throws IllegalityException;

  public static FieldQualifier from(StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field qualifier>", tokens);
    FieldQualifier it;
    String kind = tokens.sval;
    tokens.nextToken();
    if (kind.equals("indexed"))
      it = new IndexedFieldQualifier(tokens);
    else if (kind.equals("unique"))
      it = new UniqueFieldQualifier(tokens);
    else if (kind.equals("primary"))
      it = new TroidFieldQualifier(tokens);
    else if (kind.equals("nullable"))
      it = new NullableFieldQualifier(tokens);
    else if (kind.equals("size"))
      it = new SizeFieldQualifier(tokens);
    else if (kind.equals("deleted"))
      it = new DeletedFieldQualifier(tokens);
    else if (kind.equals("hidden"))
      it = new UndisplayableFieldQualifier(tokens);
    else if (kind.equals("primarydisplay"))
      it = new PrimaryDisplayFieldQualifier(tokens);
    else if (kind.equals("displayorderpriority"))
      it = new DisplayOrderPriorityFieldQualifier(tokens);
    else if (kind.equals("uneditable"))
      it = new UneditableFieldQualifier(tokens);
    else if (kind.equals("displayname"))
      it = new DisplayNameFieldQualifier(tokens);
    else if (kind.equals("description"))
      it = new DescriptionFieldQualifier(tokens);
    else
      throw new ParsingDSDException("<field qualifier>", tokens);
    return it;
  }
}
