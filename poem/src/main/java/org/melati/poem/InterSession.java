package org.melati.poem;

interface InterSession {

  static final Data nonexistent = new Data();

  Table getTable();
  Integer getTroid();
  Data dataForReading(Session session);
  Data dataForWriting(Session session);
  void delete(Session session);
  boolean valid();
}
