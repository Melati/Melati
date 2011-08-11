/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.template;


import org.melati.poem.FieldAttributes;

/**
 * An object which can load a templet given a {@link TemplateEngine}, 
 * a {@link MarkupLanguage} and a means of identifying the templet.
 */
public interface TempletLoader {

  /**
   * Return a templet by name.
   * @param templateEngine the TemplateEngine in use
   * @param markupLanguage the markuplanguage the templet is in
   * @param templetName the name of the templet
   * @return the templet
   * @throws NotFoundException if template not found
   */
  Template templet(TemplateEngine templateEngine, 
                   MarkupLanguage markupLanguage,
                   String templetName)
      throws NotFoundException;

  /**
   * Return a templet by name and purpose.
   * 
   * @param templateEngine the TemplateEngine in use
   * @param markupLanguage the markuplanguage the templet is in
   * @param purpose what the templet is for eg error
   * @param templetName the name of the templet
   * @return the templet
   * @throws NotFoundException if template not found
   */
  Template templet(TemplateEngine templateEngine, 
                   MarkupLanguage markupLanguage,
                   String purpose, String templetName)
      throws NotFoundException;
  
  /** 
   * Return a templet for a given class, looking for a template 
   * with the same name as the class in the Melati templet directory; giving a full template path as:  
   * <code>org/melati/template/TEMPLATE_ENGINE_NAME/MARKUP_LANGUAGE/java.lang.Object.wm</code>
   * which is the lowest possible template and is always found. 
   * 
   * The template is also looked for in the resource directory for that class.
   * 
   * The search results are cached, so that searches are not repeated.
   *
   * @param templateEngine the TemplateEngine in use
   * @param markupLanguage the markuplanguage the templet is in
   * @param clazz the class name to translate into a template name 
   * @return the templet
   */

  Template templet(TemplateEngine templateEngine, 
                   MarkupLanguage markupLanguage,
                   Class<?> clazz);

  /**
   * Return a templet by Class and Purpose.
   * 
   * The purpose search path is the normal search path with the 
   * purpose appended, separated by a "/".
   * 
   * If there is no template found then the normal search path is 
   * searched. 
   * 
   * The purpose is then re-appended to the supperclasses searchpath 
   * and so on up the tree, stopping at Object, which is always found.
   *    
   * @param templateEngine the TemplateEngine in use
   * @param markupLanguage the markuplanguage the templet is in
   * @param purpose what the templet is for eg error
   * @param clazz Class that templet renders 
   * @return the templet
   */
  Template templet(TemplateEngine templateEngine, 
                   MarkupLanguage markupLanguage,
                   String purpose, Class<?> clazz);

  /**
   * Return a templet to render a Field.
   * 
   * @param templateEngine the TemplateEngine in use
   * @param markupLanguage the markuplanguage the templet is in
   * @param attributes a FieldAttributes the templet is for eg a Field
   * @return the templet
   */
  Template templet(TemplateEngine templateEngine, 
                   MarkupLanguage markupLanguage,
                   FieldAttributes<?> attributes);
}
