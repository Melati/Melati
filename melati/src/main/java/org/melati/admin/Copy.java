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
package org.melati.admin;

import java.util.Enumeration;

import org.melati.LogicalDatabase;
import org.melati.Melati;
import org.melati.poem.Database;
import org.melati.poem.Field;
import org.melati.poem.Persistent;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.Table;
import org.melati.poem.util.MappedEnumeration;
import org.melati.servlet.PathInfoException;
import org.melati.servlet.TemplateServlet;
import org.melati.template.ServletTemplateContext;

/**
 * 
 * Copy one db to another.
 * 
 * @author timp
 * @since 3 Oct 2007
 *
 */
public class Copy extends TemplateServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  static Database fromDb = null;
  static Database toDb = null;
  
  
  /** 
   * {@inheritDoc}
   * @see org.melati.servlet.TemplateServlet#prePoemSession(org.melati.Melati)
   */
  protected void prePoemSession(Melati melati) throws Exception {
    super.prePoemSession(melati);
    String[] parts = melati.getPathInfoParts();
    if (parts.length != 2)
      throw new PathInfoException("Two database names expecetd");
    String fromDbName = parts[0];
    String toDbName = parts[1];

    copy(fromDbName, toDbName);
    
  }
  
  /** 
   * {@inheritDoc}
   * @see org.melati.servlet.TemplateServlet#doTemplateRequest
   */
  protected String doTemplateRequest(Melati melati,
          ServletTemplateContext templateContext) throws Exception {
    melati.setPassbackExceptionHandling();
    melati.setResponseContentType("text/html");
    templateContext.put("admin", new AdminUtils(melati));

    String[] parts = melati.getPathInfoParts();
    String fromDbName = parts[0];
    String toDbName = parts[1];

    templateContext.put("fromDbName",fromDbName);
    templateContext.put("toDbName",toDbName);
    return "org/melati/admin/CopyDone";
  }

  /**
   * @param from
   * @param to
   * @return the updated db
   */
  public static Database copy(String from, String to) { 
    fromDb = LogicalDatabase.getDatabase(from);
    toDb =  LogicalDatabase.getDatabase(to);
    return copy();
  }
  /**
   * @param fromDb
   * @param toDb
   * @return the updated db
   */
  public static Database copy(Database fromDbIn, final Database toDbIn) {
    fromDb = fromDbIn;
    toDb = toDbIn;
    return copy();
  }
  /**
   * @return the updated db
   */
  public static Database copy() { 
    if (fromDb.getClass() != toDb.getClass()) 
      throw new AnticipatedException("Both from(" + fromDb.getClass() + ") and " + 
                                     "to(" + toDb.getClass() + ") databases " + 
                                     "must be of the same class");
    toDb.inSessionAsRoot( 
            new PoemTask() {
              public void run() {
                System.err.println("PoemThread " + PoemThread.database().getDisplayName());
                try {
                  Enumeration<Table> en = fromDb.displayTables(null);
                  while(en.hasMoreElements()) { 
                    Table fromTable = (Table)en.nextElement();
                    String fromTableName = fromTable.getName();
                    System.err.println("From " + fromDb + " table " + fromTableName); 
                    Table toTable = toDb.getTable(fromTableName);
                    int count = toTable.count();
                    if (count != 0 ) {
                      System.err.println("Skipping " + toTable.getName() + " as it contains " + count + " records." );
                    } else { 
                      System.err.println(toTable.getName() + " in both and empty in destination.");
                      Enumeration<Persistent> recs = objectsFromTroids(
                              fromTable.troidSelection((String)null, 
                                                       (String)null, false, null), 
                                                       fromTable);
                      while (recs.hasMoreElements()) {
                        Persistent p = (Persistent)recs.nextElement();
                        Persistent p2 = toTable.newPersistent();
                        Enumeration<Field> fields = p.getFields();
                        while (fields.hasMoreElements()) { 
                          Field f = fields.nextElement();
                          p2.setRaw(f.getName(), f.getRaw());
                        }
                        p2.makePersistent();
                        System.err.println("Created:" + p2.displayString());
                      }
                    }
                   }
                } catch (Throwable e) {
                  e.printStackTrace();
                  e.fillInStackTrace();
                  throw new RuntimeException(e);
                }
              }
              public String toString() { 
                return "Copying";
              }
            });
    return toDb;

  }
  static Enumeration<Persistent> objectsFromTroids(Enumeration<Integer> troids, final Table t) {
    return new MappedEnumeration<Persistent, Integer>(troids) {
        public Persistent mapped(Integer troid) {
          return t.getObject(troid);
        }
      };
  }

}
