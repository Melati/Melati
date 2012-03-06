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
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.template.webmacro;

import java.io.IOException;

import org.melati.template.Template;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngineException;
import org.melati.template.TemplateIOException;
import org.melati.util.MelatiWriter;
import org.webmacro.Context;
import org.webmacro.FastWriter;
import org.webmacro.PropertyException;

/**
 * A Template for use with Melati.
 *
 */

public class WebmacroTemplate implements Template {
  private org.webmacro.Template webmacroTemplate;

  /**
   * Constructor.
   * @param t webmacro template
   */
  public WebmacroTemplate(org.webmacro.Template t) {
    webmacroTemplate = t;
  }

  /**
   * Write to the given output.
   * @param out a {@link MelatiWebmacroWriter}.
   * @throws IOException If WebMacro does
   */
  public void write(MelatiWriter out, TemplateContext templateContext) {
    try {
      if (out instanceof MelatiWebmacroWriter) {
        MelatiWebmacroWriter mww = (MelatiWebmacroWriter)out;
        FastWriter fw = mww.getFastWriter();
        try {
          webmacroTemplate.write(fw, (Context)templateContext.getContext());
        } catch (IOException e) {
          throw new TemplateIOException(e);
        }
        mww.stopUsingFastWriter(fw);
      } else {
        Object o = webmacroTemplate.evaluateAsString((Context)templateContext.getContext());
        try {
          out.write(o.toString());
        } catch (IOException e) {
          throw new TemplateIOException(e);
        }
      }
    } catch (PropertyException e) {
      throw new TemplateEngineException(e);
    } 
  }

}
