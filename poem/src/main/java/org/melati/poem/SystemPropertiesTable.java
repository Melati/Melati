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
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import java.util.*;
import java.sql.Date;
import org.melati.util.*;

public class SystemPropertiesTable extends SystemPropertiesTableBase {

  private static final Object nullEntry = new Object();

  public SystemPropertiesTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  private Hashtable cache = null;
  private long cacheSerial = 0L;

  public String get(String name) {
    if (cache == null || cacheSerial != serial(PoemThread.transaction()))
      cache = new Hashtable();
    Object value = cache.get(name);
    if (value == nullEntry)
      return null;
    else if (value != null)
      return (String)value;
    else {
      SystemProperties prop =
          (SystemProperties)getNameColumn().firstWhereEq(name);
      if (prop == null) {
	cache.put(name, nullEntry);
	return null;
      }
      else {
	String propValue = prop.getValue();
	cache.put(name, propValue == null ? nullEntry : propValue);
	return propValue;
      }
    }
  }
}
