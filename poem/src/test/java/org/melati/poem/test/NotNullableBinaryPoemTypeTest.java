/**
 * 
 */
package org.melati.poem.test;

import java.sql.PreparedStatement;

import org.melati.poem.BinaryLengthValidationPoemException;
import org.melati.poem.BinaryPoemType;
import org.melati.poem.NullTypeMismatchPoemException;
import org.melati.poem.SQLPoemType;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.AnsiStandard;
import org.melati.util.Base64;

/**
 * @author timp
 * @since 21 Dec 2006
 *
 */
public class NotNullableBinaryPoemTypeTest extends SizedAtomPoemTypeTest {

  /**
   * 
   */
  public NotNullableBinaryPoemTypeTest() {
  }

  /**
   * @param name
   */
  public NotNullableBinaryPoemTypeTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.SQLPoemTypeTest#setObjectUnderTest()
   */
  void setObjectUnderTest() {
    it = new BinaryPoemType(false, 20);
  }

  /**
   * Test method for {@link org.melati.poem.SQLType#quotedRaw(java.lang.Object)}.
   */
  public void testQuotedRaw() {
    assertEquals("'" + Base64.encode(new byte[20]) + "'", 
        ((SQLPoemType)it).quotedRaw(new byte[20]));

  }

  /**
   * Test method for {@link org.melati.poem.PoemType#toDsdType()}.
   */
  public void testToDsdType() {
    assertEquals("byte[]", it.toDsdType()); 

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#setRaw(java.sql.PreparedStatement, int, java.lang.Object)}.
   */
  public void testSetRaw() {
    try {
      ((SQLPoemType)it).setRaw((PreparedStatement)null, 1, null);
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
      //String sVal = ((SQLType)it).sqlDefaultValue();

      //Object value = it.rawOfString(sVal);
      try {
        ((SQLPoemType)it).setRaw((PreparedStatement)null, 1, new byte[20]);
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
   * {@link org.melati.poem.PoemType#rawOfString(java.lang.String)}.
   */
  public void testRawOfString() {
    super.testRawOfString();
    byte[] b = (byte[])it.rawOfString("AA");
    assertEquals(new byte[3].length, b.length);
    assertEquals(b[1],b[0]);
  }

  public void testToString() {
    assertEquals("binary(20)",it.toString());
  }
  

  /**
   * Test method for
   * {@link org.melati.poem.PoemType#assertValidRaw(java.lang.Object)}.
   */
  public void testAssertValidRaw() {
    super.testAssertValidRaw();
    try {
      it.assertValidRaw(new byte[33]);
      fail("Should have blown up");
    } catch (BinaryLengthValidationPoemException e) {
      e = null;
    }

  }

  /**
   * Test method for
   * {@link org.melati.poem.SQLType#sqlDefinition(org.melati.poem.dbms.Dbms)}.
   */
  public void testSqlDefinition() {
    super.testSqlDefinition();
    BinaryPoemType it2 = (BinaryPoemType)((BinaryPoemType)it).withSize(-1);
    it2.sqlDefinition(getDb().getDbms());
    try {
      it2.sqlDefinition(new AnsiStandard());
    } catch (SQLSeriousPoemException e) {
      e = null;
    }
  }

}
