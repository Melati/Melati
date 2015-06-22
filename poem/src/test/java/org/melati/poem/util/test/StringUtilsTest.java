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
 * Tim Joyce <timj At paneris.org>
 *
 */
package org.melati.poem.util.test;

import junit.framework.TestCase;

import org.melati.poem.util.StringUtils;

/**
 * A test for the org.melati.util.StringUtil class.
 *
 * @author TimJ@paneris.org
 */
public class StringUtilsTest extends TestCase {

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#split}.
   */
  public void testSplit() {
    String them = "one,two,three";
    assertEquals(3, StringUtils.split(them, ',').length); 
    assertEquals("two", StringUtils.split(them, ',')[1]); 
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#appendEscaped(StringBuffer, String, char)}.
   */
  public void testAppendEscaped() {
    StringBuffer buff = new StringBuffer();
    assertEquals("",buff.toString());
    StringUtils.appendEscaped(buff, "a$b", '$');
    assertEquals("a\\$b",buff.toString());
    StringUtils.appendEscaped(buff, "a$a$a$a", '$');
    assertEquals("a\\$ba\\$a\\$a\\$a",buff.toString());
    StringUtils.appendEscaped(buff, "c\\d", '$');
    assertEquals("a\\$ba\\$a\\$a\\$ac\\\\d",buff.toString());
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#appendQuoted(StringBuffer, String, char)}.
   */
  public void testAppendQuoted() {
    StringBuffer buff = new StringBuffer();
    assertEquals("",buff.toString());
    StringUtils.appendQuoted(buff, "a$b", '$');
    assertEquals("$a\\$b$",buff.toString());
    StringUtils.appendQuoted(buff, "c\\d", '$');
    assertEquals("$a\\$b$$c\\\\d$",buff.toString());
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#quoted(String, char)}.
   */
  public void testQuoted() {
    assertEquals("\"a\"", StringUtils.quoted("a",'"'));
    assertEquals("'a'", StringUtils.quoted("a",'\''));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#escaped(String, char)}.
   */
  public void testEscaped() {
 // deprecated
 //   assertEquals("Eureka\\!", StringUtils.escaped("Eureka!",'!'));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#capitalised(String)}.
   */
  public void testCapitalised() {
    assertEquals("Capitalised", StringUtils.capitalised("capitalised"));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#uncapitalised(String)}.
   */
  public void testUncapitalised() {
    assertEquals("capitalised", StringUtils.uncapitalised("Capitalised"));
  }


  /**
   * Test method for {@link org.melati.poem.util.StringUtils#tr(String, String, String)}.
   */
  public void testTrStringStringString() {
    assertEquals("Muther", StringUtils.tr("Mother","o","u"));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#tr(String, char, char)}.
   */
  @SuppressWarnings("deprecation")
  public void testTrStringCharChar() {
    assertEquals("Muther", StringUtils.tr("Mother",'o','u'));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#concatenated(String, String[])}.
   */
  public void testConcatenated() {
    String[] them = {"one", "two", "three"};
    assertEquals("one,two,three", StringUtils.concatenated(",",them));
    assertEquals("onetwothree", StringUtils.concatenated(null,them));
    assertEquals("", StringUtils.concatenated(null,new String[] {}));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#randomString(int)}.
   */
  public void testRandomString() {
    assertEquals(5, StringUtils.randomString(5).length());
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#nulled(String)}.
   */
  public void testNulled() {
    assertEquals(null, StringUtils.nulled(""));
    assertEquals("null", StringUtils.nulled("null"));
  }

  
  /**
   * Test method for {@link org.melati.poem.util.StringUtils#unNulled(String)}.
   */
  public void testUnNulled() {
    String expected = "a";
    String actual = StringUtils.unNulled("a");
    assertEquals(expected, actual);
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#unNulled(String)}.
   */
  public void testUnNulledNull() {
    String expected = "";
    String actual = StringUtils.unNulled(null);
    assertEquals(expected, actual);
  }

  /**
   * Test hex encoding and decoding.
   * Test method for {@link org.melati.poem.util.StringUtils#hexEncoding(byte[])}
   *
   */
  public void testHexEncoding() {
    String[] strings = {"01234567", "abcdef", "f1234bcd"};
    for (int i = 0; i<strings.length; i++){
      assertEquals(new String(StringUtils.hexDecoding(
          StringUtils.hexEncoding(strings[i].getBytes()))),
                   strings[i]);
    }
  }


  /**
   * Test method for {@link org.melati.poem.util.StringUtils#hexDecoding(char)}.
   */
  public void testHexDecodingChar() {
    assertEquals(new Integer(10), new Integer(StringUtils.hexDecoding('A')));
    assertEquals(new Integer(10), new Integer(StringUtils.hexDecoding('a')));
    assertEquals(new Integer(15), new Integer(StringUtils.hexDecoding('F')));
    assertEquals(new Integer(15), new Integer(StringUtils.hexDecoding('f')));
    try { 
      StringUtils.hexDecoding('g');
      fail("should have bombed");
    } catch (IllegalArgumentException e) {
      e = null;      
    }
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#hexDecoding(String)}.
   */
  public void testHexDecodingString() {
    byte[] b = StringUtils.hexDecoding("41");
    assertEquals(new Integer(65), new Integer(b[0]));
    try { 
      StringUtils.hexDecoding("411");
      fail("should have bombed");
    } catch (IllegalArgumentException e) {
      e = null;      
    }
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#isQuoted(String)}.
   */
  public void testIsQuoted() {
    assertTrue(StringUtils.isQuoted("\"a\""));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#isQuoted(String)}.
   */
  public void testIsQuotedNull() {
    assertTrue(!StringUtils.isQuoted(null));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#isQuoted(String)}.
   */
  public void testIsQuotedBlank() {
    assertTrue(!StringUtils.isQuoted(""));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#isQuoted(String)}.
   */
  public void testIsQuotedNot() {
    assertTrue(!StringUtils.isQuoted("a"));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#isQuoted(String)}.
   */
  public void testIsQuotedDouble() {
    assertTrue(StringUtils.isQuoted("\"a\""));
  }

  /**
   * Test method for {@link org.melati.poem.util.StringUtils#isQuoted(String)}.
   */
  public void testIsQuotedSingle() {
    assertTrue(StringUtils.isQuoted("\'a\'"));
  }



}
