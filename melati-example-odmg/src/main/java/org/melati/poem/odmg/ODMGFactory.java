package org.melati.poem.odmg;

/** Factory object for getting new ODMG/poem related objects **/
public class ODMGFactory
{
  private ODMGFactory() {}

  private static Database _db = Database.getNewDatabase();
  
  public static final org.odmg.Database getNewDatabase() { return getDb(); }
  
  public static final org.melati.poem.Database getPoemDatabase() 
    throws org.odmg.ODMGException
  { return getDb().getPoemDatabase(); }

  public static final org.odmg.Transaction getNewTransaction(org.odmg.Database db) { return Transaction.getNewTransaction(db); }

  static final void resetDb()
  {
    _db = null;
  }

  private static final Database getDb()
  {
    if (_db == null)
    {
      synchronized (ODMGFactory.class)
      {
        if (_db == null)
	{
	  _db = Database.getNewDatabase();
	}
      }
    }
    return _db;
  }
}
