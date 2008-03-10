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
 *     Mylesc Chippendale <mylesc At paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

package org.melati.test;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;

import org.melati.Melati;
import org.melati.poem.Database;
import org.melati.poem.PoemThread;
import org.melati.poem.SessionToken;
import org.melati.poem.transaction.Transaction;
import org.melati.servlet.ConfigServlet;
import org.melati.util.MelatiWriter;

/**
 * Displays information about the status of this JVM and the databases
 * running in it. Well, with JServ it's for this servlet zone.
 *
 * It shows us information about any Poem sessions running and
 * each transaction (think 'connection to') in a database.
 */

public class SessionAnalysisServlet extends ConfigServlet {
  private static final long serialVersionUID = 1L;
  private MelatiWriter output;

  protected void doConfiguredRequest(Melati melati)
      throws ServletException, IOException {

    melati.getResponse().setContentType("text/html");
    output = melati.getWriter();
    Date now = new Date();
    println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD " 
                 + "HTML 4.01 Transitional//EN\">\n");
    println("<html>\n");
    println("<head>\n");
    println("<title>Transaction Analysis</title>\n");
    String repeat = melati.getRequest().getParameter("repeat");
    if (repeat != null)
      println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"" 
                    + repeat + "; URL="
                    + melati.getRequest().getRequestURI() + "?repeat="
                    + repeat + "\">");
    println("  <link rel='stylesheet' title='Default' href='" + 
                 melati.getConfig().getStaticURL() + "/admin.css' \n" +
                 "type='text/css' media='screen'>\n");
    println("</head>");
    println("<body>");
    println("<h1>Transactions Analysis</h1>");
    println("<p>Run at " + now + "</p>\n");
    println("<p>JVM Free memory: " +
   NumberFormat.getInstance().format(Runtime.getRuntime().freeMemory())
                  + "</p>\n"
                  + "<p>JVM Total memory: " +
   NumberFormat.getInstance().format(Runtime.getRuntime().totalMemory())
                  + "</p>\n"
                  + "<form action=''>Reload every "  
                  + "<input name='repeat' size='5' value='"
                  + repeat + "'> seconds <input type=submit></form>\n");

    
    println("<h2>Poem sessions</h2>\n");

    Enumeration e = PoemThread.openSessions().elements();

    int totalSessions = 0;
    while(e.hasMoreElements()) {
      totalSessions++;
      SessionToken token = (SessionToken) e.nextElement();
      println("<table border='1' cellspacing='0' cellpadding='1'>");
      println(" <tr><th colspan='2'>Session: " + token + "</td></tr>");
      println(" <tr><th>Running for</th><td>" + (now.getTime() - token.getStarted()) + " ms</td></tr>");
      println(" <tr><th>Thread</th><td>" + token.getThread() + "</td></tr>");
      println(" <tr><th>PoemTransaction</th><td>"
                 + token.getTransaction() + "<br>(Database:"
                 + token.getTransaction().getDatabase() + ")</td></tr>");
      println(" <tr><th>PoemTask</th><td>" + token.getTask() + "</td></tr>\n");
      Enumeration o = token.toTidy().elements();
      if(o.hasMoreElements()) {
        println("<tr><th>Open: </th><td>");
        while (o.hasMoreElements()) {
          println(o.nextElement() + "<br>");
        }
        println("</td></tr>\n");
      }
      println("</table>\n");
    }
    println("<h4>Poem sessions in use: " + totalSessions + "</h4>\n");

    println("<h2>Initialised Databases</h2>");
    println("<table border=1 cellspacing=0 cellpadding=1>");
    println("<tr><th>Database</th><th>PoemTransaction</th>");
    println("<th>Free</th><th>Blocked</th></tr>\n");
    
    int totalDbs = 0;
    Enumeration dbs = org.melati.LogicalDatabase.initialisedDatabases().
                                                   elements();
    while(dbs.hasMoreElements()) {
      totalDbs++;
      Database db = (Database)dbs.nextElement();
      println("<tr>");
      println(" <td>" + db.getDisplayName() + "</td>");
      println(" <td>" + db + "</td>");
      println(" <td>" + db.getFreeTransactionsCount() + "</td>");
      println(" <td>" + (db.transactionsMax() - db.getFreeTransactionsCount()) + "</td>");
      //println(" <td>" + db.getLastQuery() + "</td>");
      println("</tr>");
      for(int i=0; i < db.transactionsMax(); i++) {
        boolean isFree = db.isFree(db.poemTransaction(i));
        Transaction blockedOn = db.poemTransaction(i).getBlockedOn();
        println("<tr><td>&nbsp</td>\n"
                     + "<td>" + db.poemTransaction(i) + "</td>\n"
                     + "<td bgcolor=" + (isFree ? "green" : "red") + ">"
                     + isFree + "</td>\n"
                     + "<td bgcolor=" + (blockedOn != null ? "red" : "green") + ">"
                     + (blockedOn != null  ? blockedOn.toString() : "&nbsp;") + "</td>\n"
                     + "</tr>\n");
      }
    }
    println("</table>\n");
    println("<h4>Initialised databases in use: " + totalDbs + "</h4>");
    
    
    println("</body>\n");
    println("</html>\n");
  }

  void println(String in) throws IOException { 
    output.write(in + "\n");
  }
}






