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
 */
package org.melati.template.velocity;


import java.util.Map;

import org.apache.velocity.app.event.EventCartridge;
import org.melati.template.TemplateContext;

/**
 * @author timp
 * @since  14 Apr 2008
 *
 */
public class VelocityTemplateContext implements TemplateContext {

  /** The velocity context. */
  public org.apache.velocity.VelocityContext velocityContext;

  /**
   * Constructor.
   * @param vc context
   */
  public VelocityTemplateContext(org.apache.velocity.VelocityContext vc) {    
    velocityContext = vc;
    setPropagateExceptionHandling();
  }    

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#put(java.lang.String, java.lang.Object)
   */
  public Object put(String s, Object o) {    
    return velocityContext.put(s,o);    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#get(java.lang.String)
   */
  public Object get(String s) {    
    return velocityContext.get(s);    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#getContext()
   */
  public Object getContext() {    
    return velocityContext;    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#setPassbackExceptionHandling()
   */
  public void setPassbackExceptionHandling() {
    EventCartridge ec = velocityContext.getEventCartridge();    
    if (ec == null) {    
      ec = new EventCartridge();    
      velocityContext.attachEventCartridge(ec);    
    }    
    ec.addEventHandler(new PassbackEvaluationExceptionHandler(velocityContext));        
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#setPropagateExceptionHandling()
   */
  public void setPropagateExceptionHandling() {
    EventCartridge ec = velocityContext.getEventCartridge();    
    if (ec == null) {    
      ec = new EventCartridge();    
      velocityContext.attachEventCartridge(ec);    
    }    
    ec.addEventHandler(new PropagateEvaluationExceptionHandler());        
  }

  @Override
  public void putAll(Map<String, Object> m) {
    for (String k : m.keySet()) { 
      velocityContext.put(k,m.get(k));
    }
  }
}