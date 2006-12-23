/**
 * 
 */
package org.melati.poem.test;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class SqlExceptionPoemTypeTest extends SQLPoemTypeTest {

  /**
   * 
   */
  public SqlExceptionPoemTypeTest() {
  }

  /**
   * @param name
   */
  public SqlExceptionPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new SqlExceptionPoemType(false);
  }
}
