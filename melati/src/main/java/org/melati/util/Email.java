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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.util;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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

/**
 * Send an email to one or more recipients with or without attachments.
 */
public final class Email {

  private Email() {
  }

  /**
   * Send the email.
   * @param smtpServer name of SMTP server to use
   * @param from email address and optionally name of sender
   * @param to email address and optionally name of recipient 
   * @param replyto email address and optionally name to reply to
   * @param subject subject of message
   * @param text text body of email
   */
  public static void send(String smtpServer, String from, String to,
                          String replyto, String subject, String text) 
      throws EmailException, IOException {
    File[] empty = {};
    sendWithAttachments(smtpServer, from, to, replyto, subject, text, empty);
  }

  /**
   * Send the email to a list of recipients.
   * 
   * @param smtpServer name of SMTP server to use
   * @param from email address and optionally name of sender
   * @param toList list of email addresses and optionally names of recipients
   * @param replyto email address and optionally name to reply to
   * @param subject subject of message
   * @param message text body of email
   */
  public static void sendToList(String smtpServer, String from,
          String[] toList,  String replyto, String subject,
          String message) throws EmailException, IOException {
    File[] empty = {};

    for (int i = 0; i < toList.length; i++)
      sendWithAttachments(smtpServer, from, toList[i], replyto, subject, message, empty);
  }
  
  /**
   * Send message with attachments.
   * 
   * @param smtpServer name of SMTP server to use
   * @param from email address and optionally name of sender
   * @param to email address and optionally name of recipient 
   * @param replyto email address and optionally name to reply to
   * @param subject subject of message
   * @param text text body of email
   * @param attachments Array of files to attach
   */
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
  
  /**
   * Send HTML message with attachments.
   * 
   * @param smtpServer name of SMTP server to use
   * @param from email address and optionally name of sender
   * @param to email address and optionally name of recipient 
   * @param replyto email address and optionally name to reply to
   * @param subject subject of message
   * @param plainText text body of email
   * @param htmlText HTML body of email
   * @param referenced Array of Files referenced withing the HTML body
   * @param attachments Array of files to attach
   */
  public static void sendAsHtmlWithAttachments(String smtpServer, String from,
          String to, String replyto, String subject, String plainText,
          String htmlText, File[] referenced, File[] attachments)
          throws EmailException, IOException {

    // Construct the message
    Message message = initialiseMessage(smtpServer, from, to, replyto, subject);
    try {
      Multipart mp = new MimeMultipart("related");
      MimeBodyPart mbp1 = new MimeBodyPart();
      //mbp1.setText(plainText);
      mbp1.setContent(plainText, "text/plain");
      mp.addBodyPart(mbp1);
      MimeBodyPart mbp2 = new MimeBodyPart();
      mbp2.setContent(htmlText, "text/html");
      mp.addBodyPart(mbp2);

      if (referenced != null) {
        for (int i = 0; i < referenced.length; i++) {
          File f = referenced[i];
          if (f != null) {
            MimeBodyPart mbp3 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(f);
            mbp3.setDataHandler(new DataHandler(fds));
            mbp3.setFileName(fds.getName());
            mp.addBodyPart(mbp3);
          }
        }
      }
      if (attachments != null) {
        for (int i = 0; i < attachments.length; i++) {
          File f = attachments[i];
          if (f != null) {
            MimeBodyPart mbp4 = new MimeBodyPart();
            if (f.getName() == null) {
              System.out.println("name is null");
            }
            FileDataSource fds = new FileDataSource(f);
            mbp4.setDataHandler(new DataHandler(fds));
            mbp4.setFileName(fds.getName());
            mp.addBodyPart(mbp4);
          }
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
    // this is required if InetAddress.getLocalHost().getHostName()  returns null
    // which it does nor me in Eclipse
    properties.put("mail.smtp.localhost", smtpServer);
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
      if (replyto != null) {
        Address[] replyTos = InternetAddress.parse(replyto);
        message.setReplyTo(replyTos);
      }
      message.setSubject(subject);
    } catch (Exception e) {
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
  /**
   * @return a fancy email address
   */
  public static String mailAddress(String name, String email) {
    return name + " <" + email + ">";
  }

}
