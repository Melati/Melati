/**
 * 
 */
package org.melati.admin.test;


import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.admin.AdminUtils;
import org.melati.poem.test.PoemTestCase;
import org.melati.servlet.test.MockServletRequest;
import org.melati.servlet.test.MockServletResponse;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;


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
    MockServletRequest mockHttpServletRequest = new MockServletRequest(); 
    mockHttpServletRequest.setPathInfo("melatitest/user/Selection");
    mockHttpServletRequest.setRequestURI("melatitest/user/Selection");
    System.err.println(mockHttpServletRequest.getRequestURI());
    MockServletResponse mockServletResponse = new MockServletResponse();
    m = new Melati(mc, mockHttpServletRequest, mockServletResponse);
    m.setTemplateEngine(templateEngine);
    assertNotNull(m.getTemplateEngine());
    TemplateContext templateContext =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(templateContext);
    PoemContext pc = new PoemContext();
    pc.setLogicalDatabase("melatijunit");
    pc.setTable("user");
    pc.setTroid(new Integer(1));
    pc.setMethod("Selection");
    m.setPoemContext(pc);
    au = new AdminUtils(m);
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    // Do not check whether db has changed 
    // as it has not even been initialised
    //super.tearDown();
  }

  /**
   * @see org.melati.admin.AdminUtils#AdminUtils(Melati)
   */
  public void testAdminUtils() throws Exception {
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
   * @see org.melati.admin.AdminUtils#ToggledOrderSelectionURL(Melati, String, String)
   */
  public void testToggledOrderSelectionURL() throws Exception { 
    m.loadTableAndObject();
    
    assertEquals("melatitest/user/Selection?field_order-1=23&field_order-1-toggle=true", 
        au.ToggledOrderSelectionURL(m, "field_order-1", "23"));    
  }

  /**
   * @see org.melati.admin.AdminUtils#getStaticURL()
   */
  public void testGetStaticURL() {
    assertEquals("/melatitest/melati-static/admin/static", au.getStaticURL());
  }

  /**
   * @see org.melati.admin.AdminUtils#createTree(Treeable)
   */
  public void testCreateTree() {
   // Not easy to do, as poem objects are not Treeable
    // TODO they are treeable now
  }

  /**
   * Test simpleName.
   */
  public void testSimpleName() { 
    assertEquals("admin", AdminUtils.simpleName("org.melati.admin"));    
    assertEquals("admin", AdminUtils.simpleName(".admin"));    
    assertEquals("", AdminUtils.simpleName("admin."));    
    assertEquals("admin", AdminUtils.simpleName("admin"));    
  }
  
}
