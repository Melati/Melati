package org.melati.app.test;

import junit.framework.TestCase;
import org.melati.app.DSDApp;
import org.melati.app.JavinatorApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author timp
 * @since 2017-01-27
 */
public class JavinatorAppTest extends TestCase {

  public JavinatorAppTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testRun() throws Exception {
    String fileName = "t1.tmp";
    String[] args = { "appjunit", "tableinfo", "8", "-o", fileName };
    JavinatorApp.main(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( 
        new InputStreamReader(
            new FileInputStream(fileIn)));
    int line = 0;
    while (in.ready()) {
      line++;
      if (line == 1)
        output += in.readLine();
      else 
        in.readLine();
    }
    in.close();
//    fileIn.delete();
    assertEquals(
        "// Delete this line to prevent overwriting of this file", output);
  }

}
