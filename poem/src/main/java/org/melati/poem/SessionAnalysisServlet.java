/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
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
 *     Mylesc Chippendale <mylesc@paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

package org.melati.poem;

import java.io.Writer;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

import javax.servlet.ServletException;

import org.melati.servlet.ConfigServlet;
import org.melati.servlet.MelatiContext;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.ExceptionUtils;
import org.melati.util.MelatiWriter;

/**
 * SessionAnalysisServlet
 *
 * Displays information about the status of this JVM and the databases
 * running from it. Well, with JServ it's for this servlet zone.
 *
 * It shows us information about any Poem sessions running and
 * each transaction in (think 'connection to') a database.
 */

public class SessionAnalysisServlet extends ConfigServlet {

  protected void doConfiguredRequest(Melati melati)
                              throws ServletException, IOException {

    MelatiConfig config = melati.getConfig();
    melati.getResponse().setContentType("text/html");
    MelatiWriter output = melati.getWriter();
    Date now = new Date();
    output.write("<html>\n"
                  + "<head>\n"
                  + "<title>Transaction Analysis</title>\n");
    String repeat = melati.getRequest().getParameter("repeat");
    if (repeat != null)
      output.write("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"" + repeat + "; URL="
                    + melati.getRequest().getRequestURI() + "?repeat="
                    + repeat + "\">");
    output.write("</head>\n"
                  + "<body>\n"
                  + "<h1>Transactions Analysis</h1>"
                  + "<p>Run at " + now + "</p>\n"
                  + "<p>JVM Free memory: " +
   NumberFormat.getInstance().format(Runtime.getRuntime().freeMemory())
                  + "</p>\n"
                  + "<p>JVM Total memory: " +
   NumberFormat.getInstance().format(Runtime.getRuntime().totalMemory())
                  + "</p>\n"
                  + "<form>Reload every <input name=repeat size=5 value="
                  + repeat + "> seconds <input type=submit></form>\n"
                  + "<h2>Poem sessions in use</h2>\n");

    Enumeration e = PoemThread.openSessions().elements();

    while(e.hasMoreElements()) {
      SessionToken token = (SessionToken) e.nextElement();
      output.write("<table border=1 cellspacing=0 cellpadding=1>\n <tr><tr>\n"
                   + "  <tr><th colspan=2>Session: " + token + "</td><tr>\n"
                   + "  <tr><th>Running for</th><td>"
                   + (now.getTime()-token.started) + " ms</td><tr>\n"
                   + "  <tr><th>Thread</th><td>" + token.thread + "</td><tr>\n"
                   + "  <tr><th>PoemTransaction</th><td>"
                   + token.transaction + "<br>(Database:"
                   + token.transaction.getDatabase() + ")</td><tr>\n"
                   + "  <tr><th>PoemTask</th><td>" + token.task + "</td><tr>\n"
                   + " <tr>\n"
                   + "</table>\n");
    }

    Enumeration dbs = org.melati.LogicalDatabase.initialisedDatabases().
                                                   elements();

    output.write("<h2>Initialised Databases</h2>\n"
                 + "<table border=1 cellspacing=0 cellpadding=1>"
                 + "<tr><th>Database</th><th>PoemTransation</th>"
                 + "<th>Free</th></tr>\n");
    while(dbs.hasMoreElements()) {
      Database db = (Database)dbs.nextElement();
      for(int i=0; i<db.transactionsMax(); i++) {
        boolean isFree = db.isFree(db.poemTransaction(i));
        output.write("<tr><td>" + db + "</td>\n"
                     + "<td>" + db.poemTransaction(i) + "</td>\n"
                     + "<td bgcolor=" + (isFree ? "green" : "red") + ">"
                       + isFree + "</td>\n</tr>\n");
      }
    }
    output.write("</table>\n"
                 + "</body>\n"
                 + "</html>\n");
  }


}






