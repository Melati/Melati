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

package org.melati.test;

import java.io.ByteArrayOutputStream;

import org.melati.template.ServletTemplateContext;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.servlet.PathInfoException;
import org.melati.servlet.TemplateServlet;
import org.melati.PoemContext;
import org.melati.template.webmacro.MelatiFastWriter;

import org.webmacro.WebMacro;
import org.webmacro.WM;
import org.webmacro.Context;
import org.webmacro.Template;

/**
 * Test WebMacro in standalone mode (outside the servlet API) by expanding a
 * template to a string and then including it within a template.
 * 
 * You would not normally do this this way, a much better approach would be to
 * use templets.
 * 
 * @author Tim Joyce $Revision$
 */
public class WebmacroMelatiServletTest extends TemplateServlet {

  protected String doTemplateRequest(Melati melati,
      ServletTemplateContext templateContext) throws Exception {

    if (melati.getMethod() != null && melati.getMethod().equals("StandAlone")) {
      // construct a Melati with a StringWriter instead of a servlet
      // request and response
      WebMacro wm = new WM();
      ByteArrayOutputStream sw = new ByteArrayOutputStream();
      MelatiFastWriter fmw = new MelatiFastWriter(wm.getBroker(), sw, melati
          .getEncoding());
      Melati m = new Melati(new MelatiConfig(), fmw);
      Context context2 = wm.getContext();
      context2.put("melati", m);
      context2.put("ml", m.getMarkupLanguage());
      Template template = wm.getTemplate("org/melati/test/StandAlone.wm");
      template.write(fmw.getOutputStream(), context2);
      fmw.flush();
      // write to the StringWriter
      String out = sw.toString();
      // finally, put what we have into the original templateContext
      templateContext.put("StandAlone", out);
    }
    return "org/melati/test/WebmacroMelatiServletTest";
  }

  /**
   * Set up the melati context so we don't have to specify the logicaldatabase
   * on the pathinfo.
   * 
   * This is a very good idea when writing your appications where you are
   * typically only accessing a single database
   */
  protected PoemContext poemContext(Melati melati) throws PathInfoException {
    return poemContextWithLDB(melati, "melatitest");
  }


}
