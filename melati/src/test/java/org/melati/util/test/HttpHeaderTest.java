/*
 * $Id$
 *
 * Copyright (C) 2003 Jim Wright
 *
 * Part of Melati and subject to http://melati.org/Licence.html.
 */

package org.melati.util.test;

import org.melati.util.HttpHeader;

import junit.framework.TestCase;

import java.util.*;

/**
 * Tests the corresponding class in the superpackage.
 *
 * @see HttpHeader
 * @author jimw@paneris.org
 * @version $Version$
 */
public class HttpHeaderTest extends TestCase {

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
    }

    assertTrue(taqvi.hasMoreElements());
    taqv = taqvi.nextTokenAndQValue();
    assertEquals("maize", taqv.token);
    assertEquals(1.0f, taqv.q, 0.0001f);

    assertTrue(! taqvi.hasNext());
    assertTrue(! taqvi.hasMoreElements());
    assertTrue(taqvi.next() instanceof HttpHeader.HttpHeaderException);

    try {
      cornFields = new HttpHeader("");
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }
      
    try {
      cornFields = new HttpHeader(",EmptyFirst");
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }

    try {
      cornFields = new HttpHeader("First . Second");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }

    try {
      cornFields = new HttpHeader("=");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }

    try {
      cornFields = new HttpHeader("hello; = 0.1");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }

    try {
      cornFields = new HttpHeader("hello; r = 0.1");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }
      
    try {
      cornFields = new HttpHeader("hello hello");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }

    try {
      cornFields = new HttpHeader("hello; q = p");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
    }

    try {
      cornFields = new HttpHeader("hello; q - 0.0");
      taqvi = cornFields.tokenAndQValueIterator();
      taqvi.nextTokenAndQValue();
      assertTrue(false);
    }
    catch (HttpHeader.HttpHeaderException e) {
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

