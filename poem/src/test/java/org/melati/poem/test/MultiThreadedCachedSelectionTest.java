/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.AccessToken;
import org.melati.poem.CachedSelection;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.Table;
import org.melati.poem.UnexpectedExceptionPoemException;

/**
 * FIXME this test currently breaks everything.
 * It worked once, but started shutting sessions.
 * @author timp
 * @since 24 Jan 2007
 *
 */
public class MultiThreadedCachedSelectionTest extends PoemTestCase {

  private static String result;
  protected TestDatabase db;
  
  /**
   * Constructor for CachedSelectionTest.
   * @param arg0
   */
  public MultiThreadedCachedSelectionTest(String arg0) {
    super(arg0);
    setDbName("poemtest");
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    setDbName("poemtest");
    super.setUp();
    db = (TestDatabase)getDb();
  }


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
          String fieldContent = "setWhatsit" + (serial++);
          StringField t = (StringField)table.firstSelection(null);
          if (t == null){
            System.err.println("\n*** setter: nothing to set\n");
            synchronized(result) {
              result += "\nNULL" + fieldContent;            
            }
          } else {
            System.err.println("\n*** setter: setting " + fieldContent);
            t.setStringfield(fieldContent);
            synchronized(result) {
              result += "\n" + fieldContent;
            }
          }
        }
        else if (theSignal[0] == add) {
          String fieldContent = "addedWhatsit" + (serial++);
          System.err.println("*** setter: adding " + fieldContent);
          StringField t = (StringField)table.newPersistent();
          t.setStringfield(fieldContent);
          t.makePersistent();
          synchronized(result) {
            result += "\n" + fieldContent;
          }
        }
        else if (theSignal[0] == delete) {
          StringField t = (StringField)table.firstSelection(null);
          if (t == null) {
            System.err.println("\n*** setter: nothing to delete");
            synchronized(result) {
              result += "\nempty delete";
            }
          } else {
            System.err.println("*** setter: deleting");
            t.delete_unsafe();
            System.err.println("*** setter: done deleting");
            synchronized(result) {
              result += "\ndelete";
            }
          }
        }
        else if (theSignal[0] == null) {
          System.err.println("\n*** setter done");
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
          System.err.println("\n*** getter:");
          Enumeration them = cachedSelection.objects();

          System.err.print("** got\n");
          synchronized(result) {
            result += "\ngot";
          }

          synchronized(result) {
            while (them.hasMoreElements()) {
              String s = " " + ((StringField)them.nextElement()).getStringfield();
              result += s;
              System.err.print(s);
            }
          }
          System.err.println("\n");
        }

        PoemThread.commit();
      }
    }
  }

  /**
   * Setup threads and test them. 
   */
  public void testThem() {

    result = "";
              //db.setLogSQL(true);

              Setter setter = new Setter(db.getStringFieldTable());
              setter.start();

              Getter getter = new Getter(db.getStringFieldTable());
              getter.start();

              // fails at a nap of 18ms for postgresl and hsqldb
              // fails at a nap of 700ms for Access
              int nap = 100;
              try{
              Thread.sleep(nap);
              setter.signal(Setter.add);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              setter.signal(Setter.set);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              setter.signal(Setter.add);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              setter.signal(Setter.delete);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              setter.signal(Setter.delete);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);
              getter.signal(Boolean.TRUE);
              Thread.sleep(nap);

              setter.signal(null);
              getter.signal(null);
              } catch (Exception e) {
                e.printStackTrace();
                fail();
              }
              
    System.err.println(result);
    /* 
    assertEquals("\naddedWhatsit0\n" + 
                 "got addedWhatsit0\n" + 
                 "got addedWhatsit0\n" + 
                 "setWhatsit1\n" + 
                 "got setWhatsit1\n" + 
                 "got setWhatsit1\n" + 
                 "addedWhatsit2\n" + 
                 "got setWhatsit1 addedWhatsit2\n" + 
                 "got setWhatsit1 addedWhatsit2\n" + 
                 "delete\n" + 
                 "got addedWhatsit2\n" + 
                 "got addedWhatsit2\n" + 
                 "delete\n" + 
                 "got\n" + 
                 "got",result);
    */
    // TODO think of an appropriate assertion
  }
  

}
