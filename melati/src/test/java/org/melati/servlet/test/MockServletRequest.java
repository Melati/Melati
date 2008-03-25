/**
 * 
 */
package org.melati.servlet.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author timp
 * @since 2006/12/05
 *
 */
public class MockServletRequest implements HttpServletRequest {

    Map parameters = new HashMap();
    
    /**
     * @param map the parameters
     */
    public void setParameters(Map map) {
        parameters = map;
    }
    
    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String arg0) {
        return 0;
    }

    // Note this is not correct, should be a MultiMap
    Hashtable headers = new Hashtable();
    public String getHeader(String arg0) {
        return (String)headers.get(arg0);
    }
    /**
     * @param key the header key
     * @param value the value to set it to 
     */
    public void setHeader(String key, String value) {
      headers.put(key, value);
    }
    public Enumeration getHeaders(String arg0) {
        return headers.elements();
    }

    public Enumeration getHeaderNames() {
        return headers.keys();
    }

    public int getIntHeader(String arg0) {
        return -1;
    }

    public String getMethod() {
        return null;
    }

    String pathInfo;
    public String getPathInfo() {
        return pathInfo;
    }
    /**
     * @param info the info to set
     */
    public void setPathInfo(String info) {
      pathInfo = info;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getContextPath() {
        return ""; // root context
    }

    public String getQueryString() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String arg0) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    String requestURI = null;
    public String getRequestURI() {
        return requestURI;
    }
    /**
     * @param uri the uri to set
     */
    public void setRequestURI(String uri) {
      requestURI = uri;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return "/mockServletPath/";
    }
    Object session;
    /**
     * @param s the session to set
     */
    public void setSession(Object s){
      session = s;
    }
    public HttpSession getSession(boolean arg0) {
      return (HttpSession)session;
    }

    public HttpSession getSession() {
      return (HttpSession)session;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public Object getAttribute(String arg0) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    String charEncoding = "ISO-8859-1";
    public String getCharacterEncoding() {
      return charEncoding;
    }

    public void setCharacterEncoding(String ce) throws UnsupportedEncodingException {
      if (ce != null && ce.equals("UnsupportedEncoding"))
        throw new UnsupportedEncodingException();
      charEncoding = ce;
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    /**
     * Set a parameter.
     */
    public void setParameter(String name, String value) { 
      parameters.put(name, value);
    }
    public String getParameter(String arg0) {
      if (parameters.get(arg0) == null)
        return null;
      return (String)parameters.get(arg0);
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    public String[] getParameterValues(String key) {
      return new String[] {(String)parameters.get(key)} ;
    }

    public Map getParameterMap() {
        return parameters;
    }

    public String getProtocol() {
        return null;
    }

    String scheme = "http";
    /**
     * @param s the scheme to set
     */
    public void setScheme(String s) {
      scheme = s;
    }
    public String getScheme() {
        return scheme;
    }

    public String getServerName() {
        return "localhost";
    }

    public int getServerPort() {
        return 80;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String arg0, Object arg1) {
    }

    public void removeAttribute(String arg0) {
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String arg0) {
        return new RequestDispatcher() {
        
            public void include(ServletRequest arg0, ServletResponse arg1)
                    throws ServletException, IOException {
            }
        
            public void forward(ServletRequest arg0, ServletResponse arg1)
                    throws ServletException, IOException {
            }
        };
    }

    public String getRealPath(String arg0) {
        return "test";
    }

    public String getLocalAddr() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }

    public String getLocalName() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }

    public int getLocalPort() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }

    public int getRemotePort() {
      throw new RuntimeException("TODO No one else has ever called this method." +
                                 " Do you really want to start now?");
      
    }
    
}