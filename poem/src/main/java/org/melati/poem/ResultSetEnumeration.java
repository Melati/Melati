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

package org.melati.poem;

import java.util.NoSuchElementException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.melati.poem.util.SkipEnumeration;

/**
 * An <code>Enumeration</code> created by filtering a 
 * <code>ResultSet</code> according to an abstract 
 * function applied to each element of the <code>ResultSet</code>.
 * <p>
 * Implementations must provide {@link #mapped(ResultSet)}.
 */
public abstract class ResultSetEnumeration<T> implements SkipEnumeration<T> {
  private final ResultSet resultSet;
  private int more = -1;

  /**
   * Constructor.
   * @param resultSet the ResultSet property
   */
  public ResultSetEnumeration(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  private int resultSetNext() throws SQLException {
    if (resultSet.next())
      return 1;
    else {
      try {
        resultSet.close();
      } catch (SQLException e) {
        throw new SQLSeriousPoemException(e);
      }
      return 0;
    }
  }

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#hasMoreElements()
   */
  public synchronized boolean hasMoreElements() {
    try {
      if (more == -1)
        more = resultSetNext();
      return more == 1;
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  protected abstract T mapped(ResultSet resultSetP)
      throws SQLException, NoSuchRowPoemException;

  /**
   * Return the next element.
   * <p>
   * NOTE A {@link RowDisappearedPoemException} might be thrown
   * but does not prevent subsequent use of the object.
   * @see java.util.Enumeration#nextElement()
   */
  public synchronized T nextElement() throws NoSuchElementException {
    try {
      if (more == -1)
        more = resultSetNext();

      if (more == 0)
        throw new NoSuchElementException();

      try {
        return mapped(resultSet);
      }
      finally {
        more = resultSetNext();
      }
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    } catch (NoSuchRowPoemException e) {
      throw new RowDisappearedPoemException(e);
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.SkipEnumeration#skip()
   */
  public synchronized void skip() throws NoSuchElementException {
    try {
      if (more == -1)
        more = resultSetNext();
      if (more == 0)
        throw new NoSuchElementException();
      more = resultSetNext();
    } catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }
}
