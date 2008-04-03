/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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
 *     http://paneris.org/~timp
 */

package org.melati.template.velocity;

import java.io.IOException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.MethodExceptionEventHandler;
import org.melati.template.MarkupLanguage;
import org.melati.template.TemplateEngineException;
import org.webmacro.PropertyException;

/**
 * @author timp
 * @since  1 Apr 2008
 *
 */
public class PassbackEvaluationExceptionHandler 
    extends PropagateEvaluationExceptionHandler 
    implements MethodExceptionEventHandler {

  /** The webcontext. */    
  private VelocityContext velContext;    

  /**
   * Constructor.
   */
  public PassbackEvaluationExceptionHandler(VelocityContext vc) {
    super();
    velContext = vc;
  }

  public Object methodException(Class claz, String method, Exception problem) throws Exception {
    System.err.println(method + " " + problem);
    MarkupLanguage ml = (MarkupLanguage)velContext.get("ml");
    if (ml == null) throw new PropertyException(
      "Error, to use the Passback Evaluation Exception Handler, you must " +
      "place your MarkupLanguage in the context as $ml - " + 
      method + ": " + problem, problem);
    Throwable underlying = problem;
    try {
      return ml.rendered(underlying);
    } catch (IOException e) {
      throw new TemplateEngineException(
        "The exception failed to render " + 
        method + ": " + e, e);
    }
    
  }

}
