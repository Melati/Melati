/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.test;

/**
 * Test the handling of binary data field type.
 * 
 * @see org.melati.poem.BinaryPoemType
 * @author williamc/timp
 */
public class BinaryTest extends PoemTestCase {

  
  /**
   * Constructor for BinaryTest.
   * @param arg0
   */
  public BinaryTest(String arg0) {
    super(arg0);
  }
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    setDbName(everythingDatabaseName);
    super.setUp();
    System.err.println("IN BinaryTest" + getDb().getClass().getName());
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCreate() {
    System.err.println(getDb().getClass().getName());
    BinaryField aThing = (BinaryField)((TestDatabase)getDb()).getBinaryFieldTable().firstSelection(null);
    System.out.println(
               aThing == null ?
                  "Nothing in there" :
                  "Found " + new String(aThing.getBinaryfield()));

    BinaryField bThing = (BinaryField)((TestDatabase)getDb()).getBinaryFieldTable().newPersistent();
    bThing.setBinaryfield(new byte[] { 98, -3, -120 });
    bThing.makePersistent();
  }
}
