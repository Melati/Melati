package org.melati.poem;

import org.melati.util.*;

abstract class PoemFloatingVersionedObject extends FloatingVersionedObject {

  private Database database;

  PoemFloatingVersionedObject(Database database) {
    this.database = database;
  }

  protected abstract Version backingVersion(Session session);

  protected int sessionsMax() {
    return database.sessionsMax();
  }
}
