package org.melati;

import java.util.*;
import java.io.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;

public class Melati {

  private WebContext webContext;
  private Database database;
  private MelatiContext melatiContext;
  private Table table;
  private Persistent object;

  public Melati(WebContext webContext,
                Database database, MelatiContext melatiContext)
      throws PoemException {
    this.webContext = webContext;
    this.database = database;
    this.melatiContext = melatiContext;
    if (melatiContext.table != null) {
      table = database.getTable(melatiContext.table);
      if (melatiContext.troid != null && melatiContext.troid.intValue() >= 0)
	object = table.getObject(melatiContext.troid.intValue());
    }
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
}
