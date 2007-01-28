/**
 * 
 */
package org.melati.poem.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.melati.poem.PreparedStatementFactory;

/**
 * @author timp
 * @since 26 Jan 2007
 *
 */
public class PreparedStatementFactoryTest extends PoemTestCase {

  /**
   * @param name
   */
  public PreparedStatementFactoryTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.PreparedStatementFactory#get(int)}.
   */
  public void testGet() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PreparedStatementFactory#PreparedStatementFactory(org.melati.poem.Database, java.lang.String)}.
   */
  public void testPreparedStatementFactory() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PreparedStatementFactory#preparedStatement(org.melati.poem.PoemTransaction)}.
   */
  public void testPreparedStatementPoemTransaction() {
    
  }

  /**
   * Test method for {@link org.melati.poem.PreparedStatementFactory#preparedStatement()}.
   */
  public void testPreparedStatement() throws Exception {
    PreparedStatementFactory it = new PreparedStatementFactory(getDb(),
        getDb().getUserTable().selectionSQL(null,null,null,true,false));
    assertTrue(it.toString().endsWith( 
        "(SQL: SELECT \"USER\".\"ID\" FROM \"USER\" ORDER BY \"USER\".\"NAME\")"));
    PreparedStatement ps = it.preparedStatement();
   // System.err.println(ps.toString());
    ResultSet rs = ps.executeQuery();
    rs.next();
    int troid = rs.getInt(1);
    assertEquals(1,troid);
    rs = ps.getResultSet();
    rs.next();
    troid = rs.getInt(1);
    assertEquals(1,troid);
  }

  /**
   * Test method for {@link org.melati.poem.PreparedStatementFactory#resultSet()}.
   */
  public void testResultSet() {
    
  }

}
