package org.melati.util;

public class TransactionedSerial extends Transactioned {

  private long serial = 0L;

  public TransactionedSerial(TransactionPool transactionPool) {
    super(transactionPool);
  }

  protected void load(Transaction transaction) {
    ++serial;
  }

  protected boolean upToDate(Transaction transaction) {
    return true;
  }

  protected void writeDown(Transaction transaction) {
  }

  // 
  // ---------------------
  //  TransactionedSerial
  // ---------------------
  // 

  public long current(Transaction transaction) {
    readLock(transaction);
    return serial;
  }

  public void increment(Transaction transaction) {
    writeLock(transaction);
    increment_unlocked();
  }

  public void increment_unlocked() {
    ++serial;
  }
}
