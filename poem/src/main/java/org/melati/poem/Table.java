/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import org.melati.poem.util.Cache;

import java.util.Enumeration;
import java.io.PrintStream;
import java.sql.ResultSet;

/**
 * A table.
 *
 * @since 14-Apr-2008
 */
public interface Table {
    /**
     * The database to which the table is attached.
     * @return the db
     */
    Database getDatabase();

    /**
     * Initialise the table.
     */
    void init();
    /**
     * Do stuff immediately after table initialisation.
     * <p>
     * This base method clears the column info caches and adds a listener
     * to the column info table to maintain the caches.
     * <p>
     * It may be overridden to perform other actions. For example to
     * ensure required rows exist in tables that define numeric ID's for
     * codes.
     *
     * @see #notifyColumnInfo(ColumnInfo)
     * @see #clearColumnInfoCaches()
     */
    void postInitialise();
    
    /**
     * Create the (possibly overridden) TableInfo if it has not yet been created.
     */
    void createTableInfo();
    /**
     * The table's programmatic name.  Identical with its name in the DSD (if the
     * table was defined there) and in its <TT>tableinfo</TT> entry.
     * This will normally be the same as the name in the RDBMS itself, however that name
     * may be translated to avoid DBMS specific name clashes.
     *
     * @return the table name, case as defined in the DSD
     * @see org.melati.poem.dbms.Dbms#melatiName(String)
     */
    String getName();

    /**
     * @return table name quoted using the DBMS' specific quoting rules.
     */
    String quotedName();

    /**
     * The human-readable name of the table.  POEM itself doesn't use this, but
     * it's available to applications and Melati's generic admin system as a
     * default label for the table and caption for its records.
      * @return The human-readable name of the table
      */
    String getDisplayName();

    /**
     * A brief description of the table's function.  POEM itself doesn't use
     * this, but it's available to applications and Melati's generic admin system
     * as a default label for the table and caption for its records.
     * @return the brief description
     */
    String getDescription();

    /**
     * The category of this table.  POEM itself doesn't use
     * this, but it's available to applications and Melati's generic admin system
     * as a default label for the table and caption for its records.
     *
     * @return the category
     */
    TableCategory getCategory();

    /**
     * @return the {@link org.melati.poem.TableInfo} for this table
     */
    TableInfo getInfo();

    /**
     * The troid (<TT>id</TT>) of the table's entry in the <TT>tableinfo</TT>
     * table.  It will always have one (except during initialisation, which the
     * application programmer will never see).
     *
     * @return id in TableInfo metadata table
     */
    Integer tableInfoID();

    /**
     * The table's column with a given name.  If the table is defined in the DSD
     * under the name <TT><I>foo</I></TT>, there will be an
     * application-specialised <TT>Table</TT> subclass, called
     * <TT><I>Foo</I>Table</TT> (and available as <TT>get<I>Foo</I>Table</TT>
     * from the application-specialised <TT>Database</TT> subclass) which has
     * extra named methods for accessing the table's predefined <TT>Column</TT>s.
     *
     * @param nameP name of column to get
     * @return column of that name
     * @throws org.melati.poem.NoSuchColumnPoemException if there is no column with that name
     */
    Column<?> getColumn(String nameP) throws NoSuchColumnPoemException;

    /**
     * All the table's columns.
     *
     * @return an <TT>Enumeration</TT> of <TT>Column</TT>s
     * @see org.melati.poem.Column
     */
    Enumeration<Column<?>> columns();

    /**
     * @return the number of columns in this table.
     */
    int getColumnsCount();

    /**
     * @param columnInfoID
     * @return the Column with a TROID equal to columnInfoID
     */
    Column<?> columnWithColumnInfoID(int columnInfoID);

    /**
     * The table's troid column.  Every table in a POEM database must have a
     * troid (table row ID, or table-unique non-nullable integer primary key),
     * often but not necessarily called <TT>id</TT>, so that it can be
     * conveniently `named'.
     *
     * @return the id column
     * @see #getObject(Integer)
     */
    Column<Integer> troidColumn();

    /**
     * @return The table's deleted-flag column, if any.
     */
    Column<Boolean> deletedColumn();

    /**
     * The table's primary display column, the Troid column if not set.
     * This is the column used to represent records from the table
     * concisely in reports or whatever.  It is determined
     * at initialisation time by examining the <TT>Column</TT>s
     * <TT>getPrimaryDisplay()</TT> flags.
     *
     * @return the table's display column, or <TT>null</TT> if it hasn't got one
     *
     * see Column#setColumnInfo
     * @see org.melati.poem.ReferencePoemType#_stringOfCooked
     * @see org.melati.poem.DisplayLevel#primary
     */
    Column<?> displayColumn();

    /**
     * @param column the display column to set
     */
    void setDisplayColumn(Column<?> column);

    /**
     * In a similar manner to the primary display column, each table can have
     * one primary criterion column.
     * <p>
     * The Primary Criterion is the main grouping field of the table,
     * ie the most important non-unique type field.
     * <p>
     * For example the Primary Criterion for a User table might be Nationality.
     *
     * @return the search column, if any
     * @see org.melati.poem.Searchability
     */
    Column<?> primaryCriterionColumn();

    /**
     * @param column the search column to set
     */
    void setSearchColumn(Column<?> column);

    /**
     * If the troidColumn has yet to be set then returns an empty string.
     *
     * @return comma separated list of the columns to order by
     */
    String defaultOrderByClause();

    /**
     * Clear caches.
     */
    void clearColumnInfoCaches();

    /**
     * Clears columnInfo caches, normally a no-op.
     *
     * @param infoP the possibly null ColumnInfo meta-data persistent
     */
    void notifyColumnInfo(ColumnInfo infoP);

    /**
     * Return columns at a display level in display order.
     *
     * @param level the {@link org.melati.poem.DisplayLevel} to select
     * @return an Enumeration of columns at the given level
     */
    Enumeration<Column<?>> displayColumns(DisplayLevel level);

    /**
     * @param level the {@link org.melati.poem.DisplayLevel} to select
     * @return the number of columns at a display level.
     */
    int displayColumnsCount(DisplayLevel level);

    /**
     * The table's columns for detailed display in display order.
     *
     * @return an <TT>Enumeration</TT> of <TT>Column</TT>s
     * @see org.melati.poem.Column
     * @see #displayColumns(org.melati.poem.DisplayLevel)
     * @see org.melati.poem.DisplayLevel#detail
     */
    Enumeration<Column<?>> getDetailDisplayColumns();

    /**
     * @return the number of columns at display level <tt>Detail</tt>
     */
    int getDetailDisplayColumnsCount();

    /**
     * The table's columns designated for display in a record, in display order.
     *
     * @return an <TT>Enumeration</TT> of <TT>Column</TT>s
     * @see org.melati.poem.Column
     * @see #displayColumns(org.melati.poem.DisplayLevel)
     * @see org.melati.poem.DisplayLevel#record
     */
    Enumeration<Column<?>> getRecordDisplayColumns();

    /**
     * @return the number of columns at display level <tt>Record</tt>
     */
    int getRecordDisplayColumnsCount();

    /**
     * The table's columns designated for display in a record summary, in display
     * order.
     *
     * @return an <TT>Enumeration</TT> of <TT>Column</TT>s
     * @see org.melati.poem.Column
     * @see #displayColumns(org.melati.poem.DisplayLevel)
     * @see org.melati.poem.DisplayLevel#summary
     */
    Enumeration<Column<?>> getSummaryDisplayColumns();

    /**
     * @return the number of columns at display level <tt>Summary</tt>
     */
    int getSummaryDisplayColumnsCount();

    /**
     * The table's columns designated for use as search criteria, in display
     * order.
     *
     * @return an <TT>Enumeration</TT> of <TT>Column</TT>s
     * @see org.melati.poem.Column
     */
    Enumeration<Column<?>> getSearchCriterionColumns();

    /**
     * @return the number of columns which are searchable
     */
    int getSearchCriterionColumnsCount();

    /**
     * Use this for DDL statements, ie those which alter the structure of the db.
     * Postgresql in particular does not like DDL statements being executed within a transaction.
     *
     * @param sql the SQL DDL statement to execute
     * @throws org.melati.poem.StructuralModificationFailedPoemException
     */
    void dbModifyStructure(String sql)
        throws StructuralModificationFailedPoemException;

    /**
     * Constraints are not used in POEM, but you might want to use them if
     * exporting the db or using schema visualisation tools.
     */
    void dbAddConstraints();

    /**
     * When deleting a table and used in tests.
     */
    void invalidateTransactionStuffs();

    /**
     * @param transaction possibly null if working with the committed transaction
     * @param persistent the Persistent to load
     */
    void load(PoemTransaction transaction, Persistent persistent);

    /**
     * The Transaction cannot be null, as this is trapped in
     * #deleteLock(SessionToken).
     * @param troid id of row to delete
     * @param transaction a non-null transaction
     */
    void delete(Integer troid, PoemTransaction transaction);

    /**
     * @param transaction our PoemTransaction
     * @param p the Persistent to write
     */
    void writeDown(PoemTransaction transaction, Persistent p);

    /**
     * Invalidate table cache.
     *
     * NOTE Invalidated cache elements are reloaded when next read
     */
    void uncache();

    /**
     * @param maxSize new maximum size
     */
    void trimCache(int maxSize);

    /**
     * @return the Cache Info object
     */
    Cache.Info getCacheInfo();

    /**
     * Add a {@link org.melati.poem.TableListener} to this Table.
     */
    void addListener(TableListener listener);

    /**
     * Notify the table that one if its records is about to be changed in a
     * transaction.  You can (with care) use this to support cacheing of
     * frequently-used facts about the table's records.
     *
     * @param transaction the transaction in which the change will be made
     * @param persistent  the record to be changed
     */
    void notifyTouched(PoemTransaction transaction, Persistent persistent);

    /**
     * @return the Transaction serial
     */
    long serial(PoemTransaction transaction);

    /**
     * Lock this record.
     */
    void readLock();

    /**
     * The object from the table with a given troid.
     *
     * @param troid       Every record (object) in a POEM database must have a
     *                    troid (table row ID, or table-unique non-nullable
     *                    integer primary key), often but not necessarily called
     *                    <TT>id</TT>, so that it can be conveniently `named' for
     *                    retrieval by this method.
     *
     * @return A <TT>Persistent</TT> of the record with the given troid;
     *         or, if the table was defined in the DSD under the name
     *         <TT><I>foo</I></TT>, an application-specialised subclass
     *         <TT><I>Foo</I></TT> of <TT>Persistent</TT>.  In that case, there
     *         will also be an application-specialised <TT>Table</TT> subclass,
     *         called <TT><I>Foo</I>Table</TT> (and available as
     *         <TT>get<I>Foo</I>Table</TT> from the application-specialised
     *         <TT>Database</TT> subclass), which has a matching method
     *         <TT>get<I>Foo</I>Object</TT> for obtaining the specialised object
     *         under its own type.  Note that no access checks are done at this
     *         stage: you may not be able to do anything with the object handle
     *         returned from this method without provoking a
     *         <TT>PoemAccessException</TT>.
     *
     * @exception org.melati.poem.NoSuchRowPoemException
     *                if there is no row in the table with the given troid
     *
     * @see org.melati.poem.Persistent#getTroid()
     */
    Persistent getObject(Integer troid) throws NoSuchRowPoemException;

    /**
     * The object from the table with a given troid.  See previous.
     *
     * @param troid the table row id
     * @return the Persistent
     * @throws org.melati.poem.NoSuchRowPoemException if not found
     * @see #getObject(Integer)
     */
    Persistent getObject(int troid) throws NoSuchRowPoemException;

    /**
     * The from clause has been added as an argument because it is
     * inextricably linked to the when clause, but the default is
     * {@link #quotedName()}.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param fromClause Comma separated list of table names or null for default.
     * @param whereClause SQL fragment
     * @param orderByClause Comma separated list
     * @param includeDeleted Flag as to whether to include soft deleted records
     * @param excludeUnselectable Whether to append unselectable exclusion SQL
     * TODO Should work within some kind of limit
     * @return an SQL SELECT statement put together from the arguments and
     * default order by clause.
     */
    String selectionSQL(String fromClause, String whereClause,
                               String orderByClause, boolean includeDeleted,
                               boolean excludeUnselectable);

    /**
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @return an {@link java.util.Enumeration} of Troids satisfying the criteria.
     */
    Enumeration<Integer> troidSelection(String whereClause, String orderByClause,
                                      boolean includeDeleted,
                                      PoemTransaction transaction);

    /**
     *
     * @see #troidSelection(String, String, boolean, org.melati.poem.PoemTransaction)
     * @param criteria Represents selection criteria possibly on joined tables
     * @param transaction A transaction or null for
     *                    {@link org.melati.poem.PoemThread#transaction()}
     * @return a selection of troids given arguments specifying a query
     */
    Enumeration<Integer> troidSelection(Persistent criteria, String orderByClause,
                                      boolean includeDeleted,
                                      boolean excludeUnselectable,
                                      PoemTransaction transaction);

    /**
     * @param flag whether to remember or forget
     */
    void rememberAllTroids(boolean flag);

    /**
     * @param limit the limit to set
     */
    void setCacheLimit(Integer limit);

    /**
     * A <TT>SELECT</TT>ion of troids of objects from the table meeting given
     * criteria.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * If the orderByClause is null, then the default order by clause is applied.
     * If the orderByClause is an empty string, ie "", then no ordering is
     * applied.
     *
     * @param whereClause an SQL snippet
     * @param orderByClause an SQL snippet
     * @param includeDeleted whether to include deleted records, if any
     *
     * @return an <TT>Enumeration</TT> of <TT>Integer</TT>s, which can be mapped
     *         onto <TT>Persistent</TT> objects using <TT>getObject</TT>;
     *         or you can just use <TT>selection</TT>
     *
     * @see #getObject(Integer)
     * @see #selection(String, String, boolean)
     */
    Enumeration<Integer> troidSelection(String whereClause, String orderByClause,
                                      boolean includeDeleted)
        throws SQLPoemException;

    /**
     * All the objects in the table.
     *
     * @return An <TT>Enumeration</TT> of <TT>Persistent</TT>s, or, if the table
     *         was defined in the DSD under the name <TT><I>foo</I></TT>, of
     *         application-specialised subclasses <TT><I>Foo</I></TT>.  Note
     *         that no access checks are done at this stage: you may not be able
     *         to do anything with some of the object handles in the enumeration
     *         without provoking a <TT>PoemAccessException</TT>.  If the table
     *         has a <TT>deleted</TT> column, the objects flagged as deleted will
     *         be passed over.
     * @see Selectable#selection()
     */
    Enumeration<Persistent> selection() throws SQLPoemException;

    /**
     * A <TT>SELECT</TT>ion of objects from the table meeting given criteria.
     * This is one way to run a search against the database and return the
     * results as a series of typed POEM objects.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause         SQL <TT>SELECT</TT>ion criteria for the search:
     *                            the part that should appear after the
     *                            <TT>WHERE</TT> keyword
     *
     * @return An <TT>Enumeration</TT> of <TT>Persistent</TT>s, or, if the table
     *         was defined in the DSD under the name <TT><I>foo</I></TT>, of
     *         application-specialised subclasses <TT><I>Foo</I></TT>.  Note
     *         that no access checks are done at this stage: you may not be able
     *         to do anything with some of the object handles in the enumeration
     *         without provoking a <TT>PoemAccessException</TT>.  If the table
     *         has a <TT>deleted</TT> column, the objects flagged as deleted will
     *         be passed over.
     *
     * @see org.melati.poem.Column#selectionWhereEq(Object)
     */
    Enumeration<Persistent> selection(String whereClause)
        throws SQLPoemException;

    /**
     * Get an object satisfying the where clause.
     * It is the programmer's responsibility to use this in a
     * context where only one result will be found, if more than one
     * actually exist only the first will be returned.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause         SQL <TT>SELECT</TT>ion criteria for the search:
     *                            the part that should appear after the
     *                            <TT>WHERE</TT> keyword
     * @return the first item satisfying criteria
     */
    Persistent firstSelection(String whereClause);

    /**
     * A <TT>SELECT</TT>ion of objects from the table meeting given criteria,
     * possibly including those flagged as deleted.
     *
     * If the orderByClause is null, then the default order by clause is applied.
     * If the orderByClause is an empty string, ie "", then no ordering is
     * applied.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param includeDeleted      whether to return objects flagged as deleted
     *                            (ignored if the table doesn't have a
     *                            <TT>deleted</TT> column)
     * @return a ResultSet as an Enumeration
     * @see #selection(String)
     */
    Enumeration<Persistent> selection(String whereClause, String orderByClause,
                                  boolean includeDeleted)
        throws SQLPoemException;

    /**
     * Return a selection of rows given an exemplar.
     *
     * @param criteria Represents selection criteria possibly on joined tables
     * @return an enumeration of like objects
     * @see #selection(String, String, boolean)
     */
    Enumeration<Persistent> selection(Persistent criteria)
        throws SQLPoemException;

    /**
     * Return a selection of rows given arguments specifying a query.
     *
     * @see #selection(String, String, boolean)
     * @param criteria Represents selection criteria possibly on joined tables
     * @param orderByClause Comma separated list
     * @return an enumeration of like objects with the specified ordering
     */
    Enumeration<Persistent> selection(Persistent criteria, String orderByClause)
        throws SQLPoemException;

    /**
     * Return a selection of rows given arguments specifying a query.
     *
     * @see #selection(String, String, boolean)
     * @param criteria Represents selection criteria possibly on joined tables
     * @param orderByClause Comma separated list
     * @param excludeUnselectable Whether to append unselectable exclusion SQL
     * @return an enumeration of like Persistents
     */
    Enumeration<Persistent> selection(Persistent criteria, String orderByClause,
                                  boolean includeDeleted, boolean excludeUnselectable)
        throws SQLPoemException;


    /**
     * @param whereClause
     * @return the SQL string for the current SQL dialect
     */
    String countSQL(String whereClause);

    /**
     * Return an SQL statement to count rows put together from the arguments.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param fromClause Comma separated list of table names
     * @return the SQL query
     */
    String countSQL(String fromClause, String whereClause,
                           boolean includeDeleted, boolean excludeUnselectable);

    /**
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @return the number records satisfying criteria.
     */
    int count(String whereClause,
                     boolean includeDeleted, boolean excludeUnselectable)
        throws SQLPoemException;

    /**
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @return the number records satisfying criteria.
     */
    int count(String whereClause, boolean includeDeleted)
        throws SQLPoemException;

    /**
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @return the number of records satisfying criteria.
     */
    int count(String whereClause)
        throws SQLPoemException;

    /**
     * @return the number of records in this table.
     */
    int count()
        throws SQLPoemException;

    /**
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause the SQL criteria
     * @return whether any  records satisfy criteria.
     */
    boolean exists(String whereClause) throws SQLPoemException;

    /**
     * @param persistent a {@link org.melati.poem.Persistent} with some fields filled in
     * @return whether any  records exist with the same fields filled
     */
    boolean exists(Persistent persistent);

    /**
     * Append an SQL logical expression to the given buffer to match rows
     * according to criteria represented by the given object.
     * <p>
     * This default selects rows for which the non-null fields in the
     * given object match, but subtypes may add other criteria.
     * <p>
     * The column names are now qualified with the table name so that
     * subtypes can append elements of a join but there is no filtering
     * by canselect columns.
     *
     * @todo Add mechanism for searching for Nulls (that would be query
     * constructs as per SQL parse tree, but efferent not afferent)
     *
     * @see #notifyColumnInfo(org.melati.poem.ColumnInfo)
     * @see #clearColumnInfoCaches()
     */
    void appendWhereClause(StringBuffer clause, Persistent persistent);

    /**
     * Return an SQL WHERE clause to select rows that match the non-null
     * fields of the given object.
     * <p>
     * This does not filter out any rows with a capability the user
     * does not have in a canselect column, nor did it ever filter
     * out rows deleted according to a "deleted" column.
     * But the caller usually gets a second chance to do both.
     * @return an SQL fragment
     */
    String whereClause(Persistent criteria);

    /**
     * Return an SQL WHERE clause to select rows using the given object
     * as a selection criteria and optionally deleted rows or those
     * included rows the user is not capable of selecting.
     * <p>
     * This is currently implemented in terms of
     * {@link org.melati.poem.Table#appendWhereClause(StringBuffer, org.melati.poem.Persistent)}.
     * @return an SQL fragment
     */
    String whereClause(Persistent criteria,
                              boolean includeDeleted, boolean excludeUnselectable);

    /**
     * @return an SQL fragment
     * @see #cnfWhereClause(java.util.Enumeration, boolean, boolean)
     * @see #whereClause(org.melati.poem.Persistent)
     */
    String cnfWhereClause(Enumeration<Persistent> persistents);

    /**
     * Return a Conjunctive Normal Form (CNF) where clause.
     * See http://en.wikipedia.org/wiki/Conjunctive_normal_form.
     *
     * @return an SQL fragment
     */
    String cnfWhereClause(Enumeration<Persistent> persistents,
                                 boolean includeDeleted, boolean excludeUnselectable);

    /**
     * All the objects in the table which refer to a given object.  If none of
     * the table's columns are reference columns, the <TT>Enumeration</TT>
     * returned will obviously be empty.
     * <p>
     * It is not guaranteed to be quick to execute!
     *
     * @return an <TT>Enumeration</TT> of <TT>Persistent</TT>s
     */

    Enumeration<Persistent> referencesTo(Persistent object);

    /**
     * All the columns in the table which refer to the given table.
     *
     * @param table
     * @return an Enumeration of Columns referring to the specified Table
     */
    Enumeration<Column<?>> referencesTo(Table table);

    /**
     * @return the current highest troid
     */
    int getMostRecentTroid();

    /**
     * @param persistent unused parameter, but might be needed in another troid schema
     * @return the next Troid
     */
    Integer troidFor(Persistent persistent);

    /**
     * Write a new row containing the given object.
     * <p>
     * The given object will be assigned the next troid and its internal
     * state will also be modified.
     *
     * @exception org.melati.poem.InitialisationPoemException The object failed validation
     *   (currently one of its field values failed).
     */
    void create(Persistent p)
         throws AccessPoemException, ValidationPoemException,
            InitialisationPoemException;

    /**
     * Create a new object (record) in the table.
     *
     * @param initialiser         A piece of code for setting the new object's
     *                            initial values.  You'll probably want to define
     *                            it as an anonymous class.
     *
     * @return A <TT>Persistent</TT> representing the new object, or, if the
     *         table was defined in the DSD under the name <TT><I>foo</I></TT>,
     *         an application-specialised subclass <TT><I>Foo</I></TT> of
     *         <TT>Persistent</TT>.
     *
     * @exception org.melati.poem.AccessPoemException
     *                if <TT>initialiser</TT> provokes one during its work (which
     *                is unlikely, since POEM's standard checks are disabled
     *                while it runs)
     * @exception org.melati.poem.ValidationPoemException
     *                if <TT>initialiser</TT> provokes one during its work
     * @exception org.melati.poem.InitialisationPoemException
     *                if the object is left by <TT>initialiser</TT> in a state in
     *                which not all of its fields have legal values, or in which
     *                the calling thread would not be allowed write access to the
     *                object under its <TT>AccessToken</TT>---<I>i.e.</I> you
     *                can't create objects you wouldn't be allowed to write to.
     *
     * @see org.melati.poem.Initialiser#init(Persistent)
     * @see org.melati.poem.PoemThread#accessToken()
     * @see #getCanCreate()
     */
    Persistent create(Initialiser initialiser)
        throws AccessPoemException, ValidationPoemException,
               InitialisationPoemException;

    /**
     * @return A freshly minted floating <TT>Persistent</TT> object for this table,
     * ie one without a troid set
     */
    Persistent newPersistent();

    /**
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause the criteria
     */
    void delete_unsafe(String whereClause);

    /**
     * The number of `extra' (non-DSD-defined) columns in the table.
     */
    int extrasCount();

    /**
     * The capability required for reading records from the table, unless
     * overridden in the record itself.  This simply comes from the table's
     * record in the <TT>tableinfo</TT> table.
     *
     * @return the capability needed to read this table
     */
    Capability getDefaultCanRead();

    /**
     * The capability required for updating records in the table, unless
     * overridden in the record itself.  This simply comes from the table's
     * record in the <TT>tableinfo</TT> table.
     *
     * @return the default  {@link org.melati.poem.Capability} required to write  a
     *         {@link org.melati.poem.Persistent}, if any
     */
    Capability getDefaultCanWrite();

    /**
     * The capability required for deleting records in the table, unless
     * overridden in the record itself.  This simply comes from the table's
     * record in the <TT>tableinfo</TT> table.
     * @return the default  {@link org.melati.poem.Capability} required to delete a
     *         {@link org.melati.poem.Persistent}, if any
     */
    Capability getDefaultCanDelete();

    /**
     * The capability required for creating records in the table.  This simply
     * comes from the table's record in the <TT>tableinfo</TT> table.
     *
     * @return the Capability required to write to this table
     * @see #create(Initialiser)
     */
    Capability getCanCreate();

    /**
     * @return the canReadColumn or the canSelectColumn or null
     */
    Column<Capability> canReadColumn();

    /**
     * @return the canSelectColumn or null
     */
    Column<Capability> canSelectColumn();

    /**
     * @return the canWriteColumn or null
     */
    Column<Capability> canWriteColumn();

    /**
     * @return the canDeleteColumn or null
     */
    Column<Capability> canDeleteColumn();

    /**
     * Add a {@link org.melati.poem.Column} to the database and the {@link org.melati.poem.TableInfo} table.
     *
     * @param infoP the meta data about the {@link org.melati.poem.Column}
     * @return the newly added column
     */
    Column<?> addColumnAndCommit(ColumnInfo infoP) throws PoemException;

    /**
     * @param columnInfo metadata about the column to delete, which is itself deleted
     */
    void deleteColumnAndCommit(ColumnInfo columnInfo) throws PoemException;

    /**
     * A concise string to stand in for the table.  The table's name and a
     * description of where it was defined (the DSD, the metadata tables or the
     * JDBC metadata).
     * {@inheritDoc}
     * @see Object#toString()
     */
    String toString();

    /**
     * Print some diagnostic information about the contents and consistency of
     * POEM's cache for this table to stderr.
     */
    void dumpCacheAnalysis();

    /**
     * Print information about the structure of the table to stdout.
     */
    void dump();

    /**
     * Print information to PrintStream.
     *
     * @param ps PrintStream to dump to
     */
    void dump(PrintStream ps);

    /**
     * A mechanism for caching a selection of records.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause raw SQL selection clause appropriate for this DBMS
     * @param orderByClause which field to order by or null
     * @return the results
     */
    CachedSelection<Persistent> cachedSelection(String whereClause,
                                             String orderByClause);

    /**
     * A mechanism for caching a record count.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause raw SQL selection clause appropriate for this DBMS
     * @param includeDeleted whether to include soft deleted records
     * @return a cached count
     */
    CachedCount cachedCount(String whereClause, boolean includeDeleted);

    /**
     * A mechanism for caching a record count.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause raw SQL selection clause appropriate for this DBMS
     * @param includeDeleted whether to include soft deleted records
     * @param excludeUnselectable whether to exclude columns which cannot be selected
     * @return a cached count
     */
    CachedCount cachedCount(String whereClause, boolean includeDeleted,
                                   boolean excludeUnselectable);

    /**
     * A mechanism for caching a record count.
     *
     * @param criteria a {@link org.melati.poem.Persistent} with selection fields filled
     * @param includeDeleted whether to include soft deleted records
     * @param excludeUnselectable whether to exclude columns which cannot be selected
     * @return a cached count
     */
    CachedCount cachedCount(Persistent criteria, boolean includeDeleted,
                                   boolean excludeUnselectable);

    /**
     * @param criteria a Persistent to extract where clause from
     * @return a CachedCount of records matching Criteria
     */
    CachedCount cachedCount(Persistent criteria);

    /**
     * A mechanism for caching a record count.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause raw SQL selection clause appropriate for this DBMS
     * @return a cached count
     */
    CachedCount cachedCount(String whereClause);

    /**
     * A mechanism for caching an existance.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * NOTE It is possible for the count to be written simultaneously,
     * but the cache will end up with the same result.
     *
     * @param whereClause raw SQL selection clause appropriate for this DBMS
     * @return a cached exists
     */
    CachedExists cachedExists(String whereClause);

    /**
     * A mechanism for caching a record count.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause raw SQL selection clause appropriate for this DBMS
     * @param orderByClause raw SQL order clause appropriate for this DBMS
     * @param nullable whether the ReferencePoemType is nullable
     * @return a {@link org.melati.poem.RestrictedReferencePoemType}
     */
    RestrictedReferencePoemType<?> cachedSelectionType(String whereClause,
                                     String orderByClause, boolean nullable);

    /**
     * Make up a <TT>Field</TT> object whose possible values are a selected
     * subset of the records in the table.  You can make a "dropdown" offering a
     * choice of your green customers by putting this in your handler
     *
     * <BLOCKQUOTE><PRE>
     * context.put("greens",
     *             melati.getDatabase().getCustomerTable().cachedSelectionField(
     *                 "colour = 'green'", null, true, null, "greens"));
     * </PRE></BLOCKQUOTE>
     *
     * and this in your template
     *
     * <BLOCKQUOTE><PRE>
     *   Select a customer: $ml.input($greens)
     * </PRE></BLOCKQUOTE>
     *
     * The list of member records is implicitly cached---permanently, and however
     * big it turns out to be.  So don't go mad with this.  It is recomputed on
     * demand if the contents of the table are changed.  The <TT>whereClause</TT>
     * and <TT>orderByClause</TT> you pass in are checked to see if you have
     * asked for the same list before, so however many times you call this
     * method, you should only trigger actual <TT>SELECT</TT>s when the table
     * contents have changed.  The list is also transaction-safe, in that it will
     * always reflect the state of affairs within your transaction even if you
     * haven't done a commit.
     *
     * It is the programmer's responsibility to ensure that the where clause
     * is suitable for the target DBMS.
     *
     * @param whereClause         an SQL expression (the bit after the
     *                            <TT>SELECT</TT> ... <TT>WHERE</TT>) for picking
     *                            out the records you want
     *
     * @param orderByClause       a comma-separated list of column names which
     *                            determine the order in which the records are
     *                            presented; if this is <TT>null</TT>, the
     *                            <TT>displayorderpriority</TT> attributes of the
     *                            table's columns determine the order
     *
     * @param nullable            whether to allow a blank <TT>NULL</TT> option
     *                            as the first possibility
     *
     * @param selectedTroid       the troid of the record to which the
     *                            <TT>SELECT</TT> field should initially be set
     *
     * @param nameP               the HTML name attribute of the field,
     *                            <I>i.e.</I>
     *                            <TT>&lt;SELECT NAME=<I>name</I>&gt;</TT>
     * @return a Field object
     */
    Field<?> cachedSelectionField(
        String whereClause, String orderByClause, boolean nullable,
        Integer selectedTroid, String nameP);

    /**
     * Don't call this in your application code.
     * Columns should be defined either in the DSD (in which
     * case the boilerplate code generated by the preprocessor will call this
     * method) or directly in the RDBMS (in which case the initialisation code
     * will).
     */
    void defineColumn(Column<?> column)
        throws DuplicateColumnNamePoemException,
               DuplicateTroidColumnPoemException,
               DuplicateDeletedColumnPoemException;

    /**
     * @return incremented extra columns index
     */
    int getNextExtrasIndex();

    /**
     * @param tableInfo the TableInfo to set
     */
    void setTableInfo(TableInfo tableInfo);

    /**
     * @return the {@link org.melati.poem.TableInfo} for this table.
     */
    TableInfo getTableInfo();

    /**
     * @return a DBMS table type eg TEXT 
     */
    String getDbmsTableType();
    

    /**
     * Match columnInfo with this Table's columns.
     * Conversely, create a ColumnInfo for any columns which don't have one.
     */
    void unifyWithColumnInfo() throws PoemException;

    /**
     * Unify the JDBC description of this table with the
     * meta data held in the {@link org.melati.poem.TableInfo}
     *
     * @param colDescs a JDBC {@link java.sql.ResultSet} describing the columns
     * @param primaryKey name of primary key column
     */
    void unifyWithDB(ResultSet colDescs, String primaryKey)
        throws PoemException;
}
