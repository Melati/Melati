package org.melati;

import org.melati.util.*;

public class TempletAdaptorConstructionMelatiException
    extends MelatiRuntimeException {
  public String adaptorFieldName, adaptorName;

  public TempletAdaptorConstructionMelatiException(
      String adaptorFieldName, String adaptorName, Exception problem) {
    super(problem);
    this.adaptorFieldName = adaptorFieldName;
    this.adaptorName = adaptorName;
  }

  public String getMessage() {
    return "There was a problem with the adaptor `" + adaptorName + "' " +
           "named in the field `" + adaptorFieldName + "'\n" +
           subException.getMessage();
  }
}
