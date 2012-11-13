package org.melati.template;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import org.melati.Melati;
import org.melati.poem.Persistent;
import org.melati.poem.PoemLocale;
import org.melati.template.AbstractMarkupLanguage;
import org.melati.template.AttributeMarkupLanguage;
import org.melati.template.MarkupLanguage;
import org.melati.template.TemplateIOException;
import org.melati.template.TempletLoader;
import org.melati.util.MelatiWriter;

/**
 * A representation of JSON such that Java objects can be serialised 
 * to it.
 *  
 * @author timp
 * @since 09 Nov 2012
 *
 */
public class JSONMarkupLanguage extends AbstractMarkupLanguage implements MarkupLanguage {

  public JSONMarkupLanguage(Melati melati, TempletLoader templetLoader, PoemLocale locale) {
    super("json", melati, templetLoader, locale);
  }

  public JSONMarkupLanguage(String name, AbstractMarkupLanguage other) {
    super(name, other);
  }

  @Override
  public AttributeMarkupLanguage getAttr() {
    throw new RuntimeException("Not expected to be called in JSON");
  }

  @Override
  public String escaped(String s) {
    return escape(s);
  }
  public static String escape(String s) { 
    return s
        .replace("\"", "\\\"")
        .replace("\\", "\\\\")
        .replace("/", "\\/");    
  }
  @Override
  public String escaped(Persistent o) {
    throw new RuntimeException("Not implemented yet");
  }

  @Override
  public String encoded(String s) {
    throw new RuntimeException("Not expected to be called in JSON");
  }

  @Override
  public String decoded(String s) {
    throw new RuntimeException("Not expected to be called in JSON");
  }

  /**
   * Where all content Strings are actually escaped and written out.
   * @see org.melati.template.AbstractMarkupLanguage#render(java.lang.String, org.melati.util.MelatiWriter)
   */
  @Override
  public void render(String s, MelatiWriter writer) {
    try {
      writer.write(escaped(s));
    } catch (IOException e) {
      throw new TemplateIOException("Problem writing " + s, e);
    }      
  }
  @Override
  protected void renderMarkup(String s, MelatiWriter writer) {
    throw new RuntimeException("Not expected to be called in JSON");
  }

  /**
   * Render an Object in a MarkupLanguage specific way, rendering to
   * a supplied Writer.
   *
   * NOTE The context always contains objects with the names melati, object and  ml  
   *
   * @param o - the Object to be rendered
   * @param writer - the MelatiWriter to render this Object to
   */
  protected void render(Object o, MelatiWriter writer) {
    if (o == null)
      writer.output("null");
    else if (o instanceof String) { 
        writer.output("\"" + o + "\"");
    } else if (o instanceof Boolean) { 
      if (((Boolean)o).booleanValue())
        writer.output("true");
      else
        writer.output("false");
    } else if (o instanceof List) { 
        List<?> l = (List<?>)o;
        writer.output("[");
        boolean seenOne = false;
        for (int i = 0; i < l.size(); i++) { 
          if (seenOne)
            writer.output(",");            
          render(l.get(i), writer);
          seenOne = true;
        }
        writer.output("]\n");
    } else if (o instanceof Enumeration) { 
      Enumeration<?> e = (Enumeration<?>) o;
      writer.output("[");
      boolean seenOne = false;
      while (e.hasMoreElements()) { 
        if (seenOne)
          writer.output(",");            
        render(e.nextElement(), writer);
        seenOne = true;        
      }
      writer.output("]\n");
    } else {
        Template templet =
          templetLoader.templet(melati.getTemplateEngine(), this, o.getClass());
        TemplateContext vars =
            melati.getTemplateEngine().getTemplateContext();
        vars.put("object", o);
        vars.put("melati", melati);
        vars.put("ml", melati.getMarkupLanguage());
        expandTemplet(templet, vars, writer);
    }
  }

  
}
