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

package org.melati.poem;

import java.io.Writer;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;

import org.melati.servlet.ConfigServlet;
import org.melati.servlet.MelatiContext;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.ExceptionUtils;
import org.melati.util.MelatiWriter;

// class ChippyThreadDeath extends Error {}

public class SessionAnalysisServlet extends ConfigServlet {

  protected void doConfiguredRequest(Melati melati)
                              throws ServletException, IOException {

    MelatiConfig config = melati.getConfig();
    melati.getResponse().setContentType("text/html");
    MelatiWriter output = melati.getWriter();
    output.write("<h1>Transactions</h1>");
    Date now = new Date();
    output.write("Run at "+now);
    output.write("</h1>\n");
    output.write("<h2>PoemSessions in use</h2>\n");
    output.write("<table border=1 cellspacing=0 cellpadding=1>\n <tr>\n");
    output.write("  <th>Session</th>\n");
    output.write("  <th>Started</th>\n");
    output.write("  <th>Thread</th>\n");
    output.write("  <th>PoemTransaction</th>\n");
//    output.write("  <th>AccessToken</th>\n <tr>\n");

    Enumeration e = PoemThread.openSessions().elements();
    while(e.hasMoreElements()) {
      SessionToken token = (SessionToken) e.nextElement();
      output.write(" <tr>\n");
      output.write("  <td>"+token+"</td>\n");
      output.write("  <td>"+token.started+"<br>(Running for "+(now.getTime()-token.started.getTime())+" ms)</td>\n");
      output.write("  <td>"+token.thread);
/*
      try {
        token.thread.stop(new ThreadDeath());
      }
      catch (Exception f) {
        output.write(ExceptionUtils.stackTrace(f));
      }
*/
      output.write("</td>\n");
      output.write("  <td>"+token.transaction+"<br>(Database:"+token.transaction.getDatabase()+")</td>\n");
//      output.write("  <td>"+token.accessToken+"</td>\n");
      output.write(" <tr>\n");
    }
    output.write("</table>\n");

    Enumeration dbs = org.melati.LogicalDatabase.initialisedDatabases().elements();
//    Enumeration dbs = org.melati.LogicalDatabase.getDatabases();

    output.write("<h2>Initialised Databases</h2>\n");
    output.write("<table border=1 cellspacing=0 cellpadding=1><tr><th>Database</th><th>PoemTransation</th><th>Free</th></tr>\n");
    while(dbs.hasMoreElements()) {
      Database db = (Database)dbs.nextElement();
      for(int i=0; i<db.transactionsMax(); i++) {
        output.write("<tr><td>"+db+"</td>");
        output.write("\n<td>"+db.poemTransaction(i)+"</td>\n");
        output.write("<td>"+db.isFree(db.poemTransaction(i))+"</td>\n</tr>\n");
      }
    }
    output.write("</table>\n");
  }


}






