package org.melati.doc.example;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class TestHttpServletRequest implements HttpServletRequest {

  ServletInputStream data;
  String enc = "iso-8859-1";
  Cookie[] cookies = {};
  Hashtable parameters = new Hashtable();
  String method = "GET";
  String serverName = "server.name";
  String requestURI = "http://" + serverName;
  String servletPath;
  String pathInfo;
  String pathTranslated = pathInfo;
  String queryString = null;
  String remoteUser = null;
  HttpSession httpSession = new TestHttpSession();
  boolean requestedSessionIDValid = true;

  TestHttpServletRequest(String servletPath, String pathInfo, String data) throws IOException {
    this.servletPath = servletPath;
    this.pathInfo = pathInfo;
    this.data = new TestServletInputStream(data);
  }

    public int getContentLength() {
      return -1;
    }

    public String getContentType() {
      return "text/html";
    }

    public String getProtocol() {
      return "http/1.0";
    }

    public String getScheme() {
      return "http";
    }

    public String getServerName() {
      return serverName;
    }

    public int getServerPort() {
      return 80;
    }

    public String getRemoteAddr() {
      return "127.0.0.1";
    }

    public String getRemoteHost() {
      return "localhost.localdomain";
    }

    /**
     * Applies alias rules to the specified virtual path and returns
     * the corresponding real path, or null if the translation can not
     * be performed for any reason.  For example, an HTTP servlet would
     * resolve the path using the virtual docroot, if virtual hosting
     * is enabled, and with the default docroot otherwise.  Calling
     * this method with the string "/" as an argument returns the
     * document root.
     *
     * @param path the virtual path to be translated to a real path
     */
    public String getRealPath(String path) {
      return path;
    }

    public ServletInputStream getInputStream() throws IOException {
      return data;
    }

    public String getParameter(String name) {
      Vector vals = (Vector)parameters.get(name);
      return vals == null ? null : (String)vals.elementAt(0);
    }

    public String[] getParameterValues(String name) {
      Vector vals = (Vector)parameters.get(name);
      if (vals == null)
        return null;
      else {
        String[] them = new String[vals.size()];
        vals.copyInto(them);
        return them;
      }
    }

    public Enumeration getParameterNames() {
      return parameters.keys();
    }

    public Object getAttribute(String name) {
      return null;
    }

    public BufferedReader getReader () throws IOException {
      return new BufferedReader(new InputStreamReader(data, enc));
    }

    public String getCharacterEncoding () {
      return enc;
    }

    public Cookie[] getCookies() {
      return cookies;
    }

    public String getMethod() {
      return method;
    }

    public String getRequestURI() {
      return requestURI;
    }

    /**
     * Gets the part of this request's URI that refers to the servlet
     * being invoked. Analogous to the CGI variable SCRIPT_NAME.
     *
     * @return the servlet being invoked, as contained in this
     * request's URI
     */
    public String getServletPath() {
      return servletPath;
    }

    public String getPathInfo() {
      return pathInfo;
    }

    public String getPathTranslated() {
      return pathTranslated;
    }

    public String getQueryString() {
      return queryString;
    }

    public String getRemoteUser() {
      return remoteUser;
    }

    public String getAuthType() {
      return null;
    }

    /**
     * Gets the value of the requested header field of this request.
     * The case of the header field name is ignored.
     * 
     * @param name the String containing the name of the requested
     * header field
     * @return the value of the requested header field, or null if not
     * known.
     */
    public String getHeader(String name) {
      return null;
    }

    /**
     * Gets the value of the specified integer header field of this
     * request.  The case of the header field name is ignored.  If the
     * header can't be converted to an integer, the method throws a
     * NumberFormatException.
     * 
     * @param name the String containing the name of the requested
     * header field
     * @return the value of the requested header field, or -1 if not
     * found.
     */
    public int getIntHeader(String name) {
      return -1;
    }

    /**
     * Gets the value of the requested date header field of this
     * request.  If the header can't be converted to a date, the method
     * throws an IllegalArgumentException.  The case of the header
     * field name is ignored.
     * 
     * @param name the String containing the name of the requested
     * header field
     * @return the value the requested date header field, or -1 if not
     * found.
     */
    public long getDateHeader(String name) {
      return -1L;
    }

    /**
     * Gets the header names for this request.
     *
     * @return an enumeration of strings representing the header names
     * for this request. Some server implementations do not allow
     * headers to be accessed in this way, in which case this method
     * will return null.
     */
    public Enumeration getHeaderNames() {
      return null;
    }

    /**
     * Gets the current valid session associated with this request, if
     * create is false or, if necessary, creates a new session for the
     * request, if create is true.
     *
     * <p><b>Note</b>: to ensure the session is properly maintained,
     * the servlet developer must call this method (at least once)
     * before any output is written to the response.
     *
     * <p>Additionally, application-writers need to be aware that newly
     * created sessions (that is, sessions for which
     * <code>HttpSession.isNew</code> returns true) do not have any
     * application-specific state.
     *
     * @return the session associated with this request or null if
     * create was false and no valid session is associated
     * with this request.
     */
    public HttpSession getSession (boolean create) {
      return httpSession;
    }

    /**
     * Gets the session id specified with this request.  This may
     * differ from the actual session id.  For example, if the request
     * specified an id for an invalid session, then this will get a new
     * session with a new id.
     *
     * @return the session id specified by this request, or null if the
     * request did not specify a session id
     * 
     * @see #isRequestedSessionIdValid */
    public String getRequestedSessionId () {
      return httpSession.getId();
    }

    public boolean isRequestedSessionIdValid () {
      return requestedSessionIDValid;
    }

    public boolean isRequestedSessionIdFromCookie () {
      return true;
    }

    public boolean isRequestedSessionIdFromUrl () {
      return false;
    }
}
