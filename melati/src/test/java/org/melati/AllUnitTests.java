package org.melati;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.melati.poem.dbms.test.CaseInsensitiveRegExpSQL;
import org.melati.util.test.StringUtilsTest;

/**
 * @author Tim
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AllUnitTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for Melati");
    suite.addTestSuite(CaseInsensitiveRegExpSQL.class);
    suite.addTestSuite(StringUtilsTest.class);
    return suite;
  }
}
