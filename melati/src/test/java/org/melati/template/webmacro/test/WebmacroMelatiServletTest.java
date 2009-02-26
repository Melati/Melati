/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2009 Tim Pizey
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

package org.melati.template.webmacro.test;

import org.melati.servlet.test.MockServletConfig;
import org.melati.servlet.test.MockServletRequest;
import org.melati.servlet.test.MockServletResponse;

import junit.framework.TestCase;

/**
 * @author timp
 * @since  26 Feb 2009
 *
 */
public class WebmacroMelatiServletTest extends TestCase {

  /**
   * Constructor.
   * @param name
   */
  public WebmacroMelatiServletTest(String name) {
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

  public void testMisConfiguration() throws Exception { 
    MockServletRequest mockServletRequest = new MockServletRequest(); 
    MockServletResponse mockServletResponse = new MockServletResponse();

    MisconfiguredWebmacroMelatiServlet servlet = new MisconfiguredWebmacroMelatiServlet();
    MockServletConfig mockServletConfig = new MockServletConfig();
    servlet.init(mockServletConfig);
    servlet.doGet(mockServletRequest,  
                  mockServletResponse);
    String output = mockServletResponse.getWritten();
    assertTrue(output.toString().indexOf(
        "org.melati.util.MelatiConfigurationException: " + 
        "Configured TemplateEngine (velocity) is not the required one (webmacro).") != -1); 

    servlet.destroy();
    
  }
}
