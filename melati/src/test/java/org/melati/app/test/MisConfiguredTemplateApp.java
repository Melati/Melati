package org.melati.app.test;

import org.melati.MelatiConfig;
import org.melati.app.TemplateApp;
import org.melati.template.NoTemplateEngine;
import org.melati.util.MelatiException;

/**
 * @author timp
 * @since 2007-12-06
 *
 */
public class MisConfiguredTemplateApp extends TemplateApp {

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
        config.setTemplateEngine(new NoTemplateEngine());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }


    return config;
    
  }


}
