package org.melati.app.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.melati.app.ConfigApp;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2007-12-06
 */
public class ConfigAppTest extends TestCase {

  /**
   * @param name
   */
  public ConfigAppTest(String name) {
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
    String[] args = { "fred", "-o", fileName };
    ConfigApp it = new ConfigApp();
    it.run(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( 
        new InputStreamReader(
            new FileInputStream(fileIn)));
    while (in.ready()) {
      output += in.readLine();
      output += "\n";
    }
    in.close();
    fileIn.delete();   
    assertEquals("Hello World\nYour Method was:fred\n" , output);
    assertEquals("nobody", it.getSysAdminName());
    assertEquals("nobody@nobody.com", it.getSysAdminEmail());
  }

  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testMain() throws Exception {
    String fileName = "t1.tmp";
    String[] args = { "fred", "-o", fileName };
    ConfigApp.main(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( 
        new InputStreamReader(
            new FileInputStream(fileIn)));
    while (in.ready()) {
      output += in.readLine();
      output += "\n";
    }
    in.close();
    fileIn.delete();   
    assertEquals("Hello World\nYour Method was:fred\n" , output);
  }

}
