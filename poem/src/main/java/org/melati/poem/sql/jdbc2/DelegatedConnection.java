/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense. 
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem.sql.jdbc2;

import java.sql.*;

public class DelegatedConnection implements Connection {
  protected Connection peer;
  public DelegatedConnection(Connection peer) {
    this.peer = peer;
  }
  public void clearWarnings() throws java.sql.SQLException {
    peer.clearWarnings();
  }
  public void close() throws java.sql.SQLException {
    peer.close();
  }
  public void commit() throws java.sql.SQLException {
    peer.commit();
  }
  public java.sql.Statement createStatement() throws java.sql.SQLException {
    return peer.createStatement();
  }
  public java.sql.Statement createStatement(int a, int b) throws java.sql.SQLException {
    return peer.createStatement(a, b);
  }
  public boolean getAutoCommit() throws java.sql.SQLException {
    return peer.getAutoCommit();
  }
  public java.lang.String getCatalog() throws java.sql.SQLException {
    return peer.getCatalog();
  }
  public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
    return peer.getMetaData();
  }
  public int getTransactionIsolation() throws java.sql.SQLException {
    return peer.getTransactionIsolation();
  }
  public java.util.Map getTypeMap() throws java.sql.SQLException {
    return peer.getTypeMap();
  }
  public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
    return peer.getWarnings();
  }
  public boolean isClosed() throws java.sql.SQLException {
    return peer.isClosed();
  }
  public boolean isReadOnly() throws java.sql.SQLException {
    return peer.isReadOnly();
  }
  public java.lang.String nativeSQL(java.lang.String a) throws java.sql.SQLException {
    return peer.nativeSQL(a);
  }
  public java.sql.CallableStatement prepareCall(java.lang.String a) throws java.sql.SQLException {
    return peer.prepareCall(a);
  }
  public java.sql.CallableStatement prepareCall(java.lang.String a, int b, int c) throws java.sql.SQLException {
    return peer.prepareCall(a, b, c);
  }
  public java.sql.PreparedStatement prepareStatement(java.lang.String a) throws java.sql.SQLException {
    return peer.prepareStatement(a);
  }
  public java.sql.PreparedStatement prepareStatement(java.lang.String a, int b, int c) throws java.sql.SQLException {
    return peer.prepareStatement(a, b, c);
  }
  public void rollback() throws java.sql.SQLException {
    peer.rollback();
  }
  public void setAutoCommit(boolean a) throws java.sql.SQLException {
    peer.setAutoCommit(a);
  }
  public void setCatalog(java.lang.String a) throws java.sql.SQLException {
    peer.setCatalog(a);
  }
  public void setReadOnly(boolean a) throws java.sql.SQLException {
    peer.setReadOnly(a);
  }
  public void setTransactionIsolation(int a) throws java.sql.SQLException {
    peer.setTransactionIsolation(a);
  }
  public void setTypeMap(java.util.Map a) throws java.sql.SQLException {
    peer.setTypeMap(a);
  }
}
