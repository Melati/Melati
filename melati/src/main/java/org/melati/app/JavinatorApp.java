/*
 * Copyright (C) 2017 Tim Pizey
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
 *     http://paneris.org/~timp
 */
package org.melati.app;

import org.melati.Melati;
import org.melati.poem.PoemThread;
import org.melati.template.HTMLMarkupLanguage;
import org.melati.template.JavaMarkupLanguage;
import org.melati.template.TemplateContext;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;

/**
 * @author timp
 * @since 2017-01-29
 *
 */
public class JavinatorApp extends AbstractTemplateApp {

  public JavinatorApp() {
    super();
  }
  public Melati init(String[] args) throws MelatiException {
    Melati melati = super.init(args);
    melati.setTemplateEngine(new WebmacroTemplateEngine());
    melati.setMarkupLanguage(new JavaMarkupLanguage(melati));
    System.err.println("ml: " + melati.getMarkupLanguage());
    return melati;
  }
  /**
   * The main method to override.
   *
   * @param melati A {@link Melati} with arguments and properties set
   * @param templateContext A {@link TemplateContext} containing a {@link Melati}
   * @return the name of a template to expand
   * @throws Exception if anything goes wrong
   * @see AbstractTemplateApp#doTemplateRequest
   *         (org.melati.Melati, org.melati.template.ServletTemplateContext)
   */
  protected String doTemplateRequest(Melati melati,
      TemplateContext templateContext) throws Exception {
    // Turn comments on
    templateContext.put("comments", "true");
    templateContext.put("object", melati.getTable().newPersistent());
    return dsdTemplate(templateContext);
  }
  
  /**
   *  @return the DSD template name, the extension is added by the template engine
   */
  protected String dsdTemplate(TemplateContext context) {
    context.put("database", PoemThread.database());
    // Webmacro security prevents access from within template

    // Note: getPackage() can return null dependant upon 
    // the classloader so we have to chomp the class name

    String  c = PoemThread.database().getClass().getName();
    int dot = c.lastIndexOf('.');
    String p = c.substring(0, dot);

    context.put("package", p);

    return "org/melati/poem/JdbcPersistent";
  }

  /**
   * The main entry point.
   *
   * @param args in format <code>db table troid method</code>
   *             where method is a template name
   */
  public static void main(String[] args) throws Exception{
    JavinatorApp me = new JavinatorApp();
    me.run(args);
  }

}
