/**
 * 
 */
package org.melati.poem.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.melati.poem.PoemDatabase;
import org.melati.poem.dbms.Hsqldb;

import junit.framework.TestCase;

/**
 * Test db outside of PoemTestCase so that we do not run into session closing
 * issues.<p>
 * This test excercises the jdbc metadata unification.
 * 
 * @author timp
 * @since 25 Jan 2007
 * 
 */
public class DatabaseUnifyWithDBTest extends TestCase {

  /**
   * @param name
   */
  public DatabaseUnifyWithDBTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Set up tables not in dsd to exercise other branch of unification.
   */
  public void testUnifyWithDB() throws Exception {
    Driver driver = null;
    Class driverClass;
    String url = "jdbc:hsqldb:mem:unifiable";
    driverClass = Class.forName("org.hsqldb.jdbcDriver");
    driver = (Driver)driverClass.newInstance();
    Properties info = new Properties();
    info.put("user", "sa");
    info.put("password", "");

    Connection c = driver.connect(url, info);
    Statement s = c.createStatement();
    Hsqldb dbms = new Hsqldb();
    StringBuffer sqb = new StringBuffer();
    sqb.append(dbms.createTableSql() + dbms.getQuotedName("testable") + " (");
    sqb.append(dbms.getQuotedName("id") + " INTEGER NOT NULL");
    sqb.append(", ");
    sqb.append(dbms.getQuotedName("testname") + " VARCHAR(233) NOT NULL");
    sqb.append(", ");
    sqb.append(dbms.getQuotedName("deleted") + " BOOLEAN");
    sqb.append(", ");
    sqb.append(dbms.getQuotedName("canRead") + " INTEGER");
    sqb.append(", ");
    sqb.append(dbms.getQuotedName("canCreate") + " INTEGER");
    sqb.append(", ");
    sqb.append(dbms.getQuotedName("canDelete") + " INTEGER");
    sqb.append(", ");
    sqb.append(dbms.getQuotedName("canSelect") + " INTEGER");
    sqb.append(")");

    try { 
      s.executeUpdate(sqb.toString());
    } catch (SQLException e) { 
      assertTrue(e.getMessage().indexOf("already exists") > 0);
    }
    try { 
      s.executeUpdate("CREATE UNIQUE INDEX \"TESTABLE_ID_INDEX\" ON \"TESTABLE\" (\"ID\")");    
    } catch (SQLException e) { 
      assertTrue(e.getMessage().indexOf("already exists") > 0);
    }
    StringBuffer sqb2 = new StringBuffer();
    sqb2.append("INSERT INTO " + dbms.getQuotedName("testable") + " (");
    sqb2.append(dbms.getQuotedName("id")) ;
    sqb2.append(", ");
    sqb2.append(dbms.getQuotedName("testname"))  ;
    sqb2.append(") VALUES (");
    sqb2.append("0, 't1'");
    sqb2.append(") ");

    try { 
      s.executeUpdate(sqb2.toString());
    } catch (SQLException e) { 
      assertTrue(e.getMessage().indexOf("Violation of unique index") >= 0);
    }
   
    s.close();
    c.commit();
    if (!c.isClosed()) {
      Statement st = c.createStatement();
      //st.execute("SHUTDOWN SCRIPT"); 
      st.close();
    }
    c.close();
    PoemDatabase db = new PoemDatabase();
    //db.setLogSQL(true);
    db.connect("unifiable", "org.melati.poem.dbms.Hsqldb",
        url, "sa", "", 22);
    assertEquals(22, db.getFreeTransactionsCount());
    assertTrue(db.getClass().getName() == "org.melati.poem.PoemDatabase");
    assertEquals("testable", db.getTable("testable").getName());
    db.setLogSQL(false);
  }
}
