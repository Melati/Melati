package org.melati.util;

import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public abstract class FictionalNotifiableParserCallback
    extends HTMLEditorKit.ParserCallback {
  public abstract void notifyCurrentIsFictional(boolean is);
}
