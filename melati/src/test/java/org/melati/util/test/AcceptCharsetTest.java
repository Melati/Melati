/*
 * $Id$
 *
 * Copyright (C) 2003 Jim Wright
 *
 * Part of Melati and subject to http://melati.org/Licence.html.
 */

package org.melati.util.test;

import org.melati.util.AcceptCharset;

import junit.framework.TestCase;

import java.util.*;

/**
 * Tests the corresponding class in the superpackage.
 *
 * @see AcceptCharset
 * @author jimw@paneris.org
 * @version $Version$
 */
public class AcceptCharsetTest extends TestCase {

  public AcceptCharsetTest(String testCaseName) {
    super(testCaseName);
  }

  /**
   * Test choosing charsets.
   */
  public void testChoices() throws Exception {

    String headerValue = "ISO-8859-2, utf-8;q=0.66, *;q=0.66";
    String supportedPreference[] = new String[] {
      "UTF-16",
      "UTF-8",
      "ISO-8859-1",
    };
    AcceptCharset ac = new AcceptCharset(headerValue, supportedPreference);
    assertEquals("ISO-8859-2", ac.clientChoice());
    assertEquals("UTF-16", ac.serverChoice());

    headerValue = "utf-8;q=0.66,ISO-8859-3,ISO-8859-2";
    supportedPreference = new String[] {
      "ISO-8859-1",
      "UTF-16",
      "UTF-8",
      "BOLLOX",
    };
    ac = new AcceptCharset(headerValue, supportedPreference);
    assertEquals("ISO-8859-3", ac.clientChoice());
    assertEquals("ISO-8859-1", ac.serverChoice());

    headerValue = "*;q=0.0";
    supportedPreference = new String[] {
      "UTF-16",
      "UTF-8",
      "BOLLOX",
      "ISO-8859-1",
    };
    ac = new AcceptCharset(headerValue, supportedPreference);
    assertEquals(null, ac.clientChoice());
    assertEquals(null, ac.serverChoice());
  }

}

