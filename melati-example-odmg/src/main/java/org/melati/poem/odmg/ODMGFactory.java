package org.melati.poem.odmg;

/** Factory object for getting new ODMG/poem related objects **/
public class ODMGFactory
{
  private ODMGFactory() {}

  public static final org.odmg.Database getNewDatabase() { return Database.getNewDatabase(); }

  public static final org.odmg.Transaction getNewTransaction(org.odmg.Database db) { return Transaction.getNewTransaction(db); }

}
