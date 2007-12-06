/**
 * 
 */
package org.melati.app.test;

import org.melati.Melati;
import org.melati.app.PoemApp;

/**
 * @author timp
 *
 */
public class ThrowingPoemApp extends PoemApp {

  /**
   * 
   */
  public ThrowingPoemApp() {
    super();
    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.app.AbstractPoemApp#prePoemSession(org.melati.Melati)
   */
  protected void prePoemSession(Melati melati) throws Exception {
    throw new RuntimeException("Bang!");
    
  }

  /*
   * The main entry point.
   * 
   * @param args in format <code>db table troid method</code> 
   */
  public static void main(String[] args) throws Exception {
    ThrowingPoemApp me = new ThrowingPoemApp();
    me.run(args);
  }


}
