/**
 * 
 */
package org.melati.template.test;

import javax.servlet.http.HttpServlet;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.template.NoTemplateEngine;
import org.melati.template.Template;
import org.melati.template.TemplateEngineException;
import org.melati.util.MelatiStringWriter;

/**
 * @author timp
 * @since 2007/08/21
 *
 */
public class NoTemplateEngineTest extends ServletTemplateEngineSpec {

  /**
   * @param name
   */
  public NoTemplateEngineTest(String name) {
    super(name);
    setServletTemplateEngine();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.test.TemplateEngineSpec#setTemplateEngine()
   */
  protected void setTemplateEngine() {
    templateEngine = new NoTemplateEngine();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.test.ServletTemplateEngineSpec#setServletTemplateEngine()
   */
  protected void setServletTemplateEngine() {
    servletTemplateEngine = new NoTemplateEngine();
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getName()}.
   */
  public void testGetName() {
    assertEquals("none", templateEngine.getName());    
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#templateExtension()}.
   */
  public void testTemplateExtension() {
    assertEquals(".none", templateEngine.templateExtension());        
  }
  
  /**
   * Test method for {@link org.melati.template.TemplateEngine#expandTemplate(org.melati.util.MelatiWriter, java.lang.String, org.melati.template.TemplateContext)}.
   */
  public void testExpandTemplateMelatiWriterStringTemplateContext() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    servletTemplateEngine.init(mc);
    Melati melati = new Melati(mc,new MelatiStringWriter());
    try { 
      servletTemplateEngine.expandTemplate(melati.getWriter(), (String)null, servletTemplateEngine.getTemplateContext());
      fail("Should have blown up");
    } catch (TemplateEngineException e) { 
      e = null;
    }    
  }


  
  /**
   * Test method for {@link org.melati.template.TemplateEngine#expandTemplate(org.melati.util.MelatiWriter, org.melati.template.Template, org.melati.template.TemplateContext)}.
   */
  public void testExpandTemplateMelatiWriterTemplateTemplateContext() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    servletTemplateEngine.init(mc);
    Melati melati = new Melati(mc,new MelatiStringWriter());
    try { 
      servletTemplateEngine.expandTemplate(melati.getWriter(), (Template)null, servletTemplateEngine.getTemplateContext());
      fail("Should have blown up");
    } catch (TemplateEngineException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#expandedTemplate(org.melati.template.Template, org.melati.template.TemplateContext)}.
   */
  public void testExpandedTemplate() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    servletTemplateEngine.init(mc);
    try { 
      servletTemplateEngine.expandedTemplate(null, servletTemplateEngine.getTemplateContext());
      fail("Should have blown up");
    } catch (TemplateEngineException e) { 
      e = null;
    } 
  }

  /**
   * Test method for {@link org.melati.template.TemplateEngine#getTemplateContext()}.
   */
  public void testGetTemplateContext() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    templateEngine.init(mc);
    try { 
      templateEngine.getTemplateContext(); 
      fail("Should have blown up");
    } catch (TemplateEngineException e) { 
      e = null;
    } 
  }
  /**
   * Test method for {@link org.melati.template.TemplateEngine#getEngine()}.
   */
  public void testGetEngine() {
    assertEquals("none",servletTemplateEngine.getEngine());    
  }

  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#init(org.melati.MelatiConfig, javax.servlet.http.HttpServlet)}.
   */
  public void testInitMelatiConfigHttpServlet() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    servletTemplateEngine.init(mc, (HttpServlet)null);
  }

  /**
   * Test method for {@link org.melati.template.ServletTemplateEngine#getServletTemplateContext(org.melati.Melati)}.
   */
  public void testGetServletTemplateContext() throws Exception {
    MelatiConfig mc = new MelatiConfig();
    servletTemplateEngine.init(mc);
    Melati melati = new Melati(mc, new MelatiStringWriter());
    try { 
      servletTemplateEngine.getServletTemplateContext(melati); 
      fail("Should have blown up");
    } catch (TemplateEngineException e) { 
      e = null;
    } 
    
  }

  
}
