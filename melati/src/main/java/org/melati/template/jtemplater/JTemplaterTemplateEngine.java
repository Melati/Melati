/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.template.jtemplater;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.melati.Melati;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.template.NotFoundException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiStringWriter;
import org.melati.util.MelatiSimpleWriter;
import org.melati.util.MelatiBufferedWriter;

import org.melati.jtemplater.JTemplater;

/**
 * Interface for a Template engine for use with Melati
 *
 * @author Tim Joyce
 * $Revision$
 */
public class JTemplaterTemplateEngine implements TemplateEngine
{

  // the jTemplater
  public JTemplater jt;

  /**
   * Inititialise the Engine
   */
  public void init() throws TemplateEngineException {
    try {
      jt = new JTemplater();
    } catch (Exception e) {
      throw new TemplateEngineException(e);
    }
  }

  /**
   * the name of the template engine (used to find the templets)
   */
  public String getName() {
    return "jtemplater";
  }

  /**
  * the extension of the templates used by this template engine)
  */
  public String templateExtension() {
    return ".jt";
  }

  public Object getEngine() {
    return jt;
  }

  public MelatiWriter getServletWriter(HttpServletResponse response, 
                                       boolean buffered) 
          throws IOException {
    if (buffered) {
      return new MelatiBufferedWriter(response.getWriter());
    } else {
      return new MelatiSimpleWriter(response.getWriter());
    }
  }

  public MelatiWriter getStringWriter(String encoding) 
          throws IOException {
    return new MelatiStringWriter();
  }
  
  public TemplateContext getTemplateContext(Melati melati)
  throws TemplateEngineException {
    return new JTemplaterTemplateContext(melati);
  }

  public org.melati.template.Template template(String templateName)
  throws NotFoundException {
    try {
      return new JTemplaterTemplate
      (jt.getTemplate(templateName,JTemplaterTemplateContext.getClazz()));
    } catch (Exception e) {
      throw new NotFoundException("I couldn't get the template: " +
      templateName + " because: " +e.toString());
    }
  }

  public void expandTemplate(MelatiWriter out, String templateName, 
                             TemplateContext templateContext)
  throws TemplateEngineException {
    try {
      expandTemplate(out, template(templateName), templateContext);
    } catch (NotFoundException e) {
      throw new TemplateEngineException(e);
    }
  }

  public Object getPassbackVariableExceptionHandler() {
    return null;
  }

  /**
   * Expand the Template against the context.
   */
  public void expandTemplate(MelatiWriter out, 
                             org.melati.template.Template template,
                             TemplateContext templateContext) 
                                 throws TemplateEngineException {
    template.write(out, templateContext, this);
  }

}






