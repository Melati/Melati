package org.melati.template.test;

import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.BaseFieldAttributes;
import org.melati.poem.Capability;
import org.melati.poem.Column;
import org.melati.poem.Field;
import org.melati.poem.PoemLocale;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.WMLAttributeMarkupLanguage;
import org.melati.template.WMLMarkupLanguage;
import org.melati.util.JSStaticTree;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.Tree;
import org.melati.util.test.Node;
import org.melati.util.test.TreeDatabase;

/**
 * @author timp
 * @since 2 Jul 2007
 *
 */
public abstract class WMLMarkupLanguageSpec extends MarkupLanguageSpec {

  /**
   * @param arg0
   */
  public WMLMarkupLanguageSpec(String arg0) {
    super(arg0);
  }

  /**
   * 
   */
  public WMLMarkupLanguageSpec() {
    super();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.template.test.MarkupLanguageSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    ml = new WMLMarkupLanguage(
            m, 
            ClassNameTempletLoader.getInstance(), 
            PoemLocale.HERE);
    aml = new WMLAttributeMarkupLanguage((WMLMarkupLanguage)ml);
    m.setMarkupLanguage(ml);
    assertEquals(ml, m.getMarkupLanguage());    
  }

  /**
   * Test method for rendered(Exception).
   * @throws Exception 
   * 
   * @see org.melati.template.HTMLAttributeMarkupLanguage#
   *      rendered(AccessPoemException)
   */
  public void testRenderedAccessPoemException() throws Exception {
    
    assertEquals("java.lang.Exception",aml.rendered(new Exception()));

    
    // This is the bit that differs from HTML, not sure how
    AccessPoemException ape = new AccessPoemException(
          getDb().getUserTable().guestUser(), new Capability("Cool"));
    System.err.println(ml.rendered(ape));
    assertTrue(ml.rendered(ape).indexOf(
          "org.melati.poem.AccessPoemException: " + 
          "You need the capability Cool but " + 
          "your access token _guest_ doesn&#39;t confer it") != -1);
    //assertTrue(ml.rendered(ape).indexOf("[Access denied to Melati guest user]") != -1);

    
    
    
    ape = new AccessPoemException();
    assertEquals("", aml.rendered(ape));
    //System.err.println(m.getWriter().toString());
    assertTrue(m.getWriter().toString().indexOf("[Access denied to [UNRENDERABLE EXCEPTION!]") != -1);
    ape = new AccessPoemException(
          getDb().getUserTable().guestUser(), new Capability("Cool"));
    assertEquals("", aml.rendered(ape));
      // NB Not at all sure how this value changed 
      //System.err.println(m.getWriter().toString());
      //assertTrue(m.getWriter().toString().indexOf("[Access denied to Melati guest user]") != -1);
    assertTrue(m.getWriter().toString().indexOf("[Access denied to _guest_]") != -1);

  }

  /**
   * Test that special templets are found.
   *   
   */
  public void testSpecialTemplateFound() throws Exception { 
    Column<Integer> column = getDb().getGroupMembershipTable().getUserColumn();
    BaseFieldAttributes<Integer> fa = new BaseFieldAttributes<Integer>(column, column.getType());
    Field<Integer> field = new Field<Integer>(getDb().getUserTable().administratorUser().troid(), fa);
    Object adminUtil = m.getContextUtil("org.melati.admin.AdminUtils");
    assertTrue(adminUtil instanceof org.melati.admin.AdminUtils);
    
    try { 
      ml.input(field);
      fail("Should have blown up");
    } catch (MelatiBugMelatiException e) { 
      e = null;
    }
  }
  /**
   * WML does not have SelectionWindow, perhaps it should 
   * {@inheritDoc}
   * @see org.melati.template.test.MarkupLanguageSpec#testSelectionWindowField()
   */
  public void testSelectionWindowField() throws Exception {
    
  }

  /**
   * Test method for rendered(Treeable).
   * WML Does not have this templet.
   * 
   * @see org.melati.template.MarkupLanguage#rendered(Object)
   */
  public void testRenderedTreeable() throws Exception {
    Node parent = (Node)((TreeDatabase)getDb()).getNodeTable().newPersistent();
    parent.setName("Mum");
    parent.makePersistent();
    Node kid1 = (Node)((TreeDatabase)getDb()).getNodeTable().newPersistent();
    kid1.setName("K1");
    kid1.setParent(parent);
    kid1.makePersistent();
    Node kid2 = (Node)((TreeDatabase)getDb()).getNodeTable().newPersistent();
    kid2.setName("K2");
    kid2.setParent(parent);
    kid2.makePersistent();
    Tree testTree = new Tree(parent);
    JSStaticTree tree = new JSStaticTree(testTree, "/melati-static/admin");
    m.setPoemContext(new PoemContext());
      
    String renderedTree = ml.rendered(tree);
    assertTrue(renderedTree.trim().startsWith("[org.melati.util.JSStaticTree@"));
   
  }
  


}