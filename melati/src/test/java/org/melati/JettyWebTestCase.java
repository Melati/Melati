package org.melati;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

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

  private static Server server;

  public JettyWebTestCase() {
    super();
  }

  public JettyWebTestCase(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    // Port 0 means "assign arbitrarily port number"
    server = new Server(0);
    WebAppContext wac = new WebAppContext(
        "src/test/webapp", "/");
    org.mortbay.resource.FileResource.setCheckAliases(false); 
    server.addHandler(wac);
    server.start();
  
    // getLocalPort returns the port that was actually assigned
    int actualPort = server.getConnectors()[0].getLocalPort();
    getTestContext().setBaseUrl(
        "http://localhost:" + actualPort + "/");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  public static void main(String[] args) throws Exception {
    server = new Server(8080);
    WebAppContext wac = new WebAppContext(
        "src/test/webapp", "/");
    org.mortbay.resource.FileResource.setCheckAliases(false); 
    server.addHandler(wac);
    server.start();
  }
  public void testIndex() {
    beginAt("/index.html");
    assertTextPresent("Hello World!");
  }

}