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
import java.io.File;

import org.melati.poem.FieldAttributes;

public class ClassNameTempletLoader implements TempletLoader {

  public static final ClassNameTempletLoader it = new ClassNameTempletLoader();

  private Hashtable defaultTempletOfPoemType = new Hashtable();

  private Hashtable specialTemplateNames = new Hashtable();

  public ClassNameTempletLoader() {
      // These templates cannot be overridden
    specialTemplateNames.put("org.melati.poem.ColumnTypePoemType", "select");
    specialTemplateNames.put("org.melati.poem.RestrictedReferencePoemType", "select");
    specialTemplateNames.put("org.melati.poem.ReferencePoemType", "select");
    specialTemplateNames.put("org.melati.poem.DisplayLevelPoemType", "select");
    specialTemplateNames.put("org.melati.poem.SearchabilityPoemType", "select");
    specialTemplateNames.put("org.melati.poem.IntegrityFixPoemType", "select");
  }

  protected String templetsPath(TemplateEngine templateEngine, 
                                MarkupLanguage markupLanguage) {
    return "org" + File.separatorChar + 
           "melati" + File.separatorChar + 
           "template" + File.separatorChar + 
            templateEngine.getName() + File.separatorChar + 
           "templets" + File.separatorChar +
            markupLanguage.getName() + File.separatorChar;
  }

  protected String templetPath(TemplateEngine templateEngine,
                               MarkupLanguage markupLanguage,
                               String purpose, String name) {
    if (purpose == null)
      return 
                 templetsPath(templateEngine, markupLanguage) + 
                 name +
                 templateEngine.templateExtension();
    else
      return 
                 templetsPath(templateEngine, markupLanguage) + 
                 purpose + File.separatorChar + 
                 name +
                 templateEngine.templateExtension();
  }

  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          String name) throws NotFoundException {
    return templateEngine.template(templetPath(templateEngine, markupLanguage,
                                               purpose, name));
  }

  public final Template templet(TemplateEngine templateEngine,
                                MarkupLanguage markupLanguage, String name)
      throws NotFoundException {
    return templet(templateEngine, markupLanguage, null, name);
  }

  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage, String purpose,
                          Class clazz)
      throws NotFoundException {

    String cacheKey = clazz + "/" + purpose + "/" + markupLanguage;
    Template templet = (Template)defaultTempletOfPoemType.get(cacheKey);

    if (templet == null && purpose == null) {
      String specialTemplateName =
          (String)specialTemplateNames.get(clazz.getName());
      if (specialTemplateName != null) {
        templet =
            templet(templateEngine, markupLanguage, specialTemplateName);
      }
    }
    if (templet == null) {
      while (clazz != null) {
        try {
          templet = templet(templateEngine, markupLanguage,
                            purpose, clazz.getName());
          break;
        } catch (NotFoundException e) {}
        clazz = clazz.getSuperclass();
      }
      if (templet == null) throw new 
                             ClassTempletNotFoundException(this, clazz);
      defaultTempletOfPoemType.put(cacheKey, templet);
    }

    return templet;
  }

  public final Template templet(TemplateEngine templateEngine,
                                MarkupLanguage markupLanguage, Class clazz)
      throws NotFoundException {
    return templet(templateEngine, markupLanguage, null, clazz);
  }

  public Template templet(TemplateEngine templateEngine,
                          MarkupLanguage markupLanguage,
                          FieldAttributes attributes)
      throws NotFoundException {
    if (attributes.getRenderInfo() != null)
      return templet(templateEngine, markupLanguage,
                     attributes.getRenderInfo());
    else {
      try {
        return templet(templateEngine, markupLanguage,
                       attributes.getType().getClass());
      }
      catch (ClassTempletNotFoundException e) {
        throw new DefaultTempletNotFoundException(this, attributes);
      }
    }
  }
}










