/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 Sundayta Ltd
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
 *     David Warnock (david@sundayta.co.uk)
 *     Sundayta Ltd
 *     International House, 174 Three Bridges Road, Crawley, West Sussex RH10 1LE, UK
 *
 */

package org.melati.poem.dbms;
import java.sql.*;

import org.melati.poem.ConnectionFailurePoemException;
import org.melati.util.StringUtils;
import java.sql.*;

public class AnsiStandard implements Dbms {
    private boolean driverLoaded = false;
    private String driverClassName = null;

    protected synchronized void setDriverClassName(String name) {
        driverClassName = name;
    }
    protected synchronized String getDriverClassName() {
        return driverClassName;
    }

    protected synchronized void setDriverLoaded(boolean loaded) {
        driverLoaded = loaded;
    }
    protected synchronized boolean getDriverLoaded() {
        return driverLoaded;
    }

    protected synchronized void loadDriver() {
        try {
            Class.forName (getDriverClassName());
            setDriverLoaded(true);
        } catch (java.lang.ClassNotFoundException e) {
            // A call to Class.forName() forces us to consider this exception :-)...
            setDriverLoaded(false);
        }

    }

    public Connection getConnection(String url, String user, String password) throws ConnectionFailurePoemException {
        synchronized (driverClassName) {
            if ( !getDriverLoaded() ) {
                if (getDriverClassName() == null) {
                    throw new ConnectionFailurePoemException(new SQLException("No Driver Classname set in dbms specific class"));
                }
                loadDriver();
            }
            if ( !getDriverLoaded() ) {
                throw new ConnectionFailurePoemException(new SQLException("The Driver class " + getDriverClassName() + " failed to load"));
            }
        }


        try {
            return DriverManager.getConnection (url, user, password);
        } catch (java.sql.SQLException e) {
            throw new ConnectionFailurePoemException(e);
        }
    }

    public String getQuotedName(String name) {
      StringBuffer b = new StringBuffer();
      StringUtils.appendQuoted(b, name, '"');
      return b.toString();
    }

    public String getSqlDefinition(String sqlTypeName) throws SQLException {
        return sqlTypeName;
    }

    public String getStringSqlDefinition(int size) throws SQLException {
        if (size < 0) { 
            throw new SQLException("0 length not supported in AnsiStandard Strings");
        }
        return "VARCHAR(" + size + ")";
    }


}
