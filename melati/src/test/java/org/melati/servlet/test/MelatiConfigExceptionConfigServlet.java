/**
 * 
 */
package org.melati.servlet.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.servlet.ConfigServlet;
import org.melati.util.ConfigException;

/**
 * A servlet which throws a MelatiException during configuration.
 * 
 * @author timp
 * @since 05/12/2006
 */
public class MelatiConfigExceptionConfigServlet extends ConfigServlet {

  /**
   * Shut eclipse up.
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public MelatiConfigExceptionConfigServlet() {
    super();
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doConfiguredRequest(org.melati.Melati)
   */
  protected void doConfiguredRequest(Melati melati)
      throws Exception {

  }

  protected MelatiConfig melatiConfig()  {
    throw new ConfigException("Pretend bug");
   }
}
