/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Tim Pizey
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
 */

package org.melati.util;

import java.util.Enumeration;

import org.melati.poem.util.EnumUtils;

/**
 * A {@link PagedEnumeration} which knows how big it is.
 * Ideally SQL would allow you to start at an offset.
 */
public class CountedDumbPagedEnumeration<T> extends PagedEnumerationBase<T> {
  
  /**
   * Constructor.
   * @param base underlying Enumeration
   * @param pageStart index of start of page 
   * @param pageSize  how many Elements to include upon a page 
   * @param totalCount size of the underlying Enumeration
   */
  public CountedDumbPagedEnumeration(Enumeration<T> base,
                             int pageStart, int pageSize, int totalCount) {
    pageStart = Math.max(pageStart, 1);
    this.pageStart = pageStart;
    this.pageSize = pageSize;
    this.totalCount = totalCount;
    //NOTE This is the bit that makes it dumb!
    EnumUtils.skip(base, pageStart - 1);
    page = EnumUtils.initial(base, pageSize);
    us = page.elements();
    currentPosition = pageStart - 1; 
  }

  @Override
  public Integer getNextPageStart() {
    int it = pageStart + pageSize;
    return it <= totalCount ? new Integer(it) : null;
  }

  
}
