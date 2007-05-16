package org.melati.servlet.test;

import org.melati.Melati;
import org.melati.servlet.TemplateServlet;
import org.melati.template.ServletTemplateContext;

/**
 * @author timp
 * @since 5/12/2006
 *
 */
public class ExceptionTemplateServlet extends TemplateServlet {

  /**
   * Shut eclipse up.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public ExceptionTemplateServlet() {
    super();
  }

  protected String doTemplateRequest(Melati melati,
      ServletTemplateContext templateContext) throws Exception {
    throw new Exception("A problem");
  }

}
