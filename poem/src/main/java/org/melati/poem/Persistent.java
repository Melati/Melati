package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class Persistent extends Transactioned implements Cloneable {
  private Table table;
  private Integer troid;	// or null if a floating object
  private AccessToken clearedToken;
  private boolean knownCanRead = false, knownCanWrite = false;

  boolean dirty = false;
  boolean exists = false;

  private Object[] extras = null;

  public Persistent(Table table, Integer troid) {
    super(table.getDatabase());
    this.table = table;
    this.troid = troid;
  }

  public Persistent() {
  }

  // 
  // ---------------
  //  Transactioned
  // ---------------
  // 

  protected void load(Transaction transaction) {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    table.load((PoemTransaction)transaction, this);
    // table will clear our dirty flag
  }

  protected boolean upToDate(Transaction transaction) {
    return true;
  }

  protected void writeDown(Transaction transaction) {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    table.writeDown((PoemTransaction)transaction, this);
    // table will clear our dirty flag
  }

  protected void writeLock(Transaction transaction) {
    if (troid != null) {
      super.writeLock(transaction);
      dirty = true;
      table.notifyTouched((PoemTransaction)transaction, this);
    }
  }

  /**
   * This is just to make this method available to <TT>Table</TT>.
   */

  protected void readLock(Transaction transaction) {
    super.readLock(transaction);
  }

  protected void commit(Transaction transaction) {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    super.commit(transaction);
  }

  protected void rollback(Transaction transaction) {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    super.rollback(transaction);
  }

  // 
  // ------------
  //  Persistent
  // ------------
  // 

  synchronized Object[] extras() {
    if (extras == null)
      extras = new Object[table.extrasCount()];
    return extras;
  }

  /**
   * The table from which the object comes.
   */

  public final Table getTable() {
    return table;
  }

  synchronized void setTable(Table table, Integer troid) {
    setTransactionPool(table.getDatabase());
    this.table = table;
    this.troid = troid;
  }


  /**
   * The database from which the object comes.  <I>I.e.</I>
   * <TT>getTable().getDatabase()</TT>.
   */

  public final Database getDatabase() {
    return table.getDatabase();
  }
  /**
   * FIXME This shouldn't be public because we don't in principle want people
   * to know even the troid of an object they aren't allowed to read.  However,
   * I think this information may leak out elsewhere.
   */

  public final Integer troid() {
    return troid;
  }

  /**
   * The object's troid.
   *
   * @return Every record (object) in a POEM database must have a
   *         troid (table row ID, or table-unique non-nullable integer primary
   *         key), often but not necessarily called <TT>id</TT>, so that it can
   *         be conveniently `named' for retrieval.
   *
   * @exception AccessPoemException
   *                if <TT>assertCanRead</TT> fails
   *
   * @see Table#getObject(java.lang.Integer)
   * @see #assertCanRead
   */

  public final Integer getTroid() throws AccessPoemException {
    assertCanRead();
    return troid();
  }

  // 
  // ================
  //  Access control
  // ================
  // 

  protected void readLock(SessionToken sessionToken)
      throws AccessPoemException {
    assertCanRead(sessionToken.accessToken);
    readLock(sessionToken.transaction);
  }

  protected void writeLock(SessionToken sessionToken)
      throws AccessPoemException {
    assertCanWrite(sessionToken.accessToken);
    writeLock(sessionToken.transaction);
  }

  protected void readLock() throws AccessPoemException {
    readLock(PoemThread.sessionToken());
  }

  protected void writeLock() throws AccessPoemException {
    writeLock(PoemThread.sessionToken());
  }

  /**
   * The capability required for reading the object's field values.  This is
   * used by <TT>assertCanRead</TT> (unless that's been overridden) to obtain a
   * <TT>Capability</TT> for comparison against the caller's
   * <TT>AccessToken</TT>.
   *
   * @return the capability specified by the record's <TT>canread</TT> field, or
   *         <TT>null</TT> if it doesn't have one or its value is SQL
   *         <TT>NULL</TT>
   *
   * @see #assertCanRead
   */

  protected Capability getCanRead() {
    Column canReadColumn = getTable().canReadColumn();
    return
        canReadColumn == null ? null :
            (Capability)canReadColumn.getValue(this);
  }

  /**
   * Check that you have read access to the object.  Which is to say: the
   * <TT>AccessToken</TT> associated with the POEM task executing in the
   * running thread confers the <TT>Capability</TT> required for inspecting the
   * object's fields.  The access token is set when the task is invoked using
   * <TT>Database.inSession</TT>.  The capability is determined by
   * <TT>getCanRead</TT>, which by default means the capability defined in the
   * record's <TT>canread</TT> field in the database.  If that's <TT>null</TT>,
   * the table's default <TT>canread</TT> capability is obtained using
   * <TT>getTable().getDefaultCanRead()</TT>.  If that is <TT>null</TT> as
   * well, access is granted unconditionally.
   *
   * <P>
   *
   * Although this check can in theory be quite time-consuming, in practice
   * this isn't a problem, because the most recent access token for which the
   * check succeeded is cached; repeat accesses from within the same transaction
   * are therefore quick.  And since the <TT>Table</TT> will always return a
   * fresh <TT>Persistent</TT> whenever it is interrogated, that's virtually
   * always the pattern, unless you explicitly store <TT>Persistent</TT>s in
   * cross-transaction data structures.
   *
   * <P>
   *
   * Application programmers can override this method to implement their own
   * programmatic access policies.  For instance, POEM's own <TT>TableInfo</TT>
   * class overrides it with an empty method in order to disable all read
   * protection on <TT>TableInfo</TT> objects.  More interestingly, you could
   * implement a check that depends on the values of the object's fields, as
   * given in the <TT>data</TT> argument: for example, you could allow read
   * access to an invoice record to its issuing and receiving parties.
   *
   * @param data        the field values of the object in the calling thread's
   *                    transaction (not used in the base implementation)
   * @param token       the access token on the basis of which readability is
   *                    being claimed
   *
   * @exception AccessPoemException if the check fails
   *
   * @see #getCanRead
   * @see Database#inSession
   * @see Table#getDefaultCanRead
   */

  protected void assertCanRead(AccessToken token)
      throws AccessPoemException {
    // FIXME!!!! this is wrong because token could be stale ...
    if (!(clearedToken == token && knownCanRead) && troid != null) {
      Capability canRead = getCanRead();
      if (canRead == null)
        canRead = getTable().getDefaultCanRead();
      if (canRead != null) {
        if (!token.givesCapability(canRead))
          throw new ReadPersistentAccessPoemException(this, token, canRead);
        if (clearedToken != token)
          knownCanWrite = false;
        clearedToken = token;
        knownCanRead = true;
      }
    }
  }

  public final void assertCanRead() throws AccessPoemException {
    assertCanRead(PoemThread.accessToken());
  }

  /**
   * Whether the object is readable by you.
   *
   * @see #assertCanRead
   */

  public final boolean getReadable() {
    try {
      assertCanRead();
      return true;
    }
    catch (AccessPoemException e) {
      return false;
    }
  }

  /**
   * The capability required for writing the object's field values.  This is
   * used by <TT>assertCanWrite</TT> (unless that's been overridden) to obtain a
   * <TT>Capability</TT> for comparison against the caller's
   * <TT>AccessToken</TT>.
   *
   * @return the capability specified by the record's <TT>canwrite</TT> field, or
   *         <TT>null</TT> if it doesn't have one or its value is SQL
   *         <TT>NULL</TT>
   *
   * @see #assertCanWrite
   */

  protected Capability getCanWrite() {
    Column canWriteColumn = getTable().canWriteColumn();
    return
        canWriteColumn == null ? null :
            (Capability)canWriteColumn.getValue(this);
  }

  /**
   * Check that you have write access to the object.  Which is to say: the
   * <TT>AccessToken</TT> associated with the POEM task executing in the
   * running thread confers the <TT>Capability</TT> required for updating the
   * object's fields.  The remarks made about <TT>assertCanRead</TT> apply
   * (<I>mutatis mutandis</I>) here as well.
   *
   * @see #assertCanRead
   * @see #getCanWrite
   * @see Table#getDefaultCanWrite
   */

  protected void assertCanWrite(AccessToken token)
      throws AccessPoemException {
    // FIXME!!!! this is wrong because token could be stale ...
    if (!(clearedToken == token && knownCanWrite) && troid != null) {
      Capability canWrite = getCanWrite();
      if (canWrite == null)
        canWrite = getTable().getDefaultCanWrite();
      if (canWrite != null) {
        if (!token.givesCapability(canWrite))
          throw new WritePersistentAccessPoemException(this, token, canWrite);
        if (clearedToken != token)
          knownCanRead = false;
        clearedToken = token;
        knownCanWrite = true;
      }
    }
  }

  void forceAccess(AccessToken accessToken) {
    clearedToken = accessToken;
    knownCanRead = true;
    knownCanWrite = true;
  }

  void closeAccess() {
    clearedToken = null;
    knownCanRead = false;
    knownCanWrite = false;
  }

  // 
  // ============================
  //  Reading and writing fields
  // ============================
  // 

  // 
  // --------
  //  Idents
  // --------
  // 

  /**
   * The `identifying value' of one of the object's fields.  This is the value
   * which is actually stored in the database, give as a basic Java type;
   * currently, the only fields for which this differs from the `true value'
   * returned from <TT>getValue</TT> are reference fields with type
   * <TT>ReferencePoemType</TT>.
   *
   * <P>
   *
   * If the field <TT><I>baz</I></TT> is defined in the DSD as part of a table
   * called <TT><I>foo</I></TT>, then the table's records will be represented
   * by an application-specialised subclass of <TT>Persistent</TT> called
   * <TT><I>Foo</I></TT> which provides a typed <TT>get<I>Baz</I></TT> method.
   * So the easiest way to be sure of your types is to predeclare any fields
   * you use in the DSD, use the typed field-access methods, and let the
   * compiler take the strain.  When working with generic <TT>Persistent</TT>s,
   * you probably want to use <TT>getField</TT>.
   *
   * <P>
   *
   * The value returned is relative to the transaction associated with the
   * calling thread, as set up by <TT>Database.inSession</TT>.  This means that
   * you never see the value of a field change in your transaction because of
   * another transaction's activities, unless you do a <TT>PoemThread.commit()</TT>
   * or a <TT>PoemThread.rollback()</TT>.  If you need to, you can store a
   * <TT>Persistent</TT> in a permanent data structure and access it in
   * different sessions over time---or even from concurrently running sessions,
   * though this may slow down access checking; each transaction will see the value
   * it expects.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @return The field's `identifying value'; this will be a <TT>String</TT>,
   *         <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT> or
   *         <TT>Date</TT> as appropriate.  If the field is a reference field,
   *         the result is an <TT>Integer</TT> giving the troid of the referee.
   *         If you want references to be resolved transparently to
   *         <TT>Persistent</TT>s, use <TT>getValue</TT>.  If you want a string
   *         representation of the field, use <TT>getIdentString</TT> or
   *         <TT>getValueString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getValue
   * @see #getIdentString
   * @see #getValueString
   * @see #getField
   * @see Database#inSession
   * @see PoemThread#commit
   * @see PoemThread#rollback
   * @see #assertCanRead
   */

  public Object getIdent(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).getIdent(this);
  }

  /**
   * A string representation of the `identifying value' of one of the object's
   * fields.  The value returned is relative to the transaction associated with
   * the calling thread, as set up by <TT>Database.inSession</TT>: see the
   * remarks made about <TT>getIdent</TT>.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @return Roughly, the string the underlying RDBMS would display if asked
   *         to show the field's value.  If you want reference fields to be
   *         represented by their referee's <TT>displayString()</TT> (by
   *         default, its primary display field) rather than by its troid, use
   *         <TT>getValueString</TT>.  If you want the field's value as an
   *         appropriate Java type like <TT>Integer</TT>, use <TT>getIdent</TT>
   *         or <TT>getValue</TT>---or an equivalent, but type-safe, method
   *         derived from the DSD.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getValueString
   * @see #getIdent
   * @see #getValue
   * @see #assertCanRead
   */

  public final String getIdentString(String name)
      throws AccessPoemException, NoSuchColumnPoemException {
    Column column = getTable().getColumn(name);
    return column.getType().stringOfIdent(column.getIdent(this));
  }

  /**
   * Set the `identifying value' of one of the record's fields.  This is the
   * value which is actually stored in the database, give as a basic Java type;
   * currently, the only fields for which this differs from the `true value'
   * expected by <TT>setValue</TT> are reference fields with type
   * <TT>ReferencePoemType</TT>.
   *
   * <P>
   *
   * If the field <TT><I>baz</I></TT> is defined in the DSD as part of a table
   * called <TT><I>foo</I></TT>, then the table's records will be represented
   * by an application-specialised subclass of <TT>Persistent</TT> called
   * <TT><I>Foo</I></TT> which provides a typed <TT>set<I>Baz</I></TT>
   * method.  So the easiest way to be sure of your types is to predeclare any
   * fields you use in the DSD, use the typed field-access methods, and let the
   * compiler take the strain.  When working with generic <TT>Persistent</TT>s,
   * you probably mean <TT>setIdentString</TT> anyway.
   *
   * <P>
   *
   * The change you make to the field's value will only be visible to the
   * calling thread, until it successfully completes the task started by
   * <TT>Database.inSession</TT>, or does an explicit
   * <TT>PoemThread.commit()</TT>.  Up to that point the change can be undone
   * by calling <TT>PoemThread.rollback()</TT>, and will be undone
   * automatically if the task terminates with an uncaught exception.
   *
   * <P>
   *
   * In fact, your changes are not written down to the database, even relative
   * to an uncommitted transaction, until it's actually necessary.  So multiple
   * calls to <TT>setIdent</TT> and relatives will not cause multiple SQL
   * <TT>UPDATE</TT>s to be issued.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @param ident       The new value for the field: a <TT>String</TT>,
   *                    <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT> or
   *                    <TT>Date</TT> as appropriate.  If the field is a
   *                    reference field: an <TT>Integer</TT> giving the troid
   *                    of the referee.  If you want to pass referees as actual
   *                    <TT>Persistent</TT>s, use <TT>setValue</TT>.  If you
   *                    want to set the field from a string representation
   *                    (<I>e.g.</I> typed in by the user), use
   *                    <TT>setIdentString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have write access to the
   *                object (see <TT>assertCanWrite</TT>)
   * @exception TypeMismatchPoemException
   *                if <TT>ident</TT> is of the wrong type; it's easiest to use
   *                DSD-derived typed versions of this method
   * @exception ValidationPoemException
   *                if <TT>ident</TT> is not a valid value for the field
   *                (<I>e.g.</I> a string is too long)
   *
   * @see #setValue
   * @see #setIdentString
   * @see #assertCanWrite
   * @see Database#inSession
   * @see PoemThread#commit
   * @see PoemThread#rollback
   */

  public void setIdent(String name, Object ident)
      throws NoSuchColumnPoemException, AccessPoemException,
             ValidationPoemException {
    getTable().getColumn(name).setIdent(this, ident);
  }

  /**
   * Set the `identifying value' of one of the record's fields from a string
   * representation.  The remarks about sessions (transactions) and DSD-derived
   * type-safe methods made for <TT>setIdent</TT> apply here too.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @param string      A string that will be parsed to obtain the new value
   *                    for the field.  If it's a reference field, this should
   *                    be a decimal representation of the referee's troid.  If
   *                    you want to set fields to values defined by appropriate
   *                    Java types, use <TT>setIdent</TT> or <TT>setValue</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have write access to the
   *                object (see <TT>assertCanWrite</TT>)
   * @exception ParsingPoemException
   *                if <TT>string</TT> doesn't parse as a value of the
   *                appropriate type
   * @exception ValidationPoemException
   *                if <TT>string</TT> parses to an invalid value for the field
   *                (<I>e.g.</I> it's too wide)
   *
   * @see #setIdent
   * @see #setValue
   * @see #assertCanWrite
   */

  public final void setIdentString(String name, String string)
      throws NoSuchColumnPoemException, AccessPoemException,
             ParsingPoemException, ValidationPoemException {
    Column column = getTable().getColumn(name);
    column.setIdent(this, column.getType().identOfString(string));
  }

  // 
  // --------
  //  Values
  // --------
  // 

  /**
   * The `true value' of one of the object's fields.  This is the
   * fully-interpreted value rather than the one actually stored in the
   * database; currently, the only fields for which this differs from the
   * `identifying value' return from <TT>getIdent</TT> are reference fields
   * with type <TT>ReferencePoemType</TT>.
   *
   * <P>
   *
   * The value returned is relative to the transaction associated with the
   * calling thread, as set up by <TT>Database.inSession</TT>: see the remarks
   * made about <TT>getIdent</TT>.
   *
   * <P>
   *
   * The easiest way to be sure of your types is to predeclare any fields you
   * use in the DSD, or use <TT>getField</TT>.  Again, see the remarks made
   * about <TT>getIdent</TT>.
   *
   * @return The field's `true value'; this will be a <TT>String</TT>,
   *         <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT>,
   *         <TT>Date</TT>, or, if the field is a reference field, a
   *         <TT>Persistent</TT> representing the referee.  If you just want to
   *         see referees' troids, use <TT>getIdent</TT>.  If you want a string
   *         representation of the field, use <TT>getIdentString</TT> or
   *         <TT>getValueString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getIdent
   * @see #getIdentString
   * @see #getValueString
   * @see #getField
   * @see #assertCanRead
   */

  public Object getValue(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).getValue(this);
  }

  /**
   * A string representation of the `true value' of one of the object's fields.
   * The value returned is relative to the transaction associated with the
   * calling thread, as set up by <TT>Database.inSession</TT>: see the remarks
   * made about <TT>getIdent</TT>.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @return The string the underlying RDBMS would display if asked
   *         to show the field's value, except that reference fields are
   *         represented by their referee's <TT>displayString()</TT> (by
   *         default, its primary display field) rather than by its troid.  If
   *         you want to see troids instead, use <TT>getIdentString</TT>.  If
   *         you want the field's value as an appropriate Java type like
   *         <TT>Integer</TT>, use <TT>getIdent</TT> or <TT>getValue</TT>---or
   *         an equivalent, but type-safe, method derived from the DSD.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getIdentString
   * @see #getIdent
   * @see #getValue
   * @see #assertCanRead
   * @see #displayString
   */

  public final String getValueString(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    Column column = getTable().getColumn(name);
    return column.getType().stringOfValue(column.getValue(this));
  }

  /**
   * Set the `true value' of one of the record's fields.  Like
   * <TT>setIdent</TT>, but reference fields expect to see a
   * <TT>Persistent</TT> representing their new referee rather than an
   * <TT>Integer</TT> specifying its troid.  The remarks about sessions
   * (transactions) and DSD-derived type-safe methods made for
   * <TT>setIdent</TT> apply here too.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @param ident       The new value for the field: a <TT>String</TT>,
   *                    <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT>,
   *                    <TT>Date</TT> or, for a reference field, a
   *                    <TT>Persistent</TT>.  If you want to pass referees as
   *                    troids, use <TT>setIdent</TT>.  If you want to set the
   *                    field from a string representation (<I>e.g.</I> typed
   *                    in by the user), use <TT>setIdentString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have write access to the
   *                object (see <TT>assertCanWrite</TT>)
   * @exception TypeMismatchPoemException
   *                if <TT>ident</TT> is of the wrong type; it's easiest to use
   *                DSD-derived typed versions of this method
   * @exception ValidationPoemException
   *                if <TT>ident</TT> is not a valid value for the field
   *                (<I>e.g.</I> a string is too long)
   *
   * @see #setIdent
   * @see #setIdentString
   * @see #assertCanWrite
   */

  public void setValue(String name, Object value)
      throws NoSuchColumnPoemException, ValidationPoemException,
             AccessPoemException {
    getTable().getColumn(name).setValue(this, value);
  }

  // 
  // --------
  //  Fields
  // --------
  // 

  /**
   * The value of one of the object's fields, wrapped up with type information
   * sufficient for rendering it.  Basically, value plus name plus type.  This
   * is the form in which Melati's templating facilities expect to receive
   * values for displaying them or creating input boxes.
   *
   * <P>
   *
   * If the field <TT><I>baz</I></TT> is defined in the DSD as part of a table
   * called <TT><I>foo</I></TT>, then the table's records will be represented
   * by an application-specialised subclass of <TT>Persistent</TT> called
   * <TT><I>Foo</I></TT> which provides a <TT>get<I>Baz</I>Field</TT> method.
   *
   * @see org.melati.MarkupLanguage#input(org.melati.poem.Field)
   */

  public final Field getField(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).asField(this);
  }

  private Enumeration fieldsOfColumns(Enumeration columns) {
    final Persistent _this = this;
    return
        new MappedEnumeration(columns) {
          public Object mapped(Object column) {
            return ((Column)column).asField(_this);
          }
        };
  }

  /**
   * The values of all the object's fields, wrapped up with type information
   * sufficient for rendering them.
   *
   * @return an <TT>Enumeration</TT> of <TT>Field</TT>s
   */

  public Enumeration getFields() {
    return fieldsOfColumns(getTable().columns());
  }

  /**
   * The values of all the object's fields designated for inclusion in full
   * record displays, wrapped up with type information sufficient for rendering
   * them.
   *
   * @return an <TT>Enumeration</TT> of <TT>Field</TT>s
   */

  public Enumeration getRecordDisplayFields() {
    return fieldsOfColumns(getTable().getRecordDisplayColumns());
  }

  public Enumeration getSummaryDisplayFields() {
    return fieldsOfColumns(getTable().getSummaryDisplayColumns());
  }

  public Enumeration getSearchCriterionFields() {
    return fieldsOfColumns(getTable().getSearchCriterionColumns());
  }

  // 
  // ==================
  //  Other operations
  // ==================
  // 

  /**
   * Delete the object.  Before the record is deleted from the database, POEM
   * checks to see if it is the target of any reference fields, and throws a
   * <TT>DeletionIntegrityPoemException</TT> if it is.  However, if it's safe
   * to delete the record, POEM does so---and then immediately commits your
   * transaction to make the change permanent.  So deletions can't be undone.
   * POEM also supports `soft deletion' through the use of `deleted
   * flags' (FIXME describe)
   *
   * @see PoemThread#commit
   * @see Database#referencesTo
   */

  public final void deleteAndCommit()
      throws AccessPoemException, DeletionIntegrityPoemException {

    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    getDatabase().beginExclusiveLock();
    try {
      SessionToken sessionToken = PoemThread.sessionToken();
      writeLock(sessionToken);

      Enumeration refs = getDatabase().referencesTo(this);
      if (refs.hasMoreElements())
        throw new DeletionIntegrityPoemException(this, refs);

      table.delete(troid(), sessionToken.transaction);
      if (sessionToken.transaction != null)
        sessionToken.transaction.commit();

      exists = false;
    }
    finally {
      getDatabase().endExclusiveLock();
    }
  }

  /**
   * Create a new object identical to this one.
   */

  public Persistent duplicated() throws AccessPoemException {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    Persistent it = (Persistent)clone();
    getTable().create(it);
    return it;
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  /**
   * A string briefly describing the object for diagnostic purposes.  The name
   * of its table and its troid.
   */

  public String toString() {
    return getTable().getName() + "/" + troid();
  }

  /**
   * A string describing the object for the purposes of rendering it in lists
   * presented to the user.  Unless overridden, this returns the value picked
   * out by the designated `primary display column' of the table from which the
   * object comes.  If there is no such column, the object's troid is returned
   * (as a decimal string).
   */

  public String displayString() throws AccessPoemException {
    Column displayColumn = getTable().displayColumn();
    if (displayColumn == null)
      return String.valueOf(getTroid());
    else
      return
          displayColumn.getType().stringOfValue(displayColumn.getValue(this));
  }

  // 
  // ===============
  //  Support stuff
  // ===============
  // 

  public final int hashCode() {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    return getTable().hashCode() + troid().intValue();
  }

  public final boolean equals(Object object) {
    if (object == null || !(object instanceof Persistent))
      return false;
    else {
      Persistent other = (Persistent)object;
      return troid() != null && other.troid() == troid() &&
             other.getTable() == getTable();
    }
  }

  public synchronized void invalidate() {
    super.invalidate();
    extras = null;
  }

  protected Object clone() {
    Persistent it;
    try {
      it = (Persistent)super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new UnexpectedExceptionPoemException(e);
    }

    it.extras = (Object[])extras().clone();
    it.reset();
    it.troid = null;

    return it;
  }
}
