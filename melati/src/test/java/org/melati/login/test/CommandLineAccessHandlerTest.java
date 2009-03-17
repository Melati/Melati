/**
 * 
 */
package org.melati.login.test;

import java.io.ByteArrayInputStream;

import org.melati.login.CommandLineAccessHandler;

/**
 * @author timp
 *
 */
public class CommandLineAccessHandlerTest extends AccessHandlerTestAbstract {

  /**
   * @param name
   */
  public CommandLineAccessHandlerTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();
    m.setArguments(new String[] {"melatijunit", "-u", "_administrator_", "-p", "FIXME"});    
  }
  
  /** 
   * Create the AccessHandler and set its input stream.
   * 
   * @see org.melati.login.test.AccessHandlerTestAbstract#setAccessHandler()
   */
  public void setAccessHandler() {
    CommandLineAccessHandler clah = new CommandLineAccessHandler(); 
    clah.setInput(new ByteArrayInputStream("_administrator_\nFIXME\n".getBytes()));
    it = clah;
  }

  /**
   * Test missing argument handling.
   */
  public void testEstablishUserMissingUsername() { 
    m.setArguments(new String[] {"melatijunit", "-p", "FIXME"});    
    it.establishUser(m);
    assertEquals("Melati guest user", m.getUser().displayString());
  }
  /**
   * Test missing argument handling.
   */
  public void testEstablishUserMissingPassword() { 
    m.setArguments(new String[] {"melatijunit", "-u", "_administrator_"});    
    it.establishUser(m);
    assertEquals("Melati guest user", m.getUser().displayString());
  }
}
