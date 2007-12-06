/**
 * 
 */
package org.melati.app.test;

import org.melati.MelatiConfig;
import org.melati.app.TemplateApp;
import org.melati.login.OpenAccessHandler;
import org.melati.util.InstantiationPropertyException;
import org.melati.util.MelatiException;

/**
 * @author timp
 *
 */
public class MisConfiguredTemplateApp extends TemplateApp {

  /**
   * 
   */
  public MisConfiguredTemplateApp() {
    super();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.app.AbstractConfigApp#melatiConfig()
   */
  protected MelatiConfig melatiConfig() throws MelatiException {
    MelatiConfig config = super.melatiConfig();

      try {
        config.setTemplateEngine(null);
      } catch (Exception e) {
        throw new InstantiationPropertyException(OpenAccessHandler.class
                .getName(), e);
      }


    return config;
    
  }


}
