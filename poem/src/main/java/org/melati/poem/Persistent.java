package org.melati.poem;

import java.util.*;

public class Persistent {

  private InterSession interSession;
  private AccessToken clearedToken;
  private boolean knownCanRead = false, knownCanWrite = false;

  synchronized final void init(CacheInterSession interSession) {
    this.interSession = interSession;
    clearedToken = null;
    knownCanRead = false;
    knownCanWrite = false;
  }

  synchronized final void initForConstruct(
      ConstructionInterSession interSession, AccessToken accessToken) {
    this.interSession = interSession;
    clearedToken = accessToken;
    knownCanRead = true;
    knownCanWrite = true;
  }

  public final Table getTable() {
    return interSession.getTable();
  }

  public final Database getDatabase() {
    return getTable().getDatabase();
  }

  final Integer troid() {
    return interSession.getTroid();
  }

  public final Integer getTroid() throws AccessPoemException {
    _dataForReading();
    return troid();
  }

  public boolean getReadable() {
    try {
      _dataForReading();
      return true;
    }
    catch (AccessPoemException e) {
      return false;
    }
  }

  private synchronized InterSession interSession() {
    // FIXME what happens if:
    //   we find interSession is valid but relinquish the lock for a moment
    //   before calling its dataForReading?
    if (!interSession.valid())
      interSession =
          interSession.getTable().interSession(interSession.getTroid());
    return interSession;
  }

  protected Capability getCanRead(Data data) {
    Column canReadColumn = getTable().canReadColumn();
    return
        canReadColumn == null ? null :
            (Capability)canReadColumn.getValue(data);
  }

  protected void assertCanRead(Data data, AccessToken token)
      throws AccessPoemException {
    // FIXME optimise this ...
    if (!(clearedToken == token && knownCanRead)) {
      Capability canRead = getCanRead(data);
      if (canRead == null)
        canRead = getTable().getDefaultCanRead();
      if (canRead != null) {
        if (!token.givesCapability(canRead))
          throw new AccessPoemException(token, canRead);
        if (clearedToken != token)
          knownCanWrite = false;
        clearedToken = token;
        knownCanRead = true;
      }
    }
  }

  protected Capability getCanWrite(Data data) {
    Column canWriteColumn = getTable().canWriteColumn();
    return
        canWriteColumn == null ? null :
            (Capability)canWriteColumn.getValue(data);
  }

  protected void assertCanWrite(Data data, AccessToken token)
      throws AccessPoemException {
    if (!(clearedToken == token && knownCanWrite)) {
      Capability canWrite = getCanWrite(data);
      if (canWrite == null)
        canWrite = getTable().getDefaultCanWrite();
      if (canWrite != null) {
        if (!token.givesCapability(canWrite))
          throw new AccessPoemException(token, canWrite);
        if (clearedToken != token)
          knownCanRead = false;
        clearedToken = token;
        knownCanWrite = true;
      }
    }
  }

  final Data dataUnchecked(Session session)
      throws RowDisappearedPoemException {
    Data data = interSession().dataForReading(session);
    if (data == InterSession.nonexistent)
      throw new RowDisappearedPoemException(getTable(), troid());
    return data;
  }

  protected final Data _dataForReading() throws AccessPoemException {
    SessionToken sessionToken = PoemThread.sessionToken();
    Data data = dataUnchecked(sessionToken.session);
    assertCanRead(data, sessionToken.accessToken);
    return data;
  }

  protected final Data _dataForWriting() throws AccessPoemException {
    SessionToken sessionToken = PoemThread.sessionToken();
    assertCanWrite(dataUnchecked(sessionToken.session),
                   sessionToken.accessToken);

    // FIXME this is really gross ... necessary because assertCanWrite can
    // provoke a premature writeDown

    Data data = interSession.dataForWriting(sessionToken.session);
    if (data == InterSession.nonexistent)
      throw new RowDisappearedPoemException(getTable(), troid());
    return data;
  }

  public final Data _dataSnapshot() throws AccessPoemException {
    return (Data)_dataForReading().clone();
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

  public final void setIdentString(String name, String string)
      throws AccessPoemException, ParsingPoemException,
             ValidationPoemException, NoSuchColumnPoemException {
    Column column = getTable().getColumn(name);
    column.setIdent(this, column.getType().identOfString(string));
  }

  public final void deleteAndCommit()
      throws AccessPoemException, DeletionIntegrityPoemException {

    getDatabase().beginExclusiveLock();
    try {
      SessionToken sessionToken = PoemThread.sessionToken();
      assertCanWrite(dataUnchecked(sessionToken.session),
                     sessionToken.accessToken);

      Enumeration refs = getDatabase().referencesTo(this);
      if (refs.hasMoreElements())
        throw new DeletionIntegrityPoemException(this, refs);

      interSession().delete(sessionToken.session);
      if (sessionToken.session != null)
        sessionToken.session.commit();
    }
    finally {
      getDatabase().endExclusiveLock();
    }
  }

  public Persistent duplicated() throws AccessPoemException {
    return getTable().create(_dataSnapshot());
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

  public final Field getField(String name) {
    return getTable().getColumn(name).asField(this);
  }

  public Enumeration elements() {
    return new FieldsEnumeration(this);
  }

  public String toString() {
    return getTable().getName() + "/" + troid();
  }
}
