package org.melati.poem;

public interface TableListener {
  void notifyTouched(PoemTransaction transaction,
		     Table table, Persistent persistent);
  void notifyUncached(Table table);
}
