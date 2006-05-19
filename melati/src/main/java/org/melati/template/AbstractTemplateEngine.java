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
 *     Tim Pizey <timp@paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.template;

import org.melati.template.webmacro.PassbackEvaluationExceptionHandler;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;
import org.melati.util.StringUtils;

/**
 * Common elements of a TemplateEngine. 
 *
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {

  /**
   * Constructor.
   */
  public AbstractTemplateEngine() {
    super();
  }

  /** 
   * Get a template for a given class.
   *
   * @param clazz the class name to translate into a template name 
   * @return a template
   * @throws TemplateEngineException 
   */
  public org.melati.template.Template template(Class clazz)
      throws NotFoundException, TemplateEngineException {

    // Note File.separator will not find templates in jars
    // so we use forward slash
    String templateName = StringUtils.tr(clazz.getName(),
                                         ".", "/") 
                          + templateExtension();
    return template(templateName);
  }


  /**
   * @see org.melati.template.TemplateEngine#expandTemplate(MelatiWriter,Template, TemplateContext)
   */
  public abstract void expandTemplate(MelatiWriter out, Template template,
      TemplateContext templateContext) throws TemplateEngineException;
  
  /**
   * @see org.melati.template.TemplateEngine#expandedTemplate
   */
  public abstract String expandedTemplate(Template template,
      TemplateContext templateContext) throws TemplateEngineException;

  /** 
   * Get a variable exception handler for use if there is 
   * a problem accessing a variable.
   *
   * @return a <code>PassbackVariableExceptionHandler</code> 
   *         appropriate for this engine.
   */
  public Object getPassbackVariableExceptionHandler() {
    return new PassbackEvaluationExceptionHandler();
  }


  /** 
   * @deprecated Use {@link #getStringWriter()}.
   * @todo Delete this method. Suggest 2004.
   */
  public MelatiWriter getStringWriter(String encoding) {
    return getStringWriter();
  }


  /**
   * @see org.melati.template.TemplateEngine#getStringWriter()
   */
  public abstract MelatiStringWriter getStringWriter();

  /**
   * @see org.melati.template.TemplateEngine#getEngine()
   */
  public abstract Object getEngine();

}
