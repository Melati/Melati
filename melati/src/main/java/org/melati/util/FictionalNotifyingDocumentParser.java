package org.melati.util;

import java.io.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class FictionalNotifyingDocumentParser extends DocumentParser {
  protected FictionalNotifiableParserCallback callback = null;

  public FictionalNotifyingDocumentParser(DTD dtd) {
    super(dtd);
  }

  public void parse(Reader in,
                    FictionalNotifiableParserCallback callback,
                    boolean ignoreCharSet) throws IOException {
    this.callback = callback;
    try {
      super.parse(in, callback, ignoreCharSet);
    }
    finally {
      this.callback = null;
    }
  }

  protected void handleEmptyTag(TagElement tag) throws ChangedCharSetException {
    if (callback != null) callback.notifyCurrentIsFictional(tag.fictional());
    super.handleEmptyTag(tag);
  }

  protected void handleStartTag(TagElement tag) {
    if (callback != null) callback.notifyCurrentIsFictional(tag.fictional());
    super.handleStartTag(tag);
  }

  protected void handleEndTag(TagElement tag) {
    if (callback != null) callback.notifyCurrentIsFictional(tag.fictional());
    super.handleEndTag(tag);
  }
}
