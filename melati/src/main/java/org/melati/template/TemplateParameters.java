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
 *     Tim Joyce <timj@paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.template;

/**
 * This class just wraps up a context or a parameter class and a template name to be passed in and out to the servlet
 * 
 * @author Tim Joyce
 * $Revision$
 */
public class TemplateParameters 
{

  TemplateContext templateContext = null;
  String templateName = null;
  
  public TemplateParameters(String templateName, TemplateContext context) {
    this.templateName = templateName;
    this.templateContext = context;
  }
    
  /** 
   * get the template name
   */
  public String getTemplateName() {
    return templateName;
  }
  
  /** 
   * get the parameters
   */
  public TemplateContext getTemplateContext() {
    return templateContext;
  }
    
  /** 
   * set the template name
   */
  public void setTemplateName(String name) {
    templateName = name;
  }
  
  /** 
   * set the parameters
   */
  public void setContext(TemplateContext context) {
    this.templateContext = context;
  }

}






