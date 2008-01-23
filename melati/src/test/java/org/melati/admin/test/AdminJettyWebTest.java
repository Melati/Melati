/**
 * 
 */
package org.melati.admin.test;

import org.melati.JettyWebTestCase;

/**
 * 
 * @author timp
 * @since 2008/01/01
 */
public class AdminJettyWebTest extends JettyWebTestCase {

  /**
   * 
   */
  public AdminJettyWebTest() {
    super();
  }
  /**
   * @param name
   */
  public AdminJettyWebTest(String name) {
    super(name);
  }

  // Test Page calls
  /**
   * 
   */
  public void testBadUrl() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Unknown");
    assertTextPresent("Melati Error Template");
  }
  /**
   * 
   */
  public void testAdminMain() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Main");
    assertTextPresent("You need a frames enabled browser to use the Admin Suite");
  }
  /**
   * 
   */
  public void testAdminTop() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Top");
    assertTextPresent("Melati Database Admin Suite - Options for Melatijunit database");
  }
  /**
   * 
   */
  public void testAdminTopWithTable() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Top");
    assertTextPresent("Melati Database Admin Suite - Options for Melatijunit database");
  }
  /**
   * 
   */
  public void testAdminTopWithTableAndTroid() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/Top");
    assertTextPresent("Melati Database Admin Suite - Options for Melatijunit database");
  }
  
  /**
   * 
   */
  public void testUpload() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Upload?field=tim");
    assertTextPresent("File to upload:");
  }
  
  
  /**
   * 
   */
  public void testAdminBottom() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Bottom");
    // Hmmm Should assert something, coverage is the thing
  }
  /**
   * 
   */
  public void testAdminRight() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/Right");
    // Hmmm Should assert something, coverage is the thing
  }
  /**
   * 
   */
  public void testAdminPrimarySelect() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/PrimarySelect");
    assertTextPresent("Full name");
  }
  /**
   * 
   */
  public void testAdminSelection() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/Selection?target=&returnTarget=");
    assertTextPresent("Full name");
    assertTextPresent("Melati guest user");
  }
  /**
   * 
   */
  public void testAdminEditHeader() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/EditHeader");
    assertTextPresent("User");
    assertTextPresent("_guest_");
  }
  /**
   * 
   */
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
  
  /**
   * 
   */
  public void testAdminTree() {
    setScriptingEnabled(true);
    beginAt("/Admin/melatijunit/user/0/Record");
    gotoFrame("admin_edit_header");
    assertTextPresent("_guest_");
    assertTextPresent("[ Group membership ]");
    clickLink("recordTree");
    gotoRootWindow();
    gotoFrame("admin_edituser0");
    assertTextPresent("Melati guest user tree");
    clickLinkWithText("Melati guest user");
  }
  /**
   * 
   */
  public void testAdminTreeNoScript() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/0/Tree");
    assertTextPresent("Melati guest user tree");
  }
  /**
   * 
   */
  public void testAdminTableTree() {
    setScriptingEnabled(true);
    beginAt("/Admin/melatijunit/user/Table");
    gotoFrame("admin_navigation");
    clickLink("tableTree");
    gotoRootWindow();
    gotoFrame("admin_selection");
    assertTextPresent("User table tree");
    assertLinkPresentWithText("Melati guest user");
    assertLinkPresentWithText("Melati database administrator");
  }
  /**
   * 
   */
  public void testAdminSelectionWindow() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/SelectionWindow?returnfield=field_user");
    assertTextPresent("Select a User");
  }
  /**
   * 
   */
  public void testAdminSelectionWindowPrimarySelect() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/SelectionWindowPrimarySelect?returnfield=field_user");
    assertTextPresent("Full name");
  }
  /**
   * 
   */
  public void testAdminSelectionWindowSelection() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/SelectionWindowSelection?returnfield=field_user");
    assertTextPresent("Records 1 to 2 of 2");
  }
  /**
   * 
   */
  public void testAdminPopup() {
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/user/PopUp");
    assertTextPresent("Search User Table");
  }
  /**
   * 
   */
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
  
  /**
   * Test setting the defaults.
   */
  public void testSetupStory() { 
    setScriptingEnabled(false);
    beginAt("/Admin/melatijunit/Main");
    gotoFrame("admin_top");
    clickButton("login");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME");
    checkCheckbox("rememberme");
    submit("action");
    gotoFrame("admin_top");
    clickLinkWithText("Setup");    
    beginAt("/Admin/melatijunit/Main");
    gotoFrame("admin_bottom");
    gotoFrame("admin_left");
    gotoFrame("admin_selection");
    assertTextPresent("HomepageUrl"); //FIXME URL
  }
}