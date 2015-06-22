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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.app;

import org.melati.Melati;
import org.melati.template.TemplateContext;

/**
 * An example of how to use a Template Engine with a Poem database
 * from the command line.
 *
 * Invoke:
 *
 * java -cp melati.jar:site\properties:lib\hsqldb.jar: \
 *  lib\webmacro.jar:lib\servlet.jar \
 *  org.melati.app.TemplateApp poemtest user 0 \
 *  org/melati/app/TemplateAppExample
 *
 * Where poemtest is your database, user is your table, 0 is the record id and 
 * org/melati/app/TemplateAppExample is the full name of a template.
 * 
 * From within Maven try: 
 * 
     &lt;plugin&gt;
       &lt;groupId&gt;org.codehaus.mojo&lt;/groupId&gt;
       &lt;artifactId&gt;exec-maven-plugin&lt;/artifactId&gt;
       &lt;executions&gt;
         &lt;execution&gt;
          &lt;id&gt;generate&lt;/id&gt;
          &lt;phase&gt;test&lt;/phase&gt;
          &lt;goals&gt;
           &lt;goal&gt;java&lt;/goal&gt;
          &lt;/goals&gt;
          &lt;configuration&gt;
           &lt;mainClass&gt;org.melati.app.TemplateApp&lt;/mainClass&gt;
           &lt;arguments&gt;
             &lt;argument&gt;mydb&lt;/argument&gt;
             &lt;argument&gt;mytable&lt;/argument&gt;
             &lt;argument&gt;mytroid&lt;/argument&gt;
             &lt;argument&gt;mytemplate&lt;/argument&gt;
             &lt;argument&gt;-output&lt;/argument&gt;
             &lt;argument&gt;${basedir}/src/main/webapp/WEB-INF/web.xml&lt;/argument&gt;
           &lt;/arguments&gt;
           &lt;cleanupDaemonThreads&gt;true&lt;/cleanupDaemonThreads&gt;
          &lt;/configuration&gt;
         &lt;/execution&gt;
       &lt;/executions&gt;
    &lt;/plugin&gt;

 * 
 * 
 *
 */
public class TemplateApp extends AbstractTemplateApp {

  /**
   * The main method to override.
   *
   * @param melati A {@link Melati} with arguments and properties set
   * @param templateContext A {@link TemplateContext} containing a {@link Melati}
   * @return the name of a template to expand
   * @throws Exception if anything goes wrong
   * @see org.melati.app.AbstractTemplateApp#doTemplateRequest
   *         (org.melati.Melati, org.melati.template.ServletTemplateContext)
   */
  protected String doTemplateRequest(Melati melati,
      TemplateContext templateContext) throws Exception {
    return melati.getMethod();
  }

  /**
   * The main entry point.
   *
   * @param args in format <code>db table troid method</code>
   *             where method is a template name
   */
  public static void main(String[] args) throws Exception {
    TemplateApp me = new TemplateApp();
    me.run(args);
  }
}
