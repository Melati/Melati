/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2010 Tim Pizey
 *
 *
 * Contact details for copyright holder:
 *
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.courtiouspoem.melati.test;

import org.melati.JettyWebTestCase;

/**
 * @author timp
 * @since  3 Mar 2009
 *
 */



public class CourtiouspoemJettyWebTestCase extends JettyWebTestCase {

  public CourtiouspoemJettyWebTestCase(String name) {
    super(name);
    webAppDirName = "src/main/webapp";
    contextName = "";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.JettyWebTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.JettyWebTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * If you don't know by now.
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    contextName = "";
    webAppDirName = "src/main/webapp";
    startServer(8080);
  }

  /**
   * Just to say hello.
   */
  public void testIndex() {
    beginAt("/");
    assertTextPresent("Admin");
  }
  
}
