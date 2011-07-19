/**
 * 
 */
package org.melati.app.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.melati.app.PoemApp;
import org.melati.app.UnhandledExceptionException;
import org.melati.util.UnexpectedExceptionException;
import org.melati.util.test.StringInputStream;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class PoemAppTest extends TestCase {

  /**
   * @param name
   */
  public PoemAppTest(String name) {
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
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "user", "1", "display", "-o", fileName };
    PoemApp.main(args);
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
    assertEquals("You are          : _guest_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }

  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testRunNoMethod() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "user", "1", "-o", fileName };
    PoemApp.main(args);
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
    assertEquals("You are          : _guest_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : null\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }
  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testRunTableMethod() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "user", "method", "-o", fileName };
    PoemApp.main(args);
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
    assertEquals("You are          : _guest_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : null\n" +
        "Your Method was  : method\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }

  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testRunNoTroid() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "user", "-o", fileName };
    PoemApp.main(args);
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
    assertEquals("You are          : _guest_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : null\n" + 
        "Your Troid was   : null\n" +
        "Your Method was  : user\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }
  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testRunNoTable() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "-o", fileName };
    PoemApp.main(args);
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
    assertEquals("You are          : _guest_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : null\n" + 
        "Your Troid was   : null\n" +
        "Your Method was  : null\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }


  
  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testArgumentHandling() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "user", "one", "display", "-o", fileName };
    try { 
      PoemApp.main(args);
      fail("Should have blown up");
    } catch (UnexpectedExceptionException e) { 
      assertEquals("An exception occurred in a context where it was very unexpected:\n" + 
          "Arguments `user one display' have wrong form:\n" +
          "java.lang.NumberFormatException: For input string: \"one\"",e.getMessage());
      e = null;      
    }
    File fileIn = new File(fileName);
    fileIn.delete();      
  }

  
  /**
   * Test method for {@link org.melati.app.AbstractConfigApp#run(java.lang.String[])}.
   */
  public void testPrePoemSessionThowing() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "appjunit", "user", "1", "display", "-o", fileName };
    try { 
      ThrowingPoemApp.main(args);
      fail("Should have blown up");
    } catch (RuntimeException e) { 
      assertEquals("An exception occurred in a context where it was very unexpected:\n" + 
          "Bang!",e.getMessage());
      e = null;
    }
    File fileIn = new File(fileName);
    fileIn.delete();      
  }

  /**
   * Access exceptions.
   */
  public void testAccess() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", "-o", fileName };
    ProtectedPoemApp it = new ProtectedPoemApp();
    it.setInput(new StringInputStream("_administrator_\nFIXME\n"));
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
    assertEquals("You are          : _administrator_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }
  
  /**
   * Access exceptions.
   */
  public void testAccessPrompted() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", "-o", fileName };
    ProtectedPoemApp it = new ProtectedPoemApp();
    it.setInput(new StringInputStream("bad\nwrong\nbad\nwrong\n_administrator_\nFIXME\n"));
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
    assertEquals("You are          : _administrator_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }
  /**
   * Access exceptions.
   */
  public void testAccessPromptedThreeWrongResponses() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", "-o", fileName };
    ProtectedPoemApp it = new ProtectedPoemApp();
    it.setInput(new StringInputStream("bad\nwrong\nbad\nwrong\nbad\nwrong\n_administrator_\nFIXME\n"));
    try { 
      it.run(args);
      fail("Should have bombed");
    } catch (UnhandledExceptionException e) { 
      assertEquals("You need the capability _administer_ to write the object tableInfo/0 but your access token _guest_ doesn\'t confer it"  
          , e.subException.getMessage());
      e = null;
    }
    File fileIn = new File(fileName);
    fileIn.delete();      
  }
  /**
   * Access exceptions.
   */
  public void testAccessWrongUsernameInArgs() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", "-o", fileName, 
                      "-u", "unk", "-p","unk" };
    ProtectedPoemApp it = new ProtectedPoemApp();
    try {
      it.run(args);
      fail("Should have bombed");
    } catch (UnhandledExceptionException e) { 
      assertEquals("You need the capability _administer_ to write the object tableInfo/0 but your access token _guest_ doesn\'t confer it"  
          , e.subException.getMessage());
      e = null;
    }
    File fileIn = new File(fileName);
    fileIn.delete();      
  }

  /**
   * Access exceptions.
   */
  public void testAccessWrongPasswordInArgs() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", "-o", fileName, 
                      "-u", "_administrator_", "-p","unk" };
    ProtectedPoemApp it = new ProtectedPoemApp();
    try {
      it.run(args);
      fail("Should have bombed");
    } catch (UnhandledExceptionException e) { 
      assertEquals("You need the capability _administer_ to write the object tableInfo/0 but your access token _guest_ doesn\'t confer it"  
          , e.subException.getMessage());
      e = null;
    }
    File fileIn = new File(fileName);
    fileIn.delete();      
  }

  /**
   * Test argument names
   */
  public void testArgumentNames() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", "-o", fileName, 
                      "-u", "_administrator_", "-p", "FIXME", };
    ProtectedPoemApp it = new ProtectedPoemApp();
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
    assertEquals("You are          : _administrator_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }

  /**
   * Test argument names
   */
  public void testArgumentNames2() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", 
        "-o", fileName, "-user", "_administrator_", "-pass", "FIXME", };
    ProtectedPoemApp.main(args);
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
    assertEquals("You are          : _administrator_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }
  /**
   * Test argument names
   */
  public void testArgumentNames3() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", 
        "-o", fileName, "-username", "_administrator_", "-password", "FIXME", };
    ProtectedPoemApp.main(args);
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
    assertEquals("You are          : _administrator_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }

  /**
   * Test argument names
   */
  public void testArgumentNames4() throws Exception {
    String fileName = "ttt.tmp";
    String[] args = { "user", "1", "display", 
        "-o", fileName, "--username", "_administrator_", "--password", "FIXME", };
    ProtectedPoemApp.main(args);
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
    assertEquals("You are          : _administrator_\n" +
        "Your Database was: jdbc:hsqldb:mem:appjunit\n" + 
        "Your Table was   : user (from the data structure definition)\n" + 
        "Your Troid was   : 1\n" +
        "Your Method was  : display\n" +
        "System Users\n" + 
        "============\n" +
        "  Melati guest user\n"+
        "  Melati database administrator\n" , output);
  }

  
}

