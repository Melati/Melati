package org.melati;

import org.melati.util.*;
import org.melati.poem.*;

public class UnsupportedTypeException extends MelatiException {
  public MarkupLanguage markupLanguage;
  public PoemType type;

  public UnsupportedTypeException(MarkupLanguage markupLanguage,
                                  PoemType type) {
    this.markupLanguage = markupLanguage;
    this.type = type;
  }

  public String getMessage() {
    return
        "The type " + type + " is not supported " +
        "by the markup language `" + markupLanguage + "'";
  }
}
