/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 David Warnock
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
 *     David Warnock (david@sundayta.co.uk)
 *     Sundayta Ltd
 *     International House, 174 Three Bridges Road, Crawley, West Sussex RH10 1LE, UK
 *
 */

package org.melati.poem.dbms;

import java.util.*;
import java.sql.*;
import org.melati.poem.*;
import org.melati.util.*;

public class AnsiStandard implements Dbms {
    private boolean driverLoaded = false;
    private String driverClassName = null;
    private Driver driver = null;

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
        Class driverClass;
        try {
            driverClass = Class.forName (getDriverClassName());
            setDriverLoaded(true);
        } catch (java.lang.ClassNotFoundException e) {
            // A call to Class.forName() forces us to consider this exception :-)...
            setDriverLoaded(false);
            return;
        }

        try {
            driver = (Driver)driverClass.newInstance();
        } catch (java.lang.Exception e) {
            // ... otherwise, "something went wrong" and I don't here care what
            // or have the wherewithal to do anything about it :)
            throw new UnexpectedExceptionPoemException(e);
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


        if (driver != null) {
            Properties info = new Properties();
            if (user != null) info.put("user", user);
            if (password != null) info.put("password", password);

            try {
                return driver.connect(url, info);
            } catch (SQLException e) {
                throw new ConnectionFailurePoemException(e);
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
