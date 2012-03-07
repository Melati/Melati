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
 *     Tim Pizey <timp AT paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.template;


/**
 * A Context for use by a {@link TemplateEngine} to expand a {@link Template} 
 * against.
 * 
 * TODO Extend Map
 * 
 * @see ServletTemplateContext
 */
public interface TemplateContext {
  
  /**
   * Put an object into the Context with a key.
   * 
   * @param key the key
   * @param value the object
   */
  Object put(String key, Object value);

  /**
   * Get an object from the Context by key.
   * 
   * @param s key
   * @return value
   */
  Object get(String s);

  /**
   * Return the Context itself, as an Object. 
   * 
   * @return the Context
   */
  Object getContext();

  /**
   * Set the current exception handling style.
   */
  void setPassbackExceptionHandling();

  /**
   * Set the current exception handling style.
   */
  void setPropagateExceptionHandling();

}

