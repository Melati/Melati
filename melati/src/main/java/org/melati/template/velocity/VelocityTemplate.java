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
 *     http://paneris.org/
 */

package org.melati.template.velocity;

import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiWriter;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeSingleton;

/**
 * A Velocity Template for use with Melati.
 */

public class VelocityTemplate implements org.melati.template.Template {
  private org.apache.velocity.Template template;

  /**
   * Constructor.
   * @param templateName name or Template
   * @throws ParseErrorException if there is a problem paring the template
   * @throws ResourceNotFoundException if the template is not found
   */
  public VelocityTemplate(String templateName) 
      throws ParseErrorException, ResourceNotFoundException {
    try {
      template = RuntimeSingleton.getTemplate(templateName);
    } catch (ResourceNotFoundException e) {
      throw e;
    } catch (ParseErrorException e) {
      throw e;
    } catch (Exception e) {
      throw new MelatiBugMelatiException("Problem getting Velocity template", e);
    }
  }
  
  /**
   * {@inheritDoc}
   * @see org.melati.template.Template#write
   * (org.melati.util.MelatiWriter, org.melati.template.TemplateContext)
   */
  public void write(MelatiWriter out, TemplateContext templateContext) throws TemplateEngineException {
    try {
      template.merge((VelocityContext)templateContext.getContext(),out);
    } catch (Exception e) {
      throw new TemplateEngineException(e);
    }
  }

}
