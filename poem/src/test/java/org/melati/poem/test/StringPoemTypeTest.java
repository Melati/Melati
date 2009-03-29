/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.StringPoemType;
import org.melati.poem.dbms.DbmsFactory;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 27 Feb 2007
 *
 */
public class StringPoemTypeTest extends TestCase {

  /**
   * @param name
   */
  public StringPoemTypeTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.StringPoemType#toString()}.
   */
  public void testToString() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StringPoemType#StringPoemType(boolean, int)}.
   */
  public void testStringPoemType() {
    
  }

  /**
   * Test method for {@link org.melati.poem.StringPoemType#toDsdType()}.
   */
  public void testToDsdType() {
    
  }

  /**
   * Test method for {@link org.melati.poem.BasePoemType#sqlDefinition(org.melati.poem.dbms.Dbms)}.
   */
  public void testSqlDefinition() {
    assertEquals("VARCHAR(1) NULL", new StringPoemType(true,1).
            sqlDefinition(DbmsFactory.getDbms("org.melati.poem.dbms.AnsiStandard")));
    try { 
      new StringPoemType(true,-1).
      sqlDefinition(DbmsFactory.getDbms("org.melati.poem.dbms.AnsiStandard"));
      fail("Should have bombed");
    } catch (SQLSeriousPoemException e) { 
      e = null;
    }
  }

}
