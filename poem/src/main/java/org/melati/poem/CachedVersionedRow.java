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

  public void setVersion(Session session, Version data) {
    super.setVersion(session, data);
    if (data != nonexistent)
      ((Data)data).dirty = true;
    table.notifyTouched((PoemSession)session, troid,
                        data == nonexistent ? null : (Data)data);
  }

  protected Version backingVersion(Session session) {
    Data data = table.dbData((PoemSession)session, troid);
    return data == null ? nonexistent : data; 
  } 

  protected int sessionsMax() {
    return getDatabase().sessionsMax();
  }

  /**
   * @return the row, or <TT>nonexistent</TT> if it doesn't exist
   */

  public Version versionForReading(Session session) {
    return super.versionForReading(session);
  }

  /**
   * @return an unaliased copy of the row, or <TT>nonexistent</TT>
   *         if it doesn't exist
   */

  public Version versionForWriting(Session session) {
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

  protected void writeDown(Session session, Version version) {
    Data data = (Data)version;
    if (data != nonexistent && data.dirty)
      table.writeDown((PoemSession)session, troid, data);
  }

  public void commit(Session session) {
    super.commit(session);
    table.notifyTouched(null, troid, (Data)versionForReading(null));
  }
}
