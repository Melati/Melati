package org.melati.poem.test;

import java.util.*;
import org.melati.poem.*;
import org.melati.util.*;

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
          AThing t = (AThing)table.firstSelection(null);
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
          AThing t = (AThing)table.newPersistent();
            // FIXME - line removed by ttj to allow melait to compile
//          t.setWhatsit("whatsit" + (serial++));
          t.makePersistent();
        }
        else if (theSignal[0] == delete) {
          AThing t = (AThing)table.firstSelection(null);
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
            // FIXME - line removed by ttj to allow melait to compile
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

              Setter setter = new Setter(db.getAThingTable());
              setter.start();

              Getter getter = new Getter(db.getAThingTable());
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
