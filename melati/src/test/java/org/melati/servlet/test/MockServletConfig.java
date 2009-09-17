/**
 * 
 */
package org.melati.servlet.test;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author timp
 *
 */
public class MockServletConfig implements ServletConfig {
   private MockServletContext context = null;
   private String name;
   
   /**
    * Constructor.
   * @param servletContext
   * @param servletName
   */
   public MockServletConfig(MockServletContext servletContext, String servletName) { 
     context = servletContext;
     name = servletName;
   }
  /**
   * Constructor. 
   */
  public MockServletConfig() { 
     context = new MockServletContext();
     name = "mockServlet";
   }
  
  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletConfig#getInitParameter(java.lang.String)
   */
  public String getInitParameter(String arg0) {
    return (String)initParameters.get(arg0);

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletConfig#getInitParameterNames()
   */
  public Enumeration<String> getInitParameterNames() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletConfig#getServletContext()
   */
  public ServletContext getServletContext() {
    return context;
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.ServletConfig#getServletName()
   */
  public String getServletName() {
    return name;
  }

  Hashtable<String, Object> initParameters = new Hashtable<String, Object>(); 
  /**
   * @param name of parameter
   * @param value of parameter
   */
  public void setInitParameter(String name, String value) {
    initParameters.put(name, value);
  }
  /**
   * @param string name to set
   */
  public void setServletName(String string) {
    name = string;    
  }

}
