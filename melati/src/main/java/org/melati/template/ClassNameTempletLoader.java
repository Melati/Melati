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
import org.melati.util.MelatiBugMelatiException;

import java.util.Hashtable;

/**
 * Load a template to render an object based upon the object's class.
 */
public final class ClassNameTempletLoader implements TempletLoader {

  /** The instance. */
  private static ClassNameTempletLoader it = null;

  // NOTE It is not expected that templates will be added at runtime.
  private static Hashtable<String,Template> templetForClassCache = new Hashtable<String,Template>();
  
  private static final Integer FOUND = new Integer(1);
  private static final Integer NOT_FOUND = new Integer(0);
  private static Hashtable<String,Integer> lookedupTemplateNames = new Hashtable<String,Integer>();

  /** Disable instantiation. */
  private ClassNameTempletLoader() {}

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
    // Fails to find templates in jars on Windows!!
    return "org" + File.separatorChar +
           "melati" + File.separatorChar +
           "template" + File.separatorChar +
            templateEngine.getName() + File.separatorChar +
           "templets" + File.separatorChar +
            markupLanguage.getName() + File.separatorChar;
    */
    return "org/melati/templets/" + 
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

  protected static String classpathTempletPath(Class<?> clazz, String purpose, TemplateEngine templateEngine) {
    if (purpose == null) {
      return clazz.getName().replace('.', '/') + templateEngine.templateExtension();
    } else {
      return clazz.getPackage().getName().replace('.', '/')
          + "/" + purpose
          + "/" + clazz.getSimpleName()
          + templateEngine.templateExtension();
    }
  }

  /**
   * Get a templet by name, with optional purpose. 
   * 
   * @see TempletLoader#templet
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          String name) throws NotFoundException {
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
      throws NotFoundException {
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
                          Class<?> clazz)
      throws TemplateEngineException {
    Class<?> lookupClass = clazz;
    Template templet = null;
    Template fromCache = null;
    String originalCacheKey = cacheKey(templateEngine, markupLanguage, purpose, lookupClass);
    String lookupCacheKey = originalCacheKey;
    String lookupPurpose = purpose;
    while (true) {
      fromCache = (Template)templetForClassCache.get(lookupCacheKey);
      if (fromCache != null) {
        templet = fromCache;
        break;
      }
      /*
      // FIXME currently we only have specialised templets for fields
      templet = getSpecialTemplate(lookupClass, lookupPurpose, markupLanguage, templateEngine);
      if (templet != null) {
        break;
      }
      */
      // Try to find one in the templets directory
      String templetPath = templetsTempletPath(templateEngine, markupLanguage,
              lookupPurpose, lookupClass.getName());
      templet = getTemplate(templateEngine, templetPath);
      if (templet != null)
        break;
      // Try to find one on classpath
      templetPath = classpathTempletPath(lookupClass, purpose, templateEngine);
      templet = getTemplate(templateEngine, templetPath);
      if (templet != null)
        break;
      
      if (lookupPurpose != null)
        lookupPurpose = null;
      else { 
        lookupClass = lookupClass.getSuperclass();
        lookupPurpose = purpose;
      }
      lookupCacheKey = cacheKey(templateEngine, markupLanguage, lookupPurpose, lookupClass);
    }
    // We should have at last found Object template    
    //if (templet == null)
    //  throw new MelatiBugMelatiException("Cannot even find template for Object");
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
      Class<?> lookupClass) {
    return  purpose == null ? cacheKey(templateEngine, markupLanguage, lookupClass) 
                            : lookupClass + "/" + 
                               purpose + "/" + 
                               markupLanguage + "/" + 
                               templateEngine.getName();
  }
  
  private String cacheKey(TemplateEngine templateEngine, 
      MarkupLanguage markupLanguage, 
      Class<?> lookupClass) {
    return lookupClass + 
           "/" + markupLanguage + 
           "/" + templateEngine.getName();
  }

  private Template getTemplate(TemplateEngine templateEngine, String templetPath)  { 
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

  /**
   * Get a templet for a class.
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine, MarkupLanguage, Class)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, Class<?> clazz) {
    return templet(templateEngine, markupLanguage, null, clazz);
  }

  /**
   * Get a templet either from the classname concatenated with 
   * FieldAttributes.RenederInfo or the class name.
   * 
   * {@inheritDoc}
   * @see TempletLoader#templet(TemplateEngine,MarkupLanguage,FieldAttributes)
   */
  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage,
                          FieldAttributes<?> attributes) {
    if (attributes.getRenderInfo() != null) {
      String templetName = attributes.getType().getClass().getName() 
          + "-"
          + attributes.getRenderInfo();
      try {
        return templet(templateEngine, markupLanguage, 
                templetName);
      } catch (NotFoundException e) {
        throw new MelatiBugMelatiException(
                "Templet " + templetName  + " not found", e);
      }
    } else {
        return templet(templateEngine, markupLanguage,
                attributes.getType().getClass());
    }
  }
}










