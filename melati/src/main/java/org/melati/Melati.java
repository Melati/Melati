package org.melati;

import java.util.*;
import java.io.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;

public class Melati {

  private WebContext webContext;

  public Melati(WebContext webContext) {
    this.webContext = webContext;
  }

  public HTMLMarkupLanguage getHTMLMarkupLanguage() {
    return new HTMLMarkupLanguage(webContext);
  }

  public VariableExceptionHandler getPassbackVariableExceptionHandler() {
    return
        new VariableExceptionHandler() {
          public Object handle(Variable variable, Object context,
                               Exception problem) {
            Exception underlying =
                problem instanceof VariableException ?
                  ((VariableException)problem).subException : problem;

            return
                underlying != null && underlying instanceof AccessPoemException ?
                    underlying : problem;
          }
        };
  }
}
