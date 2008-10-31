/**
 * 
 */
package org.melati.poem.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.melati.poem.Database;
import org.melati.poem.PreparedStatementFactory;
import org.melati.poem.UserTable;

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
    Database db = getDb();
    UserTable ut = db.getUserTable();
    String q = ut.selectionSQL(null,null,null,true,false);
    String expected = 
      "SELECT " + db.getDbms().getQuotedName("user") + "." + 
      getDb().getDbms().getQuotedName("id") + 
      " FROM " +db.getDbms().getQuotedName("user") + 
      " ORDER BY " + getDb().getDbms().getQuotedName("user") + "." +db.getDbms().getQuotedName("name");
      
    assertEquals(expected, q);
    PreparedStatementFactory it = new PreparedStatementFactory(db,
        expected);
    String sql = it.toString().substring(it.toString().indexOf('(') +6, it.toString().indexOf(')'));
    assertEquals(expected, sql);
    
    PreparedStatement ps = it.preparedStatement();
    ResultSet rs = ps.executeQuery();
    rs.next();
    int troid = rs.getInt(1);
    assertEquals(1,troid);
  }

  /**
   * Test method for {@link org.melati.poem.PreparedStatementFactory#resultSet()}.
   */
  public void testResultSet() throws Exception {
    PreparedStatementFactory it = new PreparedStatementFactory(getDb(),
        getDb().getUserTable().selectionSQL(null,null,null,true,false));
    ResultSet rs = it.resultSet();
    rs.next();
    int troid = rs.getInt(1);
    assertEquals(1,troid);
    
  }

}
