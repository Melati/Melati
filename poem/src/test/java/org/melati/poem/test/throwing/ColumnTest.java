package org.melati.poem.test.throwing;

import java.sql.ResultSet;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.SQLSeriousPoemException;
import org.melati.poem.dbms.test.sql.Thrower;

/**
 * @author timp
 * @since 26 Feb 2007
 *
 */
public class ColumnTest extends org.melati.poem.test.ColumnTest {

  /**
   * Constructor.
   * @param name
   */
  public ColumnTest(String name) {
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
    maxTrans = 8;
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

  public void testAsEmptyField() {
    //super.testAsEmptyField();
  }

  public void testCachedSelectionWhereEq() {
    //super.testCachedSelectionWhereEq();
  }

  public void testColumn() {
    //super.testColumn();
  }

  public void testDump() {
    //super.testDump();
  }

  public void testEnsure() {
    //super.testEnsure();
  }

  public void testEqClause() {
    //super.testEqClause();
  }

  public void testFirstFree() {
    Thrower.startThrowing(ResultSet.class, "next");
    try { 
      super.testFirstFree();
      fail("Should have blown up");
    } catch (SQLSeriousPoemException e) { 
      assertEquals("ResultSet bombed", e.innermostException().getMessage());
    } finally { 
      Thrower.stopThrowing(ResultSet.class, "next");
    }
  }

  public void testFirstWhereEq() {
    //super.testFirstWhereEq();
  }

  public void testFullQuotedName() {
    //super.testFullQuotedName();
  }

  public void testGetColumnInfo() {
    //super.testGetColumnInfo();
  }

  public void testGetCooked() {
    //super.testGetCooked();
  }

  public void testGetDatabase() {
    //super.testGetDatabase();
  }

  public void testGetDescription() {
    //super.testGetDescription();
  }

  public void testGetDisplayLevel() {
    //super.testGetDisplayLevel();
  }

  public void testGetDisplayName() {
    //super.testGetDisplayName();
  }

  public void testGetDisplayOrderPriority() {
    //super.testGetDisplayOrderPriority();
  }

  public void testGetHeight() {
    //super.testGetHeight();
  }

  public void testGetIndexed() {
    //super.testGetIndexed();
  }

  public void testGetIntegrityFix() {
    //super.testGetIntegrityFix();
  }

  public void testGetName() {
    //super.testGetName();
  }

  public void testGetRaw_unsafe() {
    //super.testGetRaw_unsafe();
  }

  public void testGetRaw() {
    //super.testGetRaw();
  }

  public void testGetRenderInfo() {
    //super.testGetRenderInfo();
  }

  public void testGetSearchability() {
    //super.testGetSearchability();
  }

  public void testGetSortDescending() {
    //super.testGetSortDescending();
  }

  public void testGetSQLType() {
    //super.testGetSQLType();
  }

  public void testGetTable() {
    //super.testGetTable();
  }

  public void testGetType() {
    //super.testGetType();
  }

  public void testGetUnique() {
    //super.testGetUnique();
  }

  public void testGetUserCreateable() {
    //super.testGetUserCreateable();
  }

  public void testGetUserEditable() {
    //super.testGetUserEditable();
  }

  public void testGetWidth() {
    //super.testGetWidth();
  }

  public void testIsDeletedColumn() {
    //super.testIsDeletedColumn();
  }

  public void testIsTroidColumn() {
    //super.testIsTroidColumn();
  }

  public void testLoad_unsafe() {
/*
    ThrowingPreparedStatement.startThrowing("setInt");
    try { 
      super.testLoad_unsafe();
      fail("Should have bombed");
    } catch (FieldContentsPoemException e) { 
      assertEquals("PreparedStatement bombed", e.innermostException().getMessage());
    } finally { 
      ThrowingPreparedStatement.stopThrowing("setInt");
    }
 */
  }

  public void testQuotedName() {
    //super.testQuotedName();
  }

  public void testReferencesTo() {
    //super.testReferencesTo();
  }

  public void testSave_unsafe() {
    //super.testSave_unsafe();
  }

  public void testSelectionWhereEq() {
    //super.testSelectionWhereEq();
  }

  public void testSetCooked() {
    //super.testSetCooked();
  }

  public void testSetDisplayLevel() {
    //super.testSetDisplayLevel();
  }

  public void testSetIntegrityFix() {
    //super.testSetIntegrityFix();
  }

  public void testSetRaw_unsafe() {
    //super.testSetRaw_unsafe();
  }

  public void testSetRaw() {
    //super.testSetRaw();
  }

  public void testSetRawString() {
    //super.testSetRawString();
  }

  public void testSetSearchability() {
    //super.testSetSearchability();
  }

  public void testToString() {
    //super.testToString();
  }
  

}
