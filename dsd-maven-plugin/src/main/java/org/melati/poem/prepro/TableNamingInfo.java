/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc@paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, UK
 */

package org.melati.poem.prepro;

public class TableNamingInfo {

  public final static String POEM = "org.melati.poem";

  /** The fully qualified name of the table (e.g. `org.melati.poem.User') */
  public String tableFQName = null;

  /** The last part of the FQName of the table (e.g. `User') */
  public String tableShortName = null;

  /** The TableNamingInfo of this Table's superclass (or null if extends
      Persistent) */
  public TableNamingInfo superclass = null;

  /** Does this Table have the same name as another table "higher up" in our
      hierarchy (i.e. a table already dealt with by DSD). */
  // If so, we must import the tableFQName before the DSD's package and
  // before melati in our java source
  public boolean hidesOther = false;

  /** Does this Table have the same name as another table "lower down" in our
      hierarchy. */
  // If so, we must use the tableFQName as the return type in the DatabaseBase
  // source file, and any tables with ReferenceTypes to this table
  public boolean hidden = false;

  public TableNamingInfo(String packageName, String name) {
    tableShortName = name;
    tableFQName = packageName + "." + name;
    superclass = null;
    hidesOther = false;
    hidden = false;
  }

  /** Calculate the type for objects in this Table */
  public String superclassMainUnambiguous() {
    return (superclass == null)
            ? "Persistent"
            : superclass.mainClassUnambiguous();
  }

  /** Calculate the type for this Table */
  public String superclassTableUnambiguous() {
    return (superclass == null)
            ? "Table"
            : superclass.tableMainClassUnambiguous();
  }

  public String includeMainString() {
    return (hidesOther && !hidden) ? "import " + mainClassFQName() + ";\n" : "";
  }

  public String includeTableString() {
    return (hidesOther && !hidden) ? "import " + tableMainClassFQName() + ";\n" : "";
  }

  public String baseClassFQName() {
    return tableFQName + "Base";
  }
  public String baseClassShortName() {
    return tableShortName + "Base";
  }

  public String baseClassUnambiguous() {
    return (hidden)
              ? baseClassFQName()
              : baseClassShortName();
  }

  /**
   * Return the fully qualified name of the main class for this table
   */
  public String mainClassFQName() {
    return tableFQName;
  }

  /**
   * Return the name of the main class for this table
   */
  public String mainClassShortName() {
    return tableShortName;
  }

  /**
   * Return a name for this class which will properly refer to this table
   * given the import lines we put in all our generated flies
   */
  public String mainClassUnambiguous() {
    return (hidden)
              ? mainClassFQName()
              : mainClassShortName();
  }

  /**
   * Find the return type we should use when calling getXxxxTable().
   * We need to make sure that this is the
   * same class as that returned by the root superclass of the same
   * name because you cannot override a function and change its
   * return type.
   * <p>
   * NB if the superclass is abstract then we
   */
  public String mainClassRootReturnClass() {
    TableNamingInfo root = getRootSameNamedSuperclass();
    return (root != null)
              ? root.mainClassUnambiguous() // should always be mainClassFQName
              : mainClassUnambiguous();
  }

  public String tableBaseClassFQName() {
    return tableFQName + "TableBase";
  }
  public String tableBaseClassShortName() {
    return tableShortName + "TableBase";
  }

  public String tableBaseClassUnambiguous() {
    return (hidden)
              ? tableBaseClassFQName()
              : tableBaseClassShortName();
  }

  public String tableMainClassFQName() {
    return tableFQName + "Table";
  }
  public String tableMainClassShortName() {
    return tableShortName + "Table";
  }

  public String tableMainClassUnambiguous() {
    return (hidden)
               ? tableMainClassFQName()
               : tableMainClassShortName();
  }

  /**
   * Find the return type we should use when calling getXxxxObject().
   * We need to make sure that this is the
   * same class as that returned by the root superclass of the same
   * name because you cannot override a function and change its
   * return type.
   * <p>
   * NB if the superclass is abstract then we
   */
  public String tableMainClassRootReturnClass() {
    TableNamingInfo root = getRootSameNamedSuperclass();
    return (root != null)
              ? root.tableMainClassUnambiguous() // should always be tableMainClassFQName
              : tableMainClassUnambiguous();
  }

  public String tableAccessorMethod() {
    return "get" + tableMainClassShortName();
  }

  /**
   * Find the top-most superclass of this table which has the same (short) name
   */
  protected TableNamingInfo getRootSameNamedSuperclass() {
    TableNamingInfo curr = this;
    while (curr != null && curr.superclass != null &&
             curr.tableShortName.equals(curr.superclass.tableShortName))
      curr = curr.superclass;
    return (curr != this) ? curr : null;
  }
}

