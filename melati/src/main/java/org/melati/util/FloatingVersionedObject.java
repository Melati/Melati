package org.melati.util;

public abstract class FloatingVersionedObject extends AbstractVersionedObject {
  
  protected abstract Version backingVersion(Session session);

  protected abstract int sessionsMax();

  /**
   * Dummy method to satisfy <TT>CacheNode</TT> (want multiple inheritance
   * really).
   */

  public Object getKey() { return null; }

  /**
   * Dummy method to satisfy <TT>CacheNode</TT> (want multiple inheritance
   * really).
   */

  public void delete(Session session) {}

  /**
   * Dummy method to satisfy <TT>CacheNode</TT> (want multiple inheritance
   * really).
   */

  public void writeDown(Session session, Version version) {}
}
