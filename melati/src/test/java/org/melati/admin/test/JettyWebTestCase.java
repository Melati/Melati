package org.melati.admin.test;

import net.sourceforge.jwebunit.junit.WebTestCase;

/**
 *
 * Much thanks to 
 * http://today.java.net/pub/a/today/2007/04/12/embedded-integration-testing-of-web-applications.html
 * 
 * @author timp
 * @since 2008/01/01
 * 
 */
public abstract class JettyWebTestCase extends WebTestCase {

  private org.mortbay.jetty.Server server;

  public JettyWebTestCase() {
    super();
  }

  public JettyWebTestCase(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    // Port 0 means "assign arbitrarily port number"
    server = new org.mortbay.jetty.Server(0);
    server.addHandler(new org.mortbay.jetty.webapp.WebAppContext(
        "/dist/melati/melati/src/test/webapp", "/"));
    server.start();
  
    // getLocalPort returns the port that was actually assigned
    int actualPort = server.getConnectors()[0].getLocalPort();
    getTestContext().setBaseUrl(
        "http://localhost:" + actualPort + "/");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testIndex() {
    beginAt("/index.html");
    assertTextPresent("Hello World!");
  }

}