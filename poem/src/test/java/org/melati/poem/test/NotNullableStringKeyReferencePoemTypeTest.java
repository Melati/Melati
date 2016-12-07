package org.melati.poem.test;


import java.util.Enumeration;

import org.melati.poem.Group;
import org.melati.poem.NullTypeMismatchPoemException;
import org.melati.poem.StringKeyReferencePoemType;
import org.melati.poem.TypeMismatchPoemException;
import org.melati.poem.ValidationPoemException;

/**
 * @author timp
 * @since 2012-06-15
 *
 */
public class NotNullableStringKeyReferencePoemTypeTest extends SQLPoemTypeSpec<String> {

  public NotNullableStringKeyReferencePoemTypeTest() {
  }

  public NotNullableStringKeyReferencePoemTypeTest(String name) {
    super(name);
  }

  void setObjectUnderTest() {
    it = new StringKeyReferencePoemType(getDb().getCapabilityTable(), 
        "name", 
        false, 
        60);
  }

  public void testAssertValidCooked() {
    if(it.getNullable())
      super.testAssertValidCooked();
    else
      try {
        it.assertValidCooked(null);
        fail("Should have blown up");
      } catch (NullTypeMismatchPoemException e) {
        e = null;
      }
    try {
      it.assertValidCooked(new Exception("Random class"));
      fail("Should have blown up");
    } catch (TypeMismatchPoemException e) {
      e = null;
    }
    it.assertValidCooked(
        it.cookedOfRaw("_administer_"));

    Group g = (Group) getDb().getGroupTable().firstSelection(null);
    try {
      it.assertValidCooked(g);
      fail("Should have blown up");
    } catch (ValidationPoemException e) {
      e = null;
    }
  }

  public void testPossibleRaws() {
    super.testPossibleRaws();
    Enumeration<String> them = it.possibleRaws();
    them = it.possibleRaws();
    int count = 0;
    while(them.hasMoreElements()) {
      them.nextElement();
      count++;
    }
    if (it.getNullable())
      assertEquals(6,count);
    else {
      Enumeration<String> them2 = it.possibleRaws();
      while (them2.hasMoreElements()) {
        String raw = them2.nextElement();
        System.err.println(raw);
      }
     assertEquals(5,count);
    }
    
  }

  public void testRawOfCooked() {
    super.testRawOfCooked();
    assertEquals("_administer_", it.rawOfCooked(getDb().administerCapability()));
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {
    assertEquals("Capability StringKeyReference on name", it.toDsdType()); 

  }

  /**
   * Test with wrong parameters.
   */
  public void testBadConstructor() {
    try {
      new StringKeyReferencePoemType(null, "name", false, 1);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
    try {
      new StringKeyReferencePoemType(getDb().getCapabilityTable(), null, false, 1);
      fail("Should have blown up");
    } catch (NullPointerException e) {
      e = null;
    }
  }
}
