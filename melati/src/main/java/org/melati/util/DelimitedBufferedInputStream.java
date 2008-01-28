/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Myles Chippendale
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
 *     Myles Chippendale <mylesc At paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

package org.melati.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Like a <code>BufferedInputStream</code> except it has a new function
 * {@link #readToDelimiter} which will only read bytes upto the start
 * of any occurrence of the delimiter in the InputStream.
 *
 * @see java.io.BufferedInputStream
 */

public class DelimitedBufferedInputStream extends BufferedInputStream {

    private int potentialMatch = -1;

    /**
     * Creates a <code>DelimitedBufferedInputStream</code>
     * and saves its  argument, the input stream
     * <code>in</code>, for later use. An internal
     * buffer array is created and  stored in <code>buf</code>.
     *
     * @param   in   the underlying input stream.
     **/
    public DelimitedBufferedInputStream(InputStream in) {
       super(in);
    }

    /**
     * Creates a <code>DelimitedBufferedInputStream</code>
     * with the specified buffer size,
     * and saves its  argument, the input stream
     * <code>in</code>, for later use.  An internal
     * buffer array of length  <code>size</code>
     * is created and stored in <code>buf</code>.
     *
     * @param   in     the underlying input stream.
     * @param   size   the buffer size.
     **/
    public DelimitedBufferedInputStream(InputStream in, int size) {
      super(in, size);
    }

    /**
     * Fills the buffer with more data, taking into account
     * shuffling and other tricks for dealing with marks.
     * Assumes that it is being called by a synchronized method.
     * This method also assumes that all data has already been read in,
     * hence pos > count.
     * <p>
     * This code is copy'n'pasted from BufferedInputString (it's private)
     **/
    private void fill() throws IOException {
      if (markpos < 0)
           pos = 0;                 /* no mark: throw away the buffer */
      else if (pos >= buf.length)   /* no room left in buffer */
           if (markpos > 0) {    /* can throw away early part of the buffer */
               int sz = pos - markpos;
               System.arraycopy(buf, markpos, buf, 0, sz);
               pos = sz;
               markpos = 0;
            }
            else if (buf.length >= marklimit) {
               markpos = -1;    /* buffer got too big, invalidate mark */
               pos = 0;         /* drop buffer contents */
            }
            else {              /* grow buffer */
               int nsz = pos * 2;
               if (nsz > marklimit)
                   nsz = marklimit;
               byte nbuf[] = new byte[nsz];
               System.arraycopy(buf, 0, nbuf, 0, pos);
               buf = nbuf;
            }

      count = pos;
      int n = in.read(buf, pos, buf.length - pos);
      if (n > 0)
        count = n + pos;
    }

    /**
     * Check to make sure that this stream has not been closed.
     * <p>
     * This code is copy'n'pasted from BufferedInputString (it's private)
     **/
    private void ensureOpen() throws IOException {
      if (in == null)
        throw new IOException("Stream closed");
    }

    /**
     * Read characters into a portion of an array, reading from the underlying
     * stream at most twice if necessary.
     **/
    private int readToDelimiter1(byte[] b, int off, int len, byte delim[])
          throws IOException {
      int avail = count - pos;
      if (avail <= 0) {
          fill();
          avail = count - pos;
          if (avail <= 0) return -1;
      }
      int cnt = (avail < len) ? avail : len;

      // indexOf sets potentialMatch
      int found = indexOf(buf, delim, pos, avail);
      int max = cnt;
      if (found != -1)
        max = found - pos;
      else if (potentialMatch != -1)
        max = potentialMatch - pos;

      cnt = (max < cnt) ? max : cnt;
      System.arraycopy(buf, pos, b, off, cnt);
      pos += cnt;

      /* We want to shuffle the buffer so that it contains
       * at least delim.length bytes after potentialMatch,
       * if we have read that far into the buffer 
       */
      if (found == -1 && potentialMatch != -1 && pos == potentialMatch) {

        /* the number of bytes to shift the buffer down by */
        int halfmark = (markpos == -1)
                       ? potentialMatch
                       : markpos;

        /* 
           the number of bytes we need to read to get all our potential
           delim into the buffer 
         */
        int extras = potentialMatch + delim.length - count;

        /* the number of bytes we want to end up in buf */
        int nsz = potentialMatch + delim.length - halfmark;
        if (nsz > buf.length) {
          // we need to grow the buffer
          byte nbuf[] = new byte[nsz];
          System.arraycopy(buf, halfmark, nbuf, 0, count - halfmark);
          buf = nbuf;
        } else {
          System.arraycopy(buf, halfmark, buf, 0, count - halfmark);
        }

        pos = pos - halfmark;
        count = count - halfmark;
        markpos = (markpos == -1) ? -1 : 0;
        int n = in.read(buf, count, buf.length - count);
        if (n > 0)
          count += n;

        /* If, after reading, we don't have enough bytes to match delim,
           add the new bytes to our output */
        if (n < extras) {
          int unfilled = len - cnt;
          if (unfilled > 0) {
            avail = count - pos;
            int cnt2 = (unfilled < avail) ? unfilled : avail;
            System.arraycopy(buf, pos, b, off+cnt, cnt2);
            cnt += cnt2;
            pos += cnt2;
          }
        }
      }
      return cnt;
    }

    /**
     * Reads bytes from this byte-input stream into the specified byte array,
     * starting at the given offset, but we stop at the first occurrence of
     * delim.
     *
     * <p> This method implements the general contract of the corresponding
     * <code>{@link InputStream#read(byte[], int, int) read}</code> method of
     * the <code>{@link InputStream}</code> class.  As an additional
     * convenience, it attempts to read as many bytes as possible by repeatedly
     * invoking the <code>read</code> method of the underlying stream.  This
     * iterated <code>read</code> continues until one of the following
     * conditions becomes true: <ul>
     *
     *   <li> The specified number of bytes have been read,
     *
     *   <li> The <code>read</code> method of the underlying stream returns
     *   <code>-1</code>, indicating end-of-file, or
     *
     *   <li> The <code>available</code> method of the underlying stream
     *   returns zero, indicating that further input requests would block.
     *
     *   <li> We encounter an occurrence of delim in the underlying stream
     *
     * </ul> If the first <code>read</code> on the underlying stream returns
     * <code>-1</code> to indicate end-of-file then this method returns
     * <code>-1</code>.  Otherwise this method returns the number of bytes
     * actually read.
     *
     * <p> Subclasses of this class are encouraged, but not required, to
     * attempt to read as many bytes as possible in the same fashion.
     *
     * @param      b     destination buffer.
     * @param      off   offset at which to start storing bytes.
     * @param      len   maximum number of bytes to read.
     * @param      delim the delimiter which we should stop reading at.
     * @return     the number of bytes read, or <code>-1</code> if the end of
     *             the stream has been reached.
     * @exception  IOException  if an I/O error occurs.
     **/
  public synchronized int readToDelimiter(byte b[], int off, 
                                          int len, byte delim[])
        throws IOException  {
    ensureOpen();
    if ((off < 0) || (off > b.length) || (len < 0) ||
            ((off + len) > b.length) || ((off + len) < 0)) {
        throw new IndexOutOfBoundsException();
    } else if (len == 0) {
        return 0;
    }

    int n = readToDelimiter1(b, off, len, delim);
    if (n <= 0) return n;
    while ((n < len) && (in.available() > 0)) {
      int n1 = readToDelimiter1(b, off + n, len - n, delim);
      if (n1 <= 0) break;
      n += n1;
    }
    return n;
  }

    /**
     * Calculates length.
     * <p>
     * Returns the index within data1 of the
     * byte array data2. It starts looking in data1 at fromIndex and only
     * considers len bytes of data1
     *  
     * @param      data1 array to look in.
     * @param      data2 array to look for.
     * @param      fromIndex where to start looking from in data1.
     * @return the index of data2 within data1 or
     *         -1 if data2 does not occur within data1
     */
    public int indexOf(byte[] data1, byte[] data2, int fromIndex) {
      if (fromIndex < 0)
        fromIndex = 0;
      return indexOf(data1, data2, fromIndex, data1.length - fromIndex);
    }

    /**
     * Returns the index within data1 of the
     * byte array data2. It starts looking in data1 at fromIndex and only
     * considers len bytes of data1
     *
     * @param      data1 array to look in.
     * @param      data2 array to look for.
     * @param      fromIndex where to start looking from in data1.
     * @param      len   maximum number of bytes of data1 to look in.
     * @return the index of data2 within data1 or
     *         -1 if data2 does not occur within data1
     **/
    public int indexOf(byte[] data1, byte[] data2, int fromIndex, int len) {
      byte[] v1 = data1;
      byte[] v2 = data2;
      if (len <= 0)
        return -1;
      if (fromIndex >= data1.length) {
        if (data1.length == 0 && fromIndex == 0 && data2.length == 0)
          return 0;
        return -1;
      }
      if (fromIndex < 0)
        fromIndex = 0;
      if (data2.length == 0)
        return fromIndex;
      int end = (fromIndex + len > data1.length) ? 
                           data1.length : fromIndex + len;
      int maxStart = end - data2.length;
      byte first = v2[0];
      int i = fromIndex;

      startSearchForFirstByte:
      while (true) {
        potentialMatch = -1;
        while (i < end && v1[i] != first)
          i++;
        if (i >= end)
          return -1;
        if (i > maxStart) {  // we might find an initial segment of delim
          potentialMatch = i;
        }
        int j = i + 1;
        int last = (potentialMatch == -1) ? i + data2.length : data1.length;
        int k = 1;
        while (j < last) {
          if (v1[j++] != v2[k++]) {
            i++;
            continue startSearchForFirstByte;
          }
        }
        return (potentialMatch == -1) ? i : -1;
      }
    }


    /**
     * Used in tests.
     * @return the buffer length
     */
    public int getBufferLength() { 
      return buf.length;
    }
    /**
     * Used in tests.
     * @return the potentialMatch
     */
    public int getPotentialMatch() {
      return potentialMatch;
    }

}
