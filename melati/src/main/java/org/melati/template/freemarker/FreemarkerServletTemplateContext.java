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
 *    
 */    
    
package org.melati.template.freemarker;    
    
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;    
    
import org.melati.template.ServletTemplateContext;    
import org.melati.template.SimpleForm;
import org.melati.util.DelegatedHttpServletRequest;
import org.melati.util.MelatiBugMelatiException;
import org.melati.servlet.MultipartFormField;    
    
/**    
 * Implements a template context for Melati with Freemarker.
 * 
 * The Request and Response are placed in the context 
 * so that they can be accessed by the Form object. 
 *     
 * @author Tim Pizey    
 */    
public class FreemarkerServletTemplateContext 
    extends FreemarkerTemplateContext 
    implements ServletTemplateContext {    
    
 /**    
  * The HTTP request object context key.    
  */    
  public static final String REQUEST = "Request";    
    
 /**    
  * The HTTP response object context key.    
  */    
  public static final String RESPONSE = "Response";    
    
  /** Mimicking the $Form behaviour of Webmacro. */
  public static final String FORM = "Form";
    
  private SimpleForm form;
  
  /**
   * Constructor.
   * @param c context containing RESPONSE and REQUEST
   */
  public FreemarkerServletTemplateContext(Map<String, Object>  c) {
    super(c);
    form = new SimpleForm((HttpServletRequest)context.get(REQUEST));
    context.put(FORM, form);
  }    
    
  /**
   * {@inheritDoc}
   * @see org.melati.template.ServletTemplateContext#getFormField(java.lang.String)
   */
  public String getFormField(String s) {    
    return form.get(s);    
  }    
    
  /**
   * Returns null as this is not a multi part form.
   * Should perhaps throw an exception.
   * @see org.melati.template.ServletTemplateContext#getMultipartFormField(java.lang.String)
   */
  public MultipartFormField getMultipartFormField(String s) {    
    throw new MelatiBugMelatiException("Cannot return a multi-part field from a non-multi-part form");
  }    
    
  /**
   * {@inheritDoc}
   * @see org.melati.template.ServletTemplateContext#getSession()
   */
  public HttpSession getSession() {    
    return ((DelegatedHttpServletRequest)context.get(REQUEST)).getSession(true);    
  }


  @Override
  public void setPassbackExceptionHandling() {
    // TODO Auto-generated method stub

  }

  @Override
  public void setPropagateExceptionHandling() {
    // TODO Auto-generated method stub

  }

}
