/**
 * 
 */
package org.melati.admin;

/**
 * @author timp
 * @since 2008/01/22
 *
 */
public class NonUniqueKeyValueAnticipatedException extends AnticipatedException {

  /**
   * @param subException
   */
  public NonUniqueKeyValueAnticipatedException(Exception subException) {
    super("You probably have not changed all the fields which need to be unique, " + 
        "so the duplicate cannot be created.",
        subException);
  }

}
