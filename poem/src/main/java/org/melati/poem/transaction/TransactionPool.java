package org.melati.util;

public interface TransactionPool {
  int transactionsMax();
  Transaction transaction(int index);
}
