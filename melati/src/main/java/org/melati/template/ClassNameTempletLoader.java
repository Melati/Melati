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

import java.util.Hashtable;

import org.melati.poem.FieldAttributes;

/**
 * Load a template to render an object based upon the object's class.
 */
public final class ClassNameTempletLoader implements TempletLoader {

  /** The instance. */
  private static ClassNameTempletLoader it = null;

  // NOTE It is not expected that templates will be added at runtime.
  private static Hashtable templetForClassCache = new Hashtable();
  private static Hashtable specialTemplateNames = new Hashtable();
  
  private static final Integer FOUND = new Integer(1);
  private static final Integer NOT_FOUND = new Integer(0);
  private static Hashtable lookedupTemplateNames = new Hashtable();

  /** Constructor. */
  private ClassNameTempletLoader() {
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

  /**
   * @return the instance
   */
  public static ClassNameTempletLoader getInstance() {
    if (it == null)
      it = new ClassNameTempletLoader();
    return it;
  }
  protected static String templetsPath(TemplateEngine templateEngine, 
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

  /**
   * @return the path in the templets directory
   */
  protected static String templetsTempletPath(TemplateEngine templateEngine,
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

  protected static String classpathTempletPath(Class clazz, TemplateEngine templateEngine) { 
    return clazz.getName().replace('.', '/') + templateEngine.templateExtension();
  }
  /**
   * Get a templet by name, with optional purpose. 
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine, AbstractMarkupLanguage, String, String)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          String name) throws TemplateEngineException {
    return templateEngine.template(templetsTempletPath(templateEngine, markupLanguage,
        purpose, name));
  }

  /**
   * Get a templet by its name, looking only in the templets directory.
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine, MarkupLanguage, String)
   */
  public Template templet(TemplateEngine templateEngine,
                                MarkupLanguage markupLanguage, String name)
      throws TemplateEngineException {
    return templet(templateEngine, markupLanguage, null, name);
  }

  /**
   * Get a templet based upon class name and optional purpose, 
   * looking in the templets directory and also the classpath.
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine, MarkupLanguage, 
   *                            String, Class)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          Class clazz)
      throws TemplateEngineException {
    Class lookupClass = clazz;
    Template templet = null;
    Template fromCache = null;
    String originalCacheKey = cacheKey(templateEngine, markupLanguage, purpose, lookupClass);
    String lookupCacheKey = originalCacheKey;
    while (lookupClass != null) {
      fromCache = (Template)templetForClassCache.get(lookupCacheKey);
      if (fromCache != null) {
        templet = fromCache;
        break;
      } 
      templet = getSpecialTemplate(lookupClass, purpose, markupLanguage, templateEngine);
      if (templet != null)
        break;
      // Try to find one in the templets directory
      String templetPath = templetsTempletPath(templateEngine, markupLanguage,
              purpose, lookupClass.getName());
      templet = getTemplate(templateEngine, templetPath);
      if (templet != null)
        break;
      // Try to find one on classpath
      templetPath = classpathTempletPath(lookupClass, templateEngine);
      templet = getTemplate(templateEngine, templetPath);
      if (templet != null)
        break;
      lookupClass = lookupClass.getSuperclass();
      lookupCacheKey = cacheKey(templateEngine, markupLanguage, purpose, lookupClass);
    }
    if (templet == null) throw new NotFoundException(this, lookupClass);
    if (fromCache == null)
      templetForClassCache.put(originalCacheKey, templet);
    if (!lookupCacheKey.equals(originalCacheKey)) { 
      if (templetForClassCache.get(lookupCacheKey) == null) 
        templetForClassCache.put(lookupCacheKey, templet);
    } 
    return templet;
  }

  private String cacheKey(TemplateEngine templateEngine, 
          MarkupLanguage markupLanguage, 
          String purpose, 
          Class lookupClass) {
    String originalClassCacheKey = lookupClass + "/" + 
                                   purpose + "/" + 
                                   markupLanguage + "/" + 
                                   templateEngine.getName();
    return originalClassCacheKey;
  }

  private Template getTemplate(TemplateEngine templateEngine, String templetPath) 
      throws TemplateEngineException { 
    Template templet = null;
    try {
      Object triedAlready = lookedupTemplateNames.get(templetPath);
      if (triedAlready != NOT_FOUND) {
        templet = templateEngine.template(templetPath);
        lookedupTemplateNames.put(templetPath, FOUND);
      } 
    } catch (NotFoundException e) {
      lookedupTemplateNames.put(templetPath, NOT_FOUND);
    }
    return templet;
  }
  
  private Template getSpecialTemplate(Class clazz, String purpose, 
          MarkupLanguage markupLanguage, TemplateEngine templateEngine) {
    Template templet = null;
    if (purpose == null) {
      String specialTemplateName =
          (String)specialTemplateNames.get(clazz.getName());
      if (specialTemplateName != null) {
        try {
          templet =
              templet(templateEngine, markupLanguage, specialTemplateName);
        } catch (TemplateEngineException e) {
          templet = null;
        }
      }
    }
    return templet;
  }

  /**
   * Get a templet for a class.
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine, MarkupLanguage, Class)
   */
  public Template templet(TemplateEngine templateEngine,
                                MarkupLanguage markupLanguage, Class clazz)
      throws TemplateEngineException {
    return templet(templateEngine, markupLanguage, null, clazz);
  }

  /**
   * Get a templet either from the FieldAttributes or 
   * the class name.
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine,MarkupLanguage,FieldAttributes)
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










