package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Capability;
import org.melati.template.test.MarkupLanguageSpec.Bomber;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;
import org.melati.util.test.Node;


/**
 * Test the HTMLMarkupLanguage and its AttributeMarkupLanguage.
 * 
 * @author timp
 * @since 18-May-2006
 */
public class JSONMarkupLanguageWebmacroTest extends JSONMarkupLanguageSpec {
  
  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    mc.setTemplateEngine(new WebmacroTemplateEngine());
  }
 
  public void testRenderedAccessPoemException() throws Exception {
    
    assertEquals("\n{\n \"class\":\"java.lang.Exception\",\n \"asString\":\"java.lang.Exception\"\n}\n",ml.rendered(new Exception()));

    AccessPoemException ape = new AccessPoemException(
          getDb().getUserTable().guestUser(), new Capability("Cool"));
    assertTrue(ml.rendered(ape),ml.rendered(ape).indexOf("You need the capability Cool but ") != -1);
    ape = new AccessPoemException();
    System.err.println(m.getWriter().toString());
    assertEquals("", m.getWriter().toString());
    ape = new AccessPoemException(
          getDb().getUserTable().guestUser(), new Capability("Cool"));
  }
  
  public void testGetAttr() {
    // we don't have one
  }
  public void testEntitySubstitution() throws Exception { 
    char pound[] = {163};
    assertEquals(new String(pound), ml.rendered(new String(pound)));
  }
  public void testEscapedPersistent() {
    // not implemented yet
    try { 
      ml.escaped(getDb().getUserTable().getUserObject(0));
    } catch (RuntimeException e) { 
      e = null;
    }
  }
  public void testEncoded() {
    try { 
      ml.encoded(" ");
    } catch (RuntimeException e) { 
      e = null;
    }
  }
  public void testRenderedObject() throws Exception {
    assertEquals("Fredd$", ml.rendered("Fredd$"));
    // Note velocity seems to leave the line end on
    //assertEquals("[1]", ml.rendered(new Integer("1")).trim());
    
    //assertEquals("1", ml.getAttr().rendered(new Integer("1")));
    try { 
      ml.getAttr().rendered(new Bomber());
      fail("Should have bombed");
    } catch (RuntimeException e) {
      assertEquals("Not expected to be called in JSON", e.getMessage());
      e = null;
    }
    
    try { 
      ml.rendered(new Bomber());
      fail("Should have bombed");
    } catch (RuntimeException e) {
      assertTrue(e.getMessage().indexOf("Bomber bombed") > -1);
      e = null;
    }
    
    Node persistent = (Node)getDb().getTable("node").newPersistent();
    persistent.setName("Mum");
    persistent.makePersistent();
    m.setPoemContext(new PoemContext());
     
    String renderedPersistent = ml.rendered(persistent);
    //System.err.println(renderedPersistent);
    assertEquals("{\n \"class\":\"org.melati.util.test.Node\",\n \"asString\":\"Node\\/0\"\n}\n", renderedPersistent);

  }
}
