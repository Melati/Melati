/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati;

import java.io.IOException;
import java.util.Vector;
import java.util.Properties;

import org.melati.poem.Database;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.util.DatabaseInitException;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.PropertiesUtils;
import org.melati.util.PropertyException;

/**
 * An object which knows how to connect to a database.
 */
public final class LogicalDatabase {
  
  private LogicalDatabase() {}


  /** Properties, named for this class. */
  private static Properties databaseDefs = null;
  
  private static final int MAX_TRANSACTIONS_DEFAULT = 8;

  private static synchronized Properties databaseDefs() {
    if (databaseDefs == null)
      try { 
        databaseDefs =
            PropertiesUtils.fromResource(LogicalDatabase.class,
                getPropertiesName());
      } catch (IOException e) {
        throw new MelatiBugMelatiException("Cannot open database properties file " + getPropertiesName(), e);
      }        
    return databaseDefs;
  }

  /** 
   * Retrieve the databases which have completed initialisation.
   *
   * @return a <code>Vector</code> of the initialised databases
   */
  public static Vector initialisedDatabases() {
    return PoemDatabaseFactory.initialisedDatabases();
  }

  
  /** 
   * Retrieve the names of the databases which have 
   * completed initialisation.
   * Note that a database which has not been used 
   * will not have been initialised.
   *
   * @return a <code>Vector</code> of the initialised database names
   */
  public static Vector getInitialisedDatabaseNames() {
    return PoemDatabaseFactory.getInitialisedDatabaseNames();
  }
  
  /** 
   * Retrieve a database by name.
   *
   * @param name the name of the database
   * @throws DatabaseInitException if any Exception is trapped
   * @return a <code>Database</code> with the name specified
   */
  public static Database getDatabase(String name) 
      throws DatabaseInitException {
    Database db = PoemDatabaseFactory.getDatabase(name);
    if (db == null) {
      Properties defs = databaseDefs();
      String pref =  LogicalDatabase.class.getName() + "." + name + ".";
      String url = null;
      String user = null;
      String pass = null;
      String clazz = null;
      String dbmsClass = null;
      String addConstraints = null;
      String logSQL  = null;
      String logCommits = null;
      int maxTrans;
      try {
        url = PropertiesUtils.getOrDie(defs, pref + "url");
        user = PropertiesUtils.getOrDie(defs, pref + "user");
        pass = PropertiesUtils.getOrDie(defs, pref + "pass");
        clazz = PropertiesUtils.getOrDie(defs, pref + "class");
        dbmsClass = PropertiesUtils.getOrDie(defs, pref + "dbmsclass");
        addConstraints = PropertiesUtils.getOrDefault(defs, pref + "addconstraints", "false");
        logSQL = PropertiesUtils.getOrDefault(defs, pref + "logsql", "false");
        logCommits = PropertiesUtils.getOrDefault(defs, pref + "logcommits", "false");
        maxTrans = PropertiesUtils.
                         getOrDefault_int(defs, pref + "maxtransactions", MAX_TRANSACTIONS_DEFAULT);
      } catch (PropertyException e) {
        throw new DatabaseInitException(getPropertiesName(),name, e);
      }
      db = PoemDatabaseFactory.getDatabase(name, url, user, pass, 
             clazz, dbmsClass, 
             new Boolean(addConstraints).booleanValue(), 
             new Boolean(logSQL).booleanValue(), 
             new Boolean(logCommits).booleanValue(),
             maxTrans);
    }
    return db;
  }  
  /**
   * Set the databaseDefs.
   */
  public static void setDatabaseDefs(Properties databaseDefsIn) {
    databaseDefs = databaseDefsIn;
  }

  /**
   * The name used with <code>getResourceAsStream</code> to 
   * get the file and the properties within it. 
   * 
   * @return Returns the defaultPropertiesName.
   */
  public static String getPropertiesName() {
    return "org.melati.LogicalDatabase.properties";
  }
}







