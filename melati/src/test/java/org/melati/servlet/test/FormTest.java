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

package org.melati.servlet.test;

import org.melati.servlet.Form;

import junit.framework.TestCase;

/**
 * @author timp
 * @since  25 Feb 2008
 *
 */
public class FormTest extends TestCase {

  /**
   * Constructor.
   * @param name
   */
  public FormTest(String name) {
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
   * Test method for {@link org.melati.servlet.Form#extractFields(org.melati.template.ServletTemplateContext, org.melati.poem.Persistent)}.
   */
  public void testExtractFields() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#extractField(org.melati.template.ServletTemplateContext, java.lang.String, boolean)}.
   */
  public void testExtractField() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getFieldNulled(org.melati.template.ServletTemplateContext, java.lang.String)}.
   */
  public void testGetFieldNulled() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getField(org.melati.template.ServletTemplateContext, java.lang.String, java.lang.String)}.
   */
  public void testGetField() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getIntegerField(org.melati.template.ServletTemplateContext, java.lang.String, java.lang.Integer)}.
   */
  public void testGetIntegerFieldServletTemplateContextStringInteger() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getIntegerField(org.melati.template.ServletTemplateContext, java.lang.String)}.
   */
  public void testGetIntegerFieldServletTemplateContextString() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getBooleanField(org.melati.template.ServletTemplateContext, java.lang.String)}.
   */
  public void testGetBooleanField() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#sameURLWith(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testSameURLWithStringStringStringString() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#sameURLWith(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
   */
  public void testSameURLWithHttpServletRequestStringString() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#sameQueryWith(java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testSameQueryWith() {
    assertEquals("noodles&eggs=sugar", Form.sameQueryWith("noodles","eggs","sugar"));
    assertEquals("eggs=sugar", Form.sameQueryWith("eggs=sugar","eggs","sugar"));
    assertEquals("eggs=sugar", Form.sameQueryWith("","eggs","sugar"));
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getFormNulled(org.melati.template.ServletTemplateContext, java.lang.String)}.
   */
  public void testGetFormNulled() {
    
  }

  /**
   * Test method for {@link org.melati.servlet.Form#getForm(org.melati.template.ServletTemplateContext, java.lang.String, java.lang.String)}.
   */
  public void testGetForm() {
    
  }

}
