/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */
package org.melati.poem.transaction;

import java.util.Vector;
import java.util.Enumeration;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.melati.poem.PoemBugPoemException;

/**
 * List of objects which need closing when a <code>Transaction</code> is
 * terminated.
 */
public class ToTidyList {

  /** Can be closed. */
  public interface Closeable {
    /**
     * Free any resources and prepare for death or reuse.
     */
    void close();
  }

  private Vector<Object> objects = new Vector<Object>();

  private static void tidy(Object o) {
    try {
      if (o instanceof ResultSet) 
        try { 
          ((ResultSet)o).close();
        } catch (SQLException e) {
          // HACK MSAccess not playing nice
          if (!e.getMessage().equals("ResultSet is closed"))
            throw e;
        }
          
      else if (o instanceof Statement)
        ((Statement)o).close();
      else if (o instanceof Reader)
        ((Reader)o).close();
      else if (o instanceof Writer)
        ((Writer)o).close();
      else if (o instanceof InputStream)
        ((InputStream)o).close();
      else if (o instanceof OutputStream)
        ((OutputStream)o).close();
      else if (o instanceof Closeable)
        ((Closeable)o).close();
    } catch (Exception e) {
      throw new PoemBugPoemException("Unexpected object type in ToTidy: " + o.getClass(), e);
    }
  }

  /**
   * Close all objects on list.
   */
  public synchronized void close() {
    for (int i = objects.size() - 1; i >= 0; --i)
      tidy(objects.elementAt(i));

    objects.setSize(0);
  }

  private void addObject(Object o) {
    objects.addElement(o);
  }

  /**
   * Add a ResultSet to the list.
   * @param o the ResultSet to add
   */
  public void add(ResultSet o) {
    addObject(o);
  }

  /**
   * Add a Statement to the list.
   * @param o the Statement to add
   */
  public void add(Statement o) {
    addObject(o);
  }

  /**
   * Add a Reader to the list.
   * @param o the Reader to add
   */
  public void add(Reader o) {
    addObject(o);
  }

  /**
   * Add a Writer to the list.
   * @param o the Writer to add
   */
  public void add(Writer o) {
    addObject(o);
  }

  /**
   * Add an InputStream to the list.
   * @param o the InputStream to add
   */
  public void add(InputStream o) {
    addObject(o);
  }

  /**
   * Add an OutputStream to the list.
   * @param o the OutputStream to add
   */
  public void add(OutputStream o) {
    addObject(o);
  }

  /**
   * Add any Closeable to the list.
   * @param o the Closeable to add
   */
  public void add(Closeable o) {
    addObject(o);
  }

  /**
   * @return an Enumeration of the items on the list
   */
  public Enumeration<Object> elements() {
    return objects.elements();
  }
}
