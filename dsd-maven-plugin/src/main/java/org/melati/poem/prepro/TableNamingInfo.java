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

/** 
 * A store for Table Name information extracted from the DSD.
 */
public class TableNamingInfo {

  static final String POEM = "org.melati.poem";

  /** This package eg org.melati.example.contacts */
  public String packageName = null;

  /** The fully qualified name of the table (e.g. `org.melati.poem.User') */
  public String tableFQName = null;

  /** The last part of the FQName of the table (e.g. `User') */
  public String tableShortName = null;

  /** The TableNamingInfo of this Table's superclass (or null if extends
      Persistent) */
  public TableNamingInfo superclass = null;

  /** 
   * Does this Table have the same name as another table "higher up" in our
   * hierarchy (that is a table already dealt with by DSD). 
   *
   * If so, we must import the tableFQName before the DSD's package and
   * before melati in our java source.
   *
   * For example <TT>org.paneris.melati.boards.model.UserTable</TT> hides 
   * <TT>org.melati.poem.UserTable</TT> and so has <TT>hidesOther</TT> true.
   */
  public boolean hidesOther = false;

  /** 
   *  Does this Table have the same name as another table "lower down" in our
   *  hierarchy. 
   *
   * If so, we must use the tableFQName as the return type in the DatabaseBase
   * source file, and any tables with ReferenceTypes to this table.
   * 
   * For example <TT>org.melati.poem.UserTable</TT> is hidden by 
   * <TT>org.paneris.melati.boards.model.UserTable</TT> and so has 
   * <TT>hidden</TT> true.
   */
  public boolean hidden = false;

  /**
   * Constructor.
   * @param packageNameIn  the fully qualified java name of this package
   * @param name           the name of this table
   */
  public TableNamingInfo(String packageNameIn, String name) {
    packageName = packageNameIn;
    tableShortName = name;
    tableFQName = packageName + "." + name;
    superclass = null;
    hidesOther = false;
    hidden = false;
  }

  /** 
   * Calculate the type for objects in this Table.
   *
   * @return the name for the Persistent class
   */
  public String superclassMainUnambiguous() {
    return (superclass == null)
            ? "Persistent"
            : superclass.mainClassUnambiguous();
  }

  /** 
   * Calculate the full type for the superclass of the Persistent.
   *
   * @return the fully qualified name for the superclass
   */
  public String superclassMainFQName() {
    return (superclass == null)
            ? "org.melati.poem.Persistent"
            : superclass.mainClassFQName();
  }

  /** 
   * Calculate the name of the superclass of the Persistent.
   *
   * @return the short name for the superclass
   */
  public String superclassMainShortName() {
    return (superclass == null)
            ? "Persistent"
            : superclass.mainClassShortName();
  }

  /** 
   * Calculate the type for this Table.
   * 
   * @return the unambiguous name
   */
  public String superclassTableUnambiguous() {
    return (superclass == null)
            ? "Table"
            : superclass.tableMainClassUnambiguous();
  }

  /** 
   * Calculate the full type for the superclass of this  Table.
   * 
   * @return the superclass fully qualified name
   */
  public String superclassTableFQName() {
    return (superclass == null)
            ? "org.melati.poem.Table"
            : superclass.tableMainClassFQName();
  }
  /** 
   * Calculate the type for the superclass of this  Table.
   * 
   * @return the superclass short name
   */
  public String superclassTableShortName() {
    return (superclass == null)
            ? "Table"
            : superclass.tableMainClassShortName();
  }

  String importMainString() {
//    return (hidesOther && !hidden) ? "import " + mainClassFQName() + ";\n" 
//                                   : "";
      return "import " + mainClassFQName() + ";\n";
  }

  String importTableString() {
//    return (hidesOther && !hidden) ? "import " + tableMainClassFQName() + 
//                             ";\n" : "";
      return "import " + tableMainClassFQName() + ";\n"; 

  }

  String baseClassFQName() {
    return packageName + ".generated." + tableShortName + "Base";
  }

  String baseClassShortName() {
    return tableShortName + "Base";
  }

  String baseClassUnambiguous() {
    return (hidden)
              ? baseClassFQName()
              : baseClassShortName();
  }

  /**
   * @return the fully qualified name of the main class for this table
   */
  public String mainClassFQName() {
    return tableFQName;
  }

  /**
   * @return the name of the main class for this table
   */
  public String mainClassShortName() {
    return tableShortName;
  }

  /**
   * @return a name for this class which will properly refer to this table
   * given the import lines we put in all our generated flies
   */
  public String mainClassUnambiguous() {
    return (hidden)
              ? mainClassFQName()
              : mainClassShortName();
  }

  /**
   * We need to make sure that this is the
   * same class as that returned by the root superclass of the same
   * name because you cannot override a function and change its
   * return type.
   *
   * @return type we should use when calling getXxxxTable().
   */
  public String mainClassRootReturnClass() {
    TableNamingInfo root = getRootSameNamedSuperclass();
    return (root != null)
              ? root.mainClassUnambiguous() // should always be mainClassFQName
              : mainClassUnambiguous();
  }

  String tableBaseClassFQName() {
    return packageName + ".generated." + tableShortName + "TableBase";
  }
  String tableBaseClassShortName() {
    return tableShortName + "TableBase";
  }

  String tableBaseClassUnambiguous() {
    return (hidden)
              ? tableBaseClassFQName()
              : tableBaseClassShortName();
  }

  String tableMainClassFQName() {
    return tableFQName + "Table";
  }
  String tableMainClassShortName() {
    return tableShortName + "Table";
  }

  String tableMainClassUnambiguous() {
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
   *
   * @return the root return class 
   */
  public String tableMainClassRootReturnClass() {
    TableNamingInfo root = getRootSameNamedSuperclass();
    return (root != null) // should always be tableMainClassFQName
              ? root.tableMainClassUnambiguous() 
              : tableMainClassUnambiguous();
  }

  String tableAccessorMethod() {
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

