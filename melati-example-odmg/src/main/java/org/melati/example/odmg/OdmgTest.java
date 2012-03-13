package org.melati.example.odmg;

import org.odmg.Database;
import org.odmg.Transaction;
import org.odmg.DCollection;
import java.util.Iterator;

/**
 * A command line application to test the ODMG API.
 *
 */

public final class OdmgTest {

  private OdmgTest() {}
  
  /**
   * Test runner.
   */
  @SuppressWarnings("unchecked")
public static void main(String[] argsIn)
    throws Exception {

    System.out.println("OdmgTest started:");

    Database db = org.melati.poem.odmg.ODMGFactory.getNewDatabase();
    System.out.println("Got a db = "+db);

    db.open("odmgplaying",Database.OPEN_READ_WRITE);

    Transaction tx = org.melati.poem.odmg.ODMGFactory.getNewTransaction(db);
    tx.begin();

    DCollection parents = (DCollection) db.lookup("parent");

    //clear out old crap
    parents.removeAll(parents);

    System.out.println("ADDING PARENTS");
    for (int i=0; i<10; i++) {
      Parent p = newParent();
      p.setName("parent-"+parents.size());
      System.out.println("Adding parent: "+p.getName()); 
      parents.add(p);
    }

    System.out.println("PARENTS DESCENDING");
    Iterator<Parent> iter = parents.select("order by name desc");
    while (iter.hasNext()) {
      Parent p = iter.next();
      System.out.println("Parent:"+p.getName());
    }

    System.out.println("PARENTS UNDER 5 ASCENDING");
    iter = parents.select("where name<'parent-5' order by name asc");
    while (iter.hasNext()) {
      Parent p = (Parent)iter.next();
      System.out.println("Parent:"+p.getName());
    }

    System.out.println("PARENTS UNDER 3 NO SORTING");
    iter = parents.select("name<'parent-3'");
    while (iter.hasNext()) {
      Parent p = (Parent)iter.next();
      System.out.println("Parent:"+p.getName());
    }

    System.out.println("PARENTS UNDER 2 WITH WHERE KEYWORD NO SORTING");
    iter = parents.select("where name<'parent-2'");
    while (iter.hasNext()) {
      Parent p = (Parent)iter.next();
      System.out.println("Parent:"+p.getName());
    }

    System.out.println("ALL PARENTS NO SORTING");
    iter = parents.select("");
    while (iter.hasNext()) {
      Parent p = (Parent)iter.next();
      System.out.println("Parent:"+p.getName());
    }

    tx.commit();
    
    System.out.println("OdmgTest done:");
  }

  private static Parent newParent()
    throws Exception {
    return (Parent)org.melati.poem.odmg.ODMGFactory.
                       getPoemDatabase().getTable("parent").newPersistent();
  }

}
