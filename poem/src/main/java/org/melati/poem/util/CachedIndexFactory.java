package org.melati.util;

import java.util.*;

public abstract class CachedIndexFactory implements IndexFactory {
  private Vector cache = new Vector();
  private static final Object nullFromFactory = new Object();

  protected abstract Object reallyGet(int index);

  public Object get(int index) {
    synchronized (cache) {
      if (cache.size() <= index) {
        cache.setSize(index + 1);
        Object it = reallyGet(index);
        cache.setElementAt(it == null ? nullFromFactory : it, index);
        return it;
      }
      else {
        Object it = cache.elementAt(index);
        if (it == null) {
          it = reallyGet(index);
          cache.setElementAt(it == null ? nullFromFactory : it, index);
          return it;
        }
        else if (it == nullFromFactory)
          return null;
        else
          return it;
      }
    }
  }

  public void invalidate(int index) {
    cache.setElementAt(null, index);
  }

  public void invalidate() {
    cache.removeAllElements();
  }
}
