package org.melati.poem;

public interface TableListener {
  void notifyTouched(PoemSession session, Table table, Integer troid,
                     Data data);
}
