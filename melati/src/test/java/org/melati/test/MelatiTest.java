/**
 * 
 */
package org.melati.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.util.MelatiException;
import org.melati.util.MelatiStringWriter;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class MelatiTest extends TestCase {

  /**
   * Constructor for MelatiTest.
   * @param name
   */
  public MelatiTest(String name) {
    super(name);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  protected PoemContext poemContext(Melati melati) throws MelatiException { 
    PoemContext it = new PoemContext();
   return it;
 }
  
  
  /*
   * Test method for 'org.melati.Melati.Melati(MelatiConfig, HttpServletRequest, HttpServletResponse)'
   */
  public void testMelatiMelatiConfigHttpServletRequestHttpServletResponse() {

  }

  /*
   * Test method for 'org.melati.Melati.Melati(MelatiConfig, MelatiWriter)'
   */
  public void testMelatiMelatiConfigMelatiWriter() {

  }

  /*
   * Test method for 'org.melati.Melati.getRequest()'
   */
  public void testGetRequest() {

  }

  /*
   * Test method for 'org.melati.Melati.setRequest(HttpServletRequest)'
   */
  public void testSetRequest() {

  }

  /*
   * Test method for 'org.melati.Melati.getResponse()'
   */
  public void testGetResponse() {

  }

  /*
   * Test method for 'org.melati.Melati.setPoemContext(PoemContext)'
   */
  public void testSetPoemContext() {

  }

  /*
   * Test method for 'org.melati.Melati.loadTableAndObject()'
   */
  public void testLoadTableAndObject() {

  }

  /*
   * Test method for 'org.melati.Melati.getPoemContext()'
   */
  public void testGetPoemContext() {

  }

  /*
   * Test method for 'org.melati.Melati.getDatabase()'
   */
  public void testGetDatabase() {

  }

  /*
   * Test method for 'org.melati.Melati.getTable()'
   */
  public void testGetTable() {

  }

  /*
   * Test method for 'org.melati.Melati.getObject()'
   */
  public void testGetObject() {

  }

  /*
   * Test method for 'org.melati.Melati.getMethod()'
   */
  public void testGetMethod() {

  }

  /*
   * Test method for 'org.melati.Melati.setTemplateEngine(TemplateEngine)'
   */
  public void testSetTemplateEngine() {

  }

  /*
   * Test method for 'org.melati.Melati.getTemplateEngine()'
   */
  public void testGetTemplateEngine() {

  }

  /*
   * Test method for 'org.melati.Melati.setTemplateContext(TemplateContext)'
   */
  public void testSetTemplateContext() {

  }

  /*
   * Test method for 'org.melati.Melati.getTemplateContext()'
   */
  public void testGetTemplateContext() {

  }

  /*
   * Test method for 'org.melati.Melati.getServletTemplateContext()'
   */
  public void testGetServletTemplateContext() {

  }

  /*
   * Test method for 'org.melati.Melati.getConfig()'
   */
  public void testGetConfig() {

  }

  /*
   * Test method for 'org.melati.Melati.getPathInfoParts()'
   */
  public void testGetPathInfoParts() {

  }

  /*
   * Test method for 'org.melati.Melati.setArguments(String[])'
   */
  public void testSetArguments() {

  }

  /*
   * Test method for 'org.melati.Melati.getArguments()'
   */
  public void testGetArguments() {

  }

  /*
   * Test method for 'org.melati.Melati.getSession()'
   */
  public void testGetSession() {

  }

  /*
   * Test method for 'org.melati.Melati.getContextUtil(String)'
   */
  public void testGetContextUtil() {
    MelatiConfig mc = null;
    Melati m = null;
    try {
      mc = new MelatiConfig();
      m = new Melati(mc, new MelatiStringWriter());
      m.setPoemContext(poemContext(m));
    } catch (MelatiException e) {
      e.printStackTrace();
      fail();
    }
    Object adminUtil = m.getContextUtil("org.melati.admin.AdminUtils");
    assertTrue(adminUtil instanceof org.melati.admin.AdminUtils);
  }

  /*
   * Test method for 'org.melati.Melati.getLogoutURL()'
   */
  public void testGetLogoutURL() {

  }

  /*
   * Test method for 'org.melati.Melati.getLoginURL()'
   */
  public void testGetLoginURL() {

  }

  /*
   * Test method for 'org.melati.Melati.getZoneURL()'
   */
  public void testGetZoneURL() {

  }

  /*
   * Test method for 'org.melati.Melati.getServletURL()'
   */
  public void testGetServletURL() {

  }

  /*
   * Test method for 'org.melati.Melati.getJavascriptLibraryURL()'
   */
  public void testGetJavascriptLibraryURL() {

  }

  /*
   * Test method for 'org.melati.Melati.getMelatiLocale()'
   */
  public void testGetMelatiLocale() {

  }

  /*
   * Test method for 'org.melati.Melati.establishCharsets()'
   */
  public void testEstablishCharsets() {

  }

  /*
   * Test method for 'org.melati.Melati.setResponseContentType(String)'
   */
  public void testSetResponseContentType() {

  }

  /*
   * Test method for 'org.melati.Melati.setMarkupLanguage(MarkupLanguage)'
   */
  public void testSetMarkupLanguage() {

  }

  /*
   * Test method for 'org.melati.Melati.getMarkupLanguage()'
   */
  public void testGetMarkupLanguage() {

  }

  /*
   * Test method for 'org.melati.Melati.getHTMLMarkupLanguage()'
   */
  public void testGetHTMLMarkupLanguage() {

  }

  /*
   * Test method for 'org.melati.Melati.sameURLWith(String, String)'
   */
  public void testSameURLWithStringString() {

  }

  /*
   * Test method for 'org.melati.Melati.sameURLWith(String)'
   */
  public void testSameURLWithString() {

  }

  /*
   * Test method for 'org.melati.Melati.getSameURL()'
   */
  public void testGetSameURL() {

  }

  /*
   * Test method for 'org.melati.Melati.setBufferingOff()'
   */
  public void testSetBufferingOff() {

  }

  /*
   * Test method for 'org.melati.Melati.setFlushingOn()'
   */
  public void testSetFlushingOn() {

  }

  /*
   * Test method for 'org.melati.Melati.gotWriter()'
   */
  public void testGotWriter() {

  }

  /*
   * Test method for 'org.melati.Melati.getURLQueryEncoding()'
   */
  public void testGetURLQueryEncoding() {

  }

  /*
   * Test method for 'org.melati.Melati.urlEncode(String)'
   */
  public void testUrlEncode() {

  }

  /*
   * Test method for 'org.melati.Melati.getEncoding()'
   */
  public void testGetEncoding() {

  }

  /*
   * Test method for 'org.melati.Melati.getWriter()'
   */
  public void testGetWriter() {

  }

  /*
   * Test method for 'org.melati.Melati.getStringWriter()'
   */
  public void testGetStringWriter() {

  }

  /*
   * Test method for 'org.melati.Melati.write()'
   */
  public void testWrite() {

  }

  /*
   * Test method for 'org.melati.Melati.getPassbackVariableExceptionHandler()'
   */
  public void testGetPassbackVariableExceptionHandler() {

  }

  /*
   * Test method for 'org.melati.Melati.setVariableExceptionHandler(Object)'
   */
  public void testSetVariableExceptionHandler() {

  }

  /*
   * Test method for 'org.melati.Melati.getUser()'
   */
  public void testGetUser() {

  }

  /*
   * Test method for 'org.melati.Melati.isReferencePoemType(Field)'
   */
  public void testIsReferencePoemType() {

  }

}
