/**
 * 
 */
package org.melati.poem.test.throwing;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PreparedSQLSeriousPoemException;
import org.melati.poem.PreparedStatementFactory;
import org.melati.poem.SQLPoemException;
import org.melati.poem.dbms.test.sql.ThrowingConnection;
import org.melati.poem.dbms.test.sql.ThrowingPreparedStatement;

/**
 * @author timp
 * @since 10 Feb 2007
 * 
 */
public class PreparedStatementFactoryTest extends
    org.melati.poem.test.PreparedStatementFactoryTest {

  /**
   * @param name
   */
  public PreparedStatementFactoryTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    PoemDatabaseFactory.removeDatabase(getDatabaseName());
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }
  protected void tearDown() throws Exception {
    try { 
      super.tearDown();
    } finally { 
      PoemDatabaseFactory.removeDatabase(getDatabaseName());      
    }
  }


  public Database getDatabase(String name) {
    maxTrans = 4;
    Database db = PoemDatabaseFactory.getDatabase(name, 
        "jdbc:hsqldb:mem:" + name,
        "sa", 
        "",
        "org.melati.poem.PoemDatabase",
        "org.melati.poem.dbms.test.HsqldbThrower", 
        false, 
        false, 
        false, maxTrans);
    return db;
  }

  public void testGet() {
    super.testGet();
  }

  public void testPreparedStatement() throws Exception {
    ThrowingConnection.startThrowing(Connection.class, "prepareStatement");
    try {
      super.testPreparedStatement();
      fail("Should have bombed");
    } catch (SQLPoemException e) {
      assertEquals("Connection bombed", e.innermostException().getMessage());
    }
    ThrowingConnection.stopThrowing(Connection.class, "prepareStatement");
  }

  public void testPreparedStatementFactory() {
    //super.testPreparedStatementFactory();
  }

  public void testPreparedStatementPoemTransaction() {
    //super.testPreparedStatementPoemTransaction();
  }

  public void testResultSet() throws Exception {
    PreparedStatementFactory it = new PreparedStatementFactory(getDb(),
        getDb().getUserTable().selectionSQL(null,null,null,true,false));
    try {
      ThrowingPreparedStatement.startThrowing(PreparedStatement.class, "executeQuery");
      it.resultSet();
      fail("Should have bombed");
    } catch (PreparedSQLSeriousPoemException e) {
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    }
    ThrowingPreparedStatement.stopThrowing(PreparedStatement.class, "executeQuery");
  }

}
  