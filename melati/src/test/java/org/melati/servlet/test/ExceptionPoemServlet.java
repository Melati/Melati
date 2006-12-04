/**
 * 
 */
package org.melati.servlet.test;

import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.servlet.PoemServlet;

/**
 * @author timp
 *
 */
public class ExceptionPoemServlet extends PoemServlet {

  /**
   * Shut eclipse up.
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public ExceptionPoemServlet() {
    super();
  }

  /**
   * @see org.melati.servlet.PoemServlet#doPoemRequest(org.melati.Melati)
   */
  protected void doPoemRequest(Melati melati)
      throws Exception {
    throw new AccessPoemException();

  }

}
