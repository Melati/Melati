#set ($dollar = "$")
/*
 * ${dollar}Source: /usr/cvsroot/melati/melati-archetype/src/main/resources/archetype-resources/src/test/java/melati/test/JettyWebTestCase.java,v ${dollar}
 * ${dollar}Revision: 1.2 ${dollar}
 *
 * Copyright (C) 2010 Tim Pizey
 *
 *
 * Contact details for copyright holder:
 *
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package ${groupId}.${artifactId}.melati.test;

import org.melati.JettyWebTestCase;

/**
 * @author timp
 * @since  3 Mar 2009
 *
 */
#macro ( capitalise $stringIn )
  #set ($firstChar = $stringIn.substring(0,1).toUpperCase())
  #set ($rest = $stringIn.substring(1))
${firstChar}${rest}#end



public class #capitalise(${artifactId})JettyWebTestCase extends JettyWebTestCase {

  public #capitalise(${artifactId})JettyWebTestCase(String name) {
    super(name);
    webAppDirName = "src/main/webapp";
    contextName = "";
  }

  /**
   * {@inheritDoc}
   * @see org.melati.JettyWebTestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.JettyWebTestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * If you don't know by now.
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    contextName = "";
    webAppDirName = "src/main/webapp";
    startServer(8080);
  }
}
