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

package org.melati.template.webmacro;

import java.io.Writer;

import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.template.NotFoundException;

import org.webmacro.WM;
import org.webmacro.InitException;
import org.webmacro.servlet.WebContext;
import org.webmacro.engine.Variable;
import org.webmacro.engine.VariableException;
import org.webmacro.engine.PropagateVariableExceptionHandler;
import org.webmacro.engine.PassbackVariableExceptionHandler;


/**
 * Interface for a Template engine for use with Melati
 *
 * @author Tim Joyce
 * $Revision$
 */
public class WebmacroTemplateEngine implements TemplateEngine {


  public static final Object check =
  org.webmacro.engine.Variable.youNeedToBeUsingAVersionOfVariableHackedForMelati;

  // the webmacro
  public WM wm;

  /**
   * Inititialise the Engine
   */
  public void init () throws TemplateEngineException {
    try {
      wm = new WM ();
    } catch (InitException e) {
      // ensure we get the full error
      e.printStackTrace (System.err);
      throw new TemplateEngineException (e.toString ());
    }
  }

  /**
   * get the generic parameters for webmacro
   */
  public TemplateContext getTemplateContext (Melati melati) {
    WebContext wc = wm.getWebContext(melati.getRequest(),melati.getResponse());
    // always put a PropagateVariableExceptionHandler in otherwise
    // we never get our errors out!
    wc.put(Variable.EXCEPTION_HANDLER, PropagateVariableExceptionHandler.it);
    return new WebmacroTemplateContext(wc);
  }
  
  public Object getPassbackVariableExceptionHandler() {
    return PassbackVariableExceptionHandler.it;
  }

  /**
   * the name of the template engine (used to find the templets)
   */
  public String getName () {
    return "webmacro";
  }

  /**
  * the extension of the templates used by this template engine)
  */
  public String templateExtension() {
    return ".wm";
  }

  /**
   * the underlying engine
   */
  public Object getEngine () {
    return wm;
  }

  /**
   * get a template given it's name
   */
  public org.melati.template.Template template (String templateName)
  throws NotFoundException {
    try {
      return new WebmacroTemplate (wm.getTemplate (templateName));
    } catch (org.webmacro.NotFoundException e) {
      throw new NotFoundException ("I couldn't find the template: " +
      templateName + " because: " +e.toString ());
    }
  }

  /**
   * Expand the Template against the context.
   */
  public void expandTemplate
  (Writer out, String templateName, TemplateContext templateContext)
  throws TemplateEngineException, AccessPoemException {
    try {
      expandTemplate (out, template (templateName), templateContext);
    } catch (VariableException problem) {
      Exception underlying = problem.innermostException();
      if (underlying instanceof AccessPoemException) 
        throw (AccessPoemException)underlying;
    } catch (NotFoundException e) {
      throw new TemplateEngineException ("I couldn't find the template: " +
      templateName + " because: " +e.toString ());
    }
  }

  /**
   * Expand the Template against the context.
   */
  public void expandTemplate(Writer out,
  org.melati.template.Template template, TemplateContext templateContext)
  throws TemplateEngineException, AccessPoemException {
    template.write (out, templateContext, this);
  }


}
