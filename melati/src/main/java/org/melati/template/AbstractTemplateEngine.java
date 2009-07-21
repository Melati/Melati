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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.template;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Common elements of a TemplateEngine. 
 *
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {

  protected Vector<String> roots = new Vector<String>();
  
  /**
   * Constructor.
   */
  public AbstractTemplateEngine() {
    super();
    addRoot("");
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.TemplateEngine#getTemplateName(java.lang.String, java.lang.String)
   */
  public String getTemplateName(String key, String classifier) {
    String templateResourceName = null;
    Enumeration<String> theRoots = getRoots();    
    while(theRoots.hasMoreElements()) { 
      String root = (String)theRoots.nextElement();
      templateResourceName = "/" + emptyOrSlashEnded(root) + 
                             emptyOrSlashEnded(classifier) +  
                             key + 
                             templateExtension();
      if (this.getClass().getResource(templateResourceName) == null) {
        templateResourceName = "/" + emptyOrSlashEnded(root) +
                               key + templateExtension();
      } else 
        break; 
       
      if (this.getClass().getResource(templateResourceName) != null) 
        break;
      else
        templateResourceName = null;
    }
    if (templateResourceName == null)
      return key;
    else
      return templateResourceName;
  }
  
  private String emptyOrSlashEnded(String in) { 
    if (in == null) 
      return "";
    if (in.equals(""))
      return "";
    if(in.endsWith("/"))
      return in;
    
    return in + "/";
  }


  /** 
   * {@inheritDoc}
   * @see org.melati.template.TemplateEngine#getRoots()
   */
  public Enumeration<String> getRoots() {
    return roots.elements();
  }
  
  /** 
   * Add root at index 0, 
   * so that the empty string is always returned last by <code>elements</code>.
   * @see org.melati.template.TemplateEngine#addRoot(java.lang.String)
   */
  public void addRoot(String root) { 
    roots.insertElementAt(root,0);
  }
  
}
