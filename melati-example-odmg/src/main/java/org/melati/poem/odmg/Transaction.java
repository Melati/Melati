package org.melati.poem.odmg;

class Transaction implements org.odmg.Transaction
{
  public static final String cvs = "$Id";

  private Database _db = null;

  static final Transaction getNewTransaction(org.odmg.Database db) { return new Transaction(db); }
  private Transaction(org.odmg.Database db)
  {
    _db = (Database)db;
  }

  //private org.melati.util.Transaction _tx = null;
  private Object _tx = null;

  public void begin() 
    throws org.odmg.ODMGRuntimeException
  {
    if (_tx != null) throw new org.odmg.TransactionInProgressException();

    try { 
      _tx = new Object();
		_db.getPoemDatabase().beginSession(org.melati.poem.AccessToken.root);  // any issues with always using 1?
    } catch (org.odmg.ODMGException exc) 
    { 
      throw new org.odmg.ODMGRuntimeException(exc.getMessage());
    }
  }

  public void commit() { throw new org.odmg.NotImplementedException(); }
  public void join() { throw new org.odmg.NotImplementedException(); }
  public void abort() { throw new org.odmg.NotImplementedException(); }
  public void checkpoint() { throw new org.odmg.NotImplementedException(); }
  public void leave() { throw new org.odmg.NotImplementedException(); }
  public void lock(Object obj, int code) { throw new org.odmg.NotImplementedException(); }
  public boolean tryLock(Object obj, int code) { throw new org.odmg.NotImplementedException(); }
  public boolean isOpen() { throw new org.odmg.NotImplementedException(); }
}
