package org.melati.poem;

import org.melati.util.*;

/**
 * I think we possibly still need this.  It looks wasteful but the alternative
 * is having a hashtable per-session for things that have changed in the
 * session.
 */

final class CachedVersionedRow extends AbstractVersionedObject
    implements VersionedRow {

  private Table table;
  private Integer troid;

  CachedVersionedRow(Table table, Integer troid) {
    this.table = table;
    this.troid = troid;
  }

  // 
  // ========
  //  Object
  // ========
  // 

  public String toString() {
    return "CachedVersionedRow(" + table.getName() + "/" +
                                   String.valueOf(troid) + ")";
  }

  // 
  // ===========
  //  CacheNode
  // ===========
  // 

  public Object getKey() {
    return troid;
  }

  // 
  // =========================
  //  AbstractVersionedObject
  // =========================
  // 

  /**
   * Don't call this when there might be outstanding writes not yet written
   * down!
   */

  public void uncacheVersion(Session session) {
    super.uncacheVersion(session);
  }

  // 
  // ==============
  //  VersionedRow
  // ==============
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

  void versionKnownToBe(Session session, Version data) {
    super.setVersion(session, data);
    table.notifyTouched((PoemSession)session, troid,
                        data == nonexistent ? null : (Data)data);
  }

  public void setVersion(Session session, Version data) {
    versionKnownToBe(session, data);
    if (data != nonexistent)
      ((Data)data).dirty = true;
  }

  protected boolean upToDate(Session session, Version data) {
    return true;
  }

  protected Version backingVersion(Session session) {
    Data data = table.dbData((PoemSession)session, troid);
    return data == null ? nonexistent : data; 
  } 

  protected int sessionsMax() {
    return getDatabase().sessionsMax();
  }

  /**
   * Obtain a <TT>Data</TT> object corresponding to the value of the table row
   * in a given session, in which it is safe for you to make changes which will
   * eventually find their way into the database.  The row is marked to be
   * written out to the database (with <TT>UPDATE</TT>, or <TT>INSERT</TT> if
   * it's a new one) at the next <TT>session.writeDown()</TT>, for instance
   * when the session is committed.
   *
   * @param session	The session (transaction) in which the writes are
   *                    to be made.  The session associated with the running
   *                    thread can be obtained from
   *                    <TT>PoemThread.session()</TT>.
   *
   * @return a <TT>Data</TT> representing the row's fields, or
   *         <TT>nonexistent</TT> if it doesn't exist in <TT>session</TT>
   *
   * @throw WriteCommittedVersionException if <TT>session</TT> is null: 
   *            you are not allowed to change a POEM database outside an
   *            insulated transaction.  This is partly a matter of policy,
   *            and FIXME partly an oversight!
   *
   * @see PoemThread#session()
   * @see WriteCommittedVersionException
   */

  public Version versionForWriting(Session session)
      throws WriteCommittedVersionException {
    if (session == null)
      throw new WriteCommittedVersionException(this);

    Data data = (Data)super.versionForWriting(session);
    table.notifyTouched((PoemSession)session, troid, data);
    if (data != nonexistent)
      ((Data)data).dirty = true;
    return data;
  }

  public synchronized void delete(Session session)
      throws DeletionIntegrityPoemException {
    table.delete(troid, (PoemSession)session);
    setVersion(session, nonexistent);
  }

  /**
   * Write a version down into the backing store.
   */

  public void writeDown(Session session) {
    Data data = (Data)touchedVersion(session);
    if (data != null && data != nonexistent && data.dirty)
      table.writeDown((PoemSession)session, troid, data);
  }

  public void commit(Session session) {
    super.commit(session);
    table.notifyTouched(null, troid, (Data)versionForReading(null));
  }
}
