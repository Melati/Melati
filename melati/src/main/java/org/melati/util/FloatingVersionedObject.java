package org.melati.util;

public abstract class FloatingVersionedObject extends AbstractVersionedObject {
  
  protected abstract Version backingVersion(Session session);

  protected abstract int sessionsMax();

  public Object getKey() { return null; }
  public void delete(Session session) {}
  public void writeDown(Session session, Version version) {}
}
