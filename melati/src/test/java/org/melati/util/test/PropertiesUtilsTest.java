package org.melati.util.test;

import org.melati.util.PropertiesUtils;
/**
 * @author timp
 * @since 2013-07-14
 */
import junit.framework.TestCase;

public class PropertiesUtilsTest extends TestCase {

  public final void testFromFile() {
  }

  public final void testFromResourceClassOfQ() throws Exception {
    // Cannot rely upon ordering
    assertEquals(7,
        PropertiesUtils.fromResource(org.melati.MelatiConfig.class).size());
  }

  public final void testFromResourceClassOfQString() {
  }

  public final void testGetOrDie() {
  }

  public final void testGetOrDefault() {
  }

  public final void testGetOrDie_int() {
  }

  public final void testGetOrDefault_int() {
  }

  public final void testInstanceOfNamedClassStringString() {
  }

  public final void testInstanceOfNamedClassPropertiesStringStringString() {
  }

}
