package org.melati.admin;

import org.melati.servlet.TemplateServlet;
import org.melati.MelatiContext;
import org.melati.template.TemplateContext;

public class Status extends TemplateServlet {

  protected TemplateContext doTemplateRequest
  (MelatiContext melati, TemplateContext context)
  throws Exception {
    context.setTemplateName("admin/Status.wm");
    return context;
  }
}
