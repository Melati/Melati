package org.melati.example.contacts;

import org.melati.servlet.TemplateServlet;

/**
 * The simplest possible servlet.
 */
public abstract class ContactsServlet extends TemplateServlet {

  public String getSysAdminName () {
    return "Melati Webmaster";
  }
  public String getSysAdminEmail () {
    return "timp@paneris.org";
  }

}









