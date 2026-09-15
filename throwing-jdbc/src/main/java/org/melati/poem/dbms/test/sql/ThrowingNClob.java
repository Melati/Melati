/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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

package org.melati.poem.dbms.test.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;

/**
 * @author timp
 * @since 5 Feb 2008
 *
 */
public class ThrowingNClob extends Thrower implements NClob {
  
  NClob it = null;

  /**
   * Constructor.
   */
  public ThrowingNClob(NClob nc) {
    it = nc;
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#free()
   */

  public void free() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "free"))
      throw new SQLException("NClob bombed");
    it.free();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#getAsciiStream()
   */

  public InputStream getAsciiStream() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getAsciiStream"))
      throw new SQLException("NClob bombed");
    return it.getAsciiStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#getCharacterStream()
   */

  public Reader getCharacterStream() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCharacterStream"))
      throw new SQLException("NClob bombed");
    return it.getCharacterStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#getCharacterStream(long, long)
   */

  public Reader getCharacterStream(long pos, long length) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getCharacterStream"))
      throw new SQLException("NClob bombed");
    return it.getCharacterStream(pos, length);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#getSubString(long, int)
   */

  public String getSubString(long pos, int length) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getSubString"))
      throw new SQLException("NClob bombed");
    return it.getSubString(pos, length);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#length()
   */

  public long length() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "length"))
      throw new SQLException("NClob bombed");
    return it.length();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#position(java.lang.String, long)
   */

  public long position(String searchstr, long start) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "position"))
      throw new SQLException("NClob bombed");
    return it.position(searchstr, start);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#position(java.sql.Clob, long)
   */

  public long position(Clob searchstr, long start) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "position"))
      throw new SQLException("NClob bombed");
    return it.position(searchstr, start);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#setAsciiStream(long)
   */

  public OutputStream setAsciiStream(long pos) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setAsciiStream"))
      throw new SQLException("NClob bombed");
    return it.setAsciiStream(pos);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#setCharacterStream(long)
   */

  public Writer setCharacterStream(long pos) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setCharacterStream"))
      throw new SQLException("NClob bombed");
    return it.setCharacterStream(pos);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#setString(long, java.lang.String)
   */

  public int setString(long pos, String str) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setString"))
      throw new SQLException("NClob bombed");
    return it.setString(pos, str);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#setString(long, java.lang.String, int, int)
   */

  public int setString(long pos, String str, int offset, int len)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setString"))
      throw new SQLException("NClob bombed");
    return it.setString(pos, str, offset, len);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Clob#truncate(long)
   */

  public void truncate(long len) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "truncate"))
      throw new SQLException("NClob bombed");
    it.truncate(len);
  }

}
