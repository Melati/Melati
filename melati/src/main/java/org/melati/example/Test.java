package org.melati.doc.example;

import java.util.*;
import java.io.*;
import org.melati.*;
import org.melati.poem.*;
import org.melati.admin.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

class TestServletInputStream extends ServletInputStream {
  InputStream is;
  TestServletInputStream(String fn) throws IOException {
    is = new FileInputStream(fn);
  }
  public int read() throws IOException {
    return is.read();
  }
  public int read(byte b[], int off, int len) throws IOException {
    return is.read(b, off, len);
  }
  public int available() throws IOException {
    return is.available();
  }
  public void close() throws IOException {
    is.close();
  }
  public void mark(int readlimit) {
    is.mark(readlimit);
  }
  public void reset() throws IOException {
    is.reset();
  }
  public boolean markSupported() {
    return is.markSupported();
  }
}

class TestHttpSession implements HttpSession {

  String id = "1234";
  long creationTime = System.currentTimeMillis();
  long lastAccessTime = creationTime;
  Hashtable values;
  boolean isNew = true;

   public String getId () {
     return id;
   }

   public HttpSessionContext getSessionContext () {
     return null;
   }

   public long getCreationTime () {
     return creationTime;
   }

   public long getLastAccessedTime () {
     return lastAccessTime;
   }

   public void invalidate () {
   }

   public void putValue (String name, Object value) {
     values.put(name, value);
   }

   public Object getValue (String name) {
     return values.get(name);
   }

   public void removeValue (String name) {
     values.remove(name);
   }

   public String [] getValueNames () {
     return new String[0];
   }

   public boolean isNew () {
     return isNew;
   }

}

class TestServletOutputStream extends ServletOutputStream {

  OutputStream os;

  TestServletOutputStream(String fn) throws IOException {
    os = new BufferedOutputStream(new FileOutputStream(fn));
  }

  public void write(int b) throws IOException {
    os.write(b);
  }

  public void write(byte b[], int off, int len) throws IOException {
    os.write(b, off, len);
  }

  public void flush() throws IOException {
    os.flush();
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    DriverManager.registerDriver((Driver)Class.forName("postgresql.Driver").newInstance());

    Admin servlet = new Admin();

    servlet.init();
    ServletRequest request = new TestHttpServletRequest(args[0], args[1], "testdata");
    ServletResponse response = new TestHttpServletResponse("/dev/tty");
    servlet.service(request, response);
  }
}
