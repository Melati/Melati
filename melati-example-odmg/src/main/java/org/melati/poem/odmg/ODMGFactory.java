package org.melati.poem.odmg;

/** Factory object for getting new ODMG/poem related objects **/
public class ODMGFactory
{
  private ODMGFactory() {}

  private static final Database _db = Database.getNewDatabase();
  
  public static final org.odmg.Database getNewDatabase() { return _db; }
  
  public static final org.melati.poem.Database getPoemDatabase() 
    throws org.odmg.ODMGException
  { return _db.getPoemDatabase(); }

  public static final org.odmg.Transaction getNewTransaction(org.odmg.Database db) { return Transaction.getNewTransaction(db); }

}
