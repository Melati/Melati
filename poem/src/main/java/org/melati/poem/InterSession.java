package org.melati.poem;

class DummyFields extends Fields {
}

/**
 * I think we possibly still need this.  It looks wasteful but the
 * alternative is having a hashtable per-session for things that have
 * changed in the session.
 */

class InterSession extends CacheNode {

  static final DummyFields
      notInCache = new DummyFields(),
      nonexistent = new DummyFields();

  Fields committedFields = notInCache;
  Persistent[] versions = null;

  private Integer troid;

  InterSession(Integer troid) {
    this.troid = troid;
  }

  public boolean isDroppable() {
    return versions != null;
  }

  public synchronized void uncacheContents() {
    committedFields = notInCache;
    // there is actually little point in uncacheing the
    // session-specific data since it ought to go away fairly soon
    // anyway
  }

  protected Object getKey() {
    return troid;
  }
}
