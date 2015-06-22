package org.melati.poem.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.melati.poem.DisplayLevelPoemType;
import org.melati.poem.NullTypeMismatchPoemException;
import org.melati.poem.PoemType;
import org.melati.poem.SQLPoemType;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.SQLType;
import org.melati.poem.TypeMismatchPoemException;

/**
 * @author timp
 * @since 21 Dec 2006
 * 
 */
abstract public class SQLPoemTypeSpec<T> extends PoemTestCase {

  protected PoemType<T> it = null;

  public SQLPoemTypeSpec() {
    super();
  }

  public SQLPoemTypeSpec(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    setObjectUnderTest();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  abstract void setObjectUnderTest();


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
   * Test method for {@link org.melati.poem.SQLType#sqlDefaultValue}.
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
    assertEquals("'" +((SQLPoemType<T>)it).sqlDefaultValue(getDb().getDbms()) + "'", 
        ((SQLPoemType<T>)it).quotedRaw(
            ((SQLPoemType<T>)it).rawOfString(
                ((SQLPoemType<T>)it).sqlDefaultValue(getDb().getDbms()))));

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#getRaw(java.sql.ResultSet, int)}.
   */
  public void testGetRaw() {
    try {
      ((SQLPoemType<T>)it).getRaw((ResultSet)null, 1);
      fail("Should have blown up");
    } catch (ClassCastException e) {
      assertTrue(it instanceof NonSQLPoemType);
    } catch (SQLSeriousPoemException e) {
      assertTrue(it instanceof SqlExceptionPoemType);
      e = null;
    } catch (NullPointerException e2) {
      e2 = null;
    }

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#setRaw(java.sql.PreparedStatement, int, java.lang.Object)}.
   */
  public void testSetRaw() {
    try {
      ((SQLPoemType<T>)it).setRaw((PreparedStatement)null, 1, null);
      fail("Should have blown up");
    } catch (ClassCastException e) {
      assertTrue(it instanceof NonSQLPoemType);
    } catch (NullPointerException e) {
      e = null;
    } catch (NullTypeMismatchPoemException e2) {
      assertFalse(it.getNullable());
      e2 = null;
    }
    try {
      @SuppressWarnings("unchecked")
      String sVal = ((SQLType<T>)it).sqlDefaultValue(getDb().getDbms());

      Object value = it.rawOfString(sVal);
      try {
        ((SQLPoemType<T>)it).setRaw((PreparedStatement)null, 1, value);
        fail("Should have blown up");
      } catch (SQLSeriousPoemException e) {
        assertTrue(it instanceof SqlExceptionPoemType);
        e = null;
      } catch (NullTypeMismatchPoemException e2) {
        assertFalse(it.getNullable());
        e2 = null;
      } catch (NullPointerException e3) {
        e3 = null;
      }
    } catch (ClassCastException e) {
      assertTrue(it instanceof NonSQLPoemType);
    }

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
    try {
      it.assertValidCooked(
          it.cookedOfRaw(
              it.rawOfString(((SQLPoemType<T>)it).sqlDefaultValue(getDb().getDbms()))));
    } catch (ClassCastException e){
      assertTrue(it instanceof NonSQLPoemType);
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
   * {@link org.melati.poem.PoemType#stringOfCooked
   *     (java.lang.Object, org.melati.poem.PoemLocale, int)}.
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
    DisplayLevelPoemType dl = new DisplayLevelPoemType();
    assertNull(it.canRepresent(dl));
    assertNull(dl.canRepresent(it));

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
    String itDsdType = it.getClass().getName().replaceFirst("PoemType","")
                           .replaceFirst("org.melati.poem.", "");
    assertEquals(itDsdType, it.toDsdType()); 

  }

}
