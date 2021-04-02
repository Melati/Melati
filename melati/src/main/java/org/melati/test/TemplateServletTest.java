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

import java.io.OutputStream;

import org.melati.servlet.Form;
import org.melati.servlet.TemplateServlet;
import org.melati.servlet.TemporaryFileFormDataAdaptorFactory;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.servlet.PathInfoException;
import org.melati.PoemContext;
import org.melati.servlet.MultipartFormField;
import org.melati.template.ServletTemplateContext;
import org.melati.util.MelatiBugMelatiException;

/**
 * Test a Melati configuration with a Template Engine and a 
 * temporary file form data adaptor.
 *
 * @author Tim Joyce
 * $Revision$
 */
public class TemplateServletTest extends TemplateServlet {
  private static final long serialVersionUID = 1L;

  protected String doTemplateRequest(Melati melati,
                                     ServletTemplateContext templateContext) 
      throws Exception {
    
    String method = melati.getMethod();
    templateContext.put("RestrictedAccessObject", 
            new RestrictedAccessObject());
    templateContext.put("servletName", this.getClass().getName());
    if (method != null) {

      if (method.equals("Upload")) {
        MultipartFormField field = templateContext.getMultipartFormField("file");
        byte[] data = field.getData();
        melati.getResponse().setContentType(field.getContentType());
        OutputStream output = melati.getResponse().getOutputStream();
        output.write(data);
        output.close();
        return null;
      }

      if (method.equals("Redirect")) {
        melati.getResponse().sendRedirect("http://www.melati.org");
        return null;
      }
      if (method.equals("Exception")) {  

        throw new MelatiBugMelatiException("It got caught!");
      }
    }
    if (Form.getFieldNulled(melati.getServletTemplateContext(), "passback") != null)
      melati.setPassbackExceptionHandling();
    else
      // No need to explicitly do this as it is the default, other than test coverage
      melati.setPropagateExceptionHandling(); 

    return("org/melati/test/TemplateServletTest");
  }

/**
 * Set up the POEM context so we don't have to specify the 
 * logicaldatabase on the pathinfo.  
 *
 * Useful when writing applications where you are typically only accessing
 * a single database.
 */
  protected PoemContext poemContext(Melati melati)
  throws PathInfoException {
    return poemContextWithLDB(melati,"melatitest");
  }

  /** 
   * Set the form data adaptor.
   * 
   * @see org.melati.servlet.ConfigServlet#melatiConfig()
   */
  protected MelatiConfig melatiConfig() {
    MelatiConfig config = super.melatiConfig();
    TemporaryFileFormDataAdaptorFactory factory = 
        new TemporaryFileFormDataAdaptorFactory();
    config.setFormDataAdaptorFactory(factory);
    return config;
  }
  
}






