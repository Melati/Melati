package org.melati.test;

import java.util.Vector;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.poem.Field;
import org.melati.util.CharsetException;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiException;
import org.melati.util.MelatiStringWriter;
import org.melati.servlet.test.MockServletRequest;

import junit.framework.TestCase;

/**
 * Test Melati.
 * 
 * @author timp
 * @since 30/05/2006
 */
public class MelatiTest extends TestCase {

  /**
   * Constructor for MelatiTest.
   * @param name
   */
  public MelatiTest(String name) {
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

  protected PoemContext poemContext(Melati melati) throws MelatiException { 
    PoemContext it = new PoemContext();
   return it;
 }
  
  
  /**
   * @see org.melati.Melati#Melati(MelatiConfig, HttpServletRequest, HttpServletResponse)
   */
  public void testMelatiMelatiConfigHttpServletRequestHttpServletResponse() {

  }

  /**
   * @see org.melati.Melati#Melati(MelatiConfig, MelatiWriter)
   */
  public void testMelatiMelatiConfigMelatiWriter() {

  }

  /**
   * @see org.melati.Melati#getRequest()
   */
  public void testGetRequest() {

  }

  /**
   * @see org.melati.Melati#setRequest(HttpServletRequest)
   */
  public void testSetRequest() {

  }

  /**
   * @see org.melati.Melati#getResponse()
   */
  public void testGetResponse() {

  }

  /**
   * @see org.melati.Melati#setPoemContext(PoemContext)
   */
  public void testSetPoemContext() {

  }

  /**
   * @see org.melati.Melati#loadTableAndObject()
   */
  public void testLoadTableAndObject() {

  }

  /**
   * @see org.melati.Melati#getPoemContext()
   */
  public void testGetPoemContext() {

  }

  /**
   * @see org.melati.Melati#getDatabase()
   */
  public void testGetDatabase() {

  }

  /**
   * @see org.melati.Melati#getKnownDatabaseNames()
   */
  public void testGetKnownDatabaseNames() {
    MelatiConfig mc = null;
    PoemContext pc = null;
    Melati m = null;
    try {
      mc = new MelatiConfig();
      m = new Melati(mc, new MelatiStringWriter());
      pc = poemContext(m);
      m.setPoemContext(pc);
      Vector known = m.getKnownDatabaseNames();
      // Fails when run in single thread 
      //assertEquals(0, known.size());
      pc.setLogicalDatabase("melatijunit");
      m.setPoemContext(pc);
      m.loadTableAndObject();
      known = m.getKnownDatabaseNames();
      // Fails when run in single thread 
      //assertEquals(1, known.size());
      assertTrue(known.size() > 0);
    } catch (MelatiException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * @see org.melati.Melati#getTable()
   */
  public void testGetTable() {

  }

  /**
   * @see org.melati.Melati#getObject()
   */
  public void testGetObject() {

  }

  /**
   * @see org.melati.Melati#getMethod()
   */
  public void testGetMethod() {

  }

  /**
   * @see org.melati.Melati#setTemplateEngine(TemplateEngine)
   */
  public void testSetTemplateEngine() {

  }

  /**
   * @see org.melati.Melati#getTemplateEngine()
   */
  public void testGetTemplateEngine() {

  }

  /**
   * @see org.melati.Melati#setTemplateContext(TemplateContext)
   */
  public void testSetTemplateContext() {

  }

  /**
   * @see org.melati.Melati#getTemplateContext()
   */
  public void testGetTemplateContext() {

  }

  /**
   * @see org.melati.Melati#getServletTemplateContext()
   */
  public void testGetServletTemplateContext() {

  }

  /**
   * @see org.melati.Melati#getConfig()
   */
  public void testGetConfig() {

  }

  /**
   * @see org.melati.Melati#getPathInfoParts()
   */
  public void testGetPathInfoParts() {

  }

  /**
   * @see org.melati.Melati#setArguments(String[])
   */
  public void testSetArguments() {

  }

  /**
   * @see org.melati.Melati#getArguments()
   */
  public void testGetArguments() {
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
    assertNull(m.getArguments());
    m.setArguments(new String[] {"hello", "world"});
    assertEquals(2, m.getArguments().length);

  }

  /**
   * @see org.melati.Melati#getSession()
   */
  public void testGetSession() {

  }

  /**
   * @see org.melati.Melati#getContextUtil(String)
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
    try { 
      m.getContextUtil("unknownClass");
      fail("Should have bombed");
    } catch (MelatiBugMelatiException e) { 
      e = null;
    }
  }

  /**
   * @see org.melati.Melati#getLogoutURL()
   * @throws Exception 
   */
  public void testGetLogoutURL() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setPoemContext(poemContext(m));
    m.setRequest(new MockServletRequest());
    assertEquals("/org.melati.login.Logout/null",m.getLogoutURL());
  }

  /**
   * @see org.melati.Melati#getLoginURL()
   * @throws Exception 
   */
  public void testGetLoginURL() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setPoemContext(poemContext(m));
    m.setRequest(new MockServletRequest());
    assertEquals("/org.melati.login.Login/null",m.getLoginURL());

  }

  /**
   * @see org.melati.Melati#getZoneURL()
   */
  public void testGetZoneURL() {

  }

  /**
   * Not used in Melati.
   * @throws Exception 
   * @see org.melati.Melati#getServletURL()
   */
  public void testGetServletURL() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setPoemContext(poemContext(m));
    m.setRequest(new MockServletRequest());
    assertEquals("http://localhost",m.getServletURL());
  }

  /**
   * @see org.melati.Melati#getJavascriptLibraryURL()
   */
  public void testGetJavascriptLibraryURL() {

  }

  /**
   * @throws Exception 
   * @see org.melati.Melati#getMelatiLocale()
   */
  public void testGetMelatiLocale() throws Exception {
    MelatiConfig mc = null;
    Melati m = null;
    mc = new MelatiConfig();
    m = new Melati(mc, new MelatiStringWriter());
    m.setPoemContext(poemContext(m));
    assertEquals("en_GB", m.getMelatiLocale().toString());
    m.setRequest(new MockServletRequest());
    assertEquals("en_GB", m.getMelatiLocale().toString());
    MockServletRequest msr = new MockServletRequest();
    msr.setHeader("Accept-Language","en-gb");
    m.setRequest(msr);
    assertEquals("en_GB", m.getMelatiLocale().toString());
    msr.setHeader("Accept-Language","not");
    assertEquals("en_GB", m.getMelatiLocale().toString());
    msr.setHeader("Accept-Language","en-us");
    assertEquals("en_US", m.getMelatiLocale().toString());
    

  }

  /**
   * @see org.melati.Melati#getMelatiLocale(String)
   */
  public void testGetMelatiLocaleString() {
    assertNull(Melati.getMelatiLocale(""));    
    assertNull(Melati.getMelatiLocale(";"));    
    assertNull(Melati.getMelatiLocale(";9"));    
    assertNull(Melati.getMelatiLocale(";nine"));    
    assertNull(Melati.getMelatiLocale("rubbish")); 
    assertNull(Melati.getMelatiLocale("rubbish;9")); 
    assertNull(Melati.getMelatiLocale("rubbish;nine")); 
    assertEquals("en_GB", Melati.getMelatiLocale("EN-GB").toString());    
    assertEquals("en_GB", Melati.getMelatiLocale("en-GB").toString());    
    assertEquals("en_GB", Melati.getMelatiLocale("en-gb").toString());    
    assertEquals("en_GB", Melati.getMelatiLocale("en-gb;").toString());    
    assertEquals("en_GB", Melati.getMelatiLocale("en-gb;9").toString());    
    assertEquals("en_GB", Melati.getMelatiLocale("en-gb;nine").toString());    
    
  }
  /**
   * @see org.melati.Melati#establishCharsets()
   */
  public void testEstablishCharsets() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setPoemContext(poemContext(m));
    MockServletRequest mock = new MockServletRequest();
    mock.setHeader("Accept-Charset", "");
    m.setRequest(mock);
    m.establishCharsets();

    mock.setHeader("Accept-Charset", "rubbish");
    m.setRequest(mock);
    try { 
      m.establishCharsets();
      fail("Should have blown up");
    } catch (CharsetException e) { 
      e = null;
    }

    mock.setHeader("Accept-Charset", "");
    mock.setCharacterEncoding(null);
    m.setRequest(mock);
    m.establishCharsets();
    
  }

  /**
   * @see org.melati.Melati#setResponseContentType(String)
   */
  public void testSetResponseContentType() {

  }

  /**
   * @see org.melati.Melati#setMarkupLanguage(MarkupLanguage)
   */
  public void testSetMarkupLanguage() {

  }

  /**
   * @see org.melati.Melati#getMarkupLanguage()
   */
  public void testGetMarkupLanguage() {
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
    assertEquals("html/en_GB", m.getMarkupLanguage().toString());
    assertEquals("html_attr/en_GB", m.getMarkupLanguage().getAttr().toString());
  }

  /**
   * @see org.melati.Melati#getHTMLMarkupLanguage()
   */
  public void testGetHTMLMarkupLanguage() {

  }

  /**
   * @see org.melati.Melati#sameURLWith(String, String)
   */
  public void testSameURLWithStringString() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setPoemContext(poemContext(m));
    MockServletRequest mock = new MockServletRequest();
    mock.setRequestURI("page");
    m.setRequest(mock);

    assertEquals("page?noodles=1", m.sameURLWith("noodles","1"));
    assertEquals("page?noodles=1", m.sameURLWith("noodles"));
  }

  /**
   * @see org.melati.Melati#sameURLWith(String)
   */
  public void testSameURLWithString() {

  }

  /**
   * @see org.melati.Melati#getSameURL()
   */
  public void testGetSameURL() {

  }

  /**
   * @see org.melati.Melati#setBufferingOff()
   */
  public void testSetBufferingOff() {

  }

  /**
   * @see org.melati.Melati#setFlushingOn()
   */
  public void testSetFlushingOn() {

  }

  /**
   * @see org.melati.Melati#gotWriter()
   */
  public void testGotWriter() {

  }

  /**
   * @see org.melati.Melati#getURLQueryEncoding()
   */
  public void testGetURLQueryEncoding() {

  }

  /**
   * @see org.melati.Melati#urlEncode(String)
   */
  public void testUrlEncode() {

  }

  /**
   * @see org.melati.Melati#getEncoding()
   */
  public void testGetEncoding() {

  }

  /**
   * @see org.melati.Melati#getWriter()
   */
  public void testGetWriter() {

  }

  /**
   * @see org.melati.Melati#getStringWriter()
   */
  public void testGetStringWriter() {

  }

  /**
   * @see org.melati.Melati#write()
   */
  public void testWrite() {

  }

  /**
   * @see org.melati.Melati#getPassbackVariableExceptionHandler()
   */
  public void testGetPassbackVariableExceptionHandler() {

  }

  /**
   * @see org.melati.Melati#setVariableExceptionHandler(Object)
   */
  public void testSetVariableExceptionHandler() {

  }

  /**
   * @see org.melati.Melati#getUser()
   */
  public void testGetUser() {
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
    assertNull(m.getUser());
  }

  /**
   * @see org.melati.Melati#isReferencePoemType(Field)
   */
  public void testIsReferencePoemType() {
  }

}
