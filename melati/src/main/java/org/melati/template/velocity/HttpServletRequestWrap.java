package org.melati.template.velocity;

/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Tim Joyce <timj@paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletInputStream;

import java.util.Enumeration;
import java.util.Locale;

import java.io.BufferedReader;
import java.io.IOException;

/* 
 * add get method that allows us to $Form.XXXX to access for parameters
 */

public class HttpServletRequestWrap implements HttpServletRequest {
    /*
     *  The object that we are wrapping, set in the CTOR
     */
    private HttpServletRequest req = null;

    /**
     *  CTOR
     *
     *  @param req HttpServletRequest object to wrap
     */
    public HttpServletRequestWrap( HttpServletRequest req )
    {
        this.req = req;
    }

    /**
     *  Accessor to allow one to retrieve the wrapped 
     *  HttpServletRequest.
     *
     *  @return Wrapped HttpServletRequest
     */
    public HttpServletRequest getWrappedObject()
    {
        return req;
    }

    /* --------- HttpServletRequest -----------  */

    public String getAuthType()
    {
        return req.getAuthType();
    }

    public Cookie[] getCookies()
    {
        return req.getCookies();
    }

    public long getDateHeader(String name)
    {
        return req.getDateHeader( name );
    }
   
    public String getHeader(String name)
    {
        return req.getHeader( name );
    }

    public Enumeration getHeaderNames()
    {
        return req.getHeaderNames();
    }

    public int getIntHeader(String name)
    {
        return req.getIntHeader( name );
    }
    
    public String getMethod()
    {
        return req.getMethod();
    }
     
    public String getPathInfo()
    {
        return req.getPathInfo();
    }

    public String getPathTranslated()
    {
        return req.getPathTranslated();
    }

    public String getQueryString()
    {
        return req.getQueryString();
    }

    public String getRemoteUser()
    {
        return req.getRemoteUser();
    }

    public String getRequestedSessionId()
    {
        return req.getRequestedSessionId();
    }
    
    public String getRequestURI()
    {
        return req.getRequestURI();
    }
    
    public String getServletPath()
    {
        return req.getServletPath();
    }
 
    public HttpSession getSession(boolean create)
    {
        return req.getSession( create );
    }

    public boolean isRequestedSessionIdValid()
    {
        return req.isRequestedSessionIdValid();
    }
 
    public boolean isRequestedSessionIdFromCookie()
    {
        return req.isRequestedSessionIdFromCookie();
    }

    /**
     *  @deprecated
     */
    public boolean isRequestedSessionIdFromUrl()
    {
        return req.isRequestedSessionIdFromUrl();
    }

    /* ----------------  ServletRequest -------------- */


    public Object getAttribute(String name)
    {
        return req.getAttribute( name );
    }

    public String getCharacterEncoding()
    {
        return req.getCharacterEncoding();
    }

    public int getContentLength()
    {
        return req.getContentLength();
    }

    public String getContentType()
    {
        return req.getContentType();
    }
    
    public ServletInputStream getInputStream() 
        throws IOException
    {
        return req.getInputStream();
    }
     
    public String getParameter(String name)
    {
        return req.getParameter( name );
    }

    public Enumeration getParameterNames()
    {
        return req.getParameterNames();
    }

    public String[] getParameterValues(String name)
    {
        return req.getParameterValues( name );
    }

  
    public String getProtocol()
    {
        return req.getProtocol();
    }

    public String getScheme()
    {
        return req.getScheme();
    }

    public String getServerName()
    {
        return req.getServerName();
    }
 
    public int getServerPort()
    {
        return req.getServerPort();
    }
    
    public BufferedReader getReader() 
        throws IOException
    {
        return req.getReader();
    }

    public String getRemoteAddr()
    {
        return req.getRemoteAddr();
    }

    public String getRemoteHost()
    {
        return req.getRemoteHost();
    }
   
    /**
     */
    public String get( String name )
    {
        return getParameter( name );
    }
   
    /**
     *  @deprecated
     */
    public String getRealPath( String path )
    {
        return req.getRealPath( path );
    }
}
