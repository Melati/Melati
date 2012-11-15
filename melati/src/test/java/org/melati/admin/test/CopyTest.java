package org.melati.admin.test;


import junit.framework.TestCase;

import org.melati.LogicalDatabase;
import org.melati.admin.AnticipatedException;
import org.melati.admin.Copy;
import org.melati.poem.PoemTask;
import org.melati.poem.test.Dynamic;
import org.melati.poem.test.EverythingDatabase;
import org.melati.poem.test.StringField;

/**
 * Test Copy.
 */
public class CopyTest extends TestCase {

  public CopyTest(String name) {
    super(name);
  }

  public void testCopyDissimilarDbs() { 
    try { 
      Copy.copy("everything", "melatitest");
      fail("Should have bombed");
    } catch (AnticipatedException e) { 
      e = null;
    }
  }
  
  public void testCopyOntoItself() { 
    try { 
      Copy.copy("everything", "everything");
      fail("Should have bombed");
    } catch (AnticipatedException e) { 
      e = null;
    }
  }
  
  public void testRecordsActuallyCopied() {
    final EverythingDatabase edb = (EverythingDatabase)LogicalDatabase.getDatabase("everything");
    final EverythingDatabase edb2 = (EverythingDatabase)LogicalDatabase.getDatabase("everything2");
    edb.inSessionAsRoot( 
        new PoemTask() {
          public void run() {
          StringField p = (StringField)(edb).getStringFieldTable().newPersistent();
            p.setStringfield("1");
            p.makePersistent();
            assertEquals(1, edb.getStringFieldTable().count());
          }
        });
    Copy.copy(edb, edb2);
    edb2.inSessionAsRoot( 
        new PoemTask() {
          public void run() {
            assertEquals(1, edb2.getStringFieldTable().count());
          }
        });
  }
    
  public void  testRecordsNotCopiedIfAnyPresent() {
    final EverythingDatabase edb = (EverythingDatabase)LogicalDatabase.getDatabase("everything");
    final EverythingDatabase edb2 = (EverythingDatabase)LogicalDatabase.getDatabase("everything2");
    edb.inSessionAsRoot( 
        new PoemTask() {
          public void run() {
            Dynamic p = (Dynamic)(edb).getDynamicTable().newPersistent();
            p.setName("1");
            p.makePersistent();
            assertEquals(3, edb.getDynamicTable().count());
          }
        });
    Copy.copy(edb, edb2);
    edb2.inSessionAsRoot( 
        new PoemTask() {
          public void run() {
            assertEquals(2, edb2.getDynamicTable().count());
          }
        });
  }
    
}
