/**
 * 
 */
package org.melati.poem;

/**
 * @author timp
 * @since 2 Feb 2007
 *
 */
public class ConnectionPendingException extends PoemException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * @param subException
   */
  public ConnectionPendingException(Exception subException) {
    super(subException);
    // TODO Auto-generated constructor stub
  }

  /**
   * Constructor.
   */
  public ConnectionPendingException() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Constructor.
   * @param message
   * @param subException
   */
  public ConnectionPendingException(String message, Exception subException) {
    super(message, subException);
    // TODO Auto-generated constructor stub
  }

  /**
   * Constructor.
   * @param message
   */
  public ConnectionPendingException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

}
