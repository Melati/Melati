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
 *     Tim Pizey <timp@paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.app;

import org.melati.Melati;
import org.melati.template.TemplateContext;

/**
 * An example of how to use a Template Engine with a poem database 
 * from the command line. 
 * 
 * Invoke: 
 * 
 * java -cp melati.jar:site\properties:lib\hsqldb.jar: \
 *  lib\webmacro.jar:lib\servlet.jar \
 *  org.melati.app.PoemTemplateExample poemtest user 0 \
 *  org/melati/app/TemplateAppExample
 * 
 */
public class TemplateAppExample extends TemplateApp {

  /**
   * The main method to override. 
   * 
   * @param melati A {@link Melati} with arguments and properties set
   * @param templateContext A {@link ServletTemplateContext} containing a {@link Melati}
   * @return the name of a template to expand
   * @throws Exception if anything goes wrong
   * @see org.melati.app.TemplateApp#doTemplateRequest
   *         (org.melati.Melati, org.melati.template.ServletTemplateContext)
   */
  protected String doTemplateRequest(Melati melati,
      TemplateContext templateContext) throws Exception {
    if (melati.getMethod() == null)
      melati.getPoemContext().setMethod("org/melati/app/TemplateAppExample");
    return melati.getMethod();
  }

  /**
   * The main entry point.
   * 
   * @param args in format <code>db table troid method</code> 
   *             where method is a template name 
   */
  public static void main(String[] args) {
    TemplateAppExample me = new TemplateAppExample();
    me.run(args);
  }
}
