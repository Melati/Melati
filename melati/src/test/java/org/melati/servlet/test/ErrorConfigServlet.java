/**
 * 
 */
package org.melati.servlet.test;

import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.servlet.ConfigServlet;

/**
 * @author timp
 *
 */
public class ErrorConfigServlet extends ConfigServlet {

  /**
   * Shut Eclipse up.
   */
  private static final long serialVersionUID = 1L;

  /**
   * A bomber.
   */
  public ErrorConfigServlet() {
    super();
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doConfiguredRequest(org.melati.Melati)
   */
  protected void doConfiguredRequest(Melati melati)
      throws Exception {
    throw new AccessPoemException();
  }

}
