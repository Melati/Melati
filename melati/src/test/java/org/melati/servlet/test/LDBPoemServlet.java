/**
 * 
 */
package org.melati.servlet.test;


import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.servlet.PathInfoException;
import org.melati.test.HttpAuthenticationPoemServletTest;

/**
 * Test setting of database by method. 
 * 
 * @author timp
 * @since 04/12/2006
 */
public class LDBPoemServlet extends HttpAuthenticationPoemServletTest {

  /**
   * Shut Eclipse up.
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public LDBPoemServlet() {
    super();
  }

  
  protected PoemContext poemContext(Melati melati) throws PathInfoException { 
    return poemContextWithLDB(melati, "melatijunit"); 
  } 

}
