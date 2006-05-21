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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.template;

import java.util.Hashtable;

import org.melati.poem.FieldAttributes;

/**
 * Load a template to render an object based upon the object's class.
 */
public class ClassNameTempletLoader implements TempletLoader {

  /** The instance. */
  public static final ClassNameTempletLoader it = new ClassNameTempletLoader();

  private Hashtable templetForClassCache = new Hashtable();

  private Hashtable specialTemplateNames = new Hashtable();

  /** Constructor. */
  public ClassNameTempletLoader() {
      // These templates cannot be overridden
    specialTemplateNames.put("org.melati.poem.ColumnTypePoemType", 
                             "select");
    specialTemplateNames.put("org.melati.poem.RestrictedReferencePoemType", 
                             "select");
    specialTemplateNames.put("org.melati.poem.ReferencePoemType", 
                             "select");
    specialTemplateNames.put("org.melati.poem.DisplayLevelPoemType", 
                             "select");
    specialTemplateNames.put("org.melati.poem.SearchabilityPoemType", 
                             "select");
    specialTemplateNames.put("org.melati.poem.IntegrityFixPoemType", 
                             "select");
  }

  protected String templetsPath(TemplateEngine templateEngine, 
                                MarkupLanguage markupLanguage) {
    /*
    // Fails to find templates in jars!!
    return "org" + File.separatorChar + 
           "melati" + File.separatorChar + 
           "template" + File.separatorChar + 
            templateEngine.getName() + File.separatorChar + 
           "templets" + File.separatorChar +
            markupLanguage.getName() + File.separatorChar;
    */
    return "org/melati/template/" +  
           templateEngine.getName() +  
           "/templets/" + 
           markupLanguage.getName() + "/";
    
    }

  protected String templetPath(TemplateEngine templateEngine,
                               MarkupLanguage markupLanguage,
                               String purpose, String name) {
    if (purpose == null)
      return 
                 templetsPath(templateEngine, markupLanguage) + 
                 name +
                 templateEngine.templateExtension();
    return 
               templetsPath(templateEngine, markupLanguage) + 
               purpose + "/" + 
               name +
               templateEngine.templateExtension();
  }

  /**
   * Get a templet by name, with optional purpose. 
   * 
   * @see TempletLoader#templet(TemplateEngine, AbstractMarkupLanguage, String, String)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          String name) throws TemplateEngineException {
    return templateEngine.template(templetPath(templateEngine, markupLanguage,
        purpose, name));
  }

  /**
   * Get a templet by its name. 
   * 
   * @see TempletLoader#templet(TemplateEngine, AbstractMarkupLanguage, String)
   */
  public final Template templet(TemplateEngine templateEngine,
                                MarkupLanguage markupLanguage, String name)
      throws TemplateEngineException {
    return templet(templateEngine, markupLanguage, null, name);
  }

  /**
   * Get a templet based upon class name and optional purpose. 
   * 
   * @see TempletLoader#templet(TemplateEngine, AbstractMarkupLanguage, 
   *                            String, Class)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          Class clazz)
      throws TemplateEngineException {

    String cacheKey = clazz + "/" + purpose + "/" + markupLanguage;
    Template templet = (Template)templetForClassCache.get(cacheKey);

    if (templet == null && purpose == null) {
      String specialTemplateName =
          (String)specialTemplateNames.get(clazz.getName());
      if (specialTemplateName != null) {
        templet =
            templet(templateEngine, markupLanguage, specialTemplateName);
      }
    }
    if (templet == null) {
      Class lookupClass = clazz;
      while (lookupClass != null) {
        try {
          // try and find one in templets directory
          templet = templet(templateEngine, markupLanguage,
                            purpose, lookupClass.getName());
          break;
        } catch (NotFoundException e) {
          // try the next one up
          e = null; // shut PMD up          
        }
        // Try to find one in classpath
        try {
          templet = templet(templateEngine, lookupClass);
          break;
        } catch (NotFoundException e2) {
          e2 = null; // shut PMD up
        }
        lookupClass = lookupClass.getSuperclass();
      }
      if (templet == null) throw new NotFoundException(this, clazz);
      templetForClassCache.put(cacheKey, templet);
    }

    return templet;
  }

  /**
   * Try to find a template in the same place as a class file. 
   * 
   * @param templateEngine Our configured TemplateEngine.
   * @param clazz 
   * @return A Template if found, otherwise throws exception
   * @throws TemplateEngineException 
   */
  private Template templet(TemplateEngine templateEngine, Class clazz) 
      throws TemplateEngineException {
    return templateEngine.template(
               clazz.getName().replace('.', '/') + 
               templateEngine.templateExtension());
  }

  /**
   * Get a templet name based upon class name.
   * 
   * @see TempletLoader#templet(TemplateEngine, AbstractMarkupLanguage, Class)
   */
  public final Template templet(TemplateEngine templateEngine,
                                MarkupLanguage markupLanguage, Class clazz)
      throws TemplateEngineException {
    return templet(templateEngine, markupLanguage, null, clazz);
  }

  /**
   * Get a templet either from the FieldAttributes or 
   * the class name.
   * 
   * @see TempletLoader#templet(TemplateEngine,AbstractMarkupLanguage,FieldAttributes)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage,
                          FieldAttributes attributes)
      throws TemplateEngineException {
    if (attributes.getRenderInfo() != null)
      return templet(templateEngine, markupLanguage,
                     attributes.getRenderInfo());
    else {
      try {
        return templet(templateEngine, markupLanguage,
                       attributes.getType().getClass());
      }
      catch (NotFoundException e) {
        throw new DefaultTempletNotFoundException(this, attributes);
      }
    }
  }
}










