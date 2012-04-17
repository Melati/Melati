/**
 * 
 */
package org.melati.template.test;

import java.util.Enumeration;

import org.melati.MelatiConfig;
import org.melati.poem.test.PoemTestCase;
import org.melati.template.Template;
import org.melati.template.TemplateContext;
import org.melati.template.TemplateEngine;

/**
 * @author timp
 * @since 2007/08/21
 *
 */
public abstract class TemplateEngineSpec extends PoemTestCase {

  protected TemplateEngine templateEngine = null;
  
  /**
   * @param name
   */
  public TemplateEngineSpec(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    setTemplateEngine();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PoemTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  
  abstract protected void setTemplateEngine();
  /**
   * Test method for {@link org.melati.template.TemplateEngine#init(org.melati.MelatiConfig)}.
   */
  public void testInit() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    templateEngine.init(mc);
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getTemplateContext()}.
   */
  public void testGetTemplateContext() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    templateEngine.init(mc);
    templateEngine.getTemplateContext(); 
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getName()}.
   */
  public abstract void testGetName();

  /**
   * Test method for {@link org.melati.template.TemplateEngine#templateExtension()}.
   */
  public abstract void testTemplateExtension();

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getRoots()}.
   */
  public void testGetRoots() {
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#addRoot(java.lang.String)}.
   */
  public void testAddRoot() {
    Enumeration<String> en = templateEngine.getRoots();
    int counter = 0;
    while (en.hasMoreElements()) { 
      en.nextElement();
      counter++;
    }
    assertEquals(1, counter);
    templateEngine.addRoot("root");
    en = templateEngine.getRoots();
    counter = 0;
    while (en.hasMoreElements()) { 
      en.nextElement();
      counter++;
    } 
    assertEquals(2, counter);
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#template(java.lang.String)}.
   */
  public void testTemplate() {
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getTemplateName(java.lang.String, java.lang.String)}.
   */
  public void testGetTemplateName() {
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#expandTemplate(org.melati.util.MelatiWriter, java.lang.String, org.melati.template.TemplateContext)}.
   * @throws Exception 
   */
  public void testExpandTemplateMelatiWriterStringTemplateContext() throws Exception {
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#expandTemplate(org.melati.util.MelatiWriter, org.melati.template.Template, org.melati.template.TemplateContext)}.
   * @throws Exception 
   */
  public void testExpandTemplateMelatiWriterTemplateTemplateContext() throws Exception {
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#expandedTemplate(org.melati.template.Template, org.melati.template.TemplateContext)}.
   * @throws Exception 
   */
  public void testExpandedTemplate() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    templateEngine.init(mc);
    TemplateContext templateContext = templateEngine.getTemplateContext();
    Template template = templateEngine.template("org/melati/template/test/Templated" + 
        templateEngine.templateExtension());
    assertEquals("Hi, this is from a template.", templateEngine.expandedTemplate(template, templateContext));
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getStringWriter()}.
   */
  public void testGetStringWriter() {
    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getEngine()}.
   */
  public void testGetEngine() {
    
  }

}
