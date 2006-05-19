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
 *     Tim Pizey <timp@paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.template.velocity;

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Loads templates from the classpath, mungs them if they are WM 
 * templates.
 * 
 * 
 * Note that this does not allow modern WebMacro syntax with
 * optionional #begin in #foreach.
 *
 * @author Tim Pizey based on work by Jason van Zyl and Tim Joyce.
 * @see WebMacroFileResourceLoader
 */
public class WebMacroClasspathResourceLoader 
    extends ClasspathResourceLoader {
  
  /**
   * Get an InputStream so that the Runtime can build a
   * template with it, munge it if it is a WM template.
   *
   * @param templateName name of template to get
   * @return InputStream containing the template
   * @throws ResourceNotFoundException if template not found
   *         in the file template path.
   * @see org.apache.velocity.runtime.resource.loader.ResourceLoader
   */
  public InputStream getResourceStream(String templateName)
      throws ResourceNotFoundException {
    if (templateName.endsWith(".wm") || templateName.endsWith(".wmm")) {
      InputStream in = super.getResourceStream(templateName);
      if (in != null)
        return WebMacroConverter.convert(in);
      else 
        return null;
    } else {
      return super.getResourceStream(templateName);
    }
  }

}
