/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Jim Wright
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
 *     Jim Wright <jimw@paneris.org>
 *     Bohemian Enterprise
 *     Predmerice nad Jizerou 77
 *     294 74
 *     Mlada Boleslav
 *     Czech Republic
 */

package org.melati.util.test;

import junit.framework.TestCase;

import org.melati.util.HttpHeader;

/**
 * Tests the corresponding class in the superpackage.
 *
 * @see HttpHeader
 * @author jimw@paneris.org
 * @version $Version$
 */
public class HttpHeaderTest extends TestCase {

  /**
   * Constructor.
   */
  public HttpHeaderTest(String testCaseName) {
    super(testCaseName);
  }

  /**
   * Test a parsing of headers.
   */
  public void testCommaSeparated() throws Exception {
    String fields = "\"malted barley\"; q=0.5, ,wheat ,,\t oats, maize";
    // Excuse the pun
    HttpHeader cornFields = new HttpHeader(fields);

    HttpHeader.TokenAndQValueIterator taqvi = cornFields.tokenAndQValueIterator();
    HttpHeader.TokenAndQValue taqv;
    
    assertTrue(taqvi.hasNext());
    taqv = taqvi.nextTokenAndQValue();
    assertEquals("malted barley", taqv.token);
    assertEquals(0.5f, taqv.q, 0.0001f);

    assertTrue(taqvi.hasNext());
    taqv = taqvi.nextTokenAndQValue();
    assertEquals("wheat", taqv.token);
    assertEquals(1.0f, taqv.q, 0.0001f);

    assertTrue(taqvi.hasNext());
    taqv = (HttpHeader.TokenAndQValue)taqvi.nextElement();
    assertEquals("oats", taqv.token);
    assertEquals(1.0f, taqv.q, 0.0001f);
    try {
      taqvi.remove();
      assertTrue(false);
    }
    catch (UnsupportedOperationException e) {
      e = null; // shut PMD up
    }

    assertTrue(taqvi.hasMoreElements());
    taqv = taqvi.nextTokenAndQValue();
    assertEquals("maize", taqv.token);
    assertEquals(1.0f, taqv.q, 0.0001f);

    assertTrue(! taqvi.hasNext());
    assertTrue(! taqvi.hasMoreElements());
    assertTrue(taqvi.next() instanceof HttpHeader.HttpHeaderException);

      
    try {
      cornFields = new HttpHeader(",EmptyFirst");
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

    try {
      cornFields = new HttpHeader("First . Second");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

    try {
      cornFields = new HttpHeader("=");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

    try {
      cornFields = new HttpHeader("hello; = 0.1");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

    try {
      cornFields = new HttpHeader("hello; r = 0.1");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }
      
    try {
      cornFields = new HttpHeader("hello hello");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

    try {
      cornFields = new HttpHeader("hello; q = p");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

    try {
      cornFields = new HttpHeader("hello; q - 0.0");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
      e = null; // shut PMD up
    }

/* tokenizer is protected
    try {
      cornFields = new HttpHeader("hello");
      cornFields.tokenizer.readQValue();
      assertTrue(false);
    }
    catch (Exception e) {
    }

    try {
      cornFields = new HttpHeader("hello");
      cornFields.tokenizer.skipCommaSeparator();
      assertTrue(false);
    }
    catch (Exception e) {
    }
*/
  }

}

