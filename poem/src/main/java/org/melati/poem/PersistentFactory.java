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
import java.util.Enumeration;

import org.melati.poem.util.ClassUtils;
import org.melati.poem.util.StringUtils;

/**
 * A factory for persisting pojos and recreating them.
 * 
 * @author timp
 * @since 14 Jun 2007
 * 
 */
public final class PersistentFactory {

  /**
   * Disallow instantiation.
   */
  private PersistentFactory() {
  }

  /**
   * @param db
   *          database to look in
   * @param pojo
   *          the instance to create a Persistent from.
   * @return A new or existing Persistent
   */
  public static Persistent fromInstance(Database db, Object pojo) {
    Table table;
    if (pojo instanceof Persistent)
      table = ((Persistent)pojo).getTable();
    else
      table = TableFactory.fromInstance(db, pojo);
    Persistent p = populatedPersistent(table, pojo);
    Persistent candidate = null;
    Enumeration candidates = table.selection(p);
    while (candidates.hasMoreElements()) {
      candidate = (Persistent)candidates.nextElement();
      if (commonFieldsEqual(p, candidate)) { 
        p = candidate;
        break;
      } 
    }
    if (candidate == null)
      p.makePersistent();
      
    return p;
  }

  private static boolean commonFieldsEqual(Persistent p, Persistent candidate) {
    Enumeration cols = p.getTable().columns();
    while (cols.hasMoreElements()) { 
      Column col = (Column)cols.nextElement();
      if (col.isTroidColumn()) 
        continue;
      if (col.getRaw(p) != col.getRaw(candidate))
        return false;
    }
    return true;
  }

  /**
   * @param table
   *          the Table the persisted pojo is to be stored in
   * @param pojo
   *          the object to populate the Persistent from
   * @return A floating Persistent with fields populated from the given pojo
   */
  public static Persistent populatedPersistent(Table table, Object pojo) {
    Persistent p = table.newPersistent();
    Class c = pojo.getClass();
    Enumeration columns = table.columns();
    while (columns.hasMoreElements()) {
      Column col = (Column)columns.nextElement();
      if(col.isTroidColumn()) continue;
      Method memberGetter;
      Object raw;
      try {
        memberGetter = c.getMethod("get"
                + StringUtils.capitalised(col.getName()), new Class[] {});
      } catch (NoSuchMethodException e) {
        try {
          memberGetter = c.getMethod("is"
                  + StringUtils.capitalised(col.getName()), new Class[] {});
        } catch (NoSuchMethodException e1) {
          throw new AppBugPoemException(
                  "No getter available for field " + col.getName(), e1);
        }
      } 
      try {
        raw = memberGetter.invoke(pojo, new Class[] {});
      } catch (Exception e) {
        throw new AppBugPoemException(
                "Problem invoking getter on column  " + col.getName(), e);
      }
      if (raw != null) {
        try {
          if (col.getType() instanceof ReferencePoemType) {
            p.setCooked(col.getName(), PersistentFactory.fromInstance(table
                    .getDatabase(), raw));
          } else {
            p.setCooked(col.getName(), raw);
          }
        } catch (TypeMismatchPoemException e) {
          throw new AppBugPoemException("Problem setting value of Column "
                  + col.getName(), e);
        }
      }
    }
    return p;
  }

  /**
   * Reincarnate an object, populated from the store.
   * 
   * @param persistent
   *          the Persistent to read from
   * @param clazz
   *          the class to create a new, populated instance of
   * @return an instance of the given Class, populated from the given Persistent
   * @throws NoSuchMethodException
   */
  public static Object from(Persistent persistent, Class clazz)
          throws NoSuchMethodException {
    Object it;
    try {
      it = clazz.newInstance();
    } catch (Exception e) {
      throw new AppBugPoemException("Problem creating new instance of "
              + clazz.getName(), e);
    }
    return populatedPojo(it, persistent);
  }

  private static Object populatedPojo(Object pojo, Persistent persistent) 
      throws NoSuchMethodException {
    Enumeration columns = persistent.getTable().columns();
    while (columns.hasMoreElements()) {
      Column col = (Column)columns.nextElement();
      if(col.isTroidColumn()) continue;
      Object cooked = col.getCooked(persistent);
      if (cooked != null) {
        String setterName = "set" + StringUtils.capitalised(col.getName());
        Method[] possibleSetters = ClassUtils.getOneArgumentMethods(pojo.getClass(), setterName);
        if(possibleSetters.length == 0 )
            throw new NoSuchMethodException("No setter called " + setterName
                    + " could be found " + "on Class " + pojo.getClass().getName());
        for (int i = 0; i < possibleSetters.length; i++) {
          if (col.getType() instanceof ReferencePoemType) {
            Object newPojo = PersistentFactory.from((Persistent)cooked,
                    possibleSetters[i].getParameterTypes()[0]);
            try {
              possibleSetters[i].invoke(pojo, new Object[] { newPojo });
            } catch (Exception e) {
              throw new AppBugPoemException(
                      "Problem setting value of Column " + col.getName(), e);
            }
          } else {
            try {
              possibleSetters[i].invoke(pojo, new Object[] { cooked });
            } catch (Exception e) {
              throw new AppBugPoemException(
                      "Problem setting value of Column " + col.getName(), e);
            }
          }
        }
      }
    }
    return pojo;
  }


}
