package org.melati.poem;

public abstract class CacheNode {
  CacheNode nextMRU = null;
  CacheNode prevMRU = null;

  synchronized void putBefore(CacheNode nextMRU) {
    if (this.nextMRU != null)
      this.nextMRU.prevMRU = prevMRU;

    if (prevMRU != null)
      prevMRU.nextMRU = this.nextMRU;

    if (nextMRU != null) {
      if (nextMRU.prevMRU != null)
        nextMRU.prevMRU.nextMRU = this;
      prevMRU = nextMRU.prevMRU;
      nextMRU.prevMRU = this;
    }
    else
      prevMRU = null;

    this.nextMRU = nextMRU;
  }

  public abstract boolean drop();
  public abstract void uncacheContents();
  public abstract int analyseContents();
  protected abstract Object getKey();
}
