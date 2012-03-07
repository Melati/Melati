/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2012 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 */
package org.melati.template.freemarker;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.melati.poem.AccessPoemException;
import org.melati.template.AbstractTemplateEngine;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.template.NotFoundException;
import org.melati.template.Template;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiWriter;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * Wrapper for the Freemarker Template Engine for use with Melati.
 * @author timp
 * @since 6 Mar 2012
 *
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine implements TemplateEngine {

  /** The name of the engine. */
  public static final String NAME = "freemarker";
  
  private Configuration config;

  /**
   * @see org.melati.template.TemplateEngine#init(org.melati.MelatiConfig)
   */
  @Override
  public void init(MelatiConfig melatiConfig) throws TemplateEngineException {
    try {
      config = new Configuration();
      config.setClassForTemplateLoading(this.getClass(), "/"); // note first argument redundant
      config.setObjectWrapper(new DefaultObjectWrapper());
    } catch (Exception e) {
      throw new TemplateEngineException(e);
    }

  }

  /**
   * @see org.melati.template.TemplateEngine#getTemplateContext(org.melati.Melati)
   */
  @Override
  public TemplateContext getTemplateContext(Melati melati) throws TemplateEngineException {
    Map<String, Object> context = new HashMap<String, Object>();
    return new FreemarkerServletTemplateContext(context);
  }

  /**
   * @see org.melati.template.TemplateEngine#getName()
   */
  @Override
  public String getName() {
    return NAME;
  }

  /**
   * @see org.melati.template.TemplateEngine#templateExtension()
   */
  @Override
  public String templateExtension() {
    return ".vm";
  }

  /**
   * @see org.melati.template.TemplateEngine#template(java.lang.String)
   */
  @Override
  public Template template(String templateName) throws NotFoundException {
    freemarker.template.Template t;
    try {
      t = config.getTemplate(templateName);
    } catch (FileNotFoundException e) {
      throw new NotFoundException("Could not find template " + templateName, e);
    } catch (Exception e) {
      throw new TemplateEngineException(e);
    }
    return new FreemarkerTemplate(t);
  }


  /**
   * @see org.melati.template.TemplateEngine#getTemplateName(java.lang.String, java.lang.String)
   */
  @Override
  public String getTemplateName(String key, String classifier) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see org.melati.template.TemplateEngine#expandTemplate(org.melati.util.MelatiWriter, java.lang.String, org.melati.template.TemplateContext)
   */
  @Override
  public void expandTemplate(MelatiWriter out, String templateName, TemplateContext templateContext) throws NotFoundException {
    expandTemplate(out, template(templateName), templateContext);
  }

  /**
   * Expand a {@link org.melati.template.Template} against the context.
   * 
   * @param out
   *        a {@link MelatiWriter} to output on
   * @param template
   *        the {@link org.melati.template.Template} to expand
   * @param templateContext
   *        the {@link TemplateContext} to expand the template against
   * @see org.melati.template.TemplateEngine#expandTemplate(org.melati.util.MelatiWriter, org.melati.template.Template, org.melati.template.TemplateContext)
   */
  @Override
  public void expandTemplate(MelatiWriter out, Template template, TemplateContext templateContext) {
    try {
      template.write(out, templateContext);
    } catch (TemplateEngineException problem) {
      Exception underlying = problem.subException;
      if (underlying instanceof AccessPoemException) {
        throw (AccessPoemException)underlying;
      }
      throw problem;
    } 

  }

  /**
   * @see org.melati.template.TemplateEngine#expandedTemplate(org.melati.template.Template, org.melati.template.TemplateContext)
   */
  @Override
  public String expandedTemplate(Template template, TemplateContext templateContext) {
    MelatiStringWriter s = new MelatiStringWriter();
    expandTemplate(s, template, templateContext);
    return s.toString();
  }

  /**
   * @see org.melati.template.TemplateEngine#getStringWriter()
   */
  @Override
  public MelatiStringWriter getStringWriter() {
    return new MelatiStringWriter();
  }

  /**
   * Get the underlying engine.
   * 
   * @see org.melati.template.TemplateEngine#getEngine()
   * @return null - for velocity there is none.
   */
  @Override
  public Object getEngine() {
    return null;
  }

}
