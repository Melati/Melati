package org.melati.util;

public interface VersionedObject {
  Version versionForReading(Session session);
  Version versionForWriting(Session session);
  void delete(Session session);
}
