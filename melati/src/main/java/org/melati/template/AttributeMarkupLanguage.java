/*
 * @since 17-May-2006
 */
package org.melati.template;

import java.io.IOException;

import org.melati.poem.AccessPoemException;
import org.melati.util.MelatiWriter;

/**
 * @author timp
 *
 */
public interface AttributeMarkupLanguage extends MarkupLanguage {

  /**
   * AccessPoemException is treated differently in an Atribute than 
   * in the main body of a page.
   */
  public abstract String rendered(AccessPoemException e) throws IOException;

  /**
   * Render, not translating line ends to BR tags.
   * 
   * @see org.melati.template.AbstractMarkupLanguage#render(String, MelatiWriter)
   */
  public abstract void render(String s, MelatiWriter writer) throws IOException;

}