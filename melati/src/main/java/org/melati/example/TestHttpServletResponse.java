package org.melati.doc.example;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TestHttpServletResponse implements HttpServletResponse {
  ServletOutputStream os;

  TestHttpServletResponse(String fn) throws IOException {
    os = new TestServletOutputStream(fn);
  }

    public void setContentLength(int len) {
    }

    public void setContentType(String type) {
    }

    public ServletOutputStream getOutputStream() throws IOException {
      return os;
    }

    public PrintWriter getWriter () throws IOException {
      return new PrintWriter(os);
    }

    public String getCharacterEncoding () {
      return "text/plain";
    }

    public void addCookie(Cookie cookie) {
    }

    public boolean containsHeader(String name) {
      return false;
    }

    public void setStatus(int sc, String sm) {
    }

    public void setStatus(int sc) {
    }

    public void setHeader(String name, String value) {
    }

    public void setIntHeader(String name, int value) {
    }

    public void setDateHeader(String name, long date) {
    }

    public void sendError(int sc, String msg) throws IOException {
    }

    public void sendError(int sc) throws IOException {
    }

    public void sendRedirect(String location) throws IOException {
    }

    public String encodeUrl (String url) {
      throw new RuntimeException("bugger");
    }

    public String encodeRedirectUrl (String url) {
      throw new RuntimeException("bugger");
    }
}
