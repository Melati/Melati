package org.melati.template.test;

import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;
import org.melati.util.test.Node;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

/**
 * Test the JavaMarkupLanguage and its AttributeMarkupLanguage using WebMacro.
 * 
 * @author timp
 * @since 2017-01-21
 */
public class JavaMarkupLanguageWebmacroTest extends JavaMarkupLanguageSpec {

  protected void melatiConfig() throws MelatiException {
    mc = new MelatiConfig();
    mc.setTemplateEngine(new WebmacroTemplateEngine());
  }


  public void testGetAttr() {
    // we don't have one
  }

  public void testEntitySubstitution() throws Exception {
    char pound[] = { 163 };
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
    // assertEquals("[1]", ml.rendered(new Integer("1")).trim());

    // assertEquals("1", ml.getAttr().rendered(new Integer("1")));
    try {
      ml.getAttr().rendered(new Bomber());
      fail("Should have bombed");
    } catch (RuntimeException e) {
      assertEquals("Not expected to be called in Java", e.getMessage());
      e = null;
    }

    try {
      ml.rendered(new Bomber());
      fail("Should have bombed");
    } catch (RuntimeException e) {
      assertTrue(e.getMessage().indexOf("Bomber bombed") > -1);
      e = null;
    }

    Node persistent = (Node) getDb().getTable("node").newPersistent();
    persistent.setName("Mum");
    persistent.makePersistent();
    m.setPoemContext(new PoemContext());

    String renderedPersistent = ml.rendered(persistent);
    assertEquals(
        "\npublic class org.melati.util.test.Node {\n" +
            " String value = \"Node\\/0\";\n" +
            " String displayValue = \"Mum\";\n" +
            "}\n",
        renderedPersistent);
  }

  public void testRenderedAccessPoemException() throws Exception {
    // not applicable
  }

  /**
   * @see org.melati.template.MarkupLanguage#renderedMarkup
   */
  public void testRenderedMarkupString() throws Exception {
    try {
      ml.renderedMarkup("</a>");
      fail("Should have bombed");
    } catch (RuntimeException e) {
      assertEquals("Not expected to be called in Java", e.getMessage());
      e = null;
    }
    assertEquals("<\\/a>", ml.rendered("</a>"));
  }
  
  /**
   * Test that toString is used if no template found.
   * FIXME a template has to be found as there is always an Object template
   * What is this test meant to be about?
   */
  public void testUntemplatedObjectUsesToString() throws Exception { 
    assertEquals("\npublic class java.util.Properties {\n" +
        "  String value = \"{}\";\n}\n", ml.rendered(new Properties()));
  }

  public void testSpecialTemplateFound() throws Exception { 
    // not applicable
  }
  public void testInputField() throws Exception {
    // not applicable
  }
  public void testInputFieldSelection() throws Exception {
    // not applicable
  }
  public void testSelectionWindowField() throws Exception {
    // not applicable
  }
  public void testInputFieldForRestrictedField() throws Exception {
    // not applicable
  }
  
  public void testInputAs() throws Exception {
  }

  public void testSearchInput() throws Exception {
  }

  public void testRenderedTreeable() throws Exception {
  }
  
  /**
   * Test NPE not thrown.
   */
  public void testNull() throws Exception {
    assertEquals("null",ml.rendered(null));
  }
  public void testRenderedList() { 
    ArrayList<Object>l = new ArrayList<Object>();
    assertEquals("[]\n", ml.rendered(l));
    l.add(null);
    assertEquals("[null]\n", ml.rendered(l));
    l.add(null);
    assertEquals("[null,null]\n", ml.rendered(l));
    l.add(Boolean.TRUE);
    assertEquals("[null,null,true]\n", ml.rendered(l));
    l.add(Boolean.FALSE);
    assertEquals("[null,null,true,false]\n", ml.rendered(l));
    l.add(null);
    assertEquals("[null,null,true,false,null]\n", ml.rendered(l));
    l.add("test");
    assertEquals("[null,null,true,false,null,\"test\"]\n", ml.rendered(l));
    
    Vector<Object> v = new Vector<Object>();
    assertEquals("[]\n", ml.rendered(v));
    v.add("one");
    assertEquals("[\"one\"]\n", ml.rendered(v));
    v.add("one");
    assertEquals("[\"one\",\"one\"]\n", ml.rendered(v));
    assertEquals("[\"one\",\"one\"]\n", ml.rendered(v.elements()));    
  }

  public void testRenderedSelection() {
    // not applicable
  }
}
