/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Tim Pizey <Timp@paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.template;

import java.io.IOException;

import org.melati.poem.Field;
import org.melati.poem.Persistent;

/**
 * MarkupLanguage provides a variety of methods for rendering objects in a
 * template.  
 *
 * Each object to be rendered has 3 methods:
 * 1 - String rendered(Object o) - this will render the object to a String
 * 2 - void render(Object o) - renders the object to melati.getWriter()
 * 3 - void render(Object o, MelatiWriter w) - render the object to w.
 *
 * When this class was written it was thought that for maximum 
 * efficiency one should render the object direct to the output stream using
 * method (2) above.  
 * However now all but (1) is deprecated. 
 */
public interface MarkupLanguage {

  /**
   * See org/melati/admin/EditHeader.wm
   * See org/melati/admin/PrimarySelect.wm
   * @return the AttributeMarkupLanguage to use for rendering attributes.
   */
  public abstract AttributeMarkupLanguage getAttr();

  /**
   * Get the name of this Markup Language.
   *
   * @return name - the name associated with this markup language.
   *                This is used to determine where to load
   *                templates from ie 'html' templates are
   *                found in the 'html' directory.
   */
  public abstract String getName();

  /**
   * Render an Object in a MarkupLanguage specific way, returning a String.
   *
   * @return - the object rendered as a String in a MarkupLanguage specific way.
   * @param o - the Object to be rendered
   * @throws IOException - if there is a problem during rendering
   */
  public abstract String rendered(Object o) throws IOException;


  /**
   * Render a String in a MarkupLanguage specific way, limiting it's length.
   *
   * @param s - the string to be rendered
   * @param limit - the lenght to trim the string to
   * @throws IOException - if there is a problem during rendering
   * @return - the String having been rendered in a MarkupLanguage specific way.
   */
  public abstract String rendered(String s, int limit) throws IOException;

  /**
   * Render a Date Field Object in a MarkupLanguage specific way, 
   * returning a MEDIUM Date format String.
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   * @throws TemplateEngineException - if there is a problem with the
   *                                   ServletTemplateEngine
   */
  public abstract String rendered(Field field)
          throws TemplateEngineException, IOException;

  /**
   * Render a Field Object in a MarkupLanguage specific way, 
   * returning a String.
   * Defaults to a limit of 10,000,000. 
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @param style - a style to format this Field.
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   * @throws TemplateEngineException - if there is a problem with the
   *                                   ServletTemplateEngine
   */
  public abstract String rendered(Field field, int style)
          throws TemplateEngineException, IOException;

  /**
   * Render a Field Object in a MarkupLanguage specific way, 
   * returning a String.
   *
   * see org.melati.poem.DatePoemType#_stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @param style - a style to format this Field.
   * @param limit - the length to trim the rendered string to
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   * @throws TemplateEngineException - if there is a problem with the
   *                                   ServletTemplateEngine
   */
  public abstract String rendered(Field field, int style, int limit)
          throws TemplateEngineException, IOException;

  /**
   * Render a Date Field Object in a MarkupLanguage specific way, 
   * returning a SHORT Date format String.
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   * @throws TemplateEngineException - if there is a problem with the
   *                                   ServletTemplateEngine
   */
  public abstract String renderedShort(Field field)
          throws TemplateEngineException, IOException;

  /**
   * Render a Date Field Object in a MarkupLanguage specific way, 
   * returning a MEDIUM Date format String.
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   * @throws TemplateEngineException - if there is a problem with the
   *                                   ServletTemplateEngine
   */
  public abstract String renderedMedium(Field field)
          throws TemplateEngineException, IOException;

  /**
   * Render a Date Field Object in a MarkupLanguage specific way, 
   * returning a LONG Date format String.
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   */
  public abstract String renderedLong(Field field) throws IOException;

  /**
   * Render a Date Field Object in a MarkupLanguage specific way, 
   * returning a FULL Date format String.
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   */
  public abstract String renderedFull(Field field)
          throws TemplateEngineException, IOException;

  /**
   * Render a Date Field Object in a MarkupLanguage specific way, 
   * returning a START Date format String.
   *
   * @see org.melati.poem.DatePoemType#stringOfCooked
   *              (java.lang.Object,org.melati.util.MelatiLocale, int)
   * 
   * @param field - the Field to be rendered
   * @return - the Field rendered as a String in a MarkupLanguage specific way.
   * @throws IOException - if there is a problem during rendering
   */
  public abstract String renderedStart(Field field)
          throws TemplateEngineException, IOException;

  
  
  //
  // =========
  //  Widgets
  // =========
  //
  /**
   * Get an input widget for this Field.
   * 
   * @param field The Field
   * @return The default input widget for the Field type
   */
  public abstract String input(Field field) throws TemplateEngineException,
          IOException;

  /**
   * Get an input widget for this Field defined by name.
   * 
   * @param field The Field
   * @param templetName the templet to use instead of the default
   * @return The specified input widget for the Field type
   */
  public abstract String inputAs(Field field, String templetName)
          throws TemplateEngineException, IOException;

  /**
   * Get an input widget for this Field specifying the null value.
   * 
   * @param field The Field
   * @param nullValue the value to use for null for example in a dropdown. 
   * @return The default input widget for the Field type with a specified null value
   */
  public abstract String searchInput(Field field, String nullValue)
          throws TemplateEngineException, IOException;

  /**
   * Retrieve a {@link Template} given its name.
   * 
   * @param templetName the string file name of the template
   * @return a Template
   * @throws TemplateEngineException 
   *         if not template not found on template path or classpath
   */
  public abstract Template templet(String templetName)
          throws TemplateEngineException;

  /**
   * Retrieve a class specific {@link Template} given it and 
   * a purpose.
   * 
   * @param clazz the class we wish to render
   * @return a Template
   * @throws TemplateEngineException 
   *         if not template not found on template path or classpath
   */
  public abstract Template templet(Class clazz)
          throws TemplateEngineException;

  /**
   * Retrieve a class specific {@link Template} given it and 
   * a purpose.
   * 
   * @param purpose for example 'error', 'input', 'display', '3d'
   * @param clazz the class we wish to render
   * @return a Template
   * @throws TemplateEngineException 
   *         if no template found on template path or classpath
   */
  public abstract Template templet(String purpose, Class clazz)
          throws TemplateEngineException;


  /**
   * Escape a String.
   * 
   * @param s the String to escape
   * @return the escaped String
   */
  public abstract String escaped(String s);

  /**
   * Get the DisplayString of a <code>Persistent</code> and 
   * escape that using the current locale and a MEDIUM DateFormat.
   * 
   * See org/melati/admin/SelectionWindowSelection.wm
   * See org/melati/admin/Update.wm
   * @param o
   * @return the escaped DisplayString
   */
  public abstract String escaped(Persistent o);

  /**
   * Encode a String using the User's encoding. 
   * 
   * @param s the String to encode
   * @return the encoded String
   */
  public abstract String encoded(String s);
  
  
}