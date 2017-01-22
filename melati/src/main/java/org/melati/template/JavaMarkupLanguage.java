package org.melati.template;

import org.melati.Melati;
import org.melati.poem.Persistent;
import org.melati.poem.PoemLocale;
import org.melati.util.MelatiWriter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

/**
 * A representation of Java such that POEM constituents can be round tripped.
 *
 * Intended for creating Hibernate classes from POEM.
 *  
 * @author timp
 * @since 2017-01-21
 *
 */
public class JavaMarkupLanguage extends AbstractMarkupLanguage implements MarkupLanguage {

  public JavaMarkupLanguage(Melati melati, TempletLoader templetLoader, PoemLocale locale) {
    super("java", melati, templetLoader, locale);
  }

  @Override
  public AttributeMarkupLanguage getAttr() {
    throw new RuntimeException("Not expected to be called in Java");
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
    throw new RuntimeException("Not expected to be called in Java");
  }

  @Override
  public String decoded(String s) {
    throw new RuntimeException("Not expected to be called in Java");
  }

  /**
   * Where all content Strings are actually escaped and written out.
   * @see AbstractMarkupLanguage#render(String, MelatiWriter)
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
    throw new RuntimeException("Not expected to be called in Java");
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
