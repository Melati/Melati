package org.melati.admin;

import org.melati.servlet.TemplateServlet;
import org.melati.Melati;
import org.melati.template.TemplateContext;

public class Status extends TemplateServlet {

  protected String doTemplateRequest(Melati melati, TemplateContext context)
      throws Exception {
    return "org/melati/admin/Status";
  }
}
