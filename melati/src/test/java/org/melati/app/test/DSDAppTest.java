/**
 * 
 */
package org.melati.app.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.melati.app.DSDApp;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class DSDAppTest extends TestCase {

  /**
   * @param name
   */
  public DSDAppTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testRun() throws Exception {
    String fileName = "t1.tmp";
    String[] args = { "appjunit", "-o", fileName };
    DSDApp.main(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( 
        new InputStreamReader(
            new FileInputStream(fileIn)));
    int line = 0;
    while (in.ready()) {
      line++;
      if (line == 2)
        output += in.readLine();
      else 
        in.readLine();
    }
    in.close();
    fileIn.delete();
    assertEquals(" * DSD for jdbc:hsqldb:mem:appjunit (org.melati.poem.PoemDatabase)" , output);
  }

}
