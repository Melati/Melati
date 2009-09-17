/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2009 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.servlet.test;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author timp
 * @since  26 Feb 2009
 *
 */
@SuppressWarnings("deprecation")
public class MockHttpSession implements HttpSession {
  
  private Hashtable<String,Object> values;

  /**
   * Constructor.
   */
  public MockHttpSession() {
    values = new Hashtable<String,Object>();
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
   */
  public Object getAttribute(String name) {
    return values.get(name);
    }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getAttributeNames()
   */
  public Enumeration<String> getAttributeNames() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getCreationTime()
   */
  public long getCreationTime() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getId()
   */
  public String getId() {
    return "1";
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getLastAccessedTime()
   */
  public long getLastAccessedTime() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
   */
  public int getMaxInactiveInterval() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getServletContext()
   */
  public ServletContext getServletContext() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getSessionContext()
   */
  public HttpSessionContext getSessionContext() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
   */
  public Object getValue(String name) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#getValueNames()
   */
  public String[] getValueNames() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#invalidate()
   */
  public void invalidate() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#isNew()
   */
  public boolean isNew() {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
   */
  public void putValue(String name, Object value) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
   */
  public void removeAttribute(String name) {
    values.remove(name);
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
   */
  public void removeValue(String name) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
   */
  public void setAttribute(String name, Object value) {
    values.put(name, value);
  }

  /**
   * {@inheritDoc}
   * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
   */
  public void setMaxInactiveInterval(int interval) {
    throw new RuntimeException("TODO No one else has ever called this method."
        + " Do you really want to start now?");

  }

}
