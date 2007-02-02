/**
 * 
 */
package org.melati.poem;

/**
 * @author timp
 * @since 2 Feb 2007
 *
 */
public class DatabaseInitialisationPoemException extends PoemException {

  /** The name of the database. */
  public String name;


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * @param subException
   */
  public DatabaseInitialisationPoemException(Exception subException) {
    super(subException);
  }

  /**
   * Constructor.
   */
  public DatabaseInitialisationPoemException() {
  }

  /**
   * Constructor.
   * @param message
   * @param subException
   */
  public DatabaseInitialisationPoemException(String name,
          Exception subException) {
    super(subException);
    this.name = name;
  }

  /**
   * Constructor.
   * @param message
   */
  public DatabaseInitialisationPoemException(String message) {
    super(message);
  }
  /** @return The detail message. */
  public String getMessage() {
    return
        "Something went wrong trying to open the logical database `" +
        name + "' \n" + subException;
  }

}
