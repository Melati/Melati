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

import java.sql.SQLException;

public class Interbase extends AnsiStandard {
    public Interbase() {
        setDriverClassName("interbase.interclient.Driver");
    }

    public String getQuotedName(String name) {
        return "q" + name;
    }

    public String getSqlDefinition(String sqlTypeName) throws SQLException {
        if (sqlTypeName.equals("BOOLEAN")) {
            return ("INT");
        }
        return super.getSqlDefinition(sqlTypeName);
    }
    
    public String getStringSqlDefinition(int size) throws SQLException {
        if (size < 0) { 
            return "blob sub_type 1";
        }
        return super.getStringSqlDefinition(size);
    }

    public String getBinarySqlDefinition(int size) throws SQLException {
        if (size < 0) { 
            throw new SQLException(
              "FIXME don't know what to call unlimited length binary in " +
              "Interbase: someone find out?");
        }
        return super.getBinarySqlDefinition(size);
    }
}
