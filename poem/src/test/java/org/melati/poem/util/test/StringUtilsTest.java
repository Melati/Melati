package org.melati.util.test;

import org.melati.util.StringUtils;

import junit.framework.TestCase;

/**
 * @author Tim
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringUtilsTest extends TestCase {

  /**
   * Constructor for StringUtil.
   * @param arg0
   */
  public StringUtilsTest(String arg0) {
    super(arg0);
  }

  public void testIsQuotedNull() {
    assertTrue(!StringUtils.isQuoted(null));
  }

  public void testIsQuotedBlank() {
    assertTrue(!StringUtils.isQuoted(""));
  }

  public void testIsQuotedNot() {
    assertTrue(!StringUtils.isQuoted("a"));
  }

  public void testIsQuotedDouble() {
    assertTrue(StringUtils.isQuoted("\"a\""));
  }

  public void testIsQuotedSingle() {
    assertTrue(StringUtils.isQuoted("\'a\'"));
  }

  public void testUnNulled() {
    String expected = "a";
    String actual = StringUtils.unNulled("a");
    assertEquals(expected, actual);
  }

  public void testUnNulledNull() {
    String expected = "";
    String actual = StringUtils.unNulled(null);
    assertEquals(expected, actual);
  }

}
