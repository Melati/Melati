/*
 * $Source$
 * $Revision$
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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.util;

import java.util.*;

public class DumbPageEnumeration implements PageEnumeration {
  
  private int pageStart, pageSize;
  private Vector page;
  private int totalCount;
  private boolean totalCountIsMinimum;
  private Enumeration us;
  private int currentPosition;

  public DumbPageEnumeration(Enumeration base,
                             int pageStart, int pageSize, int countHorizon) {
    this.pageStart = pageStart = Math.max(pageStart, 1);
    this.pageSize = pageSize;
    int c = EnumUtils.skip(base, pageStart - 1);
    page = EnumUtils.initial(base, pageSize);
    totalCount = c + page.size() +
                     EnumUtils.skip(base, countHorizon - (c + page.size()));
    totalCountIsMinimum = base.hasMoreElements();
    us = page.elements();
	currentPosition = pageStart-1; 
  }

  public DumbPageEnumeration(SkipEnumeration base,
                             int pageStart, int pageSize, int countHorizon) {
    this((Enumeration)base, pageStart, pageSize, countHorizon);
  }

  // 
  // -------------
  //  Enumeration
  // -------------
  // 

  public boolean hasMoreElements() {
    return us.hasMoreElements();
  }

  public Object nextElement() {
	currentPosition++;
    return us.nextElement();
  }

  // 
  // -----------------
  //  PageEnumeration
  // -----------------
  // 

  public int getPageStart() {
    return pageStart;
  }

  public int getCurrentPosition() {
    return currentPosition;
  }

  public int getNextPosition() {
    if (hasMoreElements()) return currentPosition+1;
	return 0;
  }
  
  public boolean nextElementOnThisPage() {
	if (hasMoreElements() && getPageEnd() >= getNextPosition()) return true;
	return false;
  }

  public int getPageSize() {
    return page.size();
  }

  public int getPageEnd() {
    return pageStart + page.size() - 1;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public boolean getTotalCountIsMinimum() {
    return totalCountIsMinimum;
  }

  public Integer getPrevPageStart() {
    int it = pageStart - pageSize;
    return it < 0 ? null : new Integer(it);
  }

  public Integer getNextPageStart() {
    int it = pageStart + pageSize;
    return totalCountIsMinimum || it <= totalCount ? new Integer(it) : null;
  }

  public Vector getPageStartList() {
    Vector ret = new Vector(totalCount / pageSize);
    int i=1;
    while((i-1)*pageSize < totalCount) {
      ret.addElement(new Page(i,(i-1)*pageSize+1));
      i++;
    }
    return ret;
  }

  public class Page {
    int number;
    int start;
    public Page(int number, int start) {
      this.number = number;
      this.start = start;
    }
    public int getNumber() {return number;}
    public int getStart() {return start;}
  };

}
