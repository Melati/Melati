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

  private org.melati.util.Transaction _tx = null;

  public void begin() 
  {
    if (_tx != null) throw new org.odmg.TransactionInProgressException();
 
    _tx = _db.getPoemDatabase().transaction(1);  // any issues with always using 1?
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
