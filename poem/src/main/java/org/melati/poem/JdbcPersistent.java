/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2007 Tim Pizey
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.poem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import org.melati.poem.transaction.Transaction;
import org.melati.poem.transaction.Transactioned;
import org.melati.poem.util.FlattenedEnumeration;
import org.melati.poem.util.MappedEnumeration;

/**
 * The object representing a single table row; this is the <B>PO</B> in POEM!
 * <p>
 * Instances are also used to represent selection criteria.
 *
 * @author WilliamC At paneris.org
 */

public class JdbcPersistent extends Transactioned implements Persistent, Cloneable {
  private Table<?> table;
  private Integer troid;        // null if a floating object
  private AccessToken clearedToken;
  private boolean
      knownCanRead = false, knownCanWrite = false, knownCanDelete = false;

  /**
   * Might this object have as yet unsaved modifications?
   * <p>
   * This is set to true when a write lock is obtained and this
   * happens when a value is assigned to a column, except when an
   * "unsafe" method is used.
   * <p>
   * It is set to false when this is written to the database,
   * even if not yet committed.
   */
  private boolean dirty = false;

  private static final int NONEXISTENT = 0, EXISTENT = 1, DELETED = 2;
  private int status = NONEXISTENT;

  private Object[] extras = null;
  /**
   * Constructor.
   */
  public JdbcPersistent() {
  }

  /**
   * Constructor.
   * @param table the table of the Persistent
   * @param troid its Table Row Object Id
   */
  public JdbcPersistent(JdbcTable<?> table, Integer troid) {
    super(table.getDatabase());
    this.table = table;
    this.troid = troid;
  }

  /**
   * Constructor.
   * @param tableName String name of a table
   * @param troidString String integer representation
   */
  public JdbcPersistent(String tableName, String troidString) {
    super(PoemThread.database());
    this.table = PoemThread.database().getTable(tableName);
    this.troid = new Integer(troidString);
   }

  // 
  // --------
  //  Status
  // --------
  // 

  final void setStatusNonexistent() {
    status = NONEXISTENT;
  }

  final void setStatusExistent() {
    status = EXISTENT;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#statusNonexistent()
   */
  public final boolean statusNonexistent() {
    return status == NONEXISTENT;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#statusExistent()
   */
  public final boolean statusExistent() {
    return status == EXISTENT;
  }

  // 
  // ***************
  //  Transactioned
  // ***************
  // 

  /**
   * Throws an exception if this Persistent has a null troid.
   */
  private void assertNotFloating() {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);
  }

  /**
   * Throws <tt>RowDisappearedPoemException</tt> if this Persistent has a status of DELETED.
   */
  private void assertNotDeleted() {
    if (status == DELETED)
      throw new RowDisappearedPoemException(this);
  }

  /**
   * Called if not uptodate.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.transaction.Transactioned#
   *   load(org.melati.poem.transaction.Transaction)
   */
  protected void load(Transaction transaction) {
    if (troid == null) // I cannot contrive a test to cover this case, but hey
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    table.load((PoemTransaction)transaction, this);
    // table will clear our dirty flag and set status
  }

  /**
   * Whether we are up to date with respect to current Transaction.
   * <p>
   * Return the inherited validity flag.
   * 
   * {@inheritDoc}
   * @see org.melati.poem.transaction.Transactioned#
   *   upToDate(org.melati.poem.transaction.Transaction)
   */
  protected boolean upToDate(Transaction transaction) {
    return valid;
  }

  /**
   * Write the persistent to the database if this might be necessary.
   * <p>
   * It may be necessary if field values have been set since we last
   * did a write i.e. this persistent is dirty.
   * It will not be necessary if this persistent is deleted.
   * An exception will occur if it does not exist in the database.
   */
  protected void writeDown(Transaction transaction) {
    if (status != DELETED) {
      assertNotFloating();
      table.writeDown((PoemTransaction)transaction, this);
      // table will clear our dirty flag
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.transaction.Transactioned#writeLock(org.melati.poem.transaction.Transaction)
   */
  protected void writeLock(Transaction transaction) {
    if (troid != null) {
      super.writeLock(transaction);
      assertNotDeleted();
      dirty = true;
      table.notifyTouched((PoemTransaction)transaction, this);
    }
  }

  /**
   * This is just to make this method available to <TT>Table</TT>.
   */
  protected void readLock(Transaction transaction) {
    if (troid != null) {
      super.readLock(transaction);
      assertNotDeleted();
    }
  }

  /**
   * Previously deletion was treated as non-rollbackable, 
   * as deleteAndCommit was the only deletion mechanism. 
   * 
   * {@inheritDoc}
   * @see org.melati.poem.transaction.Transactioned#
   *   commit(org.melati.poem.transaction.Transaction)
   */
  protected void commit(Transaction transaction) {
    //if (status != DELETED) {
      assertNotFloating();
      super.commit(transaction);
    //}
  }

  protected void rollback(Transaction transaction) {
    //if (status != DELETED) {
    assertNotFloating();
    if (status == DELETED)
      status = EXISTENT;
    super.rollback(transaction);
    //}
  }

  // 
  // ************
  //  Persistent
  // ************
  // 

 /** 
 * {@inheritDoc}
 * @see org.melati.poem.Persistent#makePersistent()
 */
  public void makePersistent() {
    getTable().create(this);
  }
  
  /* New extra columns could have been added since we were created */
  synchronized Object[] extras() {
    if (extras == null)
      extras = new Object[table.extrasCount()];
    else if (extras.length < table.extrasCount() ) {
      Object[] newExtras = new Object[table.extrasCount()];
      System.arraycopy(extras, 0, newExtras, 0, extras.length);
      extras = newExtras;
    }
    return extras;
  }

 /** 
 * {@inheritDoc}
 * @see org.melati.poem.Persistent#getTable()
 */
  public final Table<?> getTable() {
    return table;
  }

  synchronized void setTable(JdbcTable<?> table, Integer troid) {
    setTransactionPool(table.getDatabase());
    this.table = table;
    this.troid = troid;
  }


 /** 
 * {@inheritDoc}
 * @see org.melati.poem.Persistent#getDatabase()
 */
  public final Database getDatabase() {
    return table.getDatabase();
  }

  /**
   * @return The Table Row Object Id for this Persistent.
   * 
   * FIXME This shouldn't be public because we don't in principle want people
   * to know even the troid of an object they aren't allowed to read.  However,
   * I think this information may leak out elsewhere.
   * To fix is not simple, as generated setters rely upon a lock-free read of the object to set. 
   * 
   * {@inheritDoc}
   * 
   * @see org.melati.poem.Persistable#troid()
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
   * @see #assertCanRead()
   */

  public final Integer getTroid() throws AccessPoemException {
    assertCanRead();
    return troid();
  }

  // 
  // ----------------
  //  Access control
  // ----------------
  // 

  protected void existenceLock(SessionToken sessionToken) {
    super.readLock(sessionToken.transaction);
  }

  protected void readLock(SessionToken sessionToken)
      throws AccessPoemException {
    assertCanRead(sessionToken.accessToken);
    readLock(sessionToken.transaction);
  }

  protected void writeLock(SessionToken sessionToken)
      throws AccessPoemException {
    if (troid != null)
      assertCanWrite(sessionToken.accessToken);
    writeLock(sessionToken.transaction);
  }

  protected void deleteLock(SessionToken sessionToken)
      throws AccessPoemException {
    if (troid != null)
      assertCanDelete(sessionToken.accessToken);
    writeLock(sessionToken.transaction);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#existenceLock()
   */
  public void existenceLock() {
    existenceLock(PoemThread.sessionToken());
  }

  /**
   * Check if we may read this object and then lock it.
   * @throws AccessPoemException if current AccessToken does not give read Capability
   */
  protected void readLock() throws AccessPoemException {
    readLock(PoemThread.sessionToken());
  }

  /**
   * Check if we may write to this object and then lock it.
   * @throws AccessPoemException if current AccessToken does not give write Capability
   */
  protected void writeLock() throws AccessPoemException {
    writeLock(PoemThread.sessionToken());
  }

  /**
   * The capability required for reading the object's field values.  This is
   * used by <TT>assertCanRead</TT> (unless that's been overridden) to obtain a
   * <TT>Capability</TT> for comparison against the caller's
   * <TT>AccessToken</TT>.
   * <p>
   * NOTE If a canRead column is defined then it will override this method.
   *
   * @return the capability specified by the record's <TT>canread</TT> field, 
   *         or <TT>null</TT> if it doesn't have one or its value is SQL
   *         <TT>NULL</TT>
   *
   * @see #assertCanRead
   */

  protected Capability getCanRead() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanRead(org.melati.poem.AccessToken)
   */

  public void assertCanRead(AccessToken token)
      throws AccessPoemException {
    // FIXME!!!! this is wrong because token could be stale ...
    if (!(clearedToken == token && knownCanRead) && troid != null) {
      Capability canRead = getCanRead();
      if (canRead == null)
        canRead = getTable().getDefaultCanRead();
      if (canRead != null) {
        if (!token.givesCapability(canRead))
          throw new ReadPersistentAccessPoemException(this, token, canRead);
        if (clearedToken != token) {
          knownCanWrite = false;
          knownCanDelete = false;
        }
        clearedToken = token;
        knownCanRead = true;
      }
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanRead()
   */
  public final void assertCanRead() throws AccessPoemException {
    assertCanRead(PoemThread.accessToken());
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getReadable()
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
   * used by <TT>assertCanWrite</TT> (unless that's been overridden) to obtain 
   * a <TT>Capability</TT> for comparison against the caller's
   * <TT>AccessToken</TT>.
   * <p>
   * NOTE If a canWrite column is defined then it will override this method.
   *
   * @return the capability specified by the record's <TT>canwrite</TT> field,
   *         or <TT>null</TT> if it doesn't have one or its value is SQL
   *         <TT>NULL</TT>
   *
   * @see #assertCanWrite
   */
  protected Capability getCanWrite() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanWrite(org.melati.poem.AccessToken)
   */

  public void assertCanWrite(AccessToken token)
      throws AccessPoemException {
    // FIXME!!!! this is wrong because token could be stale ...
    if (!(clearedToken == token && knownCanWrite) && troid != null) {
      Capability canWrite = getCanWrite();
      if (canWrite == null)
        canWrite = getTable().getDefaultCanWrite();
      if (canWrite != null) {
        if (!token.givesCapability(canWrite))
          throw new WritePersistentAccessPoemException(this, token, canWrite);
        if (clearedToken != token) {
          knownCanRead = false;
          knownCanDelete = false;
        }
        clearedToken = token;
        knownCanWrite = true;
      }
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanWrite()
   */
  public final void assertCanWrite() throws AccessPoemException {
    assertCanWrite(PoemThread.accessToken());
  }

  /**
   * The capability required for deleting the object.  This is
   * used by <TT>assertCanDelete</TT> (unless that's been overridden) 
   * to obtain a <TT>Capability</TT> for comparison against the caller's
   * <TT>AccessToken</TT>.
   * <p>
   * NOTE If a canDelete column is defined then it will override this method.
   *
   * @return the capability specified by the record's <TT>candelete</TT> field,
   *         or <TT>null</TT> if it doesn't have one or its value is SQL
   *         <TT>NULL</TT>
   *
   * @see #assertCanDelete
   */
  protected Capability getCanDelete() {
    return null;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanDelete(org.melati.poem.AccessToken)
   */

  public void assertCanDelete(AccessToken token)
      throws AccessPoemException {
    // FIXME!!!! this is wrong because token could be stale ...
    if (!(clearedToken == token && knownCanDelete) && troid != null) {
      Capability canDelete = getCanDelete();
      if (canDelete == null)
        canDelete = getTable().getDefaultCanDelete();
      if (canDelete != null) {
        if (!token.givesCapability(canDelete))
          throw new DeletePersistentAccessPoemException(this, token, canDelete);
        if (clearedToken != token) {
          knownCanRead = false;
          knownCanWrite = false;
        }
        clearedToken = token;
        knownCanDelete = true;
      }
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanDelete()
   */
  public final void assertCanDelete() throws AccessPoemException {
    assertCanDelete(PoemThread.accessToken());
  }

  /**
   * The capability required to select the object.
   * <p>
   * Any persistent which has a <tt>canSelect</tt> field will override this method. 
   * <p>
   * There is no <code>assertCanSelect()</code> yet because I don't understand
   * this stale token stuff!
   *
   * @return the capability the user needs to select this record
   * @todo document use-case or delete
   */
  protected Capability getCanSelect() {
    return null;
  }
  
  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanCreate(org.melati.poem.AccessToken)
   */

  public void assertCanCreate(AccessToken token) {
    Capability canCreate = getTable().getCanCreate();
    if (canCreate != null && !token.givesCapability(canCreate))
      throw new CreationAccessPoemException(getTable(), token, canCreate);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#assertCanCreate()
   */
  public final void assertCanCreate() throws AccessPoemException {
    assertCanCreate(PoemThread.accessToken());
  }


  // 
  // ============================
  //  Reading and writing fields
  // ============================
  // 

  // 
  // ------
  //  Raws
  // ------
  // 

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getRaw(java.lang.String)
   */
  public Object getRaw(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).getRaw(this);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getRawString(java.lang.String)
   */

  public final String getRawString(String name)
      throws AccessPoemException, NoSuchColumnPoemException {
    Column<?> column = getTable().getColumn(name);
    return column.getType().stringOfRaw(column.getRaw(this));
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#setRaw(java.lang.String, java.lang.Object)
   */

  public void setRaw(String name, Object raw)
      throws NoSuchColumnPoemException, AccessPoemException,
             ValidationPoemException {
    getTable().getColumn(name).setRaw(this, raw);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#setRawString(java.lang.String, java.lang.String)
   */

  public final void setRawString(String name, String string)
      throws NoSuchColumnPoemException, AccessPoemException,
             ParsingPoemException, ValidationPoemException {
    Column<?> column = getTable().getColumn(name);
    column.setRaw(this, column.getType().rawOfString(string));
  }

  // 
  // --------
  //  Values
  // --------
  // 

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getCooked(java.lang.String)
   */

  public Object getCooked(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).getCooked(this);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getCookedString(java.lang.String, org.melati.poem.PoemLocale, int)
   */

  public final String getCookedString(String name, PoemLocale locale,
                                     int style)
      throws NoSuchColumnPoemException, AccessPoemException {
    Column<?> column = getTable().getColumn(name);
    return column.getType().stringOfCooked(column.getCooked(this),
                                          locale, style);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#setCooked(java.lang.String, java.lang.Object)
   */

  public void setCooked(String name, Object cooked)
      throws NoSuchColumnPoemException, ValidationPoemException,
             AccessPoemException {
    getTable().getColumn(name).setCooked(this, cooked);
  }

  // 
  // --------
  //  Fields
  // --------
  // 

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getField(java.lang.String)
   */
  public final Field<?> getField(String name)
      throws NoSuchColumnPoemException, AccessPoemException {
    return getTable().getColumn(name).asField(this);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#fieldsOfColumns(java.util.Enumeration)
   */
  public Enumeration<Field<?>> fieldsOfColumns(Enumeration<Column<?>> columns) {
    final JdbcPersistent _this = this;
    return
        new MappedEnumeration<Field<?>, Column<?>>(columns) {
          public Field<?> mapped(Column<?> column) {
            return column.asField(_this);
          }
        };
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getFields()
   */

  public Enumeration<Field<?>> getFields() {
    return fieldsOfColumns(getTable().columns());
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getRecordDisplayFields()
   */

  public Enumeration<Field<?>> getRecordDisplayFields() {
    return fieldsOfColumns(getTable().getRecordDisplayColumns());
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getDetailDisplayFields()
   */
  public Enumeration<Field<?>> getDetailDisplayFields() {
    return fieldsOfColumns(getTable().getDetailDisplayColumns());
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getSummaryDisplayFields()
   */
  public Enumeration<Field<?>> getSummaryDisplayFields() {
    return fieldsOfColumns(getTable().getSummaryDisplayColumns());
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getSearchCriterionFields()
   */
  public Enumeration<Field<?>> getSearchCriterionFields() {
    return fieldsOfColumns(getTable().getSearchCriterionColumns());
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getPrimaryDisplayField()
   */
  public Field<?> getPrimaryDisplayField() {
    return getTable().displayColumn().asField(this);
  }

  // 
  // ==================
  //  Other operations
  // ==================
  // 

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#delete(java.util.Map)
   */
  public void delete(Map<Column<?>, IntegrityFix> integrityFixOfColumn) {
    
    assertNotFloating();

    deleteLock(PoemThread.sessionToken());

    Enumeration<Column<?>> columns = getDatabase().referencesTo(getTable());
    Vector<Enumeration<Persistent>> refEnumerations = new Vector<Enumeration<Persistent>>();

    while (columns.hasMoreElements()) {
      Column<?> column = columns.nextElement();

      IntegrityFix fix;
      try {
        fix = integrityFixOfColumn == null ?
                null : integrityFixOfColumn.get(column);
      }
      catch (ClassCastException e) {
        throw new AppBugPoemException(
            "integrityFixOfColumn argument to Persistent.deleteAndCommit " +
                "is meant to be a Map from Column to IntegrityFix",
            e);
      }

      if (fix == null)
        fix = column.getIntegrityFix();

      refEnumerations.addElement(
          fix.referencesTo(this, column, column.selectionWhereEq(troid()),
                           integrityFixOfColumn));

    }

    Enumeration<Persistent> refs = new FlattenedEnumeration<Persistent>(refEnumerations.elements());

    if (refs.hasMoreElements())
      throw new DeletionIntegrityPoemException(this, refs);

    delete_unsafe();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#delete_unsafe()
   */
  public void delete_unsafe() {
    assertNotFloating();
    SessionToken sessionToken = PoemThread.sessionToken();
    deleteLock(sessionToken);
    try {
      status = DELETED;
      table.delete(troid(), sessionToken.transaction);
    } catch (PoemException e) {
      status = EXISTENT;
      throw e;
    }
  }

 
  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#delete()
   */
  public final void delete() {
    delete(null);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#deleteAndCommit(java.util.Map)
   */
  public void deleteAndCommit(Map<Column<?>, IntegrityFix> integrityFixOfColumn)
      throws AccessPoemException, DeletionIntegrityPoemException {

    getDatabase().beginExclusiveLock();
    try {
      delete(integrityFixOfColumn);
      PoemThread.commit();
    }
    catch (RuntimeException e) {
      PoemThread.rollback();
      throw e;
    }
    finally {
      getDatabase().endExclusiveLock();
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#deleteAndCommit()
   */
  public final void deleteAndCommit()
      throws AccessPoemException, DeletionIntegrityPoemException {
    deleteAndCommit(null);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#duplicated()
   */
  public Persistent duplicated() throws AccessPoemException {
    assertNotFloating();
    assertNotDeleted();
    return (JdbcPersistent)clone();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#duplicatedFloating()
   */
  public Persistent duplicatedFloating() throws AccessPoemException {
    return (JdbcPersistent)clone();
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  /**
   * A string briefly describing the object for diagnostic purposes.  The name
   * of its table and its troid.
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    if (getTable() == null) {
       return "null/" + troid();      
    }
    return getTable().getName() + "/" + troid();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#displayString(org.melati.poem.PoemLocale, int)
   */
  public String displayString(PoemLocale locale, int style)
      throws AccessPoemException {
    Column<?> displayColumn = getTable().displayColumn();
    if (displayColumn.isTroidColumn() && this.troid == null)
      return "null";
    else
      return displayColumn.getType().stringOfCooked(displayColumn.getCooked(this),
                                                  locale, style);
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#displayString(org.melati.poem.PoemLocale)
   */
  public String displayString(PoemLocale locale) 
      throws AccessPoemException {
    return displayString(locale, DateFormat.MEDIUM);
  }
  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#displayString()
   */
  public String displayString() 
      throws AccessPoemException {
    return displayString(PoemLocale.HERE, DateFormat.MEDIUM);
  }

  // 
  // ===============
  //  Support stuff
  // ===============
  // 

  /**
   * {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  public final int hashCode() {
    if (troid == null)
      throw new InvalidOperationOnFloatingPersistentPoemException(this);

    return getTable().hashCode() + troid().intValue();
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public final boolean equals(Object object) {
    if (object == null || !(object instanceof Persistent))
      return false;
    else {
      JdbcPersistent other = (JdbcPersistent)object;
      return other.troid() == troid() &&
             other.getTable() == getTable();
    }
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.transaction.Transactioned#invalidate()
   */
  public synchronized void invalidate() {
    assertNotFloating();
    super.invalidate();
    extras = null;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  protected Object clone() {
    // to clone it you have to be able to read it
    assertCanRead();
    JdbcPersistent it;
    try {
      it = (JdbcPersistent)super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new UnexpectedExceptionPoemException(e, "Object no longer supports clone.");
    }

    it.extras = (Object[])extras().clone();
    it.reset();
    it.troid = null;
    it.status = NONEXISTENT;

    return it;
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#dump()
   */
  public String dump() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    dump(ps);
    return baos.toString();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#dump(java.io.PrintStream)
   */
  public void dump(PrintStream p) {
    p.println(getTable().getName() + "/" + troid());
    for (Enumeration<Field<?>> f = getRecordDisplayFields(); f.hasMoreElements();) {
      p.print("  ");
      ((Field<?>)f.nextElement()).dump(p);
      p.println();
    }
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#postWrite()
   */
  public void postWrite() {
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#postInsert()
   */
  public void postInsert() {
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#postModify()
   */
  public void postModify() {
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#preEdit()
   */
  public void preEdit() {
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#postEdit(boolean)
   */
  public void postEdit(boolean creating) {
  }

  // 
  // =================================
  // Use to Represent Query Constructs
  // =================================
  //

  /**
   * Return a SELECT query to count rows matching criteria represented
   * by this object.
   *
   * @param includeDeleted whether to include soft deleted records
   * @param excludeUnselectable Whether to append unselectable exclusion SQL 
   * @return an SQL query string
   */
  protected String countMatchSQL(boolean includeDeleted,
                              boolean excludeUnselectable) {
    return getTable().countSQL(
      fromClause(),
      getTable().whereClause(this),
      includeDeleted, excludeUnselectable);
  }

  /**
   * Return an SQL FROM clause for use when selecting rows using criteria
   * represented by this object.
   * <p>
   * By default just the table name is returned, quoted as necessary for
   * the DBMS.
   * <p>
   * Subtypes must ensure the result is compatible with the
   * result of {@link Table #appendWhereClause(StringBuffer, JdbcPersistent)}.
   * @return an SQL snippet 
   */
  protected String fromClause() {
    String result = getTable().quotedName();
    return result;
  }

  public Treeable[] getChildren() {
    Enumeration<Persistent> refs = getDatabase().referencesTo(this);
    Vector<Persistent> v = new Vector<Persistent>();
    while (refs.hasMoreElements())
      v.addElement(refs.nextElement());
    Treeable[] kids;
    synchronized (v) {
      kids = new Treeable[v.size()];
      v.copyInto(kids);
    }

    return kids;
  }

  /** 
   * NOTE This will be overridden if the persistent has a field called <tt>name</tt>. 
   * {@inheritDoc}
   * @see org.melati.poem.Persistent#getName()
   */
  public String getName() {
    return displayString();
  }

  /**
   * @return the dirty
   */
  public boolean isDirty() {
    return dirty;
  }

  /**
   * @param dirty the dirty to set
   */
  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }

}
