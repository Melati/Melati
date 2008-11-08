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

import org.melati.servlet.test.MockServletRequest;
import org.melati.util.HttpUtil;

import junit.framework.TestCase;

/**
 * @author timp
 * @since  8 Nov 2008
 *
 */
public class HttpUtilTest extends TestCase {

  /**
   * Constructor.
   * @param name
   */
  public HttpUtilTest(String name) {
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
   * Test method for {@link org.melati.util.HttpUtil#appendZoneURL(java.lang.StringBuffer, javax.servlet.http.HttpServletRequest)}.
   */
  public void testAppendZoneURL() {
    StringBuffer it = new StringBuffer();
    HttpUtil.appendZoneURL(it, new MockServletRequest());
    assertEquals("http://localhost/servletContext/mockServletPath",it.toString());
  }

  /**
   * Test method for {@link org.melati.util.HttpUtil#getServerURL(javax.servlet.http.HttpServletRequest)}.
   */
  public void testGetServerURL() {
    assertEquals("http://localhost",HttpUtil.getServerURL( new MockServletRequest()));
  }

  /**
   * Test method for {@link org.melati.util.HttpUtil#appendRelativeZoneURL(java.lang.StringBuffer, javax.servlet.http.HttpServletRequest)}.
   */
  public void testAppendRelativeZoneURL() {
    StringBuffer it = new StringBuffer();
    HttpUtil.appendRelativeZoneURL(it, new MockServletRequest());
    assertEquals("/servletContext/mockServletPath",it.toString());
  }

  /**
   * Test method for {@link org.melati.util.HttpUtil#zoneURL(javax.servlet.http.HttpServletRequest)}.
   */
  public void testZoneURL() {
    assertEquals("http://localhost/servletContext/mockServletPath",HttpUtil.zoneURL(new MockServletRequest()));
  }

  /**
   * Test method for {@link org.melati.util.HttpUtil#servletURL(javax.servlet.http.HttpServletRequest)}.
   */
  public void testServletURL() {
    assertEquals("http://localhost/servletContext/mockServletPath/",HttpUtil.servletURL(new MockServletRequest()));
  }

  /**
   * Test method for {@link org.melati.util.HttpUtil#getRelativeRequestURL(javax.servlet.http.HttpServletRequest)}.
   */
  public void testGetRelativeRequestURL() {
    assertEquals("/servletContext/mockServletPath/",HttpUtil.getRelativeRequestURL(new MockServletRequest()));
  }

}
