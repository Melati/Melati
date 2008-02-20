/**
 * 
 */
package org.melati.test;

import java.util.Properties;

import org.melati.MelatiConfig;
import org.melati.servlet.FormDataAdaptorFactory;
import org.melati.template.SimpleDateAdaptor;
import org.melati.template.YMDDateAdaptor;
import org.melati.template.YMDHMSTimestampAdaptor;
import org.melati.util.ConfigException;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 14-Dec-2006
 */
public class MelatiConfigTest extends TestCase {

  /**
   * Constructor for MelatiConfigTest.
   * 
   * @param name
   */
  public MelatiConfigTest(String name) {
    super(name);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /**
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.MelatiConfig#MelatiConfig()
   */
  public void testMelatiConfig() {

  }

  /**
   * @throws Exception
   * @see org.melati.MelatiConfig#MelatiConfig(String)
   */
  public void testMelatiConfigString()
      throws Exception {
    MelatiConfig mc = new MelatiConfig("org.melati.MelatiConfig");
    assertEquals("/test/melati-static/admin/static", mc.getStaticURL());

    try {
      mc = new MelatiConfig("nonexistantProperties");
      fail("Should have blown up");
    } catch (ConfigException e) {
      assertTrue(e.getMessage().indexOf("Is it in your CLASSPATH")>= 0); 
    }

    // Bad locale setting
    try {
      mc = new MelatiConfig("bad.MelatiConfig");
      fail("Should have blown up");
    } catch (ConfigException e) {
      System.err.println(e);
      assertTrue(e.getMessage().indexOf("is not a valid language tag")>= 0); 
    }

  }

  /**
   * @see org.melati.MelatiConfig#MelatiConfig(Properties)
   */
  public void testMelatiConfigProperties() throws Exception {
    Properties p = new Properties();
    p.setProperty("org.melati.MelatiConfig.staticURL", "test");
    MelatiConfig mc = new MelatiConfig(p);
    assertEquals("test", mc.getStaticURL());
  }
  /**
   * @see org.melati.MelatiConfig#getServletTemplateEngine()
   */
  public void testGetServletTemplateEngine() {

  }

  /**
   * @see org.melati.MelatiConfig#getTemplateEngine()
   */
  public void testGetTemplateEngine() {

  }

  /**
   * @see org.melati.MelatiConfig#setTemplateEngine(TemplateEngine)
   */
  public void testSetTemplateEngine() {

  }

  /**
   * @see org.melati.MelatiConfig#getAccessHandler()
   */
  public void testGetAccessHandler() {

  }

  /**
   * @see org.melati.MelatiConfig#setAccessHandler(AccessHandler)
   */
  public void testSetAccessHandler() {

  }

  /**
   * @see org.melati.MelatiConfig#getTempletLoader()
   */
  public void testGetTempletLoader() {

  }

  /**
   * @see org.melati.MelatiConfig#setTempletLoader(TempletLoader)
   */
  public void testSetTempletLoader() {

  }

  /**
   * @see org.melati.MelatiConfig#getFormDataAdaptorFactory()
   */
  public void testGetFormDataAdaptorFactory() {

  }

  /**
   * @see org.melati.MelatiConfig#setFormDataAdaptorFactory(FormDataAdaptorFactory)
   */
  public void testSetFormDataAdaptorFactory() {

  }

  /**
   * @see org.melati.MelatiConfig#getJavascriptLibraryURL()
   */
  public void testGetJavascriptLibraryURL() {

  }

  /**
   * @see org.melati.MelatiConfig#setJavascriptLibraryURL(String)
   */
  public void testSetJavascriptLibraryURL() {

  }

  /**
   * @see org.melati.MelatiConfig#getStaticURL()
   */
  public void testGetStaticURL() {

  }

  /**
   * @see org.melati.MelatiConfig#setStaticURL(String)
   */
  public void testSetStaticURL() {

  }

  /**
   * Note not used outside tests.
   * 
   * @throws Exception
   * @see org.melati.MelatiConfig#getTemplatePath()
   */
  public void testGetTemplatePath()
      throws Exception {
    MelatiConfig mc = new MelatiConfig();
    assertEquals(".", mc.getTemplatePath());
  }

  /**
   * @see org.melati.MelatiConfig#setTemplatePath(String)
   */
  public void testSetTemplatePath() {

  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#getLogoutPageServletClassName()
   */
  public void testGetLogoutPageServletClassName() throws Exception {
    assertEquals("org.melati.login.Logout", MelatiConfig.getLogoutPageServletClassName());
 }

  /**
   * @see org.melati.MelatiConfig#setLogoutPageServletClassName(String)
   */
  public void testSetLogoutPageServletClassName() {

  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#getLoginPageServletClassName()
   */
  public void testGetLoginPageServletClassName() throws Exception {
    assertEquals("org.melati.login.Login", MelatiConfig.getLoginPageServletClassName());
  }

  /**
   * @see org.melati.MelatiConfig#setLoginPageServletClassName(String)
   */
  public void testSetLoginPageServletClassName() {

  }

  /**
   * @see org.melati.MelatiConfig#getPoemLocale()
   */
  public void testGetPoemLocale() throws Exception {
   // MelatiConfig mc = new MelatiConfig();
   // assertEquals(mc.getPoemLocale(), mc.getMelatiLocale());

  }

  /**
   * @see org.melati.MelatiConfig#setPoemiLocale(PoemLocale)
   */
  public void testSetPoemLocale() {

  }

  /**
   * @see org.melati.MelatiConfig#getPreferredCharsets()
   */
  public void testGetPreferredCharsets() {

  }

  /**
   * @see org.melati.MelatiConfig#setPreferredCharsets(Vector)
   */
  public void testSetPreferredCharsets() {

  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#getFdaFactory()
   */
  public void testGetFdaFactory() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    FormDataAdaptorFactory fdaf = mc.getFdaFactory();
    assertNotNull(fdaf);
  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#setFdaFactory(FormDataAdaptorFactory)
   */
  public void testSetFdaFactory() throws Exception {
  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#getYMDDateAdaptor()
   */
  public void testGetYMDDateAdaptor() throws Exception {
    YMDDateAdaptor it = MelatiConfig.getYMDDateAdaptor();
    assertNotNull(it);

  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#getYMDHMSTimestampAdaptor()
   */
  public void testGetYMDHMSTimestampAdaptor() throws Exception {
    YMDHMSTimestampAdaptor it = MelatiConfig.getYMDHMSTimestampAdaptor();
    assertNotNull(it);
  }

  /**
   * @throws Exception 
   * @see org.melati.MelatiConfig#getSimpleDateAdaptor()
   */
  public void testGetSimpleDateAdaptor() throws Exception {
    SimpleDateAdaptor it = MelatiConfig.getSimpleDateAdaptor();
    assertNotNull(it);
  }

}
