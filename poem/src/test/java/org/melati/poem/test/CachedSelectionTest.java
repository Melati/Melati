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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */
package org.melati.poem.test;

import java.util.Enumeration;
import org.melati.poem.Table;
import org.melati.poem.AccessToken;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.CachedSelection;
import org.melati.poem.UnexpectedExceptionPoemException;

public class CachedSelectionTest {

  abstract static class PoemTaskThread extends Thread {

    Table table;

    PoemTaskThread(Table table) {
      this.table = table;
    }

    abstract void run_() throws Exception;

    public void run() {
      table.getDatabase().inSession(
          AccessToken.root,
          new PoemTask() {
            public void run() {
              try {
                run_();
              }
              catch (Exception e) {
                throw new UnexpectedExceptionPoemException(e);
              }
            }
          });
    }
  }

  static class Setter extends PoemTaskThread {

    static class Signal {}

    static final Signal
        set = new Signal(), add = new Signal(), delete = new Signal();

    Signal[] theSignal = new Signal[1];
    int serial = 0;

    Setter(Table table) {
      super(table);
    }

    void signal(Signal signal) {
      synchronized (theSignal) {
        theSignal[0] = signal;
        theSignal.notifyAll();
      }
    }

    void run_() throws Exception {
      for (;;) {
        synchronized (theSignal) {
          theSignal.wait();
        }

        if (theSignal[0] == set) {
          Athing t = (Athing)table.firstSelection(null);
          if (t == null)
            System.err.println("\n*** setter: nothing to set\n");
          else {
            System.err.println("\n*** setter: setting\n");
            // FIXME - line removed by ttj to allow melait to compile
//            t.setWhatsit("whatsit" + (serial++));
          }
        }
        else if (theSignal[0] == add) {
          System.err.println("\n*** setter: adding\n");
          Athing t = (Athing)table.newPersistent();
            // FIXME - line removed by ttj to allow melati to compile
//          t.setWhatsit("whatsit" + (serial++));
          t.makePersistent();
        }
        else if (theSignal[0] == delete) {
          Athing t = (Athing)table.firstSelection(null);
          if (t == null)
            System.err.println("\n*** setter: nothing to delete\n");
          else {
            System.err.println("\n*** setter: deleting\n");
            t.delete_unsafe();
            System.err.println("\n*** setter: done deleting\n");
          }
        }
        else if (theSignal[0] == null) {
          System.err.println("\n*** setter done\n");
          break;
        }

        PoemThread.commit();
      }
    }
  }

  static class Getter extends PoemTaskThread {

    Object[] theSignal = new Object[1];
    CachedSelection cachedSelection;

    Getter(Table table) {
      super(table);
      cachedSelection = new CachedSelection(table, null, null, null);
    }

    void signal(Object signal) {
      synchronized (theSignal) {
        theSignal[0] = signal;
        theSignal.notifyAll();
      }
    }

    void run_() throws Exception {
      for (;;) {
        synchronized (theSignal) {
          theSignal.wait();
        }

        if (theSignal[0] == null) {
          System.err.println("\n*** getter done\n");
          break;
        }
        else {
          System.err.println("\n*** getter:\n");
          Enumeration them = cachedSelection.objects();

          System.err.print("\n*** got");

          while (them.hasMoreElements())
            // FIXME - line removed by ttj to allow melati to compile
//            System.err.print(" " + ((AThing)them.nextElement()).getWhatsit());

          System.err.println("\n");
        }

        PoemThread.commit();
      }
    }
  }

  public static void main(final String[] args) throws Exception {
    final TestDatabase db = new TestDatabase();
    try {
      db.connect("org.melati.poem.dbms.Postgresql", "jdbc:postgresql:test",
                 "postgres", "*", 3);
    }
    catch (Exception e) {
      System.err.println("Is postgres running?\n" +
                         "Did you create database test?\n" +
                         "Is user `postgres', password `*' valid?\n");
      throw e;
    }

    db.inSession(
        AccessToken.root,       // FIXME
        new PoemTask() {
          public void run() {
            try {
              db.setLogSQL(true);

              Setter setter = new Setter(db.getAthingTable());
              setter.start();

              Getter getter = new Getter(db.getAthingTable());
              getter.start();

              Thread.sleep(1000);
              setter.signal(Setter.add);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              setter.signal(Setter.set);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              setter.signal(Setter.add);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              setter.signal(Setter.delete);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              setter.signal(Setter.delete);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);
              getter.signal(Boolean.TRUE);
              Thread.sleep(1000);

              setter.signal(null);
              getter.signal(null);
            }
            catch (Exception e) {
              throw new UnexpectedExceptionPoemException(e);
            }
          }
        });
  }
}
