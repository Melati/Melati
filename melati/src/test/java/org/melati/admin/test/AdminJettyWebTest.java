/**
 * 
 */
package org.melati.admin.test;

import net.sourceforge.jwebunit.junit.WebTestCase;
/**
 * Much thanks to 
 * http://today.java.net/pub/a/today/2007/04/12/embedded-integration-testing-of-web-applications.html
 * 
 * @author timp
 * 
 */
public class AdminJettyWebTest extends WebTestCase {

  private org.mortbay.jetty.Server server;

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

  public void testIndex() {
    beginAt("/index.html");
    assertTextPresent("Hello World!");
  }

  // Test Page calls
  public void testBadUrl() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Unknown");
    assertTextPresent("Melati Error Template");
  }
  public void testAdminMain() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Main");
    assertTextPresent("You need a frames enabled browser to use the Admin Suite");
  }
  public void testAdminTop() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Top");
    assertTextPresent("Melati Admin Suite");
  }
  public void testAdminTopWithTable() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Top");
    assertTextPresent("Melati Admin Suite");
  }
  public void testAdminTopWithTableAndTroid() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/Top");
    assertTextPresent("Melati Admin Suite");
  }
  
  
  
  public void testAdminBottom() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Bottom");
    // Hmmm Should assert something, coverage is the thing
  }
  public void testAdminRight() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/Right");
    // Hmmm Should assert something, coverage is the thing
  }
  public void testAdminPrimarySelect() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/PrimarySelect");
    assertTextPresent("Full name");
  }
  public void testAdminSelection() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Selection");
    assertTextPresent("Full name");
    assertTextPresent("Melati guest user");
  }
  public void testAdminEditHeader() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/EditHeader");
    assertTextPresent("User");
    assertTextPresent("_guest_");
  }
  public void testAdminEdit() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/Edit");
    assertTextPresent("Full name");
    assertTextPresent("_guest_");
  }
  /**
   * Test that login is required.
   */
  public void testAdminEditAdministrator() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/1/Edit");
    assertTextPresent("You need to log in.");
    setTextField("field_login", "_administrator55_");
    setTextField("field_password", "FIXME");
    checkCheckbox("rememberme");
    submit("action");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME_not");
    checkCheckbox("rememberme");
    submit("action");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME");
    checkCheckbox("rememberme");
    submit("action");
    assertTextPresent("Full name");
    assertTextPresent("_administrator_");
    
    beginAt("/Logout/melatijunit");
    beginAt("/Admin/melatijunit/user/1/Edit");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME");
    checkCheckbox("rememberme");
    submit("action");
    assertTextPresent("Full name");
    assertTextPresent("_administrator_");
  }
  
  public void testAdminDSD() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/DSD");
    assertTextPresent("Generated for _guest_");
    assertTextPresent("package org.melati.poem;");
  }
  /**
   * Move to login
   */
  public void testLoginWithContinuation() {
    setScriptingEnabled(false);
    beginAt("/Login/melatijunit?continuationURL=/index.html");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME");
    checkCheckbox("rememberme");
    submit("action");
    assertTextPresent("Hello World!");
  }
}