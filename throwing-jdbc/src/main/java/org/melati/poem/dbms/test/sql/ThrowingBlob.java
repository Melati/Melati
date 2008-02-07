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
import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author timp
 * @since 5 Feb 2008
 *
 */
public class ThrowingBlob extends ThrowingBlobVariant implements Blob {


  /**
   * Constructor.
   */
  public ThrowingBlob(Blob b) {
    it = b;
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#getBinaryStream()
   */
  public InputStream getBinaryStream() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBinaryStream"))
      throw new SQLException("Blob bombed");
    return it.getBinaryStream();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#getBytes(long, int)
   */
  public byte[] getBytes(long pos, int length) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "getBytes"))
      throw new SQLException("Blob bombed");
    return it.getBytes(pos, length);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#length()
   */
  public long length() throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "length"))
      throw new SQLException("Blob bombed");
    return it.length();
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#position(byte[], long)
   */
  public long position(byte[] pattern, long start) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "position"))
      throw new SQLException("Blob bombed");
    return it.position(pattern, start);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#position(java.sql.Blob, long)
   */
  public long position(Blob pattern, long start) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "position"))
      throw new SQLException("Blob bombed");
    return it.position(pattern, start);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#setBinaryStream(long)
   */
  public OutputStream setBinaryStream(long pos) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setBinaryStream"))
      throw new SQLException("Blob bombed");
    return it.setBinaryStream(pos);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#setBytes(long, byte[])
   */
  public int setBytes(long pos, byte[] bytes) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setBytes"))
      throw new SQLException("Blob bombed");
    return it.setBytes(pos, bytes);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#setBytes(long, byte[], int, int)
   */
  public int setBytes(long pos, byte[] bytes, int offset, int len)
          throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "setBytes"))
      throw new SQLException("Blob bombed");
    return it.setBytes(pos, bytes, offset, len);
  }

  /** 
   * {@inheritDoc}
   * @see java.sql.Blob#truncate(long)
   */
  public void truncate(long len) throws SQLException {
    if (shouldThrow(this.getClass().getInterfaces()[0], "truncate"))
      throw new SQLException("Blob bombed");
    it.truncate(len);
  }

}
