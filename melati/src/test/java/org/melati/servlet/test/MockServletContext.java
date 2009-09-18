/**
 * 
 */
package org.melati.servlet.test;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author timp
 * @since 26 Jun 2007
 *
 */
public class MockServletContext implements ServletContext {

  /**
   * Introduced in 2.4
   * @see javax.servlet.ServletContext#getContextPath()
   */
  public String getContextPath() {
    throw new RuntimeException("TODO No one else has ever called this method." +
                               " Do you really want to start now?");
    
  }

  /**
   * 
   */
  public MockServletContext() {
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
   */
  public Object getAttribute(String name) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getAttributeNames()
   */
  public Enumeration<String> getAttributeNames() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getContext(java.lang.String)
   */
  public ServletContext getContext(String uripath) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
   */
  public String getInitParameter(String name) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getInitParameterNames()
   */
  public Enumeration<String> getInitParameterNames() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getMajorVersion()
   */
  public int getMajorVersion() {
    return 0;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
   */
  public String getMimeType(String file) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getMinorVersion()
   */
  public int getMinorVersion() {
    return 0;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
   */
  public RequestDispatcher getNamedDispatcher(String name) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
   */
  public String getRealPath(String path) {
    return "/dist/melati/melati/src/test/webapp";
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
   */
  public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getResource(java.lang.String)
   */
  public URL getResource(String path) throws MalformedURLException {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getResourceAsStream(java.lang.String)
   */
  public InputStream getResourceAsStream(String path) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
   */
  public Set<String> getResourcePaths(String arg0) {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getServerInfo()
   */
  public String getServerInfo() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getServlet(java.lang.String)
   */
  public Servlet getServlet(String name) throws ServletException {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getServletContextName()
   */
  public String getServletContextName() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getServletNames()
   */
  public Enumeration<String> getServletNames() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#getServlets()
   */
  public Enumeration<?> getServlets() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#log(java.lang.String)
   */
  public void log(String msg) {
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#log(java.lang.Exception, java.lang.String)
   */
  public void log(Exception exception, String msg) {
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#log(java.lang.String, java.lang.Throwable)
   */
  public void log(String message, Throwable throwable) {
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#removeAttribute(java.lang.String)
   */
  public void removeAttribute(String name) {
  }

  /** 
   * {@inheritDoc}
   * @see javax.servlet.ServletContext#setAttribute(java.lang.String, java.lang.Object)
   */
  public void setAttribute(String name, Object object) {
  }

  Hashtable<String,String> expectations = new Hashtable<String,String>();
  
  /**
   * @param key
   * @param value
   */
  public void expectAndReturn(String key, String value) {
    expectations.put(key, value);
  }

}
