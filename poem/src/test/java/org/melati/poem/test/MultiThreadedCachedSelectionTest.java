/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;
import java.util.Random;

import org.melati.poem.AccessToken;
import org.melati.poem.CachedSelection;
import org.melati.poem.PoemTask;
import org.melati.poem.PoemThread;
import org.melati.poem.Table;
import org.melati.poem.UnexpectedExceptionPoemException;

/**
 * @author timp
 * @since 24 Jan 2007
 * 
 */
public class MultiThreadedCachedSelectionTest extends EverythingTestCase {

  private static String theResult;

  protected static EverythingDatabase db;

  public MultiThreadedCachedSelectionTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
    db = (EverythingDatabase)getDb();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  abstract static class PoemTaskThread extends Thread {

    Table<?> table;

    PoemTaskThread(Table<?> table) {
      this.table = table;
    }

    abstract void run_() throws Exception;

    public void run() {
      //System.err.println("MTCST thread name: "+ Thread.currentThread().getName());
      table.getDatabase().inSession(AccessToken.root, new PoemTask() {
        public void run() {
          try {
            run_();
          } catch (Exception e) {
            throw new UnexpectedExceptionPoemException(e);
          }
        }
      });
    }
  }

  static class Setter extends PoemTaskThread {

    static class Signal {
    }

    static final Signal 
      set = new Signal(){ public String toString() {return "set";}}, 
      add = new Signal(){ public String toString() {return "add";}},
      delete = new Signal() { public String toString() {return "delete";}};

    static Signal[] theSignal = new Signal[1];

    int serial = 0;

    Setter(Table<?> table) {
      super(table);
    }

    void signal(Signal signal) {
      System.err.println("Setter Signal changing from "
              +theSignal[0] + " to " + signal);
      synchronized (theSignal) {
        theSignal[0] = signal;
        theSignal.notifyAll();
      }
    }

    void run_() throws Exception {
      System.err.println("Setter thread name: "+ (int)Thread.currentThread().getName().charAt(0));
      for (;;) {
        synchronized (theSignal) {
          theSignal.wait();
        }

        if (theSignal[0] == set) {
          String fieldContent = "setWhatsit" + (serial++);
          StringField t = (StringField)table.firstSelection(null);
          if (t == null) {
            System.out.println("\n*** setter: nothing to set\n");
            synchronized (theResult) {
              theResult += "\nNULL" + fieldContent;
            }
          } else {
            System.out.println("\n*** setter: setting " + fieldContent);
            t.setStringfield(fieldContent);
            synchronized (theResult) {
              theResult += "\n" + fieldContent;
            }
          }
        } else if (theSignal[0] == add) {
          String fieldContent = "addedWhatsit" + (serial++);
          System.out.println("*** setter: adding " + fieldContent);
          StringField t = (StringField)table.newPersistent();
          t.setStringfield(fieldContent);
          t.makePersistent();
          synchronized (theResult) {
            theResult += "\n" + fieldContent;
          }
        } else if (theSignal[0] == delete) {
          StringField t = (StringField)table.firstSelection(null);
          if (t == null) {
            System.out.println("\n*** setter: nothing to delete");
            synchronized (theResult) {
              theResult += "\nempty delete";
            }
          } else {
            System.out.println("*** setter: deleting");
            t.delete_unsafe();
            System.out.println("*** setter: done deleting");
            synchronized (theResult) {
              theResult += "\ndelete";
            }
          }
        } else if (theSignal[0] == null) {
          System.out.println("\n*** setter done");
          break;
        } else fail("WTF");

        PoemThread.commit();
      }
      PoemThread.commit();
    }
  }

  static class Getter extends PoemTaskThread {

    static Object[] theSignal = new Object[1];

    CachedSelection<?> cachedSelection;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    Getter(Table<?> table) {
      super(table);
      cachedSelection = new CachedSelection(table, null, null, null);
    }

    void signal(Object signal) throws Exception {
      System.err.println("Getter Signal changing from "
              +theSignal[0] + " to " + signal);
      synchronized (theSignal) {
        theSignal[0] = signal;
        theSignal.notifyAll();
      }
    }

    void run_() throws Exception {
      System.err.println("Getter thread name: "+ (int)Thread.currentThread().getName().charAt(0));
      for (;;) {
        synchronized (theSignal) {
          theSignal.wait();
        }

        if (theSignal[0] == null) {
          System.out.println("\n*** getter done\n");
          signal("done");
          break;
        } else {
          System.out.println("\n*** getter:");
          Enumeration<?> them = cachedSelection.objects();

          System.out.print("** got\n");
          synchronized (theResult) {
            theResult += "\ngot";
          }

          synchronized (theResult) {
            while (them.hasMoreElements()) {
              String s = " "
                      + ((StringField)them.nextElement()).getStringfield();
              theResult += s;
              //System.out.print(s);
            }
          }
          //System.out.println("\n");
        }
        PoemThread.commit();
      }
      PoemThread.commit();
    }
  }

  /**
   * Setup threads and test them.
   * @throws Exception 
   */
  public void testThem() throws Exception {
    System.err.println("Start of test method");
    theResult = "";

    Setter setter = new Setter(db.getStringFieldTable());
    Getter getter = new Getter(db.getStringFieldTable());

    setter.start();
    getter.start();

    try {
      Thread.sleep(nap());
      setter.signal(Setter.add);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      setter.signal(Setter.set);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      setter.signal(Setter.add);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      setter.signal(Setter.delete);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      setter.signal(Setter.delete);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      getter.signal(Boolean.TRUE);
      Thread.sleep(nap());
      
      System.err.println(getDb().getFreeTransactionsCount() +  "!=" +  (getDb().transactionsMax() -1));
      setter.signal(null);
      getter.signal(null);
      int max = getDb().transactionsMax() -1;
      int trans = getDb().getFreeTransactionsCount();
      while(trans != max) { 
        Thread.sleep(1000);
        System.err.println("Slept because free transactions = " + trans);
        trans = getDb().getFreeTransactionsCount();
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
    System.err.println("End of test method");
    // System.out.println(result);
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

  private int nap() { 
    Random generator = new Random();
    int it = generator.nextInt(80);
    //System.err.println(it);
    return it;
  }
}
