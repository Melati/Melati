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

import java.io.Writer;
import org.melati.MelatiContext;

/**
 * Interface for a Template engine for use with Melati
 *
 * @author Tim Joyce
 * $Revision$
 */
public interface TemplateEngine
{

  /**
   * Construct a new Engine
   */
  public void init() throws TemplateEngineException;

  /**
   * get the generic parameters for this engine
   */
  public TemplateContext getTemplateContext(MelatiContext melatiContext)
  throws TemplateEngineException;

  /**
  * the name of the template engine (used to find the templets)
  */
  public String getName();
  
  /** 
   * get a template given it's name
  */
  public Template template(String templateName) throws NotFoundException;
  
  /** 
   * Expand the Template against the context.
   */
  public void expandTemplate(Writer out, TemplateContext templateContext) throws TemplateEngineException;
  
  /** 
   * Expand the Template against the context.
   */
  public void expandTemplate(Writer out, String templateName, TemplateContext templateContext) throws TemplateEngineException;
  
  /** 
   * Expand the Template against the context.
   */
  public void expandTemplate(Writer out, Template template, TemplateContext templateContext) throws TemplateEngineException;

    
  /** 
   * get the underlying engine
   */
  public Object getEngine();

}






