/**
 * 
 */
package org.melati.servlet.test;

/**
 * Exception with matching templates on classpath.
 * @author timp
 * @since 14-Dec-2006
 */
public class ClasspathRenderedException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public ClasspathRenderedException() {
    super();
  }

  /**
   * Constructor.
   * @param message the message
   */
  public ClasspathRenderedException(String message) {
    super(message);
  }


}
