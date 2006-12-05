/**
 * 
 */
package org.melati.servlet.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
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

import org.melati.util.EmptyEnumeration;

class MockServletRequest implements HttpServletRequest {

    Map parameters = new HashMap();
    
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

    public String getHeader(String arg0) {
        return null;
    }

    public Enumeration getHeaders(String arg0) {
        return null;
    }

    public Enumeration getHeaderNames() {
        return null;
    }

    public int getIntHeader(String arg0) {
        return 0;
    }

    public String getMethod() {
        return null;
    }

    public String getPathInfo() {
        return null;
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

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return null;
    }
    Object session;
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

    public String getCharacterEncoding() {
      return "ISO-8859-1";
    }

    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
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

    public String getParameter(String arg0) {
        return (String) parameters.get(arg0);
    }

    public Enumeration getParameterNames() {
        return new EmptyEnumeration();
    }

    public String[] getParameterValues(String arg0) {
        return null;
    }

    public Map getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    String scheme = "http";
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
    
}