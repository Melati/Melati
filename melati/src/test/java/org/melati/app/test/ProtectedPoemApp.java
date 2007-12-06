/**
 * 
 */
package org.melati.app.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.app.InvalidArgumentsException;
import org.melati.app.PoemApp;
import org.melati.login.AccessHandler;
import org.melati.login.CommandLineAccessHandler;
import org.melati.login.OpenAccessHandler;
import org.melati.util.InstantiationPropertyException;
import org.melati.util.MelatiException;
import org.melati.util.test.StringInputStream;

/**
 * @author timp
 *
 */
public class ProtectedPoemApp extends PoemApp {

  /**
   * 
   */
  public ProtectedPoemApp() {
    super();
    
  }
  /**
   * {@inheritDoc}
   * @see org.melati.app.AbstractConfigApp#melatiConfig()
   */
  protected MelatiConfig melatiConfig() throws MelatiException {
    MelatiConfig config = super.melatiConfig();

      try {
        config.setAccessHandler((AccessHandler)CommandLineAccessHandler.class
                .newInstance());
      } catch (Exception e) {
        throw new InstantiationPropertyException(OpenAccessHandler.class
                .getName(), e);
      }


    return config;
    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.app.AbstractTemplateApp#init(java.lang.String[])
   */
  public Melati init(String[] args) throws MelatiException {
    Melati melati = super.init(args);
    CommandLineAccessHandler ah = (CommandLineAccessHandler)melati.getConfig().getAccessHandler();
    ah.setInput(new StringInputStream("_administrator_\nFIXME\n"));
    return melati;
    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.app.AbstractPoemApp#prePoemSession(org.melati.Melati)
   */
  protected void doPoemRequest(Melati melati) throws Exception {
    // Need to be logged in to do this
    melati.getDatabase().getUserTable().getTableInfo().
        setDefaultcanread(melati.getDatabase().getCanAdminister());
    super.doPoemRequest(melati);    
  }
  
  /*
   * The main entry point.
   * 
   * @param args in format <code>db table troid method</code> 
   */
  public static void main(String[] args) throws Exception {
    ProtectedPoemApp me = new ProtectedPoemApp();
    me.run(args);
  }
  protected PoemContext poemContext(Melati melati) 
      throws InvalidArgumentsException {
    return poemContextWithLDB(melati,"appjunit");
  }


}
