/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Tim Joyce
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Tim Joyce <timj@paneris.org>
 */
package org.melati.poem.dbms.test;

import junit.framework.TestCase;

import org.melati.poem.Persistable;
import org.melati.poem.dbms.AnsiStandard;
import org.melati.poem.dbms.Hsqldb;
import org.melati.poem.dbms.Postgresql;
import org.melati.poem.dbms.Oracle;

import com.mockobjects.dynamic.Mock;

/**
 * Test to ensure that CaseInsensitiveRegExpSQL behaves in the same 
 * way for all DBMS classes.
 *
 * @author Tim Toyce
 */
public class DbmsCaseTest extends TestCase {

  /**
   * Constructor for CaseInsensitiveRegExpSQL.
   * @param arg0 First argument
   */
  public DbmsCaseTest(String arg0) {
    super(arg0);
  }

  public void testGivesCapabilitySQL() {
    Mock userControl = new Mock(Persistable.class);
    userControl.expectAndReturn("getTroid",new Integer(42));
    AnsiStandard unit = new AnsiStandard();
    String actual = unit.givesCapabilitySQL((Persistable)userControl.proxy(),"hello");
    String expected = "SELECT * FROM \"groupmembership\" " + 
                      "WHERE \"user\" = 42 AND " + 
                      "EXISTS ( SELECT \"groupcapability\".\"group\" " + 
                      "FROM \"groupcapability\" WHERE " + 
                      "\"groupcapability\".\"group\" = " + 
                      "\"groupmembership\".\"group\" AND " + 
                      "\"capability\" = hello)";
    assertEquals(expected,actual);
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

  /**
   * 
   */
  public void testOracle() {
    String expected = "b LIKE '%a%'";
    Oracle db = new Oracle();
    String actual = db.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

}




