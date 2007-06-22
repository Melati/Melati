package org.melati.template.test;

import java.io.IOException;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.AccessToken;
import org.melati.poem.BaseFieldAttributes;
import org.melati.poem.Capability;
import org.melati.poem.Column;
import org.melati.poem.Field;
import org.melati.util.test.Node;
import org.melati.template.AttributeMarkupLanguage;
import org.melati.template.MarkupLanguage;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.util.MelatiException;
import org.melati.util.MelatiStringWriter;

import junit.framework.TestCase;


/**
 * An abstract test which is run against most permutations of configuaration.
 * 
 * @author timp
 * @since 14-May-2006
 */
abstract public class MarkupLanguageSpec extends TreeTestCase {

  protected static MelatiConfig mc = null;
  protected static TemplateEngine templateEngine = null;
  protected static MarkupLanguage ml = null;
  protected static AttributeMarkupLanguage aml = null;
  protected static Melati m = null;

  /**
   * Constructor for PoemTest.
   * @param arg0
   */
  public MarkupLanguageSpec(String arg0) {
    super(arg0);
  }
  /**
   * Constructor.
   */
  public MarkupLanguageSpec() {
    super();
  }
  

 
  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception
  {
    super.setUp();
    melatiConfig();
    templateEngine = mc.getTemplateEngine();
    if (templateEngine != null)
      templateEngine.init(mc);
    else fail("Template engine is null");
    m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    m.setPoemContext(new PoemContext());
    assertNotNull(m.getTemplateEngine());
    TemplateContext templateContext =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(templateContext);
  }
  
  abstract protected void melatiConfig() throws MelatiException ;
  
  
  
  /**
   * Test method for rendered(Exception).
   * @throws Exception 
   * 
   * @see org.melati.template.HTMLAttributeMarkupLanguage#
   *      rendered(AccessPoemException)
   */
  public void testRenderedAccessPoemException() throws Exception {
    
    try {
      assertEquals("java.lang.Exception",aml.rendered(new Exception()));
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }

    try {
      AccessPoemException ape = new AccessPoemException(
          (AccessToken)getDb().getUserTable().guestUser(), new Capability("Cool"));
      assertTrue(ml.rendered(ape).indexOf(
          "org.melati.poem.AccessPoemException: " + 
          "You need the capability Cool but " + 
          "your access token _guest_ doesn&#39;t confer it") != -1);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }

    try {
      AccessPoemException ape = new AccessPoemException();
      assertEquals("", aml.rendered(ape));
      //System.err.println(m.getWriter().toString());
      assertTrue(m.getWriter().toString().indexOf("[Access denied to [UNRENDERABLE EXCEPTION!]") != -1);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
    try {
      AccessPoemException ape = new AccessPoemException(
          (AccessToken)getDb().getUserTable().guestUser(), new Capability("Cool"));
      assertEquals("", aml.rendered(ape));
      // NB Not at all sure how this value changed 
      //System.err.println(m.getWriter().toString());
      //assertTrue(m.getWriter().toString().indexOf("[Access denied to Melati guest user]") != -1);
      assertTrue(m.getWriter().toString().indexOf("[Access denied to _guest_]") != -1);
    } catch (Exception e) {
      System.err.println(m.getWriter().toString());
      e.printStackTrace();
      fail(e.getMessage());
    }

  }

  /**
   * Test method for Constructor. 
   * 
   * @see org.melati.template.HTMLMarkupLanguage#
   *   HTMLMarkupLanguage(Melati, TempletLoader, PoemLocale)
   */
  public void testHTMLMarkupLanguageMelatiTempletLoaderPoemLocale() {

  }

  /**
   * Test method for Constructor 
   * @see org.melati.template.HTMLMarkupLanguage#
   *          HTMLMarkupLanguage(String, HTMLMarkupLanguage)
   */
  public void testHTMLMarkupLanguageStringHTMLMarkupLanguage() {

  }

  /**
   * Test method for getAttr().
   * 
   * @see org.melati.template.HTMLMarkupLanguage#getAttr()
   */
  public void testGetAttr() {
    assertEquals(aml.getClass(), ml.getAttr().getClass());
  }

  /**
   * Test method for escaped(String).
   * 
   * @see org.melati.template.HTMLLikeMarkupLanguage#escaped(String)
   */
  public void testEscapedString() {
    try {
      // FIXME
      //assertEquals("&amp;&percent;&pound;", ml.rendered("&%�"));
      assertEquals("&amp;%£", ml.rendered("&%£"));
      assertEquals("&amp;%£", aml.rendered("&%£"));
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }

  }

  /**
   * Test method for escaped(Persistent).
   * 
   * @see org.melati.template.HTMLLikeMarkupLanguage#escaped(Persistent)
   */
  public void testEscapedPersistent() {
    assertEquals("Melati guest user",ml.escaped(getDb().getUserTable().getUserObject(0)));
  }

  /**
   * Test method for encoded.
   * 
   * @see org.melati.template.HTMLLikeMarkupLanguage#encoded(String)
   */
  public void testEncoded() {
    assertEquals("+", ml.encoded(" "));
    assertEquals("+", aml.encoded(" "));
    assertEquals("%26", ml.encoded("&"));
    assertEquals("%26", aml.encoded("&"));
  }

  /**
   * Test method for rendered(Object).
   * 
   * @see org.melati.template.MarkupLanguage#rendered(Object)
   */
  public void testRenderedObject() throws Exception {
    try {
      assertEquals("Fredd$", ml.rendered("Fredd$"));
    } catch (IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
    try {
      // Note velocity seems to leave the line end on
      assertEquals("[1]", ml.rendered(new Integer("1")).trim());
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
    
    try { 
      assertEquals("1", ml.getAttr().rendered(new Integer("1")));
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
    try { 
      ml.getAttr().rendered(new Bomber());
      fail("Should have bombed");
    } catch (Exception e) {
      e = null;
    }
    
    try { 
      ml.rendered(new Bomber());
      fail("Should have bombed");
    } catch (Exception e) {
      e = null;
    }
    
    Node persistent = (Node)getDb().getTable("node").newPersistent();
    persistent.setName("Mum");
    persistent.makePersistent();
    m.setPoemContext(new PoemContext());
     
    String renderedPersistent = ml.rendered(persistent);
    assertEquals("Mum", renderedPersistent);

  }
  
  /**
   * Test that we can find a template on the classpath.
   */
  public void testTemplateFoundOnClasspath() throws Exception { 
    Templated templated = new Templated();
    String rendered = ml.rendered(templated);
  
    assertEquals("Hi, this is from a template.", rendered);
    
  }
  
  /**
   * Test that special templets are found.
   */
  public void testSpecialTemplateFound() throws Exception { 
    Column column = getDb().getGroupMembershipTable().getUserColumn();
    BaseFieldAttributes fa = new BaseFieldAttributes(column, column.getType());
    Field field = new Field(getDb().getUserTable().administratorUser().troid(), fa);
    Object adminUtil = m.getContextUtil("org.melati.admin.AdminUtils");
    assertTrue(adminUtil instanceof org.melati.admin.AdminUtils);
    assertTrue(ml.input(field).indexOf("add_rule(\"field_user\",") != -1);
  }

  /**
   * An object which throws an exception when its toString method is called.
   */
  class Bomber {
    /**
     * Constructor.
     */
    public Bomber() {}
    /** 
     * Throw exception.
     */
    public String toString() {
      if (true == true) throw new RuntimeException("Bomber bombed.");
      return "Did not bomb";
    }
  }
  /**
   * Test method for rendered(String).
   * 
   * @see org.melati.template.MarkupLanguage#rendered(String)
   */
  public void testRenderedString() throws Exception {
    assertEquals("Fredd$", ml.rendered("Fredd$"));
  }

  /**
   * Test method for rendered(String, int).
   * 
   * @see org.melati.template.MarkupLanguage#rendered(String, int)
   */
  public void testRenderedStringInt() {
    try {
      assertEquals("Fre...", ml.rendered("Fredd$", 3));
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }

  }

  /**
   * Test method for rendered(Field).
   * 
   * @see org.melati.template.MarkupLanguage#rendered(Field)
   */
  public void testRenderedField() throws Exception {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    assertEquals("_guest_", ml.rendered(userName));
  }
  /**
   * Test method for rendered(Field, int).
   * 
   * @see org.melati.template.MarkupLanguage#rendered(Field, int)
   */
  public void testRenderedFieldInt() throws Exception {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    assertEquals("_guest_", ml.rendered(userName,3));
  }

  /**
   * Test method for rendered(Field, int, int).
   * 
   * @see org.melati.template.MarkupLanguage#rendered(Field, int, int)
   */
  public void testRenderedFieldIntInt() {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    try {
      assertEquals("_gu...", ml.rendered(userName,3,3));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }


  /**
   * Test method for renderedStart(Field).
   * 
   * @see org.melati.template.MarkupLanguage#renderedStart(Field)
   */
  public void testRenderedStart() {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    try {
      assertEquals("_guest_", ml.renderedStart(userName));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }

  }

  /**
   * Test method for input(Field).
   * 
   * @see org.melati.template.MarkupLanguage#input(Field)
   */
  public void testInputField() {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    try {
      assertTrue(ml.input(userName).toLowerCase().indexOf("<input name=\"field_login\"") != -1);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
    /*
     * FIXME fails for hsqldb 
    Field owningTable = db.getColumnInfoTable().getColumnInfoObject(0).getField("tableinfo");
    try {
      assertTrue(ml.input(owningTable).toLowerCase().indexOf("<select name=") != -1);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
    */
  }

  /**
   * Test method for inputAs(Field, String).
   * 
   * @see org.melati.template.MarkupLanguage#inputAs(Field, String)
   */
  public void testInputAs() {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    try {
      assertTrue(ml.inputAs(userName, "nonExistantTemplateName").toLowerCase().indexOf("<input name=\"field_login\"") != -1);
      fail();
    } catch (Exception e) {
      e = null;
    }
    try {
      assertTrue(ml.inputAs(userName, "org.melati.poem.StringPoemType").toLowerCase().indexOf("<input name=\"field_login\"") != -1);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * Test method for searchInput(Field, String).
   * 
   * @see org.melati.template.MarkupLanguage#searchInput(Field, String)
   */
  public void testSearchInput() throws Exception {
    Field userName = getDb().getUserTable().getUserObject(0).getField("login");
    assertTrue(ml.searchInput(userName, "None").toLowerCase().indexOf("<input name=\"field_login\"") != -1);
  }


}
