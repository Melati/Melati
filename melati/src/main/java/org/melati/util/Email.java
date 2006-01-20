/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
package org.melati.util;

import java.io.File;
import java.io.IOException;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.melati.poem.Database;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

/**
 * Send an email to one or more recipients with or without attachments.
 */
public final class Email {

  public static String SMTPSERVER = "SMTPServer";

  private Email() {
  }

  /**
   * @deprecated try to disentangle poem and utils
   */
  public static void send(Database database, String from, String to,
                          String replyto, String subject, String text) 
      throws EmailException, IOException {
    File[] empty = {};
    String smtpServer = database.getSettingTable().get(SMTPSERVER);
    sendWithAttachments(smtpServer, from, to, replyto, subject, text, empty);
  }

  public static void send(String smtpServer, String from, String to,
                          String replyto, String subject, String text) 
      throws EmailException, IOException {
    File[] empty = {};
    sendWithAttachments(smtpServer, from, to, replyto, subject, text, empty);
  }

  /**
   * @deprecated apparentlyTo ignored
   */
  public static void sendToList(Database database, String from,
          String[] toList, String apparentlyTo, String replyto, String subject,
          String message) throws EmailException, IOException {
    File[] empty = {};

    String smtpServer = database.getSettingTable().get(SMTPSERVER);
    for (int i = 0; i < toList.length; i++)
      sendWithAttachments(smtpServer, from, toList[i], replyto, subject, message, empty);
  }

  /**
   * @deprecated try to disentangle poem and utils
   */
  public static void sendWithAttachments(Database database, String from,
                                         String to, String replyto, 
                                         String subject, String text, 
                                         File[] attachments)
       throws EmailException, IOException {
    // Get our smtp server from the database
    String smtpServer = database.getSettingTable().get(SMTPSERVER);
    sendWithAttachments(smtpServer, from, to, replyto, subject, text, attachments);
  }


  public static void sendWithAttachments(String smtpServer, String from,
          String to, String replyto, String subject, String text, File[] attachments)
          throws EmailException, IOException {

    // Construct the message
    Message message = initialiseMessage(smtpServer, from, to, replyto, subject);
    try {
      // create and fill the first, text message part
      MimeBodyPart mbp1 = new MimeBodyPart();
      mbp1.setText(text);
      Multipart mp = new MimeMultipart();
      mp.addBodyPart(mbp1);
      for (int i = 0; i < attachments.length; i++) {
        File f = attachments[i];
        if (f != null) {
          // create the second message part
          MimeBodyPart mbp2 = new MimeBodyPart();
          // attach the file to the message
          FileDataSource fds = new FileDataSource(f);
          mbp2.setDataHandler(new DataHandler(fds));
          mbp2.setFileName(fds.getName());
          mp.addBodyPart(mbp2);
        }
      }
      // add the Multipart to the message
      message.setContent(mp);
    } catch (Exception e) {
      e.printStackTrace();
      throw new EmailException("Problem creating message: " + e.toString());
    }
    // send the message
    post(message);
  }
  public static void sendAsHtmlWithAttachments(String smtpServer, String from,
          String to, String replyto, String subject, String htmlText, 
          File[] referenced, File[] attachments)
          throws EmailException, IOException {

    // Construct the message
    Message message = initialiseMessage(smtpServer, from, to, replyto, subject);
    try {
      Multipart mp = new MimeMultipart("related");
      MimeBodyPart mbp1 = new MimeBodyPart();
      mbp1.setContent(htmlText, "text/html");
      mp.addBodyPart(mbp1);
      for (int i = 0; i < referenced.length; i++) {
        File f = referenced[i];
        if (f != null) {
          MimeBodyPart mbp2 = new MimeBodyPart();
          FileDataSource fds = new FileDataSource(f);
          mbp2.setDataHandler(new DataHandler(fds));
          mbp2.setFileName(fds.getName());
          mp.addBodyPart(mbp2);
        }
      }
      for (int i = 0; i < attachments.length; i++) {
        File f = attachments[i];
        if (f != null) {
          MimeBodyPart mbp2 = new MimeBodyPart();
          if (f.getName() == null){
            System.out.println("name is null");
          } 
          FileDataSource fds = new FileDataSource(f);
          mbp2.setDataHandler(new DataHandler(fds));
          mbp2.setFileName(fds.getName());
          mp.addBodyPart(mbp2);
        }
      }
      // add the Multipart to the message
      message.setContent(mp);
    } catch (Exception e) {
      e.printStackTrace();
      throw new EmailException("Problem creating message: " + e.toString());
    }
    // send the message
    post(message);
  }

  private static Message initialiseMessage(String smtpServer, String from,
          String to, String replyto, String subject) throws EmailException {
    // Create the JavaMail session
    // The properties for the whole system, sufficient to send a mail
    // and much more besides.
    java.util.Properties properties = System.getProperties();
    properties.put("mail.smtp.host", smtpServer);
    Session session = Session.getInstance(properties, null);
    MimeMessage message = new MimeMessage(session);
    // Set the from address
    Address fromAddress;
    try {
      fromAddress = new InternetAddress(from);
      message.setFrom(fromAddress);
      // Parse and set the recipient addresses
      Address[] toAddresses = InternetAddress.parse(to);
      message.setRecipients(Message.RecipientType.TO, toAddresses);
      /*
       * Address[] ccAddresses = InternetAddress.parse(cc);
       * message.setRecipients(Message.RecipientType.CC,ccAddresses);
       * 
       * Address[] bccAddresses = InternetAddress.parse(bcc);
       * message.setRecipients(Message.RecipientType.BCC,bccAddresses);
       */
      message.setSubject(subject);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new EmailException("Problem sending message: " + e.toString());
    }
    return message;
  }
  
  private static void post(Message message) throws EmailException {
    try {
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new EmailException("Problem sending message: " + e.toString());
    }
    
  }

}
