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

package org.melati.util;

import sun.net.smtp.SmtpClient;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.melati.poem.Database;

/** 
 * Send an email to one or more recipients.
 *
 * @todo replace with org.paneris.ftc.controller.Email
 * @deprecated Uses Sun class
 */
public class Email {

  public static String SMTPSERVER = "SMTPServer";

  static SimpleDateFormat formatter = 
                       new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");

  public static void send(Database database,
                          String from,
                          String to,
                          String replyto,
                          String subject,
                          String message) throws EmailException, IOException {

    String[] toList = {to};
    sendToList(database, from, toList, to, replyto, subject, message);
  }

  public static void sendToList(Database database,
                                String from,
                                String[] toList,
                                String apparentlyTo,
                                String replyto,
                                String subject,
                                String message) throws EmailException,
                                                       IOException {

    SmtpClient smtp;
    String smtpserver = database.getSettingTable().get(SMTPSERVER);
    try {
      smtp = new SmtpClient(smtpserver);
    } catch (Exception e) {
      throw new EmailException("Couldn't create smtp client " + 
                               smtpserver + ", " + e.toString());
    }

    // construct the data
    Date now = new Date();
    String nowString = formatter.format(now);
    smtp.from(from);
    for (int i=0; i<toList.length; i++)
      smtp.to(toList[i]);

    PrintStream msg = smtp.startMessage();
    msg.println("Date: " + nowString);
    msg.println("From: " + from);
    msg.println("To: " + apparentlyTo);
    msg.println("Subject: " + subject);
    if (!replyto.equals(""))
      msg.println("Reply-to: " + replyto);
    msg.println();
    msg.println(message);
    smtp.closeServer();
  }

}
