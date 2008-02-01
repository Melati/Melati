/**
 * 
 */
package org.melati.admin.test;

import javax.servlet.http.HttpServletRequest;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.admin.AdminUtils;
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
   * @see org.melati.admin.AdminUtils#getStaticURL()
   */
  public void testGetStaticURL() {
    assertEquals("/melati-static/admin/static", au.getStaticURL());
  }

  /**
   * @see org.melati.admin.AdminUtils#createTree(Treeable)
   */
  public void testCreateTree() {
   // Not easy to do, as poem objects are not Treeable
    // TODO they are treeable now
  }

}
