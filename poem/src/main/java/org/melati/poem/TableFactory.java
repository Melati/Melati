/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.poem;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import org.melati.poem.util.ClassUtils;
import org.melati.poem.util.StringUtils;


/**
 * Given an Object or class create a set of Tables to represent the graph 
 * it is the starting node of, and populate it for an Object.
 * 
 * The rules are a sub-set of the Hibernate rules: 
 * Only public types can be persisted.
 * Only members with a public setter and getter will be persisted.
 * If the object has a field called Id then the Persistent troid column will be called 
 * <tt>poemId</tt>.  
 *  
 * @author timp
 * @since 12 Jun 2007
 * 
 */
public final class TableFactory {

  /**
   * Disable instantiation.
   */
  private TableFactory() {
  }

  /**
   * @param db
   *          the database to create table in and lookup existing tables
   * @param pojo
   *          class to introspect
   * @return A new or existing table
   */
  public static Table fromInstance(Database db, Object pojo) {
    return fromClass(db, pojo.getClass());
  }

  /**
   * @param db
   *          the database to create table in and lookup existing tables
   * @param clazz
   *          class to introspect
   * @return A new or existing table
   */
  public static Table fromClass(Database db, Class clazz) {
    if (clazz.isPrimitive())
      throw new IllegalArgumentException(
      "Cannot create a Table for a primitive type: " + clazz.getName());
    if (clazz.isInterface())
      throw new IllegalArgumentException(
      "Cannot create a Table for an interface: " + clazz.getName());
    if(!Modifier.isPublic(clazz.getModifiers())) 
      throw new IllegalArgumentException(
        "Cannot create a Table for a non public type: "+ clazz.getName());
    if (clazz.isArray() && !(clazz == byte[].class))
      throw new IllegalArgumentException("Cannot create a Table for an array: " + clazz.getName());
    String name = clazz.getName();
    String simpleName = name.substring(name.lastIndexOf('.') + 1);
    try {
      return db.getTable(simpleName);
    } catch (NoSuchTablePoemException e) {
      System.err.println("Creating a table for : " + name);
    }
    // We will have to create one
    TableInfo tableInfo = (TableInfo)db.getTableInfoTable().newPersistent();
    tableInfo.setName(simpleName);
    tableInfo.setDisplayname(simpleName + " introspected table");
    tableInfo.setDisplayorder(13);
    tableInfo.setSeqcached(Boolean.FALSE);
    tableInfo.setCategory(TableCategoryTable.NORMAL);
    tableInfo.setCachelimit(555);
    tableInfo.makePersistent();

    Table table = new Table(db, simpleName, DefinitionSource.runtime);
    String troidName = "id";
    if (ClassUtils.getNoArgMethod(clazz, "getId") != null &&
        !(Persistent.class.isAssignableFrom(clazz)))
      troidName = "poemId";
    table.defineColumn(new ExtraColumn(table, troidName, TroidPoemType.it,
            DefinitionSource.runtime, table.getNextExtrasIndex()));
    table.setTableInfo(tableInfo);
    table.unifyWithColumnInfo();
    table.unifyWithDB(null);

    PoemThread.commit();
    db.defineTable(table);
    Hashtable props = new Hashtable();

    Method[] methods = clazz.getMethods();

    for (int i = 0; i < methods.length; i++) {
      if (Modifier.isPublic(methods[i].getModifiers())) {
        if (methods[i].getName().startsWith("set") 
            && ! methods[i].getName().equals("set")
            && Character.isUpperCase(methods[i].getName().toCharArray()[3])
            && methods[i].getParameterTypes().length == 1
            && (methods[i].getParameterTypes()[0] == byte[].class || 
                ! methods[i].getParameterTypes()[0].isArray())
            && !methods[i].getParameterTypes()[0].isInterface()
            && ! Collection.class.isAssignableFrom(methods[i].getParameterTypes()[0])
            && ! Map.class.isAssignableFrom(methods[i].getParameterTypes()[0])
            ) {
          String propName = methods[i].getName().substring(3);
          propName = StringUtils.uncapitalised(propName);
          Prop p = (Prop)props.get(propName);
          Class setClass = methods[i].getParameterTypes()[0];
          if (p == null) {
            p = new Prop(propName);
            p.setSet(setClass);
          } else { 
            if (p.getGot() != null) { 
              if(p.getGot() == setClass)
                p.setSet(setClass);
            } else { 
              p.setSet(setClass);                
            }
          }
          props.put(propName, p);
        }
        
        if (methods[i].getParameterTypes().length == 0
            && ! methods[i].getReturnType().isInterface()
            && (methods[i].getReturnType() == byte[].class || 
                !methods[i].getReturnType().isArray())
            && !Collection.class.isAssignableFrom(methods[i].getReturnType())
            && !Map.class.isAssignableFrom(methods[i].getReturnType())
        ) {
          String propName = null;
          if ((methods[i].getName().startsWith("get")) && 
               Character.isUpperCase(methods[i].getName().toCharArray()[3]))
            propName = methods[i].getName().substring(3);
          else if (methods[i].getName().startsWith("is") && 
                   Character.isUpperCase(methods[i].getName().toCharArray()[2])) 
            propName = methods[i].getName().substring(2);
          if (propName != null) { 
            propName = StringUtils.uncapitalised(propName);
            Prop p = (Prop)props.get(propName);
            Class gotClass = methods[i].getReturnType();
            if (p == null) {
              p = new Prop(propName);
              p.setGot(gotClass);
            } else { 
              p.setGot(gotClass);
            }
            props.put(propName, p);
          }
        }
      }
    }
    Enumeration propsEn = props.elements();
    while (propsEn.hasMoreElements()) {
      Prop p = (Prop)propsEn.nextElement();
      if (p.getGot() != null && 
          p.getGot() == p.getSet()) { 
        addColumn(table, p.getName(), p.getGot(), p.getGot() == p.getSet());
      }
    }
    return table;
  }

  private static void addColumn(Table table, String name, Class fieldClass,
          boolean hasSetter) {
    ColumnInfo columnInfo = (ColumnInfo)table.getDatabase()
            .getColumnInfoTable().newPersistent();
    columnInfo.setTableinfo(table.getInfo());
    columnInfo.setName(name);
    
    columnInfo.setDisplayname(name);
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("An introspected member");
    columnInfo.setUsercreateable(hasSetter);
    columnInfo.setUsereditable(hasSetter);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(true);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    if (fieldClass == java.lang.Boolean.class) {
      columnInfo.setTypefactory(PoemTypeFactory.BOOLEAN);
      columnInfo.setSize(1);
      columnInfo.setWidth(10);
    } else if (fieldClass == boolean.class) {
      columnInfo.setTypefactory(PoemTypeFactory.BOOLEAN);
      columnInfo.setSize(1);
      columnInfo.setWidth(10);
    } else if (fieldClass == java.lang.Integer.class) {
      columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
    } else if (fieldClass == int.class) {
      columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
    } else if (fieldClass == java.lang.Double.class) {
      columnInfo.setTypefactory(PoemTypeFactory.DOUBLE);
    } else if (fieldClass == double.class) {
      columnInfo.setTypefactory(PoemTypeFactory.DOUBLE);
    } else if (fieldClass == java.lang.Long.class) {
      columnInfo.setTypefactory(PoemTypeFactory.LONG);
    } else if (fieldClass == long.class) {
      columnInfo.setTypefactory(PoemTypeFactory.LONG);
    } else if (fieldClass == java.math.BigDecimal.class) {
      columnInfo.setTypefactory(PoemTypeFactory.BIGDECIMAL);
      columnInfo.setPrecision(22);
      columnInfo.setScale(2);
    } else if (fieldClass == java.lang.String.class) {
      columnInfo.setTypefactory(PoemTypeFactory.STRING);
      columnInfo.setSize(-1);
    } else if (fieldClass == java.sql.Date.class) {
      columnInfo.setTypefactory(PoemTypeFactory.DATE);
    } else if (fieldClass == java.sql.Timestamp.class) {
      columnInfo.setTypefactory(PoemTypeFactory.TIMESTAMP);
    } else if (fieldClass == byte[].class) {
      columnInfo.setTypefactory(PoemTypeFactory.BINARY);
    }
    else {
      Table referredTable = fromClass(table.getDatabase(), fieldClass);
      columnInfo.setTypefactory(PoemTypeFactory.forCode(table.getDatabase(),
              referredTable.getInfo().troid().intValue()));
    }
    columnInfo.makePersistent();
    table.addColumnAndCommit(columnInfo);
  }

  /**
   * Details of a member variable.
   */
  static final class Prop {
    String name = null;
    Class set = null;
    Class got = null;
    
    Prop(String name) {
      this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * @return the got
     */
    public Class getGot() {
      return got;
    }

    /**
     * @param got
     *          the Class of the getter
     */
    public void setGot(Class got) {
      this.got = got;
    }

    /**
     * @return the set
     */
    public Class getSet() {
      return set;
    }

    /**
     * @param set
     *          the Class of the setter
     */
    public void setSet(Class set) {
      this.set = set;
    }

  }
}
