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


import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Capability;
import org.melati.poem.PoemThread;
import org.melati.servlet.TemplateServlet;
import org.melati.servlet.PathInfoException;
import org.melati.servlet.MelatiContext;
import org.melati.template.TemplateContext;
import org.melati.util.MelatiException;

/**
 * Base class to use Melati with Servlets.
 * Simply extend this class, override the doRequest method
 *
 * @author Tim Joyce
 * $Revision$
 */

/**
 * Illustrate exception handling.
 *
 */
public class TemplateServletTestOverride extends TemplateServlet {

  protected String doTemplateRequest(Melati melati,
                                     TemplateContext templateContext) 
      throws Exception {

    // provoke an exception to prove us right!
    Capability admin = PoemThread.database().getCanAdminister();
    AccessToken token = PoemThread.accessToken();
    throw new AccessPoemException(token, admin);
  }

/**
 * Demonstrates how to use a different melati configuration.
 *
 */
  protected MelatiConfig melatiConfig() throws MelatiException {
    MelatiConfig config = super.melatiConfig();
    config.setAccessHandler(new HttpBasicAuthenticationAccessHandler());
    return config;
  }

/**
 * set up the melati context so we don't have to specify the 
 * logicaldatabase on the pathinfo.  
 *
 * This is a very good idea when
 * writing your appications where you are typically only accessing
 * a single database
 */
  protected MelatiContext melatiContext(Melati melati)
      throws PathInfoException {
    return melatiContextWithLDB(melati,"melatitest");
  }
  
}






