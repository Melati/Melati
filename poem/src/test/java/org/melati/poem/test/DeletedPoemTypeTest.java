/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DeletedPoemType;
import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.IntegerPoemType;

/**
 * @author timp
 * @since 6 Jan 2007
 */
public class DeletedPoemTypeTest extends NotNullableIntegerPoemTypeTest {

  /**
   * 
   */
  public DeletedPoemTypeTest() {
  }

  /**
   * @param name
   */
  public DeletedPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new DeletedPoemType();
  }

  /**
   * Test method for {@link org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    assertFalse(it.canRepresent(new DisplayLevelPoemType()) instanceof IntegerPoemType);
  }

  public void testToString() {
    assertEquals("deleted (BOOLEAN (org.melati.poem.DeletedPoemType))", it.toString());
  }

  
}
