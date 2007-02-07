/**
 * 
 */
package org.melati.poem.test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;

import org.melati.poem.ColumnInfo;
import org.melati.poem.ColumnRenamePoemException;
import org.melati.poem.Database;
import org.melati.poem.DisplayLevel;
import org.melati.poem.DuplicateColumnNamePoemException;
import org.melati.poem.DuplicateDeletedColumnPoemException;
import org.melati.poem.DuplicateTroidColumnPoemException;
import org.melati.poem.ExecutingSQLPoemException;
import org.melati.poem.IntegrityFix;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemTypeFactory;
import org.melati.poem.Searchability;
import org.melati.poem.StandardIntegrityFix;
import org.melati.poem.TableInfo;
import org.melati.poem.UserTable;
import org.melati.poem.util.EnumUtils;

/**
 * Test the addition, and lter the deletion, of columns to a table.
 * @author timp
 * @since 01-Februray-2007
 */
public class DynamicTableTest extends EverythingTestCase {

  /**
   * Constructor for PoemTest.
   * @param arg0
   */
  public DynamicTableTest(String arg0) {
    super(arg0);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void databaseUnchanged() { 
    ColumnInfo ci = (ColumnInfo)getDb().getColumnInfoTable().getNameColumn().firstWhereEq("testdeletedcol");
    if (ci != null) { 
      System.err.println("Cleaning up: " + ci);
      ci.delete();
    }
    ci = (ColumnInfo)getDb().getColumnInfoTable().getNameColumn().firstWhereEq("testdeletedcol2");
    if (ci != null) { 
      System.err.println("Cleaning up: " + ci);
      ci.delete();
    }
    
    // It is not good enough to drop the new columns, as the deleted columnInfo's 
    // are still referred to, so drop the whole table
    try { 
      //getDb().sqlUpdate("ALTER TABLE " + getDb().getDbms().getQuotedName("dynamic") + 
      //        " DROP COLUMN " + getDb().getDbms().getQuotedName("testdeletedcol"));
      getDb().sqlUpdate("DROP TABLE " + getDb().getDbms().getQuotedName("dynamic"));
    } catch (ExecutingSQLPoemException e) { 
      System.err.println(e.getMessage());
      //assertTrue(e.getMessage().indexOf("it does not exist") > 0);
    }
    PoemDatabaseFactory.removeDatabase(databaseName);
  } 
  
  public Database getDb() {
    Database db = super.getDb(); 
    db.setLogSQL(true);
    return db;
  }

  

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitTroid() {
    UserTable ut = getDb().getUserTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testtroidcol");
    columnInfo.setDisplayname("Test Troid Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Troid column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.TROID);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.detail);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    try {
      columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
      fail("Should have blown up");
    } catch (DuplicateTroidColumnPoemException e) {
      e = null;
    }
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitDeleted() throws Exception {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testdeletedcol");
    columnInfo.setDisplayname("Test Deleted Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Deleted column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.DELETED);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdeletedcol").selectionWhereEq(Boolean.FALSE)).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdeletedcol").selectionWhereEq(Boolean.FALSE)).size());
    assertEquals(Boolean.FALSE, ut.two().getRaw("testdeletedcol"));
    assertEquals(Boolean.FALSE, ut.two().getCooked("testdeletedcol"));
    assertEquals(Boolean.FALSE, ut.getObject(0).getCooked("testdeletedcol"));
    try {
      columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
      fail("Should have blown up");
    } catch (DuplicateColumnNamePoemException e) {
      assertEquals("Can't add duplicate column dynamic.testdeletedcol: "
          + "deleted (BOOLEAN (org.melati.poem.DeletedPoemType)) "
          + "(from the running application) to dynamic "
          + "(from the data structure definition)", e.getMessage());
      e = null;
    }
    try {
      columnInfo.setName("testdeletedcol2");
      fail("Should have blown up");
    } catch (ColumnRenamePoemException e) {
      e = null;
    }
    ColumnInfo columnInfo2 = (ColumnInfo)getDb().getColumnInfoTable()
        .newPersistent();
    columnInfo2.setTableinfo(ti);
    columnInfo2.setName("testdeletedcol2");
    columnInfo2.setDisplayname("Test duplicate Deleted Column");
    columnInfo2.setDisplayorder(99);
    columnInfo2.setSearchability(Searchability.yes);
    columnInfo2.setIndexed(false);
    columnInfo2.setUnique(false);
    columnInfo2.setDescription("A non-nullable extra Deleted column");
    columnInfo2.setUsercreateable(true);
    columnInfo2.setUsereditable(true);
    columnInfo2.setTypefactory(PoemTypeFactory.DELETED);
    columnInfo2.setSize(-1);
    columnInfo2.setWidth(20);
    columnInfo2.setHeight(1);
    columnInfo2.setPrecision(0);
    columnInfo2.setScale(0);
    columnInfo2.setNullable(false);
    columnInfo2.setDisplaylevel(DisplayLevel.summary);
    columnInfo2.makePersistent();
    try {
      columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo2);
      fail("Should have blown up");
    } catch (DuplicateDeletedColumnPoemException e) {
      assertEquals("Can't add testdeletedcol2 to dynamic as a deleted column, "
          + "because it already has one, "
          + "i.e. dynamic.testdeletedcol: deleted "
          + "(BOOLEAN (org.melati.poem.DeletedPoemType)) "
          + "(from the running application)", e.getMessage());
      e = null;
    }
//    String query = "ALTER TABLE " + getDb().getUserTable().quotedName() + 
//    " DROP COLUMN " + getDb().quotedName(columnInfo.getName());
//    getDb().sqlUpdate(query);
//    columnInfo.delete();
//    getDb().getUserTable()
//    columnInfo2.delete();
//    getDb().uncacheContents();
//    getDb().unifyWithDB();
    PoemThread.commit();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitType() {
    DynamicTable dt = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = dt.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testtypecol");
    columnInfo.setDisplayname("Test Type Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Type column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.TYPE);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    Integer t = null;
    Enumeration en = dt.selection();
    Dynamic d = (Dynamic) en.nextElement();
    t = (Integer)d.getRaw("testtypecol");
    int count = 0;
    while (en.hasMoreElements()) {
      d = (Dynamic) en.nextElement();
      if (d.statusExistent()) {
        assertEquals(t, (Integer)d.getRaw("testtypecol"));
        count++;
      }
    }
    assertEquals(1, count);
    
    PoemTypeFactory t2 = null;
    Enumeration en2 = dt.selection();
    t2 = (PoemTypeFactory) ((Dynamic) en2.nextElement()).getCooked("testtypecol");
    while (en2.hasMoreElements()) {
      System.err.println(t2.getName());
      assertEquals(t2.getName(), ((PoemTypeFactory) ((Dynamic) en2.nextElement())
          .getCooked("testtypecol")).getName());
    }

    assertEquals(2, EnumUtils.vectorOf(
        dt.getColumn("testtypecol").selectionWhereEq(new Integer(0))).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        dt.getColumn("testtypecol").selectionWhereEq(new Integer(0))).size());
    assertEquals(new Integer(0), dt.two().getRaw("testtypecol"));
    assertEquals("user", ((PoemTypeFactory) dt.two().getCooked("testtypecol")).getName());
    assertEquals("user", ((PoemTypeFactory) dt.getObject(0).getCooked(
        "testtypecol")).getName());
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitBoolean() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testbooleancol");
    columnInfo.setDisplayname("Test Boolean Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Boolean column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.BOOLEAN);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testbooleancol").selectionWhereEq(Boolean.FALSE)).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testbooleancol").selectionWhereEq(Boolean.FALSE)).size());
    assertEquals(Boolean.FALSE, ut.two().getRaw("testbooleancol"));
    assertEquals(Boolean.FALSE, ut.two().getCooked(
        "testbooleancol"));
    assertEquals(Boolean.FALSE, ut.getObject(0).getCooked("testbooleancol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitInteger() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testintegercol");
    columnInfo.setDisplayname("Test Integer Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Integer column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testintegercol").selectionWhereEq(new Integer(0))).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testintegercol").selectionWhereEq(new Integer(0))).size());
    assertEquals(new Integer(0), ut.two()
        .getRaw("testintegercol"));
    assertEquals(new Integer(0), ut.two().getCooked(
        "testintegercol"));
    assertEquals(new Integer(0), ut.getObject(0).getCooked("testintegercol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }
  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitNullableInteger() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testnullableintegercol");
    columnInfo.setDisplayname("Test Nullable Integer Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A nullable extra Integer column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(true);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(0, EnumUtils.vectorOf(
        ut.getColumn("testnullableintegercol").selectionWhereEq(new Integer(0))).size());
    PoemThread.commit();
    assertEquals(0, EnumUtils.vectorOf(
        ut.getColumn("testnullableintegercol").selectionWhereEq(new Integer(0))).size());
    assertNull(ut.two().getRaw("testnullableintegercol"));
    assertNull(ut.two().getCooked("testnullableintegercol"));
    assertNull(ut.getObject(0).getCooked("testnullableintegercol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitDouble() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testdoublecol");
    columnInfo.setDisplayname("Test Double Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Double column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.DOUBLE);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdoublecol").selectionWhereEq(new Double(0))).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdoublecol").selectionWhereEq(new Double(0))).size());
    assertEquals(new Double(0), ut.two().getRaw("testdoublecol"));
    assertEquals(new Double(0), ut.two().getCooked(
        "testdoublecol"));
    assertEquals(new Double(0), ut.getObject(0).getCooked("testdoublecol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitLong() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testlongcol");
    columnInfo.setDisplayname("Test Long Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Long column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.LONG);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testlongcol").selectionWhereEq(new Long(0))).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testlongcol").selectionWhereEq(new Long(0))).size());
    assertEquals(new Long(0), ut.two().getRaw("testlongcol"));
    assertEquals(new Long(0), ut.two().getCooked("testlongcol"));
    assertEquals(new Long(0), ut.getObject(0).getCooked("testlongcol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitBigDecimal() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testbigdecimalcol");
    columnInfo.setDisplayname("Test Big Decimal Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Big Decimal column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.BIGDECIMAL);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testbigdecimalcol").selectionWhereEq(new BigDecimal(0.0)))
        .size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testbigdecimalcol").selectionWhereEq(new BigDecimal(0.0)))
        .size());
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitString() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("teststringcol");
    columnInfo.setDisplayname("Test String Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra String column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.STRING);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("teststringcol").selectionWhereEq("default")).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("teststringcol").selectionWhereEq("default")).size());
    assertEquals("default", ut.two().getRaw("teststringcol"));
    assertEquals("default", ut.two().getCooked("teststringcol"));
    assertEquals("default", ut.getObject(0).getCooked("teststringcol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitPassword() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testpasswordcol");
    columnInfo.setDisplayname("Test Password Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Password column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.PASSWORD);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testpasswordcol").selectionWhereEq("default")).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testpasswordcol").selectionWhereEq("default")).size());
    assertEquals("default", ut.two().getRaw("testpasswordcol"));
    assertEquals("default", ut.two().getCooked("testpasswordcol"));
    assertEquals("default", ut.getObject(0).getCooked("testpasswordcol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitDate() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testdatecol");
    columnInfo.setDisplayname("Test Date Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Date column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.DATE);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdatecol").selectionWhereEq(
            new java.sql.Date(new Date().getTime()))).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdatecol").selectionWhereEq(
            new java.sql.Date(new Date().getTime()))).size());
    assertEquals(new java.sql.Date(new Date().getTime()).toString(), ut
        .two().getRaw("testdatecol").toString());
    assertEquals(new java.sql.Date(new Date().getTime()).toString(), ut
        .two().getCooked("testdatecol").toString());
    assertEquals(new java.sql.Date(new Date().getTime()).toString(), ut
        .getObject(0).getCooked("testdatecol").toString());
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitTimestamp() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testtimestampcol");
    columnInfo.setDisplayname("Test Timestamp Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Timestamp column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.TIMESTAMP);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    Timestamp t = null;
    Enumeration en = ut.selection();
    t = (Timestamp) ((Dynamic) en.nextElement()).getRaw("testtimestampcol");
    while (en.hasMoreElements()) {
      assertEquals(t, (Timestamp) ((Dynamic) en.nextElement())
          .getRaw("testtimestampcol"));
    }
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testtimestampcol").selectionWhereEq(t)).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testtimestampcol").selectionWhereEq(t)).size());
    assertEquals(t, ut.two().getRaw("testtimestampcol"));
    assertEquals(t, ut.two().getCooked("testtimestampcol"));
    assertEquals(t, ut.getObject(0).getCooked("testtimestampcol"));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void brokentestAddColumnAndCommitBinary() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testbinarycol");
    columnInfo.setDisplayname("Test Binary Column");
    columnInfo.setDisplayorder(199);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Binary column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.BINARY);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    byte[] t = null;
    Enumeration en = ut.selection();
    t = (byte[]) ((Dynamic) en.nextElement()).getRaw("testbinarycol");
    while (en.hasMoreElements()) {
      assertEquals(t.length, ((byte[]) ((Dynamic) en.nextElement())
          .getRaw("testbinarycol")).length);
    }
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testbinarycol").selectionWhereEq(t)).size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testbinarycol").selectionWhereEq(t)).size());
    assertEquals(t.length, ((byte[])ut.two().getRaw("testbinarycol")).length);
    assertEquals(t.length, ((byte[])ut.two().getCooked("testbinarycol")).length);
    assertEquals(t.length,
        ((byte[]) ut.getObject(0).getCooked("testbinarycol")).length);
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitDisplaylevel() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testdisplaylevelcol");
    columnInfo.setDisplayname("Test Displaylevel Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Displaylevel column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.DISPLAYLEVEL);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    Integer t = null;
    Enumeration en = ut.selection();
    t = (Integer) ((Dynamic) en.nextElement()).getRaw("testdisplaylevelcol");
    while (en.hasMoreElements()) {
      assertEquals(t, (Integer) ((Dynamic) en.nextElement())
          .getRaw("testdisplaylevelcol"));
    }

    DisplayLevel t2 = null;
    Enumeration en2 = ut.selection();
    t2 = (DisplayLevel) ((Dynamic) en2.nextElement())
        .getCooked("testdisplaylevelcol");
    while (en2.hasMoreElements()) {
      System.err.println(t2);
      assertEquals(t2, ((DisplayLevel) ((Dynamic) en2.nextElement())
          .getCooked("testdisplaylevelcol")));
    }

    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdisplaylevelcol").selectionWhereEq(new Integer(0)))
        .size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testdisplaylevelcol").selectionWhereEq(new Integer(0)))
        .size());
    assertEquals(new Integer(0), ut.two().getRaw(
        "testdisplaylevelcol"));
    assertEquals(DisplayLevel.primary, ((DisplayLevel) ut.two()
        .getCooked("testdisplaylevelcol")));
    assertEquals(DisplayLevel.primary, ((DisplayLevel) ut.getObject(0)
        .getCooked("testdisplaylevelcol")));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitSearchability() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testsearchabilitycol");
    columnInfo.setDisplayname("Test searchability Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Searchability column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.SEARCHABILITY);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    Integer t = null;
    Enumeration en = ut.selection();
    t = (Integer) ((Dynamic) en.nextElement()).getRaw("testsearchabilitycol");
    while (en.hasMoreElements()) {
      assertEquals(t, (Integer) ((Dynamic) en.nextElement())
          .getRaw("testsearchabilitycol"));
    }

    Searchability t2 = null;
    Enumeration en2 = ut.selection();
    t2 = (Searchability) ((Dynamic) en2.nextElement())
        .getCooked("testsearchabilitycol");
    while (en2.hasMoreElements()) {
      System.err.println(t2);
      assertEquals(t2, ((Searchability) ((Dynamic) en2.nextElement())
          .getCooked("testsearchabilitycol")));
    }

    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testsearchabilitycol").selectionWhereEq(new Integer(0)))
        .size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testsearchabilitycol").selectionWhereEq(new Integer(0)))
        .size());
    assertEquals(new Integer(0), ut.two().getRaw(
        "testsearchabilitycol"));
    assertEquals(Searchability.primary, ((Searchability) ut.two()
        .getCooked("testsearchabilitycol")));
    assertEquals(Searchability.primary, ((Searchability) ut.getObject(0)
        .getCooked("testsearchabilitycol")));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }

  /**
   * @see org.melati.poem.Table#addColumnAndCommit(ColumnInfo)
   */
  public void testAddColumnAndCommitIntegrityfix() {
    DynamicTable ut = ((EverythingDatabase)getDb()).getDynamicTable();
    ColumnInfo columnInfo = (ColumnInfo) getDb().getColumnInfoTable()
        .newPersistent();
    TableInfo ti = ut.getTableInfo();
    columnInfo.setTableinfo(ti);
    columnInfo.setName("testintegrityfixcol");
    columnInfo.setDisplayname("Test Integrityfix Column");
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("A non-nullable extra Integrityfix column");
    columnInfo.setUsercreateable(true);
    columnInfo.setUsereditable(true);
    columnInfo.setTypefactory(PoemTypeFactory.INTEGRITYFIX);
    columnInfo.setSize(-1);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    columnInfo.makePersistent();
    getDb().setLogSQL(true);
    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);
    Integer t = null;
    Enumeration en = ut.selection();
    t = (Integer) ((Dynamic) en.nextElement()).getRaw("testIntegrityfixcol");
    while (en.hasMoreElements()) {
      assertEquals(t, (Integer) ((Dynamic) en.nextElement())
          .getRaw("testIntegrityfixcol"));
    }

    IntegrityFix t2 = null;
    Enumeration en2 = ut.selection();
    t2 = (IntegrityFix) ((Dynamic) en2.nextElement())
        .getCooked("testIntegrityfixcol");
    while (en2.hasMoreElements()) {
      System.err.println(t2);
      assertEquals(t2, ((IntegrityFix) ((Dynamic) en2.nextElement())
          .getCooked("testIntegrityfixcol")));
    }

    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testIntegrityfixcol").selectionWhereEq(new Integer(2)))
        .size());
    PoemThread.commit();
    assertEquals(2, EnumUtils.vectorOf(
        ut.getColumn("testIntegrityfixcol").selectionWhereEq(new Integer(2)))
        .size());
    assertEquals(new Integer(2), ut.two().getRaw(
        "testIntegrityfixcol"));
    assertEquals(StandardIntegrityFix.prevent, ((IntegrityFix) ut
        .two().getCooked("testIntegrityfixcol")));
    assertEquals(StandardIntegrityFix.prevent, ((IntegrityFix) ut.getObject(0)
        .getCooked("testIntegrityfixcol")));
    columnInfo.delete();
    getDb().setLogSQL(false);
  }


}
