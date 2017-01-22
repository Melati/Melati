package org.melati.template.test;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import org.melati.MelatiConfig;
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Capability;
import org.melati.poem.Field;
import org.melati.poem.PoemThread;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.MelatiException;
import org.melati.util.test.Node;

/**
 * Test the JSONMarkupLanguage and its AttributeMarkupLanguage.
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

    assertEquals(
        "\n{\n \"class\":\"java.lang.Exception\",\n \"asString\":\"java.lang.Exception\"\n}\n",
        ml.rendered(new Exception()));

    AccessPoemException ape = new AccessPoemException(getDb().getUserTable()
        .guestUser(), new Capability("Cool"));
    assertTrue(ml.rendered(ape),
        ml.rendered(ape).indexOf("You need the capability Cool but ") != -1);
    ape = new AccessPoemException();
    System.err.println(m.getWriter().toString());
    assertEquals("", m.getWriter().toString());
    ape = new AccessPoemException(getDb().getUserTable().guestUser(),
        new Capability("Cool"));
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

    Node persistent = (Node) getDb().getTable("node").newPersistent();
    persistent.setName("Mum");
    persistent.makePersistent();
    m.setPoemContext(new PoemContext());

    String renderedPersistent = ml.rendered(persistent);
    assertEquals(
        "{\n \"class\":\"org.melati.util.test.Node\",\n \"value\":\"Mum\"\n}",
        renderedPersistent);
  }

  /**
   * @see org.melati.template.MarkupLanguage#renderedMarkup
   */
  public void testRenderedMarkupString() throws Exception {
    try {
      ml.renderedMarkup("</a>");
      fail("Should have bombed");
    } catch (RuntimeException e) {
      assertEquals("Not expected to be called in JSON", e.getMessage());
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
    assertEquals("{\n \"class\":\"java.util.Properties\",\n \"asString\":\"{}\"\n}\n", ml.rendered(new Properties()));
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
    PoemThread.setAccessToken(getDb().getUserTable().guestUser());
    Field<?> password = getDb().getUserTable().getPasswordColumn().asEmptyField();
    System.err.println(ml.rendered(getDb().getUserTable().administratorUser()));
    System.err.println(ml.input(password));
    assertEquals("{\n \"class\":\"org.melati.poem.Field\",\n" + 
                    " \"asString\":\"password: \"\n}\n", ml.input(password));
    assertEquals("", ml.rendered(password));
    try { 
      assertEquals("", ml.rendered(ml.rendered(getDb().getUserTable().administratorUser().getField("password"))));
      fail("Should have bombed");
    } catch (AccessPoemException e) { 
      e = null;
    } 
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

    assertEquals("[{\n \"class\":\"org.melati.poem.User\",\n \"value\":\"Melati guest user\"\n}"+
        ",{\n \"class\":\"org.melati.poem.User\",\n \"value\":\"Melati database administrator\"\n}]\n", ml.rendered(getDb().getUserTable().selection())); 
  }
}
