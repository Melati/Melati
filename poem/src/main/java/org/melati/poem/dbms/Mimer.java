/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2002 Tim Pizey
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
 *     Tim Pizey (timp@paneris.org)
 *
 */


package org.melati.poem.dbms;

import org.melati.poem.PoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.BooleanPoemType;
import org.melati.poem.IntegerPoemType;

/**
 * A Driver for Mimer ( NOT WORKING YET!!!)
 */
public class Mimer extends AnsiStandard {

  public Mimer() {
     setDriverClassName("com.mimer.jdbc.Driver");
  }

  public String getStringSqlDefinition(int size)  {
    if (size < 0) return "VARCHAR(2500)";
    return "VARCHAR(" + size + ")";
  }

  public PoemType canRepresent(PoemType storage, PoemType type) {
    if (storage instanceof StringPoemType &&
        type instanceof StringPoemType) {

        if (((StringPoemType)storage).getSize() == 2500 &&
            ((StringPoemType)type).getSize() == -1) {
           return type;
        } else {
           return storage.canRepresent(type);
        }
    } else if (storage instanceof IntegerPoemType &&
          type instanceof BooleanPoemType) {
        return type;
    } else {
      return storage.canRepresent(type);
    }
  }



  public String getSqlDefinition(String sqlTypeName) {
    if (sqlTypeName.equals("BOOLEAN")) {
          return ("INT");
    }
    return super.getSqlDefinition(sqlTypeName);
  }


}



