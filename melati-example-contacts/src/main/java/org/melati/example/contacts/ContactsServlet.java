package org.melati.example.contacts;

import org.melati.servlet.TemplateServlet;

/**
 * The simplest possible servlet.
 */
public abstract class ContactsServlet extends TemplateServlet {

  /** 
   * @return the System Administrators name.
   */
  public String getSysAdminName () {
    return "Melati Webmaster";
  }

  /** 
   * @return the System Administrators email address.
   */
  public String getSysAdminEmail () {
    return "timp@paneris.org";
  }

}









