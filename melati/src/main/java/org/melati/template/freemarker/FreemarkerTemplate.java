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
 *     http://paneris.org/
 */

package org.melati.template.freemarker;

import java.io.IOException;

import org.melati.template.Template;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.util.MelatiWriter;

import freemarker.template.TemplateException;

/**
 * A Freemarker Template for use with Melati.
 * 
 * @author timp
 * @since 6 Mar 2012
 *
 */
public class FreemarkerTemplate implements Template {

  private freemarker.template.Template template;


  public FreemarkerTemplate(freemarker.template.Template t) {
    this.template = t;
  }

  /**
   * @see org.melati.template.Template#write(org.melati.util.MelatiWriter, org.melati.template.TemplateContext)
   */
  @Override
  public void write(MelatiWriter out, TemplateContext templateContext) 
          throws TemplateEngineException {
    try {
      template.process(templateContext, out);
    } catch (TemplateException e) {
      throw new TemplateEngineException(e);
    } catch (IOException e) {
      throw new TemplateEngineException(e);
    }
  }

}