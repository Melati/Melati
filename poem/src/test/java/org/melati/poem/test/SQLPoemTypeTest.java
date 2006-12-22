/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.NullTypeMismatchPoemException;
import org.melati.poem.PoemType;
import org.melati.poem.TypeMismatchPoemException;

/**
 * @author timp
 * @since 21 Dec 2006
 * 
 */
abstract public class SQLPoemTypeTest extends PoemTestCase {

  /**
   * @param name
   */
  public SQLPoemTypeTest() {
    super();
  }

  /**
   * @param name
   */
  public SQLPoemTypeTest(String name) {
    super(name);
  }

  protected PoemType it = null;

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    setObjectUnderTest();
  }

  abstract void setObjectUnderTest();

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#sqlTypeCode()}.
   */
  public void testSqlTypeCode() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#sqlDefinition(org.melati.poem.dbms.Dbms)}.
   */
  public void testSqlDefinition() {

  }

  /**
   * Test method for {@link org.melati.poem.SQLType#sqlDefaultValue()}.
   */
  public void testSqlDefaultValue() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#sqlTypeDefinition(org.melati.poem.dbms.Dbms)}.
   */
  public void testSqlTypeDefinition() {

  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#getRaw(java.sql.ResultSet, int)}.
   */
  public void testGetRaw() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#setRaw(java.sql.PreparedStatement, int, java.lang.Object)}.
   */
  public void testSetRaw() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#assertValidRaw(java.lang.Object)}.
   */
  public void testAssertValidRaw() {
    if (it.getNullable())
      it.assertValidRaw(null);
    else
      try {
        it.assertValidRaw(null);
        fail("Should have blown up");
      } catch (NullTypeMismatchPoemException e) {
        e = null;
      }
    try {
      it.assertValidRaw(new Exception("Random class"));
      fail("Should have blown up");
    } catch (TypeMismatchPoemException e) {
      e = null;
    }

  }

  /**
   * Test method for {@link org.melati.poem.PoemType#possibleRaws()}.
   */
  public void testPossibleRaws() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#stringOfRaw(java.lang.Object)}.
   */
  public void testStringOfRaw() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#rawOfString(java.lang.String)}.
   */
  public void testRawOfString() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#assertValidCooked(java.lang.Object)}.
   */
  public void testAssertValidCooked() {
    if (it.getNullable())
      it.assertValidCooked(null);
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
  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#cookedOfRaw(java.lang.Object)}.
   */
  public void testCookedOfRaw() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#rawOfCooked(java.lang.Object)}.
   */
  public void testRawOfCooked() {
    if (it.getNullable())
      assertNull(it.rawOfCooked(null));
    else
      try {
        it.rawOfCooked(null);
        fail("Should have blown up");
      } catch (NullTypeMismatchPoemException e) {
        e = null;
      }
  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#stringOfCooked(java.lang.Object, org.melati.util.MelatiLocale, int)}.
   */
  public void testStringOfCooked() {

  }

  /**
   * Test method for {@link org.melati.poem.PoemType#getNullable()}.
   */
  public void testGetNullable() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    assertNull(it.canRepresent(new DisplayLevelPoemType()));
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemType#withNullable(boolean)}.
   */
  public void testWithNullable() {

  }

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#saveColumnInfo(org.melati.poem.ColumnInfo)}.
   */
  public void testSaveColumnInfo() {

  }

  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {

  }

}
