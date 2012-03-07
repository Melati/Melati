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
 *     Tim Joyce <timj At paneris.org>    
 *    
 */    
    
package org.melati.template.velocity;    
    
import javax.servlet.http.HttpSession;    
    
import org.melati.template.ServletTemplateContext;    
import org.melati.template.SimpleForm;
import org.melati.util.DelegatedHttpServletRequest;
import org.melati.util.MelatiBugMelatiException;
import org.melati.servlet.MultipartFormField;    
import org.apache.velocity.VelocityContext;    
    
/**    
 * Implements a template context for Melati with Velocity.
 * 
 * The Request and Response are placed in the context 
 * so that they can be accessed by the Form object. 
 *     
 * @author Tim Joyce    
 */    
public class VelocityServletTemplateContext 
    extends VelocityTemplateContext 
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
   * @param vc context containing RESPONSE and REQUEST
   */
  public VelocityServletTemplateContext(VelocityContext vc) {
    super(vc);
    form = new SimpleForm((DelegatedHttpServletRequest)velocityContext.get(REQUEST));
    velocityContext.put(FORM, form);
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
    return ((DelegatedHttpServletRequest)velocityContext.get(REQUEST)).getSession(true);    
  }
    
}    
    
    
    
    
    
    
