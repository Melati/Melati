package org.melati.poem.dbms.test;

import junit.framework.TestCase;

import org.melati.poem.dbms.AnsiStandard;
import org.melati.poem.dbms.Hsqldb;
import org.melati.poem.dbms.Postgresql;

/**
 * @author Tim
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CaseInsensitiveRegExpSQL extends TestCase {

  /**
   * Constructor for CaseInsensitiveRegExpSQL.
   * @param arg0
   */
  public CaseInsensitiveRegExpSQL(String arg0) {
    super(arg0);
  }

  public void testHsqldb() {
    String expected = "a LIKE %b%";
    Hsqldb db = new Hsqldb();
    String actual = db.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

  public void testHsqldbQuoted() {
    String expected = "a LIKE \'%b%\'";
    Hsqldb db = new Hsqldb();
    String actual = db.caseInsensitiveRegExpSQL("a", "\"b\"");
    assertEquals(expected, actual);
  }

  public void testHsqldbBlank() {
    String expected = " LIKE %%";
    Hsqldb db = new Hsqldb();
    String actual = db.caseInsensitiveRegExpSQL("", "");
    assertEquals(expected, actual);
  }

  public void testAnsiStandard() {
    String expected = "a REGEXP b";
    AnsiStandard db = new AnsiStandard();
    String actual = db.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

  public void testPostgresql() {
    String expected = "a ~* b";
    Postgresql db = new Postgresql();
    String actual = db.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

}
