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

import java.util.Enumeration;

import org.melati.Melati;
import org.melati.poem.User;
import org.melati.app.PoemApp;

/**
 * An example of how to access a POEM database from the command line. 
 * 
 * Invoke: 
 * <pre>
 * java -cp melati.jar;site\properties;lib\hsqldb.jar 
 *     org.melati.app.PoemAppExample poemtest
 * </pre>
 * (Note: if you have configured a WebmacroTemplateEngine then you will 
 * need webmacro.jar and servlet.jar in your classpath).
 */
public class PoemAppExample extends PoemApp {

  /**
   * The main method to override. 
   * 
   * @param melati A {@link Melati} with arguments and properties set
   * @throws Exception if anything goes wrong
   * @see org.melati.app.PoemApp#doPoemRequest(org.melati.Melati)
   */
  protected void doPoemRequest(Melati melati) throws Exception {
    System.err.println("Hello World");
    System.out.println("Your Database was: " + melati.getDatabase());
    System.out.println("Your Table was   : " + melati.getTable());
    System.out.println("Your Troid was   : " + melati.getObject().getTroid());
    System.out.println("Your Method was  : " + melati.getMethod());
    System.out.println("System Users");
    System.out.println("============");
    Enumeration e = melati.getDatabase().getUserTable().selection(); 
    while(e.hasMoreElements()) {
      System.out.println("  " + ((User)e.nextElement()).getName());
    }
  }

  /**
   * The main entry point.
   * 
   * @param args in format <code>db table troid method</code> 
   */
  public static void main(String[] args) {
    PoemAppExample me = new PoemAppExample();
    me.run(args);
  }
}
