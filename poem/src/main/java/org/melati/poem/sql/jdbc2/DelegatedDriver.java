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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.sql.jdbc2;

import java.sql.*;

public class DelegatedDriver implements Driver {
  protected Driver peer;
  public DelegatedDriver(Driver peer) {
    this.peer = peer;
  }
  public boolean acceptsURL(java.lang.String a) throws java.sql.SQLException {
    return peer.acceptsURL(a);
  }
  public java.sql.Connection connect(java.lang.String a, java.util.Properties b) throws java.sql.SQLException {
    return peer.connect(a, b);
  }
  public int getMajorVersion() {
    return peer.getMajorVersion();
  }
  public int getMinorVersion() {
    return peer.getMinorVersion();
  }
  public java.sql.DriverPropertyInfo[] getPropertyInfo(java.lang.String a, java.util.Properties b) throws java.sql.SQLException {
    return peer.getPropertyInfo(a, b);
  }
  public boolean jdbcCompliant() {
    return peer.jdbcCompliant();
  }
}
