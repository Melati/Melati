package org.melati.util;

interface CachedVersionedObject extends VersionedObject {
  void uncacheVersion(Session session);

  void writeDown(Session session);
  void commit(Session session);
  void unSee(Session session);
}
