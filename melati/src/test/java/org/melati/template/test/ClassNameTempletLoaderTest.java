/**
 * 
 */
package org.melati.template.test;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Capability;
import org.melati.poem.Field;
import org.melati.poem.test.PoemTestCase;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.MarkupLanguage;
import org.melati.template.NotFoundException;
import org.melati.template.Template;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.template.webmacro.WebmacroServletTemplateEngine;
import org.melati.util.MelatiStringWriter;


/**
 * @author timp
 * @since 22 Jun 2007
 *
 */
public class ClassNameTempletLoaderTest extends PoemTestCase {

  /**
   * @param name
   */
  public ClassNameTempletLoaderTest(String name) {
    super(name);
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /** 
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.template.ClassNameTempletLoader#getInstance()}.
   */
  public void testGetInstance() {
    
  }

  /**
   * Test method for {@link org.melati.template.ClassNameTempletLoader#templet(org.melati.template.TemplateEngine, org.melati.template.MarkupLanguage, java.lang.String, java.lang.String)}.
   */
  public void testTempletTemplateEngineMarkupLanguageStringString() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    TemplateEngine templateEngine = mc.getTemplateEngine();
    Templated templated = new Templated();
    templateEngine.init(mc);
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    m.setPoemContext(new PoemContext());
    assertNotNull(m.getTemplateEngine());
    MarkupLanguage ml = m.getMarkupLanguage();
    Template t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml, templated.getClass());
    TemplateContext tc =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(tc);
    tc.put("melati", m);
    tc.put("ml", ml);
    tc.put("object", new Integer("1"));
    m.setTemplateContext(tc);
    t.write(m.getWriter(), m.getTemplateContext(), templateEngine);
    t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml, "error", new Exception().getClass());
    tc = m.getTemplateContext();
    tc.put("melati", m);
    tc.put("ml", ml);
    // Wrong object type, should be an Exception
    tc.put("object", new Integer("1"));
    try {
      t.write(m.getWriter(),tc, m.getTemplateEngine());
      System.err.println(m.getWriter().toString());
      if(templateEngine instanceof WebmacroServletTemplateEngine)
        fail("Should have bombed");
      // Velocity just ignores the calling of a non-existant method 
      // within an if
    } catch (TemplateEngineException e) {
      e = null;
      // Pass - we should have passed in an exception as the object
    } 

    t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml, "error",new Exception().getClass());
    tc = m.getTemplateContext();
    tc.put("melati", m);
    tc.put("ml", ml);
    tc.put("object",new Exception("A message"));
    m.setTemplateContext(tc);
    t.write(m.getWriter(),tc, m.getTemplateEngine());
    assertTrue(m.getWriter().toString().indexOf("A message") != -1);

    t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml, "error", new AccessPoemException().getClass());
    tc = m.getTemplateContext();
    tc.put("melati", m);
    tc.put("ml", m.getMarkupLanguage());
    tc.put("object", new AccessPoemException(getDb().getUserTable().guestUser(),new Capability("Cool")));
    m.setTemplateContext(tc);
    t.write(m.getWriter(),tc, m.getTemplateEngine());
    //System.err.println(m.getWriter().toString());
    assertTrue(m.getWriter().toString().indexOf("[Access denied to Melati guest user]") != -1);
    
  }

  /**
   * Test method for {@link org.melati.template.ClassNameTempletLoader#templet(
   * org.melati.template.TemplateEngine, org.melati.template.MarkupLanguage, java.lang.String)}.
   */
  public void testTempletTemplateEngineMarkupLanguageString() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    TemplateEngine templateEngine = mc.getTemplateEngine();
    templateEngine.init(mc);
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    m.setPoemContext(new PoemContext());
    assertNotNull(m.getTemplateEngine());
    MarkupLanguage ml = m.getMarkupLanguage();
    try {
      ClassNameTempletLoader.getInstance().templet(
              templateEngine, ml,new Integer("1").getClass().getName());
      fail("Should have bombed");
    } catch (NotFoundException e) {
      e = null;
    }
    Template t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml, new Object().getClass().getName());
    TemplateContext tc =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(tc);
    tc.put("melati", m);
    tc.put("ml", ml);
    tc.put("object", new Object());
    t.write(m.getWriter(),tc, m.getTemplateEngine());
    assertTrue(m.getWriter().toString().startsWith("[java.lang.Object@"));

    t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml,"org.melati.poem.ReferencePoemType-SelectionWindow");
    tc = m.getTemplateContext();
    tc.put("melati", m);
    tc.put("ml", ml);
    Field<?> tableField = getDb().getColumnInfoTable().
                           getColumnInfoObject(0).getField("tableinfo");
    tc.put("object", tableField);
    t.write(m.getWriter(),tc, m.getTemplateEngine());
    System.err.println(m.getWriter().toString());
    assertTrue(m.getWriter().toString().indexOf("tableinfo_button") != -1);


  }

  /**
   * Test method for {@link org.melati.template.ClassNameTempletLoader#templet(
   *                        org.melati.template.TemplateEngine, 
   *                        org.melati.template.MarkupLanguage, 
   *                        java.lang.String, 
   *                        java.lang.Class)}.
   */
  public void testTempletTemplateEngineMarkupLanguageStringClass() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    TemplateEngine templateEngine = mc.getTemplateEngine();
    Templated templated = new Templated();
    templateEngine.init(mc);
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    m.setPoemContext(new PoemContext());
    assertNotNull(m.getTemplateEngine());
    MarkupLanguage ml = m.getMarkupLanguage();
    Template t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml, templated.getClass());
    TemplateContext tc =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(tc);
    tc.put("melati", m);
    tc.put("ml", ml);
    tc.put("object", new Integer("1"));
    m.setTemplateContext(tc);
    t.write(m.getWriter(), m.getTemplateContext(), templateEngine);
    assertEquals("Hi, this is from a template.", m.getWriter().toString());
  }

  /**
   * Test method for {@link org.melati.template.ClassNameTempletLoader#templet(
   * org.melati.template.TemplateEngine, 
   * org.melati.template.MarkupLanguage, 
   * java.lang.Class)}.
   */
  public void testTempletTemplateEngineMarkupLanguageClass() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    TemplateEngine templateEngine = mc.getTemplateEngine();
    templateEngine.init(mc);
    Melati m = new Melati(mc, new MelatiStringWriter());
    m.setTemplateEngine(templateEngine);
    m.setPoemContext(new PoemContext());
    assertNotNull(m.getTemplateEngine());
    TemplateContext templateContext =
      templateEngine.getTemplateContext(m);
    m.setTemplateContext(templateContext);
    MarkupLanguage ml = m.getMarkupLanguage();
    Template t = ClassNameTempletLoader.getInstance().templet(
            templateEngine, ml,
            new Integer("1").getClass());
    TemplateContext tc = m.getTemplateContext();
    tc.put("melati", m);
    tc.put("ml", ml);
    tc.put("object", new Integer("1"));
    t.write(m.getWriter(), tc, m.getTemplateEngine());
    // FIXME too much whitespace remaining
    assertEquals("[1]", m.getWriter().toString().trim());
    
  }

  /**
   * Test method for {@link org.melati.template.ClassNameTempletLoader#templet(org.melati.template.TemplateEngine, org.melati.template.MarkupLanguage, org.melati.poem.FieldAttributes)}.
   */
  public void testTempletTemplateEngineMarkupLanguageFieldAttributes() {
    
  }

}
