package org.melati.poem;

public class Persistent {

  private Table table;
  private Integer troid;
  private SessionToken sessionToken;

  // if `interSession' is null we are free-floating, either because we
  // are being initialised (if flags_initialising) or because we are
  // uncached; in the latter case we must cache ourselves on write

  private InterSession interSession;

  // if `fields' is null they haven't been accessed yet and can safely
  // be initialised to the committed version on read or write

  private Fields fields;
  private int flags = 0;
  private static final int
      flags_knownCanRead  = 1 << 0,
      flags_knownCanWrite = 1 << 1,
      flags_touched       = 1 << 2,
      flags_initialising  = 1 << 3;

  protected Persistent() {
  }

  final void init(Table table, Integer troid,
                  SessionToken sessionToken, InterSession interSession) {
    this.table = table;
    this.troid = troid;
    this.sessionToken = sessionToken;
    this.interSession = interSession;
    if (interSession == null)
      fields = newFields();
    else
      fields = null;
  }

  final void initForInit(Table table, Integer troid,
                         SessionToken sessionToken) {
    init(table, troid, sessionToken, null);
    flags |= flags_initialising;
  }

  final SessionToken getSessionToken() {
    return sessionToken;
  }

  protected Fields _newFields() {
    return new Fields();
  }

  public final Fields newFields() {
    Fields them = _newFields();
    them.extras = new Object[getTable().getExtrasCount()];
    return them;
  }

  public final Table getTable() {
    return table;
  }

  public final Database getDatabase() {
    return getTable().getDatabase();
  }

  final Integer getTroid() {
    return troid;
  }

  private void assertRightSession() {
    if (sessionToken == null)
      throw new SessionFinishedPoemException();
    if (sessionToken.thread != Thread.currentThread())
      throw new WrongSessionPoemException(
          sessionToken, PoemThread.sessionToken(), getTable());
  }

  final Fields getFields() {
    return fields;
  }

  final void setInterSession(InterSession interSession) {
    flags &= ~flags_initialising;
    this.interSession = interSession;
  }

  protected Capability getCanRead(Fields fields) {
    Column canReadColumn = getTable().getCanReadColumn();
    return
        canReadColumn == null ? null :
            (Capability)canReadColumn.getValue(fields);
  }

  protected void assertCanRead(Fields fields, AccessToken token)
      throws AccessPoemException {
    Capability canRead = getCanRead(fields);
    if (canRead == null)
      canRead = getTable().getCanRead();
    if (!token.givesCapability(canRead))
      throw new AccessPoemException(token, canRead);
  }

  protected Capability getCanWrite(Fields fields) {
    Column canWriteColumn = getTable().getCanWriteColumn();
    return
        canWriteColumn == null ? null :
            (Capability)canWriteColumn.getValue(fields);
  }

  protected void assertCanWrite(Fields fields, AccessToken token)
      throws AccessPoemException {
    Capability canWrite = getCanWrite(fields);
    if (canWrite == null)
      canWrite = getTable().getCanWrite();
    if (!token.givesCapability(canWrite))
      throw new AccessPoemException(token, canWrite);
  }

  /**
   * FIXME not `synchronized' (though I think the worst that can
   * happen is it's fetched twice from the DB).
   */

  private Fields committedFields() {
    Fields them = interSession.committedFields;
    if (them != InterSession.notInCache)
      return them;
    else {
      Fields got = newFields();
      if (!getTable().load(sessionToken.session, getTroid(), got))
        got = InterSession.nonexistent;

      synchronized (interSession) {
        if (interSession.committedFields == InterSession.notInCache)
          interSession.committedFields = got;
        return interSession.committedFields;
      }
    }
  } 

  private Fields dbSessionFields() {
    Fields got = newFields();
    return
        getTable().load(sessionToken.session, getTroid(), got) ?
            got : InterSession.nonexistent;
  }

  final Fields fieldsUnchecked() {
    Fields them = fields;
    if (them == null) {
      Fields got = committedFields();
      synchronized (this) {
        if (fields == null)
          fields = got;
        else
          them = fields;
      }
      sessionToken.session.notifySeen(this);
    }
    else if (them == InterSession.notInCache) {
      Fields got = dbSessionFields();
      synchronized (this) {
        if (fields == InterSession.notInCache)
          fields = got;
        else
          them = fields;
      }
    }

    if (them == InterSession.nonexistent)
      throw new RowDisappearedPoemException(getTable(), getTroid());

    return them;
  }

  protected final Fields _fieldsForReading() throws AccessPoemException {
    assertRightSession();

    if (interSession == null)
      // own copy
      return fields;

    Fields them = fieldsUnchecked();

    if ((flags & flags_knownCanRead) == 0) {
      assertCanRead(them, sessionToken.accessToken);
      flags |= flags_knownCanRead;
    }

    return them;
  }

  synchronized final void markTouched() {
    if ((flags & flags_touched) == 0) {
      flags |= flags_touched;
      sessionToken.session.notifyTouched(this);
    }
  }

  protected final Fields _fieldsForWriting() throws AccessPoemException {
    assertRightSession();

    if (interSession == null) {
      if ((flags & flags_initialising) == 0) {
        // get ourselves into the cache
        throw new Error("FIXME");
      }
      // own copy
      return fields;
    }

    Fields them = fieldsUnchecked();

    synchronized (this) {
      if ((flags & flags_touched) == 0) {
        fields = them = (Fields)them.clone(); 
        flags |= flags_touched;
        sessionToken.session.notifyTouched(this);
      }
    }

    if ((flags & flags_knownCanWrite) == 0) {
      assertCanWrite(them, sessionToken.accessToken);
      flags |= flags_knownCanWrite;
    }

    return them;
  }

  public final Fields _fieldsSnapshot() throws AccessPoemException {
    return (Fields)_fieldsForReading().clone();
  }

  synchronized final void unSee() {
    if (sessionToken != null && interSession != null) {
      synchronized (interSession) {
        Persistent[] versions = interSession.versions;
        // shouldn't be null since we exist and are valid
        versions[sessionToken.session.getIndex()] = null;
        // FIXME this is relatively expensive: do we bother?
        int v;
        for (v = versions.length - 1; v >= 0; --v)
          if (versions[v] != null) break;
        if (v < 0)
          interSession.versions = null;
      }
    }

    sessionToken = null;
    interSession = null;
  }

  final void commit() {
    Fields newCommittedVersion = fields;
    if (newCommittedVersion != null)
      interSession.committedFields = newCommittedVersion;
  }

  public Object getIdent(String name)
      throws AccessPoemException, NoSuchColumnPoemException {
    return getTable().getColumn(name).getIdent(this);
  }

  public void setIdent(String name, Object ident)
      throws AccessPoemException, ValidationPoemException,
             NoSuchColumnPoemException {
    getTable().getColumn(name).setIdent(this, ident);
  }

  public Object getValue(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).getValue(this);
  }

  public void setValue(String name, Object value)
      throws NoSuchColumnPoemException, ValidationPoemException,
             AccessPoemException {
    getTable().getColumn(name).setValue(this, value);
  }
}
