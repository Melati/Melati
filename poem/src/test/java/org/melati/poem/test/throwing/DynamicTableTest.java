/**
 * 
 */
package org.melati.poem.test.throwing;

import java.sql.DatabaseMetaData;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.sql.Thrower;

/**
 * @author timp
 * @since 11 Jun 2007
 *
 */
public class DynamicTableTest extends org.melati.poem.test.DynamicTableTest {

  /**
   * Constructor. 
   * @param arg0
   */
  public DynamicTableTest(String arg0) {
    super(arg0);
  }
  protected void setUp() throws Exception {
    PoemDatabaseFactory.removeDatabase(getDatabaseName());
    super.setUp();
    assertEquals("org.melati.poem.dbms.test.HsqldbThrower",getDb().getDbms().getClass().getName());
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public Database getDatabase(String name) {

    maxTrans = 8;
    Database db = PoemDatabaseFactory.getDatabase(name, 
        "jdbc:hsqldb:mem:" + name,
        "sa", 
        "",
        "org.melati.poem.test.EverythingDatabase",
        "org.melati.poem.dbms.test.HsqldbThrower", 
        false, 
        false, 
        false, maxTrans);
    return db;
  }
  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitBigDecimal()
   */
  public void testAddColumnAndCommitBigDecimal() {
    //super.testAddColumnAndCommitBigDecimal();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitBinary()
   */
  public void testAddColumnAndCommitBinary() {
    //super.testAddColumnAndCommitBinary();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitBoolean()
   */
  public void testAddColumnAndCommitBoolean() {
    //super.testAddColumnAndCommitBoolean();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitDate()
   */
  public void testAddColumnAndCommitDate() {
    //super.testAddColumnAndCommitDate();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitDeleted()
   */
  public void testAddColumnAndCommitDeleted() throws Exception {
    //super.testAddColumnAndCommitDeleted();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitDisplaylevel()
   */
  public void testAddColumnAndCommitDisplaylevel() {
    //super.testAddColumnAndCommitDisplaylevel();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitDouble()
   */
  public void testAddColumnAndCommitDouble() {
    //super.testAddColumnAndCommitDouble();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitInteger()
   */
  public void testAddColumnAndCommitInteger() {
    //super.testAddColumnAndCommitInteger();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitIntegrityfix()
   */
  public void testAddColumnAndCommitIntegrityfix() {
    //super.testAddColumnAndCommitIntegrityfix();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitLong()
   */
  public void testAddColumnAndCommitLong() {
    //super.testAddColumnAndCommitLong();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitNullableInteger()
   */
  public void testAddColumnAndCommitNullableInteger() {
    //super.testAddColumnAndCommitNullableInteger();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitPassword()
   */
  public void testAddColumnAndCommitPassword() {
    //super.testAddColumnAndCommitPassword();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitSearchability()
   */
  public void testAddColumnAndCommitSearchability() {
    //super.testAddColumnAndCommitSearchability();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitString()
   */
  public void testAddColumnAndCommitString() {
    //super.testAddColumnAndCommitString();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitTimestamp()
   */
  public void testAddColumnAndCommitTimestamp() {
    //super.testAddColumnAndCommitTimestamp();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitTroid()
   */
  public void testAddColumnAndCommitTroid() {
    //super.testAddColumnAndCommitTroid();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddColumnAndCommitType()
   */
  public void testAddColumnAndCommitType() {
    //super.testAddColumnAndCommitType();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testAddTableAndCommit()
   */
  public void testAddTableAndCommit() throws Exception {
    Thrower.startThrowing(DatabaseMetaData.class, "getIndexInfo");
    try { 
      super.testAddTableAndCommit();
    } catch (SQLSeriousPoemException e) {
      assertEquals("DatabaseMetaData bombed", e.innermostException().getMessage());      
      Thrower.stopThrowing(DatabaseMetaData.class, "getIndexInfo");
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.DynamicTableTest#testExtraColumnAsField()
   */
  public void testExtraColumnAsField() {
    //super.testExtraColumnAsField();
  }

}
