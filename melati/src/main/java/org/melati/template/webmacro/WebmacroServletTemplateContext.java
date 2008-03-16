/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
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
 * Tim Joyce <timj At paneris.org>
 *
 */

package org.melati.template.webmacro;

import javax.servlet.http.HttpSession;

import org.melati.template.ServletTemplateContext;
import org.melati.servlet.MultipartFormField;
import org.webmacro.Context;
import org.webmacro.servlet.WebContext;

/**
 * Implements a template context for Melati using Webmacro.
 * 
 * @author Tim Joyce
 */
public class WebmacroServletTemplateContext 
    extends WebmacroTemplateContext
    implements ServletTemplateContext {

  /** The webcontext. */
  public WebContext webContext;

  /**
   * Constructor.
   * @param wc WebMacro context
   */
  public WebmacroServletTemplateContext(Context wc) {
    super(wc);
    webContext = (WebContext)wc;
    // always put a PropagateVariableExceptionHandler in otherwise
    // we never get our errors out!
    webContext.setEvaluationExceptionHandler(
      new PropagateEvaluationExceptionHandler());
  }

  /**
   * WebMacro may or may not return null if field not present, so ensure it does.
   * {@inheritDoc}
   * @see org.melati.template.ServletTemplateContext#getForm(java.lang.String)
   */
  public String getForm(String k) {
    String s =  webContext.getForm(k);
    if (s != null && s.equals(""))
      s = null;
    return s;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.ServletTemplateContext#getMultipartForm(java.lang.String)
   */
  public MultipartFormField getMultipartForm(String s) {
    return null;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.ServletTemplateContext#getSession()
   */
  public HttpSession getSession() {
    return webContext.getSession();
  }
  
}






