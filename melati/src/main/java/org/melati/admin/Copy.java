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
import org.melati.PoemContext;
import org.melati.poem.AccessPoemException;
import org.melati.poem.AccessToken;
import org.melati.poem.Column;
import org.melati.poem.Field;
import org.melati.poem.Initialiser;
import org.melati.poem.JdbcTable;
import org.melati.poem.NoSuchTablePoemException;
import org.melati.poem.Persistent;
import org.melati.poem.PoemDatabase;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.Table;
import org.melati.poem.TableInfo;
import org.melati.poem.ValidationPoemException;
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
  static PoemDatabase fromDb = null;
  static PoemDatabase toDb = null;
  
  
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
  
  protected void postPoemSession(Melati melati) throws Exception {
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
   */
  public static void copy(String from, String to) { 
    PoemContext fromContext = new PoemContext();
    PoemContext toContext = new PoemContext();
    fromContext.setLogicalDatabase(from);
    toContext.setLogicalDatabase(to);
    fromDb = (PoemDatabase)LogicalDatabase.getDatabase(fromContext.getLogicalDatabase());
    toDb = (PoemDatabase)LogicalDatabase.getDatabase(toContext.getLogicalDatabase());
    toDb.inSession(AccessToken.root, 
            new PoemTask() {
              public void run() {
                System.err.println("PoemThread " + PoemThread.database().getDisplayName());
                try {
                  Enumeration en = fromDb.getDisplayTables(null);
                  while(en.hasMoreElements()) { 
                    Table fromTable = (Table)en.nextElement();
                    String fromTableName = fromTable.getName();
                    System.err.println("From:" + fromTableName); 
                    Table toTable = null;
                    try { 
                      toTable = toDb.getTable(fromTableName);
                    } catch (NoSuchTablePoemException e) {
                      TableInfo fromTableInfo = (TableInfo)fromTable.getInfo();
                      TableInfo toTableInfo = (TableInfo)toDb.getTableInfoTable().newPersistent();
                      Enumeration fromTIFields = fromTableInfo.getFields();
                      while (fromTIFields.hasMoreElements())
                      { 
                        Field f = (Field)fromTIFields.nextElement();
                        if (! (f.getType() instanceof  org.melati.poem.TroidPoemType)) {
                          System.err.println(f.getType() + " " + f.getDisplayName());
                          toTableInfo.setRaw(f.getName(), f.getRaw());
                        }
                      }
                      toTableInfo.makePersistent(); 
                      String troidName = fromTable.troidColumn().getName();
                      toTable = toDb.addTableAndCommit(toTableInfo, troidName);
                      System.err.println("Created table:" + fromTableName); 
                    }
                    if (!fromTable.getCategory().getName().equals("System") && 
                            !fromTable.getCategory().getName().equals("User")) {
                      System.err.println("Existing in both:" + fromTableName + "=" + toTable);
                      Enumeration oldRecs = toTable.selection();
                      while (oldRecs.hasMoreElements()) {
                        Persistent p = (Persistent)oldRecs.nextElement();
                        System.err.println("Deleting:" + p.displayString());
                        p.delete();
                      }
                      
                      Enumeration recs = ((JdbcTable)fromTable).objectsFromTroids(
                              fromTable.troidSelection((String)null, 
                                                       (String)null, false, null));
                      while (recs.hasMoreElements()) {
                        Persistent p = (Persistent)recs.nextElement();
                        Persistent p2 = toTable.newPersistent();
                        Enumeration fields = p.getFields();
                        while (fields.hasMoreElements()) { 
                          Field f = (Field)fields.nextElement();
                          p2.setRaw(f.getName(), f.getRaw());
                        }
                        p2.makePersistent();
                        //System.err.println("Created:" + p2.displayString());
                      }
                    } else { 
                      System.err.println("Ignoring " + fromTable.getDisplayName() + 
                              " as it is a " +  fromTable.getCategory().getName() + " table");
                    }
                   }
                } catch (Throwable e) {
                  e.printStackTrace();
                  e.fillInStackTrace();
                  throw new RuntimeException(e);
                }
                toDb.disconnect();
                //fromDb.disconnect();
              }
              public String toString() { 
                return "Copying";
              }
            });

  }
  /**
   * Creates a row for a table using field data in a template context.
   */
  protected Persistent create(Table table, final Persistent from) {
    Persistent result =
      table.create(
        new Initialiser() {
          public void init(Persistent object)
            throws AccessPoemException, ValidationPoemException {
            for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
              Column column = (Column)c.nextElement();
              Object raw = object.getCooked(column.getName());
              column.setRaw(object,raw);
            }
          }
        });
    result.postEdit(true);
    return result;
  }

}
