package org.melati;

import java.io.*;
import java.text.*;
import org.webmacro.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import org.webmacro.servlet.*;
import org.webmacro.broker.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.melati.templets.*;

public abstract class MarkupLanguage {

  private String name;
  private WebContext webContext;
  private TempletLoader templetLoader;
  private MelatiLocale locale;

  public MarkupLanguage(String name, WebContext webContext,
                        TempletLoader templetLoader, MelatiLocale locale) {
    this.name = name;
    this.webContext = webContext;
    this.templetLoader = templetLoader;
    this.locale = locale;
  }

  protected MarkupLanguage(String name, MarkupLanguage other) {
    this(name, other.webContext, other.templetLoader, other.locale);
  }

  public String getName() {
    return name;
  }

  public abstract String rendered(String s);

  public abstract String rendered(AccessPoemException e);

  public abstract String rendered(Exception e);

  public String rendered(Object o) {
    if (o instanceof AccessPoemException)
      return rendered((AccessPoemException)o);
    if (o instanceof Exception)
      return rendered((Exception)o);
    else
      return rendered(o.toString());
  }

  public String rendered(Field field, int style) {
    try {
      return rendered(field.getCookedString(locale, style));
    }
    catch (AccessPoemException e) {
      VariableExceptionHandler handler =
          (VariableExceptionHandler)webContext.get(Variable.EXCEPTION_HANDLER);
      if (handler != null)
        return rendered(handler.handle(null, webContext, e));
      else
        throw e;
    }
  }

  public String renderedShort(Field field) {
    return rendered(field, DateFormat.SHORT);
  }

  public String renderedMedium(Field field) {
    return rendered(field, DateFormat.MEDIUM);
  }

  public String renderedLong(Field field) {
    return rendered(field, DateFormat.LONG);
  }

  public String renderedFull(Field field) {
    return rendered(field, DateFormat.FULL);
  }

  public String rendered(Field field) {
    return renderedMedium(field);
  }

  /**
   * This is just <TT>rendered</TT> for now, but it is guaranteed
   * always to evaluate to a plain old string suitable for
   * (<I>e.g.</I>) putting in a <TT>&lt;TEXTAREA&gt;</TT>, which
   * <TT>rendered</TT> might not.
   */

  public String renderedString(Field field) {
    return rendered(field);
  }

  //
  // =========
  //  Widgets
  // =========
  //

  public String input(Field field)
      throws UnsupportedTypeException, WebMacroException {
    return input(field, null, "", false);
  }

  public String inputAs(Field field, String templetName)
      throws UnsupportedTypeException, WebMacroException {
    return input(field, templetName, "", false);
  }

  public String searchInput(Field field, String nullValue)
      throws UnsupportedTypeException, WebMacroException {
    return input(field, null, nullValue, true);
  }

  protected String input(Field field, String templetName,
			 String nullValue, boolean overrideNullable) 
      throws UnsupportedTypeException, WebMacroException {

    try {
      field.getRaw();
    }
    catch (AccessPoemException e) {
      VariableExceptionHandler handler =
          (VariableExceptionHandler)webContext.get(Variable.EXCEPTION_HANDLER);
      if (handler != null)
        return rendered(handler.handle(null, webContext, e));
      else
        throw e;
    }

    Object previousNullValue = null;
    if (overrideNullable) {
      final PoemType nullable =
	  field.getType().withNullable(true);
      field = field.withNullable(true);
	  new Field(field.getRaw(), field) {
	    public PoemType getType() {
	      return nullable;
	    }
	  };
      previousNullValue = webContext.put("nullValue", nullValue);
    }

    Template templet =
        templetName == null ?
          templetLoader.templet(webContext.getBroker(), this, field) :
          templetLoader.templet(webContext.getBroker(), this, templetName);

    Object previous = webContext.put("field", field);
    try {
      return (String)templet.evaluate(webContext);
    }
    finally {
      if (previous == null)
        webContext.remove("field");
      else
        webContext.put("field", previous);

      if (overrideNullable)
	if (previousNullValue == null)
          webContext.remove("nullValue");
	else
	  webContext.put("nullValue", previousNullValue);
    }
  }
}
