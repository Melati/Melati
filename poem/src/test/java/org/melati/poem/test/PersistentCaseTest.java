package org.melati.poem.test;

import org.melati.poem.Persistent;

import junit.framework.TestCase;

public class PersistentCaseTest extends TestCase {

  public void testToStringNull() {
    Persistent unit = new Persistent();
    assertEquals("null/null", unit.toString());
  }

  public void testEqualsNull() {
    Persistent unitOne = new Persistent();
    Persistent unitTwo = new Persistent();
    assertTrue(unitOne.equals(unitTwo));
  }

}
