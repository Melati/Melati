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

public class DbmsFactory {

    private static final Hashtable dbmsObjects = new Hashtable();

    // FIXME
    // What horrible exception handling. We need a non SQL Exception here
    public static final Dbms getDbms(String dbmsClass) throws ConnectionFailurePoemException {
        synchronized (dbmsObjects) {
            try {
                Dbms dbms = (Dbms)dbmsObjects.get(dbmsClass);
                if (dbms != null) {
                    return dbms;
                }

                Object dbmsObject = Class.forName(dbmsClass).newInstance();

                if (!(dbmsObject instanceof Dbms)) {
                    throw new ClassCastException(
                                                "The .class=" + dbmsClass + " entry named a class of type " +
                                                dbmsObject.getClass() + ", " +
                                                "which is not an org.melati.poem.dbms.Dbms");
                }

                dbms = (Dbms)dbmsObject;
                dbmsObjects.put(dbmsClass, dbms);
                return dbms;
            } catch (Exception e) {
                throw new ConnectionFailurePoemException( new SQLException(e.getMessage()));
            }
        }

    }
}

