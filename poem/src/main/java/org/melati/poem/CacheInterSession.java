package org.melati.poem;

/**
 * I think we possibly still need this.  It looks wasteful but the alternative
 * is having a hashtable per-session for things that have changed in the
 * session.
 */

final class CacheInterSession extends CacheNode implements InterSession {

  private static final Data notInCache = new Data();

  private Data committedVersion = notInCache;

  // `(seenMask & session.mask) != 0' iff `session' has seen the current
  // `committedVersion' and must be shielded from future changes

  private int seenMask = 0;

  // `(touchedMask & session.mask) != 0' iff `session' has changed
  // its version of the data.

  private int touchedMask = 0;

  // `versions[session.index] == version (!= null)' iff `session' has its own
  // copy of this row independent of `committedVersion'.  `version' can be
  // `nonexistent', or `notInCache' in which case its value must be retrieved
  // by an intra-transaction `SELECT'.  Different sessions' versions can alias
  // each other provided they have only so far been read and not written.
  //
  // Invariants:
  // versions[session.index] != null => (seenMask & session.mask) != 0
  // (touchedMask & session.mask) != 0 => versions[session.index] != null

  private Data[] versions = null;

  private Table table;
  private Integer troid;

  CacheInterSession(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }

  // 
  // ========
  //  Object
  // ========
  // 

  public String toString() {
    return
        "InterSession(" + table.getName() + "/" + String.valueOf(troid) + ")";
  }

  // 
  // ===========
  //  CacheNode
  // ===========
  // 

  public synchronized boolean drop() {
    if (seenMask == 0) {
      committedVersion = null;
      return true;
    }
    else
      return false;
  }

  public boolean valid() {
    return committedVersion != null;
  }

  public synchronized void uncacheContents() {
    committedVersion = notInCache;
    // there is actually little point in uncacheing the
    // session-specific data since it ought to go away fairly soon
    // anyway
  }

  public synchronized int analyseContents() {
    int size = committedVersion == notInCache ? 0 : 1;
    if (versions != null) {
      int datasThere = 0;
      for (int i = 0; i < versions.length; ++i) {
        Data data = versions[i];
        if (data != null) {
          ++datasThere;
          if (data != notInCache)
            ++size;
        }
      }
      if (datasThere == 0)
        System.err.println("*** ERROR " + this + " has an empty `versions'");
    }

    return size;
  }

  protected Object getKey() {
    return troid;
  }

  // 
  // ===================
  //  CacheInterSession
  // ===================
  // 

  public Table getTable() {
    return table;
  }

  public Database getDatabase() {
    return table.getDatabase();
  }

  public Integer getTroid() {
    return troid;
  }

  static Data copy(Data d) {
    return d == notInCache || d == nonexistent ? d : (Data)d.clone();
  } 

  synchronized Data cachedData(Session session) {
    Data data =
        session == null ? committedVersion :
        versions != null ? versions[session.index()] :
        null;

    return data == null || data == notInCache || data == nonexistent ?
               null : data;
  }

  synchronized void setData(Session session, Data data) {
    if (versions == null)
      versions = new Data[getDatabase().sessionsMax()];
    versions[session.index()] = data;
    session.notifyTouched(this);
    touchedMask |= session.mask();
    session.notifySeen(this);
    seenMask |= session.mask();
    if (data != nonexistent)
      data.dirty = true;
  }

  /**
   * @return the row, or <TT>InterSession.nonexistent</TT> if it doesn't exist
   */

  public synchronized Data dataForReading(Session session) {
    if (session != null && versions != null) {
      Data version = versions[session.index()];
      if (version == notInCache) {
        version = table.dbData(session, troid);
        return versions[session.index()] =
                   version == null ? nonexistent : version;
      }
      else if (version != null)
        return version;
    }

    if (committedVersion == notInCache) {
      committedVersion = table.dbCommittedData(troid);
      if (committedVersion == null)
        committedVersion = nonexistent;
    }

    if (session != null && (seenMask & session.mask()) == 0) {
      session.notifySeen(this);
      seenMask |= session.mask();
    }

    return committedVersion;
  }

  // must be called `synchronized(this)'

  private void setCommittedVersion(Data toCommit) {
    // Give any sessions that need it the old committed version to see
    // again in future, even if it's `notInCache' or `nonexistent'.

    if (seenMask != 0)
      for (int s = 0, mask = 1; s < versions.length; ++s, mask <<= 1)
        if ((seenMask & mask) != 0 && versions[s] == null)
          versions[s] = committedVersion;

    // Commit the new version, even if it's `notInCache' or `nonexistent'

    committedVersion = toCommit;
  }

  /**
   * @return an unaliased copy of the row, or <TT>InterSession.nonexistent</TT>
   *         if it doesn't exist
   */

  public synchronized Data dataForWriting(Session session) {
    Data version = dataForReading(session);

    if ((touchedMask & session.mask()) == 0) {
      if (versions == null)
        versions = new Data[getDatabase().sessionsMax()];
      version = versions[session.index()] = copy(version);
      session.notifyTouched(this);
      touchedMask |= session.mask();
    }

    if (version != notInCache && version != nonexistent)
      version.dirty = true;

    return version;
  }

  public synchronized void delete(Session session)
      throws DeletionIntegrityPoemException {
    table.delete(troid, session);
    if (session == null)
      setCommittedVersion(nonexistent);
    else {
      seenMask |= session.mask();
      session.notifySeen(this);
      touchedMask |= session.mask();
      if (versions == null)
        versions = new Data[getDatabase().sessionsMax()];
      versions[session.index()] = nonexistent;
    }
  }

  void writeDown(Session session) {
    Data data = null;

    synchronized (this) {
      if ((touchedMask & session.mask()) != 0)
        data = versions[session.index()];
    }

    if (data != null && data != notInCache && data != nonexistent &&
        data.dirty)
      table.writeDown(session, troid, data);
  }

  synchronized void unSee(Session session) {
    seenMask &= session.negMask();
    touchedMask &= session.negMask();
    if (seenMask == 0)
      versions = null;
    else if (versions != null)
      versions[session.index()] = null;
  }

  synchronized void commit(Session session) {
    if (versions != null) {
      if ((touchedMask & session.mask()) == 0)
        throw new PoemBugPoemException("committing touched version");
      seenMask &= session.negMask();
      Data toCommit = versions[session.index()];
      if (toCommit != null) {
        versions[session.index()] = null;
        setCommittedVersion(toCommit);
      }
    }

    unSee(session);
  }
}
