/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2005 Tim Pizey
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
 * Tim Pizey <timp@paneris.org>
 * http://paneris.org/~timp
 */

package org.melati.template.webmacro;


import org.melati.template.TemplateContext;
import org.webmacro.Context;
import org.webmacro.engine.EvaluationExceptionHandler;

/**
 * Implements a template context for Melati / Webmacro without a Servlet.
 * 
 */
public class WebmacroTemplateContext implements TemplateContext {

  Context context;

  /**
   * Constructor.
   * 
   * @param c a {@link Context} to use to evaluate against
   */
  public WebmacroTemplateContext(Context c) {
    context = c;
    // always put a PropagateVariableExceptionHandler in otherwise
    // we never get our errors out!
    context.setEvaluationExceptionHandler(
      new PropagateEvaluationExceptionHandler());
  }

  /**
   * Set a value in the Context.
   * 
   * @param s key
   * @param o value
   * @see org.melati.template.TemplateContext#put(java.lang.String, java.lang.Object)
   */
  public void put(String s, Object o) {
    context.put(s,o);
  }

  /**
   * Get a value by key.
   * 
   * @param s key
   * @return value
   * @see org.melati.template.TemplateContext#get(java.lang.String)
   */
  public Object get(String s) {
    return context.get(s);
  }

  /**
   * Get the context itself.
   * 
   * @return the context
   * @see org.melati.template.TemplateContext#getContext()
   */
  public Object getContext() {
    return context;
  }

  /**
   * Set the Variable Exception Handler.
   * 
   * @param veh the Variable Exception Handler
   * @see org.melati.template.TemplateContext#setVariableExceptionHandler(java.lang.Object)
   */
  public void setVariableExceptionHandler(Object veh) {
    context.setEvaluationExceptionHandler((EvaluationExceptionHandler)veh);
  }
}






