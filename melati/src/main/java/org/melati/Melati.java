package org.melati;

import java.util.*;
import java.io.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.melati.templets.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;

public class Melati {

  private WebContext webContext;
  private Database database;
  private MelatiContext melatiContext;
  private MelatiLocale locale;
  private TempletLoader templetLoader;
  private Table table;
  private Persistent object;

  public Melati(WebContext webContext,
                Database database, MelatiContext melatiContext,
                MelatiLocale locale, TempletLoader templetLoader)
      throws PoemException {
    this.webContext = webContext;
    this.database = database;
    this.melatiContext = melatiContext;
    this.locale = locale;
    this.templetLoader = templetLoader;

    if (melatiContext.table != null) {
      table = database.getTable(melatiContext.table);
      if (melatiContext.troid != null && melatiContext.troid.intValue() >= 0)
        object = table.getObject(melatiContext.troid.intValue());
    }
  }

  public HTMLMarkupLanguage getHTMLMarkupLanguage() {
    return new HTMLMarkupLanguage(webContext, templetLoader, locale);
  }

  public YMDDateAdaptor getYMDDateAdaptor() {
    return YMDDateAdaptor.it;
  }

  public VariableExceptionHandler getPassbackVariableExceptionHandler() {
    return
        new VariableExceptionHandler() {
          public Object handle(Variable variable, Object context,
                               Exception problem) {
            Exception underlying =
                problem instanceof VariableException ?
                  ((VariableException)problem).subException : problem;

            return underlying != null &&
                   underlying instanceof AccessPoemException ?
                     underlying : problem;
          }
        };
  }

  public User getUser() {
    // FIXME oops, POEM studiously assumes there isn't necessarily a user, only
    // an AccessToken

    try {
      return (User)PoemThread.accessToken();
    }
    catch (NotInSessionPoemException e) {
      return null;
    }
    catch (NoAccessTokenPoemException e) {
      return null;
    }
    catch (ClassCastException e) {
      return null;
    }
  }

  public Database getDatabase() {
    return database;
  }

  public String getLogicalDatabaseName() {
    return melatiContext.logicalDatabase;
  }

  public Table getTable() {
    return table;
  }

  public Persistent getObject() {
    return object;
  }

  public MelatiContext getContext() {
    return melatiContext;
  }

  public String getMethod() {
    return getContext().method;
  }

  public static void extractFields(WebContext context, Persistent object) {
    for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String formFieldName = "field-" + column.getName();
      String rawString = context.getForm(formFieldName);

      if (rawString == null) {
        String adaptorFieldName = formFieldName + "-adaptor";
        String adaptorName = context.getForm(adaptorFieldName);
        if (adaptorName != null) {
          TempletAdaptor adaptor;
          try {
	    // FIXME cache this instantiation
            adaptor = (TempletAdaptor)Class.forName(adaptorName).newInstance();
          }
          catch (Exception e) {
            throw new TempletAdaptorConstructionMelatiException(
                      adaptorFieldName, adaptorName, e);
          }

          column.setRaw(object, adaptor.rawFrom(context, formFieldName));
        }
      }
      else if (rawString.equals(""))
        if (column.getType().getNullable())
          column.setRaw(object, null);
        else
          column.setRawString(object, "");
      else
        column.setRawString(object, rawString);
    }
  }
}
