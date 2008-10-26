/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.util.test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.melati.util.HTMLUtils;

import junit.framework.TestCase;

/**
 * @author timp
 * @since  25 Oct 2008
 *
 */
public class HTMLUtilsTest extends TestCase {

  /**
   * Constructor.
   * @param name
   */
  public HTMLUtilsTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#add(javax.swing.text.html.parser.ContentModel, javax.swing.text.html.parser.Element, javax.swing.text.html.parser.Element)}.
   */
  public void testAdd() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#addToContentModels(javax.swing.text.html.parser.DTD, javax.swing.text.html.parser.Element, javax.swing.text.html.parser.Element)}.
   */
  public void testAddToContentModels() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#dtdForHTMLParser()}.
   */
  public void testDtdForHTMLParser() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#entityFor(char, boolean, java.nio.charset.CharsetEncoder, boolean)}.
   */
  public void testEntityFor() {
    cTest(null);
    CharsetEncoder ce = Charset.forName("ISO-8859-1").newEncoder();
    cTest(ce);
    ce = Charset.forName("UTF-8").newEncoder();
    cTest(ce);
    
  }
  private void cTest(CharsetEncoder ce) {
    assertNull(HTMLUtils.entityFor('a',true, ce, false));
    assertEquals("&Aacute;",HTMLUtils.entityFor("\u00C1".charAt(0),false, null, false));
    char it = 193;
    assertEquals("&Aacute;",HTMLUtils.entityFor(it,false, ce, false));
    assertEquals("&Aacute;",HTMLUtils.entityFor("\u00C1".charAt(0),false, ce, true));
    System.err.println("\u00C1".charAt(0));
    System.err.println(new Integer("\u00C1".charAt(0)));
    System.err.println(new Integer("?".charAt(0)));
    System.err.println("Acirc=" +new Integer("Â".charAt(0)));
    System.err.println(HTMLUtils.entityFor("Â".charAt(0),false, ce, true));
    System.err.println(HTMLUtils.entityFor(it,false, ce, true));
    System.err.println("");
    
  }
  /**
   * Test method for {@link org.melati.util.HTMLUtils#entitied(java.lang.String, boolean, java.lang.String, boolean)}.
   */
  public void testEntitiedStringBooleanStringBoolean() {
    dTest("ISO-8859-1");
    dTest("UTF-8");
  }
  void dTest(String csName){
    System.err.println(HTMLUtils.entitied("Â",false, csName, true));
    
  }
  /**
   * Test method for {@link org.melati.util.HTMLUtils#entitied(java.lang.String)}.
   */
  public void testEntitiedString() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#jsEscapeFor(char)}.
   */
  public void testJsEscapeFor() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#jsEscaped(java.lang.String)}.
   */
  public void testJsEscaped() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#write(java.io.Writer, javax.swing.text.html.HTML.Tag, javax.swing.text.AttributeSet)}.
   */
  public void testWrite() {
  }

  /**
   * Test method for {@link org.melati.util.HTMLUtils#stringOf(javax.swing.text.html.HTML.Tag, javax.swing.text.AttributeSet)}.
   */
  public void testStringOf() {
  }

}
