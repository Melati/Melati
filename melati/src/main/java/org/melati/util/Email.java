/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.util;

import sun.net.smtp.SmtpClient;
import java.io.*;
import java.util.Date;
import java.text.*;
import org.melati.poem.*;

public class Email {

    public static void send(
             Database database,
             String from,
		     String to,
		     String replyto,
		     String subject,
		     String message) throws EmailException, IOException {

        String[] toList = {to};
		sendToList(database,from,toList,to,replyto,subject,message);
    }

    public static void sendToList(
             Database database,
             String from,
		     String[] toList,
		     String apparentlyTo,
		     String replyto,
		     String subject,
		     String message) throws EmailException, IOException {

        SmtpClient smtp;
		String smtpserver = database.getSettingTable().get("smtpserver");
        try {
            smtp = new SmtpClient(smtpserver);
        } catch (Exception e) {
            throw new EmailException("Couldn't create smtp client " + smtpserver + ", " + e.toString());
        }
        
        // construct the data
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat ("dd MMM yyyy hh:mm:ss zzz");
        String nowString = formatter.format(now);
        smtp.from(from);
        for (int i=0; i<toList.length; i++)
            smtp.to(toList[i]);
        PrintStream msg = smtp.startMessage();
        msg.println("Date: " + nowString);
        msg.println("From: " + from);
        msg.println("To: " + apparentlyTo);
        msg.println("Subject: " + subject);
        if (!replyto.equals("")) msg.println("Reply-to: " + replyto);
        msg.println();
        msg.println(message);
        smtp.closeServer();
    }

}
