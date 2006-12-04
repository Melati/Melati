/**
 * 
 */
package org.melati.servlet.test;

import org.melati.Melati;
import org.melati.servlet.ConfigServlet;
import org.melati.util.ConnectionPendingException;

/**
 * @author timp
 *
 */
public class DbPendingErrorConfigServlet extends ConfigServlet {

  /**
   * Shut eclipse up.
   */
  private static final long serialVersionUID = 1L;

  /**
   * A bomber.
   */
  public DbPendingErrorConfigServlet() {
    super();
  }

  /* (non-Javadoc)
   * @see org.melati.servlet.ConfigServlet#doConfiguredRequest(org.melati.Melati)
   */
  protected void doConfiguredRequest(Melati melati)
      throws Exception {
    throw new ConnectionPendingException("testdb");
  }

}
