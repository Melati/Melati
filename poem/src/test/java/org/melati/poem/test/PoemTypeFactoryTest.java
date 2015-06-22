package org.melati.poem.test;

import org.melati.poem.Database;
import org.melati.poem.PoemTypeFactory;

/**
 * @author timp
 * @since 29 Jan 2007
 *
 */
public class PoemTypeFactoryTest extends PoemTestCase {

  public PoemTypeFactoryTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.PoemTypeFactory#PoemTypeFactory(int)}.
   */
  public void testPoemTypeFactory() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemTypeFactory#getCode()}.
   */
  public void testGetCode() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemTypeFactory#getName()}.
   */
  public void testGetName() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PoemTypeFactory#getDisplayName()}.
   */
  public void testGetDisplayName() {
    assertEquals("TROID", PoemTypeFactory.TROID.getName());
    assertEquals("DELETED", PoemTypeFactory.DELETED.getName());
    assertEquals("TYPE", PoemTypeFactory.TYPE.getName());
    assertEquals("BOOLEAN", PoemTypeFactory.BOOLEAN.getName());
    assertEquals("INTEGER", PoemTypeFactory.INTEGER.getName());
    assertEquals("DOUBLE", PoemTypeFactory.DOUBLE.getName());
    assertEquals("STRING", PoemTypeFactory.STRING.getName());
    assertEquals("DATE", PoemTypeFactory.DATE.getName());
    assertEquals("PASSWORD", PoemTypeFactory.PASSWORD.getName());
    assertEquals("TIMESTAMP", PoemTypeFactory.TIMESTAMP.getName());
    assertEquals("DISPLAYLEVEL", PoemTypeFactory.DISPLAYLEVEL.getName());
    assertEquals("SEARCHABILITY", PoemTypeFactory.SEARCHABILITY.getName());
    assertEquals("BINARY", PoemTypeFactory.BINARY.getName());
    assertEquals("LONG", PoemTypeFactory.LONG.getName());
    assertEquals("INTEGRITYFIX", PoemTypeFactory.INTEGRITYFIX.getName());
    assertEquals("BIGDECIMAL", PoemTypeFactory.BIGDECIMAL.getName());    
  }

  /**
   * Test method for {@link org.melati.poem.PoemTypeFactory#getDescription()}.
   */
  public void testGetDescription() {
    assertEquals("...", PoemTypeFactory.TROID.getDescription());
    assertEquals("...", PoemTypeFactory.DELETED.getDescription());
    assertEquals("...", PoemTypeFactory.TYPE.getDescription());
    assertEquals("...", PoemTypeFactory.BOOLEAN.getDescription());
    assertEquals("...", PoemTypeFactory.INTEGER.getDescription());
    assertEquals("...", PoemTypeFactory.DOUBLE.getDescription());
    assertEquals("...", PoemTypeFactory.STRING.getDescription());
    assertEquals("...", PoemTypeFactory.DATE.getDescription());
    assertEquals("...", PoemTypeFactory.PASSWORD.getDescription());
    assertEquals("...", PoemTypeFactory.TIMESTAMP.getDescription());
    assertEquals("...", PoemTypeFactory.DISPLAYLEVEL.getDescription());
    assertEquals("...", PoemTypeFactory.SEARCHABILITY.getDescription());
    assertEquals("...", PoemTypeFactory.BINARY.getDescription());
    assertEquals("...", PoemTypeFactory.LONG.getDescription());
    assertEquals("...", PoemTypeFactory.INTEGRITYFIX.getDescription());
    assertEquals("...", PoemTypeFactory.BIGDECIMAL.getDescription());
  }

  /**
   * Test method for {@link org.melati.poem.PoemTypeFactory#
   * forCode(org.melati.poem.Database, int)}.
   */
  public void testForCode() {
    Database db = getDb();
    PoemTypeFactory userType = PoemTypeFactory.forCode(db,0);
    assertEquals("User", userType.getDisplayName());
    assertEquals("A registered User of the database", userType.getDescription());
  }

}
