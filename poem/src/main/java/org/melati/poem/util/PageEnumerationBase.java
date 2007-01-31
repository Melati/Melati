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

package org.melati.poem.util;

import java.util.Vector;
import java.util.Enumeration;


/**
 * All the code common to {@link CountedDumbPageEnumeration}
 * and {@link DumbPageEnumeration}.
 */
public abstract class PageEnumerationBase implements PageEnumeration {
  
  int pageStart, pageSize;
  Vector page;
  int totalCount;
  Enumeration us;
  int currentPosition;


  // 
  // -------------
  //  Enumeration
  // -------------
  // 

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#hasMoreElements()
   */
  public boolean hasMoreElements() {
    return us.hasMoreElements();
  }

  /**
   * {@inheritDoc}
   * @see java.util.Enumeration#nextElement()
   */
  public Object nextElement() {
    currentPosition++;
    return us.nextElement();
  }

  // 
  // -----------------
  //  PageEnumeration
  // -----------------
  // 
  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.PageEnumeration#getPageStart()
   */
  public int getPageStart() {
    return pageStart;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.PageEnumeration#getPageEnd()
   */
  public int getPageEnd() {
    return pageStart + page.size() - 1;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.PageEnumeration#getTotalCount()
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.PageEnumeration#getNextPageStart()
   */
  public Integer getNextPageStart() {
    int it = pageStart + pageSize;
    return it <= totalCount ? new Integer(it) : null;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.util.PageEnumeration#getPrevPageStart()
   */
  public Integer getPrevPageStart() {
    int it = pageStart - pageSize;
    return it < 0 ? null : new Integer(it);
  }

  // 
  // Our methods
  // 
  
  /**
   * @return where we are in the sequence
   */
  public int getCurrentPosition() {
    return currentPosition;
  }

  /**
   * @return the position of the next element in the sequence
   */
  public int getNextPosition() {
    return hasMoreElements() ? currentPosition + 1 : 0;
  }
  
  /**
   * @return whether there are more elements on this page
   */
  public boolean nextElementOnThisPage() {
    return hasMoreElements() && getPageEnd() >= getNextPosition();
  }

  /**
   * @return the number of elements on a page
   */
  public int getPageSize() {
    return page.size();
  }



  /**
   * @return the start position of page
   */
  public Vector getPageStartList() {
    Vector ret = new Vector(totalCount / pageSize);
    int i = 1;
    while((i - 1)*pageSize < totalCount) {
      ret.addElement(new Page(i,(i - 1)*pageSize+1));
      i++;
    }
    return ret;
  }


/**
 * A 'screen full' of results.
 */
  public class Page {
    int number;
    int start;
    
    /**
     * Constructor.
     * @param number page number in collection 
     * @param start position in collection of first item on page
     */
    public Page(int number, int start) {
      this.number = number;
      this.start = start;
    }
    /**
     * @return the page number 
     */
    public int getNumber() {
      return number;
    }
    /**
     * @return the starting position of the page
     */
    public int getStart() {
      return start; 
    }
  }

}
