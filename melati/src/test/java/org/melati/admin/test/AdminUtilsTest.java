/**
 * 
 */
package org.melati.admin.test;

import javax.servlet.http.HttpServletRequest;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.admin.AdminUtils;
import org.melati.poem.Field;
import org.melati.poem.test.PoemTestCase;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.util.MelatiStringWriter;

import com.mockobjects.dynamic.Mock;

/**
 * @author timp
 *
 */
public class AdminUtilsTest extends PoemTestCase {
  protected static MelatiConfig mc = null;
  protected static TemplateEngine templateEngine = null;
  protected static Melati m = null;
  protected AdminUtils au;
  /**
   * Constructor for AdminUtilsTest.
   * @param name
   */
  public AdminUtilsTest(String name) {
    super(name);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
    mc = new MelatiConfig();
    templateEngine = mc.getTemplateEngine();
    if (templateEngine != null)
      templateEngine.init(mc);
    else fail();
    m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    assertNotNull(m.getTemplateEngine());
    TemplateContext templateContext =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(templateContext);
    PoemContext pc = new PoemContext();
    pc.setLogicalDatabase("melatijunit");
    pc.setTable("user");
    pc.setTroid(new Integer(1));
    m.setPoemContext(pc);
    Mock mockHttpServletRequest = new Mock(HttpServletRequest.class); 
    
    mockHttpServletRequest.expectAndReturn( "getCharacterEncoding", "ISO-8859-1"); 
    mockHttpServletRequest.expectAndReturn( "getPathInfo", "/melatitest/user/1"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getContextPath", "mockContextPath"); 
    mockHttpServletRequest.expectAndReturn( "getServletPath", "/Admin"); 
    mockHttpServletRequest.expectAndReturn( "getHeader", "Authorization", null); 
    mockHttpServletRequest.expectAndReturn( "getServerName", "mockServer.net"); 
    mockHttpServletRequest.expectAndReturn( "getScheme", "mockScheme"); 
    mockHttpServletRequest.expectAndReturn( "getHeader", "Accept-Language", null); 
    m.setRequest((HttpServletRequest)mockHttpServletRequest.proxy());    
    au = new AdminUtils(m);
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.admin.AdminUtils#AdminUtils(Melati)
   */
  public void testAdminUtils() throws Exception {
  }

  /**
   * @see org.melati.admin.AdminUtils#MainURL(String)
   */
  public void testMainURL() {
    assertEquals("mockContextPath/Admin/test/Main", au.MainURL("test"));
  }

  /**
   * @see org.melati.admin.AdminUtils#TopURL(Melati)
   */
  public void testTopURL() {
    assertEquals("mockContextPath/Admin/melatijunit/Top", au.TopURL(m));
  }

  /**
   * @see org.melati.admin.AdminUtils#BottomURL(Table)
   */
  public void testBottomURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/Bottom", au.BottomURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#LeftURL(Table)
   */
  public void testLeftURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/Left", au.LeftURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#PrimarySelectURL(Table)
   */
  public void testPrimarySelectURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/PrimarySelect", au.PrimarySelectURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#SelectionURL(Table)
   */
  public void testSelectionURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/Selection", au.SelectionURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils.SelectionRightURL(Table)
   */
  public void testSelectionRightURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/SelectionRight", au.SelectionRightURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#NavigationURL(Table)
   */
  public void testNavigationURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/Navigation", au.NavigationURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#RightURL(Persistent)
   */
  public void testRightURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/Right", au.RightURL(m.getObject()));
  }

  /**
   * @see org.melati.admin.AdminUtils#EditHeaderURL(Persistent)
   */
  public void testEditHeaderURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/EditHeader", au.EditHeaderURL(m.getObject()));
  }

  /**
   * @see org.melati.admin.AdminUtils#EditURL(Persistent)
   */
  public void testEditURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/Edit", au.EditURL(m.getObject()));
  }

  /**
   * @see org.melati.admin.AdminUtils#TreeURL(Persistent)
   */
  public void testTreeURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/Tree", au.TreeURL(m.getObject()));
  }

  /**
   * @see org.melati.admin.AdminUtils#TreeControlURL(Persistent)
   */
  public void testTreeControlURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/TreeControl", au.TreeControlURL(m.getObject()));
  }

  /**
   * @see org.melati.admin.AdminUtils#AddURL(Table)
   */
  public void testAddURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/Add", au.AddURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#PopUpURL(Table)
   */
  public void testPopUpURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/PopUp", au.PopUpURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#SelectionWindowURL(Table)
   */
  public void testSelectionWindowURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/SelectionWindow", au.SelectionWindowURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#SelectionWindowPrimarySelectURL(Table)
   */
  public void testSelectionWindowPrimarySelectURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/SelectionWindowPrimarySelect", au.SelectionWindowPrimarySelectURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#SelectionWindowSelectionURL(Table)
   */
  public void testSelectionWindowSelectionURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/SelectionWindowSelection", 
                 au.SelectionWindowSelectionURL(m.getTable()));
  }

  /**
   * @see org.melati.admin.AdminUtils#StatusURL()
   */
  public void testStatusURL() {
    assertEquals("mockContextPath/org.melati.admin.Status/melatijunit", 
                 au.StatusURL());
  }

  /**
   * @see org.melati.admin.AdminUtils#SessionURL()
   */
  public void testSessionURL() {
    assertEquals("mockContextPath/org.melati.test.SessionAnalysisServlet", 
                 au.SessionURL());
  }

  /**
   * @see org.melati.admin.AdminUtils#DsdURL()
   */
  public void testDsdURL() {
    assertEquals("mockContextPath/Admin/melatijunit/DSD", 
                 au.DsdURL());
  }

  /**
   * @see org.melati.admin.AdminUtils#UploadURL(Table, Persistent, Field)
   */
  public void testUploadURL() {
    m.loadTableAndObject();
    Field f = getDb().getUserTable().getNameColumn().asEmptyField();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/Upload?field=name", 
                 au.UploadURL(m.getTable(), m.getObject(), f));
  }

  /**
   * @see org.melati.admin.AdminUtils#UploadHandlerURL(Table, Persistent, String)
   */
  public void testUploadHandlerURL() {
    m.loadTableAndObject();
    assertEquals("mockContextPath/Admin/melatijunit/user/1/UploadDone?field=name", 
        au.UploadHandlerURL(m.getTable(), m.getObject(), "name"));
  }

  /**
   * @see org.melati.admin.AdminUtils#
   *          specialFacilities(Melati, MarkupLanguage, Persistent)
   */
  public void testSpecialFacilities() throws Exception {
    m.loadTableAndObject();
    assertEquals("", 
        au.specialFacilities(m, m.getMarkupLanguage(), m.getObject()));
  }

  /**
   * @see org.melati.admin.AdminUtils#getStaticURL()
   */
  public void testGetStaticURL() {
    assertEquals("/melati-static/admin", au.getStaticURL());
  }

  /**
   * @see org.melati.admin.AdminUtils#createTree(Treeable)
   */
  public void testCreateTree() {
   // Not easy to do, as poem objects are not Treeable
  }

  /**
   * @see org.melati.admin.AdminUtils#isTreeable(Persistent)
   */
  public void testIsTreeable() {
    m.loadTableAndObject();
    assertFalse(au.isTreeable(m.getObject()));
  }

}
