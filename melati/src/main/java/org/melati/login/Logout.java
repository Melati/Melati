/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.login;

import javax.servlet.http.HttpSession;

import org.melati.Melati;
import org.melati.template.ServletTemplateContext;
import org.melati.servlet.Form;
import org.melati.servlet.TemplateServlet;

/**
 * A servlet which deletes login session cookies to 
 * log a user off from the system.
 *
 */
public class Logout extends TemplateServlet {
  private static final long serialVersionUID = 1L;

  protected String logoutTemplate(String name) {
    /*
    // Fails to find templates in jars!!
    return "org" + File.separatorChar + 
           "melati" + File.separatorChar +
           "login" + File.separatorChar + 
           name;
    */
    return "org/melati/login/" + name;
    }

  protected String logoutSuccessTemplate() {
    return logoutTemplate("Logout");
  }

  protected String doTemplateRequest(Melati melati, ServletTemplateContext context)
                                                            throws Exception {
    HttpSession session = melati.getSession();
    String returnURL = Form.getFieldNulled(context,"continuationURL");
    if (returnURL == null) {
      context.put("continuationURL", "/");
    } else {
      context.put("continuationURL", returnURL);
    }
    session.removeAttribute(HttpSessionAccessHandler.USER);

    return logoutSuccessTemplate();
  }
  
}
