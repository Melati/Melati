package org.melati.example.contacts;

import org.melati.servlet.TemplateServlet;

abstract public class ContactsServlet extends TemplateServlet {

  public String getSysAdminName () {
    return "Melati Webmaster";
  }
  public String getSysAdminEmail () {
    return "timp@paneris.org";
  }

}









