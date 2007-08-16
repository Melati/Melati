package org.melati.app.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.melati.Melati;
import org.melati.app.InvalidArgumentsException;
import org.melati.app.TemplateApp;
import org.melati.util.ConfigException;
import org.melati.util.UnexpectedExceptionException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Generated code for the test suite <b>TemplateAppTest</b> located at
 * <i>/melati/src/test/java/org/melati/app/test/TemplateAppTest.testsuite</i>.
 * 
 */
public class TemplateAppTest extends TestCase {
  /**
   * Constructor for TemplateAppTest.
   * 
   * @param name
   */
  public TemplateAppTest(String name) {
    super(name);
  }

  /**
   * Returns the JUnit test suite that implements the <b>TemplateAppTest</b>
   * definition.
   */
  public static Test suite() {
    TestSuite templateAppTest = new TestSuite(TemplateAppTest.class);
    return templateAppTest;
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    //System.gc();
  }

  /**
   * @see org.melati.app.TemplateApp#init(String[])
   */
  public void testInit() throws Exception {
    TemplateApp ta = new TemplateApp();
    String[] args = { "appjunit", "user", "0", "method", "field", "value" };
    Melati m = ta.init(args);
    System.err.println("M length:" + m.getArguments().length);

    assertEquals("appjunit", m.getDatabase().getName());
    System.err.println("Table:" + m.getPoemContext().getTable());
    System.err.println("Table:" + m.getTable());
    Hashtable f = (Hashtable)m.getTemplateContext().get("Form");
    assertEquals("value", f.get("field"));

  }

  /**
   * @see org.melati.app.TemplateApp#init(String[])
   */
  public void testInitWithUnmatcheArgs0() throws Exception {
    TemplateApp ta = new TemplateApp();
    String[] args = { "appjunit", "user", "0", "method", "field", "value",
        "unmatched" };
    try {
      ta.init(args);
      fail("Should have bombed");
    } catch (InvalidArgumentsException e) {
      e = null;
    }
  }

  /**
   * @see org.melati.app.TemplateApp#main(String[])
   */
  public void testMain() throws Exception {
    String fileName = "t.tmp";
    String[] args = { "appjunit", "user", "0",
        "org/melati/app/TemplateApp", "field", "value", "-o", fileName };
    TemplateApp it = new TemplateApp();
    it.run(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(fileIn)));
    while (in.ready()) {
      output += in.readLine();
    }
    in.close();
    //fileIn.delete();      
    //System.err.print(output);
    assertEquals("Hello _guest_" + 
            "You have expanded template org/melati/app/TemplateApp.wm " + 
            "Your melati contains:" + 
            "Database : jdbc:hsqldb:mem:appjunit" + 
            "Table    : user (from the data structure definition)"  +
            "Object   : _guest_" + 
            "Troid    : 0" + 
            "Method   : org/melati/app/TemplateApp" + 
            "System Users" + 
            "============" +
            "  Melati guest user" + 
            "  Melati database administrator" +  
            "Form settings" + 
            "=============" + 
            "  field value", output);
  }
  
  /**
   * @see org.melati.app.TemplateApp#main(String[])
   */
  public void borkedTestMainOneArg() throws Exception {
    String fileName = "t1.tmp";
    String[] args = { "appjunit", "-o", fileName };
    TemplateApp it = new TemplateApp();
    it.run(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(fileIn)));
    output += in.readLine();
    while (in.ready()) {
      output += in.readLine();
    }
    in.close();
    //fileIn.delete();      
    System.err.print(":" + output + ":");
    assertEquals("Hello _guest_" + 
            "You have expanded template org/melati/app/TemplateApp.wm " + 
            "Your melati contains:" + 
            "Database : jdbc:hsqldb:mem:appjunit" + 
            "Table    : null"  +
            "Object   : null" + 
            "Troid    : null" + 
            "Method   : null" + 
            "System Users" + 
            "============" +
            "  Melati guest user" + 
            "  Melati database administrator" , output);
  }

  /**
   * @see org.melati.app.TemplateApp#main(String[])
   */
  public void borkedTestMainTwoArgs() throws Exception {
    String fileName = "t2.tmp";
    String[] args = { "appjunit", "user", "-o", fileName };
    TemplateApp it = new TemplateApp();
    try { 
      it.run(args);
      fail("Should have blown up");
    } catch (UnexpectedExceptionException e) { 
      e = null;
    }
    fileName = "t2a.tmp";
    args = new String[] { "appjunit", "org/melati/app/TemplateApp.wm", "-o", fileName };
    TemplateApp.main(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(fileIn)));
    while (in.ready()) {
      output += in.readLine();
    }
    in.close();
    //fileIn.delete();      
    System.err.print(output);
    assertEquals("Hello _guest_" + 
            "You have expanded template org/melati/app/TemplateApp.wm " + 
            "Your melati contains:" + 
            "Database : jdbc:hsqldb:mem:appjunit" + 
            "Table    : null"  +
            "Object   : null" + 
            "Troid    : null" + 
            "Method   : org/melati/app/TemplateApp.wm" + 
            "System Users" + 
            "============" +
            "  Melati guest user" + 
            "  Melati database administrator", output);
  }
  
  /**
   * @see org.melati.app.TemplateApp#main(String[])
   */
  public void borkedTestMainThreeArgs() throws Exception {
    String fileName = "t3.tmp";
    String[] args = { "appjunit", "user", "0",
         "-o", fileName };
    TemplateApp it = new TemplateApp();
    it.run(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(fileIn)));
    while (in.ready()) {
      output += in.readLine();
    }
    in.close();
    //fileIn.delete();      
    //System.err.print(output);
    assertEquals("Hello _guest_" + 
            "You have expanded template org/melati/app/TemplateApp.wm " + 
            "Your melati contains:" + 
            "Database : jdbc:hsqldb:mem:appjunit" + 
            "Table    : user (from the data structure definition)"  +
            "Object   : _guest_" + 
            "Troid    : 0" + 
            "Method   : null" + 
            "System Users" + 
            "============" +
            "  Melati guest user" + 
            "  Melati database administrator" , output);
  }

  
  /**
   * @see org.melati.app.TemplateApp#main(String[])
   */
  public void borkedTestMainFourArgs() throws Exception {
    String fileName = "t4.tmp";
    String[] args = { "appjunit", "user", "0",
        "org/melati/app/TemplateApp",  "-o", fileName };
    TemplateApp it = new TemplateApp();
    it.run(args);
    String output = "";
    File fileIn = new File(fileName);
    BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(fileIn)));
    while (in.ready()) {
      output += in.readLine();
    }
    in.close();
    //fileIn.delete();      
    System.err.print(output);
    assertEquals("Hello _guest_" + 
            "You have expanded template org/melati/app/TemplateApp.wm " + 
            "Your melati contains:" + 
            "Database : jdbc:hsqldb:mem:appjunit" + 
            "Table    : user (from the data structure definition)"  +
            "Object   : _guest_" + 
            "Troid    : 0" + 
            "Method   : org/melati/app/TemplateApp" + 
            "System Users" + 
            "============" +
            "  Melati guest user" + 
            "  Melati database administrator" , output);
  }

  
  /**
   * @see org.melati.app.TemplateApp#main(String[])
   */
  public void testMainZeroArgs() throws Exception {
    String fileName = "t0.tmp";
    String[] args = { "-o", fileName };
    TemplateApp it = new TemplateApp();
    try { 
      it.run(args);
      fail("Should have bombed");
    } catch (ConfigException e) {
      e = null;
    }
  }

}
