/*
 * Created on 07-Feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.melati.app;

import org.melati.Melati;
import org.melati.app.PoemApp;
import org.melati.poem.PoemTask;

/**
 * @author timp
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PoemAppExample extends PoemApp implements PoemTask {

  /* (non-Javadoc)
   * @see org.melati.app.PoemApp#doPoemRequest(org.melati.Melati)
   */
  protected void doPoemRequest(Melati melati) throws Exception {
    // TODO Auto-generated method stub
System.err.println("Hello World");
  }

  public static void main(String[] args) {
    PoemAppExample me = new PoemAppExample();
    me.run();
  }
}
