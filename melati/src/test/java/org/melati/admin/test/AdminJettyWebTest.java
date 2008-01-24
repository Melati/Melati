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
   private String dbName = "melatijunit";
  
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
    beginAt("/Admin/" + dbName + "/Unknown");
    assertTextPresent("Melati Error Template");
  }
  /**
   * 
   */
  public void testAdminMain() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/Main");
    assertTextPresent("You need a frames enabled browser to use the Admin Suite");
  }
  /**
   * 
   */
  public void testAdminTop() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/Top");
    assertTextPresent("Melati Database Admin Suite - Options for Melatijunit database");
  }
  /**
   * 
   */
  public void testAdminTopWithTable() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/Top");
    assertTextPresent("Melati Database Admin Suite - Options for Melatijunit database");
  }
  /**
   * 
   */
  public void testAdminTopWithTableAndTroid() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/0/Top");
    assertTextPresent("Melati Database Admin Suite - Options for Melatijunit database");
  }
  
  /**
   * 
   */
  public void testUpload() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/Upload?field=tim");
    assertTextPresent("File to upload:");
  }
  
  
  /**
   * 
   */
  public void testAdminBottom() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/Bottom");
    // Hmmm Should assert something, coverage is the thing
  }
  /**
   * 
   */
  public void testAdminRight() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/0/Right");
    // Hmmm Should assert something, coverage is the thing
  }
  /**
   * 
   */
  public void testAdminPrimarySelect() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/PrimarySelect");
    assertTextPresent("Full name");
  }
  /**
   * 
   */
  public void testAdminSelection() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/Selection?target=&returnTarget=");
    assertTextPresent("Full name");
    assertTextPresent("Melati guest user");
  }
  /**
   * 
   */
  public void testAdminEditHeader() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/0/EditHeader");
    assertTextPresent("User");
    assertTextPresent("_guest_");
  }
  /**
   * 
   */
  public void testAdminEdit() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/0/Edit");
    assertTextPresent("Full name");
    assertTextPresent("_guest_");
  }
  /**
   * Test that login is required.
   */
  public void testAdminEditAdministrator() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/1/Edit");
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

    // Note that logging out has no effect if rememberme was chosen
    gotoPage("/Logout/" + dbName + "");
    gotoPage("/Admin/" + dbName + "/user/1/Edit");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME");
    submit("action");
    assertTextPresent("Updated a User Record");
    assertTextPresent("Done");
  }
  
  /**
   * 
   */
  public void testAdminTree() {
    setScriptingEnabled(true);
    beginAt("/Admin/" + dbName + "/user/0/Record");
    gotoFrame("admin_edit_header");
    assertTextPresent("_guest_");
    assertTextPresent("[ Group membership ]");
    clickLink("recordTree");
    gotoRootWindow();
    gotoFrame("admin_edit_user_0");
    assertTextPresent("Melati guest user tree");
    clickLinkWithText("Melati guest user");
  }
  /**
   * 
   */
  public void testAdminTreeNoScript() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/0/Tree");
    assertTextPresent("Melati guest user tree");
  }
  /**
   * 
   */
  public void testAdminTableTree() {
    setScriptingEnabled(true);
    beginAt("/Admin/" + dbName + "/user/Table");
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
    beginAt("/Admin/" + dbName + "/user/SelectionWindow?returnfield=field_user");
    assertTextPresent("Select a User");
  }
  /**
   * 
   */
  public void testAdminSelectionWindowPrimarySelect() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/SelectionWindowPrimarySelect?returnfield=field_user");
    assertTextPresent("Full name");
  }
  /**
   * 
   */
  public void testAdminSelectionWindowSelection() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/SelectionWindowSelection?returnfield=field_user");
    assertTextPresent("Records 1 to 2 of 2");
  }
  /**
   * 
   */
  public void testAdminPopup() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/user/PopUp");
    assertTextPresent("Search User Table");
  }
  /**
   * 
   */
  public void testAdminDSD() {
    setScriptingEnabled(false);
    beginAt("/Admin/" + dbName + "/DSD");
    assertTextPresent("Generated for _guest_");
    assertTextPresent("package org.melati.poem;");
  }
  /**
   * Move to login
   */
  public void testLoginWithContinuation() {
    setScriptingEnabled(false);
    beginAt("/Login/" + dbName + "?continuationURL=/index.html");
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
    loginAsAdministrator();
    gotoFrame("admin_top");
    clickLinkWithText("Setup");    
    deleteRecord("setting","org.melati.admin.Admin.ScreenStylesheetURL", 0);
    deleteRecord("setting","org.melati.admin.Admin.PrimaryDisplayTable", 1);
    deleteRecord("setting","org.melati.admin.Admin.HomepageURL", 2);
    gotoPage("/Admin/" + dbName + "/setting/Main");
    gotoFrame("admin_bottom");
    gotoFrame("admin_left");
    gotoFrame("admin_selection");
    assertTextPresent("No records found");
  }
  /**
   * Start and end at top window.
   * @param tableName 
   * @param uniqueKeyValue
   */
  private void deleteRecord(String tableName, String uniqueKeyValue, int troid) {
    gotoPage("/Admin/" + dbName + "/" + tableName + "/Main");
    gotoFrame("admin_bottom");
    gotoFrame("admin_left");
    gotoFrame("admin_selection");
    clickLinkWithText(uniqueKeyValue);
    gotoRootWindow();
    gotoFrame("admin_bottom");
    gotoFrame("admin_record");
    gotoFrame("admin_edit_" + tableName + "_" + troid);
    clickButton("delete");
    gotoPage("/Admin/" + dbName + "/Main");
  }
  /**
   * Returns us to top frame.
   */
  private void loginAsAdministrator() {
    beginAt("/Admin/" + dbName + "/Main");
    gotoFrame("admin_top");
    clickButton("login");
    setTextField("field_login", "_administrator_");
    setTextField("field_password", "FIXME");
    checkCheckbox("rememberme");
    submit("action");
  }
}