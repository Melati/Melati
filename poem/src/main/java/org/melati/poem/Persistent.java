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

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Map;

/**
 * The object representing a single table row; this is the <B>PO</B> in POEM!
 * <p>
 * Instances are also used to represent selection criteria.
 *
 * @author timp
 * @since 4 Jul 2007
 *
 */
public interface Persistent extends Persistable, Treeable {

  /**
   * @return whether this object has been persisted
   */
  boolean statusNonexistent();

  /**
   * @return whether this object has been deleted
   */
  boolean statusExistent();

  /** 
   * A convenience method to create this Persistent.
   */
  void makePersistent();

  /**
   * The Table from which the object comes, 
   * complete with metadata.
   * @return the Table
   */
  Table getTable();
  
  /**
   * @param table
   */
  //void setTable(Table table, Integer Troid);

  /**
   * @return The database from which the object comes.  <I>I.e.</I>
   * <TT>getTable().getDatabase()</TT>.
   */
  Database getDatabase();

  /**
   * Lock without actually reading.
   */
  void existenceLock();

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
   * check succeeded is cached; repeat accesses from within the same 
   * transaction are therefore quick.
   *
   * <P>
   *
   * Application programmers can override this method to implement their own
   * programmatic access policies.  For instance, POEM's own <TT>TableInfo</TT>
   * class overrides it with an empty method in order to disable all read
   * protection on <TT>TableInfo</TT> objects.  More interestingly, you could
   * implement a check that depends on the values of the object's fields:
   * for example, you could allow read access to an invoice record to its
   * issuing and receiving parties.
   *
   * @param token       the access token on the basis of which readability is
   *                    being claimed
   *
   * @exception AccessPoemException if the check fails
   *
   * @see Database#inSession
   * @see JdbcTable#getDefaultCanRead
   *
   */

  void assertCanRead(AccessToken token) throws AccessPoemException;

  /**
   * @throws AccessPoemException if current accessToken does not grant read capability
   */
  void assertCanRead() throws AccessPoemException;

  /**
   * @return Whether the object is readable by current AccessToken
   *
   * @see #assertCanRead()
   */

  boolean getReadable();

  /**
   * Check that you have write access to the object.  Which is to say: the
   * <TT>AccessToken</TT> associated with the POEM task executing in the
   * running thread confers the <TT>Capability</TT> required for updating the
   * object's fields.  The remarks made about <TT>assertCanRead</TT> apply
   * (<I>mutatis mutandis</I>) here as well.
   *
   * @see #assertCanRead()
   * @see JdbcTable#getDefaultCanWrite
   */

  void assertCanWrite(AccessToken token) throws AccessPoemException;

  /**
   * @throws AccessPoemException if current accessToken does not grant wraite capability
   */
  void assertCanWrite() throws AccessPoemException;

  /**
   * Check that you have delete access to the object.  Which is to say: the
   * <TT>AccessToken</TT> associated with the POEM task executing in the
   * running thread confers the <TT>Capability</TT> required for updating the
   * object's fields.  The remarks made about <TT>assertCanRead</TT> apply
   * (<I>mutatis mutandis</I>) here as well.
   *
   * @see #assertCanRead()
   * @see JdbcTable#getDefaultCanDelete
   *
   */

  void assertCanDelete(AccessToken token) throws AccessPoemException;

  /**
   * @throws AccessPoemException if current accessToken does not grant delete capability
   */
  void assertCanDelete() throws AccessPoemException;

  /**
   * Check that you have create access to the object.  Which is to say: the
   * <TT>AccessToken</TT> associated with the POEM task executing in the
   * running thread confers the <TT>Capability</TT> required for creating the
   * object. The capability is determined solely by <TT>getCanCreate</TT>
   * from the table. Unlike <TT>assertCanRead</TT> and <TT>assertCanWrite</TT>
   * there is no idea of having a default <TT>Capability</TT> defined 
   * in the table which could be overridden by a <TT>canwrite</TT> field
   * in the persistent (since the persistent has not yet been been written).
   *
   * <P>
   *
   * Application programmers can override this method to implement their own
   * programmatic access policies.
   *
   * @see #assertCanRead()
   * @see #assertCanWrite()
   * @see JdbcTable#getCanCreate
   */

  void assertCanCreate(AccessToken token);

  /**
   * @throws AccessPoemException if current accessToken does not grant create capability
   */
  void assertCanCreate() throws AccessPoemException;

  /**
   * The `identifying value' of one of the object's fields.  This is the value
   * which is actually stored in the database, given to you as a basic Java
   * type; currently, the only fields for which this differs from the `true
   * value' returned from <TT>getCooked</TT> are reference fields with type
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
   * another transaction's activities, unless you do a
   * <TT>PoemThread.commit()</TT> or a <TT>PoemThread.rollback()</TT>.  If you
   * need to, you can store a <TT>Persistent</TT> in a permanent data structure
   * and access it in different sessions over time---or even from concurrently
   * running sessions, though this may slow down access checking; each
   * transaction will see the value it expects.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @return The field's `identifying value'; this will be a <TT>String</TT>,
   *         <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT> or
   *         <TT>Date</TT> as appropriate.  If the field is a reference field,
   *         the result is an <TT>Integer</TT> giving the troid of the referee.
   *         If you want references to be resolved transparently to
   *         <TT>Persistent</TT>s, use <TT>getCooked</TT>.  
   *         If you want a string representation of the field, 
   *         use <TT>getRawString</TT> or <TT>getCookedString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getCooked
   * @see #getRawString
   * @see #getCookedString
   * @see #getField
   * @see Database#inSession
   * @see PoemThread#commit
   * @see PoemThread#rollback
   * @see #assertCanRead()
   */
  Object getRaw(String name) throws NoSuchColumnPoemException,
          AccessPoemException;

  /**
   * A string representation of the `identifying value' of one of the object's
   * fields.  The value returned is relative to the transaction associated with
   * the calling thread, as set up by <TT>Database.inSession</TT>: see the
   * remarks made about <TT>getRaw</TT>.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @return Roughly, the string the underlying RDBMS would display if asked
   *         to show the field's value.  If you want reference fields to be
   *         represented by their referee's <TT>displayString()</TT> (by
   *         default, its primary display field) rather than by its troid, use
   *         <TT>getCookedString</TT>.  If you want the field's value as an
   *         appropriate Java type like <TT>Integer</TT>, use <TT>getRaw</TT>
   *         or <TT>getCooked</TT>---or an equivalent, but type-safe, method
   *         derived from the DSD.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getCookedString
   * @see #getRaw
   * @see #getCooked
   * @see #assertCanRead()
   */

  String getRawString(String name) throws AccessPoemException,
          NoSuchColumnPoemException;

  /**
   * Set the `identifying value' of one of the record's fields.  This is the
   * value which is actually stored in the database, given by you as a basic
   * Java type; currently, the only fields for which this differs from the
   * `true value' expected by <TT>setCooked</TT> are reference fields with type
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
   * you probably mean <TT>setRawString</TT> anyway.
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
   * calls to <TT>setRaw</TT> and relatives will not cause multiple SQL
   * <TT>UPDATE</TT>s to be issued.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @param raw       The new value for the field: a <TT>String</TT>,
   *                    <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT> or
   *                    <TT>Date</TT> as appropriate.  If the field is a
   *                    reference field: an <TT>Integer</TT> giving the troid
   *                    of the referee.  If you want to pass referees as actual
   *                    <TT>Persistent</TT>s, use <TT>setCooked</TT>.  If you
   *                    want to set the field from a string representation
   *                    (<I>e.g.</I> typed in by the user), use
   *                    <TT>setRawString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have write access to the
   *                object (see <TT>assertCanWrite</TT>)
   * @exception ValidationPoemException
   *                if <TT>raw</TT> is not a valid value for the field
   *                (<I>e.g.</I> a string is too long)
   *
   * @see #setCooked
   * @see #setRawString
   * @see #assertCanWrite()
   * @see Database#inSession
   * @see PoemThread#commit
   * @see PoemThread#rollback
   */

  void setRaw(String name, Object raw) throws NoSuchColumnPoemException,
          AccessPoemException, ValidationPoemException;

  /**
   * Set the `identifying value' of one of the record's fields from a string
   * representation.  The remarks about sessions (transactions) and DSD-derived
   * type-safe methods made for <TT>setRaw</TT> apply here too.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @param string      A string that will be parsed to obtain the new value
   *                    for the field.  If it's a reference field, this should
   *                    be a decimal representation of the referee's troid.  If
   *                    you want to set fields to values defined by appropriate
   *                    Java types, use <TT>setRaw</TT> or <TT>setCooked</TT>.
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
   * @see #setRaw
   * @see #setCooked
   * @see #assertCanWrite()
   */

  void setRawString(String name, String string)
          throws NoSuchColumnPoemException, AccessPoemException,
          ParsingPoemException, ValidationPoemException;

  /**
   * The `true value' of one of the object's fields.  This is the
   * fully-interpreted value rather than the one actually stored in the
   * database; currently, the only fields for which this differs from the
   * `identifying value' return from <TT>getRaw</TT> are reference fields
   * with type <TT>ReferencePoemType</TT>.
   *
   * <P>
   *
   * The value returned is relative to the transaction associated with the
   * calling thread, as set up by <TT>Database.inSession</TT>: see the remarks
   * made about <TT>getRaw</TT>.
   *
   * <P>
   *
   * The easiest way to be sure of your types is to predeclare any fields you
   * use in the DSD, or use <TT>getField</TT>.  Again, see the remarks made
   * about <TT>getRaw</TT>.
   *
   * @return The field's `true value'; this will be a <TT>String</TT>,
   *         <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT>,
   *         <TT>Date</TT>, or, if the field is a reference field, a
   *         <TT>Persistent</TT> representing the referee.  If you just want to
   *         see referees' troids, use <TT>getRaw</TT>.  If you want a string
   *         representation of the field, use <TT>getRawString</TT> or
   *         <TT>getCookedString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getRaw
   * @see #getRawString
   * @see #getCookedString
   * @see #getField
   * @see #assertCanRead()
   */

  Object getCooked(String name) throws NoSuchColumnPoemException,
          AccessPoemException;

  /**
   * A string representation of the `true value' of one of the object's fields.
   * For example the return value for the user table's category field would be 
   * User. 
   * The value returned is relative to the transaction associated with the
   * calling thread, as set up by <TT>Database.inSession</TT>: see the remarks
   * made about <TT>getRaw</TT>.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   * @param locale      A PoemLocale eg PoemLocale.HERE
   * @param style       A date format
   *
   * @return The string the underlying RDBMS would display if asked
   *         to show the field's value, except that reference fields are
   *         represented by their referee's <TT>displayString()</TT> (by
   *         default, its primary display field) rather than by its troid.  If
   *         you want to see troids instead, use <TT>getRawString</TT>.  If
   *         you want the field's value as an appropriate Java type like
   *         <TT>Integer</TT>, use <TT>getRaw</TT> or <TT>getCooked</TT>---or
   *         an equivalent, but type-safe, method derived from the DSD.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have read access to the
   *                object (see <TT>assertCanRead</TT>)
   *
   * @see #getRawString
   * @see #getRaw
   * @see #getCooked
   * @see #assertCanRead()
   * @see #displayString
   */

  String getCookedString(String name, PoemLocale locale, int style)
          throws NoSuchColumnPoemException, AccessPoemException;

  /**
   * Set the `true value' of one of the record's fields.  Like
   * <TT>setRaw</TT>, but reference fields expect to see a
   * <TT>Persistent</TT> representing their new referee rather than an
   * <TT>Integer</TT> specifying its troid.  The remarks about sessions
   * (transactions) and DSD-derived type-safe methods made for
   * <TT>setRaw</TT> apply here too.
   *
   * @param name        the name of the field (<I>i.e.</I> the name of the
   *                    column in the RDBMS and DSD)
   *
   * @param cooked      the new value for the field: a <TT>String</TT>,
   *                    <TT>Boolean</TT>, <TT>Integer</TT>, <TT>Double</TT>,
   *                    <TT>Date</TT> or, for a reference field, a
   *                    <TT>Persistent</TT>.  If you want to pass referees as
   *                    troids, use <TT>setRaw</TT>.  If you want to set the
   *                    field from a string representation (<I>e.g.</I> typed
   *                    in by the user), use <TT>setRawString</TT>.
   *
   * @exception NoSuchColumnPoemException
   *                if the field named doesn't exist
   * @exception AccessPoemException
   *                if the calling thread doesn't have write access to the
   *                object (see <TT>assertCanWrite</TT>)
   * @exception ValidationPoemException
   *                if <TT>cooked</TT> is not a valid value for the field
   *                (<I>e.g.</I> a string is too long)
   *
   * @see #setRaw
   * @see #setRawString
   * @see #assertCanWrite()
   */

  void setCooked(String name, Object cooked)
          throws NoSuchColumnPoemException, ValidationPoemException,
          AccessPoemException;

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
   * @param name column name
   * @return the Field of that name
   * @throws NoSuchColumnPoemException if there is no column of that name
   * @throws AccessPoemException if the current AccessToken does not grant access capability
   */
  Field getField(String name) throws NoSuchColumnPoemException,
          AccessPoemException;

  /**
   * Create Fields from Columns. 
   * 
   * @param columns an Enumeration of Columns
   * @return an Enumeration of Fields 
   */
  Enumeration<Field> fieldsOfColumns(Enumeration<Column> columns);

  /**
   * The values of all the object's fields, wrapped up with type information
   * sufficient for rendering them.
   *
   * @return an <TT>Enumeration</TT> of <TT>Field</TT>s
   */

  Enumeration<Field> getFields();

  /**
   * The values of all the object's fields designated for inclusion in full
   * record displays, wrapped up with type information sufficient for rendering
   * them.
   *
   * @return an <TT>Enumeration</TT> of <TT>Field</TT>s
   * @see DisplayLevel#record
   */

  Enumeration<Field> getRecordDisplayFields();

  /**
   * All fields at the detailed display level in display order.
   *
   * @return an <TT>Enumeration</TT> of <TT>Field</TT>s
   * @see DisplayLevel#detail
   */
  Enumeration<Field> getDetailDisplayFields();

  /**
   * All fields at the summary display level in display order.
   *
   * @return an <TT>Enumeration</TT> of <TT>Field</TT>s
   * @see DisplayLevel#summary
   */
  Enumeration<Field> getSummaryDisplayFields();

  /**
   * @return an <TT>Enumeration</TT> of searchable <TT>Field</TT>s
   */
  Enumeration<Field> getSearchCriterionFields();

  /**
   * @return the Primary Display Column as a Field
   */
  Field getPrimaryDisplayField();

  /**
   * Delete the object.  Before the record is deleted from the database, POEM
   * checks to see if it is the target of any reference fields.  What happens
   * in this case is determined by the <TT>integrityfix</TT> setting of the
   * referring column, unless that's overridden via the
   * <TT>integrityFixOfColumn</TT> argument.  By default, a
   * <TT>DeletionIntegrityPoemException</TT> is thrown, but this behaviour can
   * be changed through the admin interface.
   *
   * @see IntegrityFix
   * @see PoemThread#commit
   *
   * @param integrityFixOfColumn
   *            A map from {@link Column} to {@link IntegrityFix} which says
   *            how referential integrity is to be maintained for each column
   *            that can refer to the object being deleted.  May be
   *            <TT>null</TT> to mean `empty'.  If a column isn't mentioned,
   *            the default behaviour for the column is used.  (The default 
   *            is {@link StandardIntegrityFix#prevent}.)
   */
  void delete(Map<Column, IntegrityFix> integrityFixOfColumn);

  /**
   * Delete without access checks.
   */
  void delete_unsafe();

  /**
   * Delete this persistent, with default integrity checks, 
   * ie disallow deletion if object referred to by others.
   */
  void delete();

  /**
   * Delete the object, with even more safety checks for referential integrity.
   * As {@link #delete(java.util.Map)}, but waits for exclusive access to the
   * database before doing the delete, and commits the session immediately
   * afterwards.  
   * <p>
   * This used to be the only deletion entry point allowed, but
   * now we ensure that the possible race condition involving new
   * pointers to the deleted object created during the deletion process is
   * covered. So it is recommended to use {@link #delete(java.util.Map)}
   * unless you really want this functionality.
   *
   */
  void deleteAndCommit(Map<Column, IntegrityFix> integrityFixOfColumn)
          throws AccessPoemException, DeletionIntegrityPoemException;

  /**
   * Convenience method with default integrity fix. 
   * 
   * @throws AccessPoemException
   * @throws DeletionIntegrityPoemException
   */
  void deleteAndCommit() throws AccessPoemException,
          DeletionIntegrityPoemException;

  /**
   * Create a new object like this one.
   * This Persistent must not be floating.
   * 
   * @return A floating clone
   */
  Persistent duplicated() throws AccessPoemException;

  /**
   * Create a new persistent like this one, regardless of 
   * whether this Persistent has been written to the dbms yet.
   * @return A floating clone
   */
  Persistent duplicatedFloating() throws AccessPoemException;

  /**
   * A string describing the object for the purposes of rendering it in lists
   * presented to the user.  Unless overridden, this returns the value picked
   * out by the designated `primary display column' of the table from which the
   * object comes.  If there is no such column, the object's troid is returned
   * (as a decimal string).
   * 
   * @param locale our locale
   * @param style 
   *      a DateFormat (only applicable to those rare objects whose summary column is a date)
   * @return the String to display
   * @throws AccessPoemException 
   *         if current User does not have viewing {@link Capability}
   */
  String displayString(PoemLocale locale, int style)
          throws AccessPoemException;

  /**
   * Defaults to DateFormat.MEDIUM.
   * @return Default String for display.
   * 
   * @throws AccessPoemException 
   *         if current User does not have viewing {@link Capability}
   */
  String displayString(PoemLocale locale) throws AccessPoemException;

  /**
   * @return Default String for display.
   * 
   * @throws AccessPoemException 
   *         if current User does not have viewing {@link Capability}
   */
  String displayString() throws AccessPoemException;

  /**
   * @return the dump String
   */
  String dump();

  /**
   * Dump to a PrintStream.
   * @param p the PrintStream to dump to
   */
  void dump(PrintStream p);

  /**
   * Called after this persistent is written to the database
   * on being inserted or modified.
   * <p>
   * This is called after postInsert() or postModify().
   * <p>
   * This is low level and there is a limit to what you can
   * do here without causing infinitely recursive calls.
   */
  void postWrite();

  /**
   * Called after this persistent is written to the database
   * for the first time.
   * <p>
   * This is low level and there is a limit to what you can
   * do here without causing infinitely recursive calls.
   */
  void postInsert();

  /**
   * Called after this persistent is updated and written to the
   * database replacing the existing record it represents.
   * <p>
   * Not called when it is written to the database for the
   * first time.
   * <p>
   * This is low level and there is a limit to what you can
   * do here without causing infinitely recursive calls.
   */
  void postModify();

  /**
   * Optionally called before an instance is edited by the user.
   * <p>
   * See {@link #postEdit(boolean)} for additional comments.
   * However, it is not called when a newly created row is
   * edited.
   */
  void preEdit();

  /**
   * Optionally called after this instance is edited by a user.
   * <p>
   * Unlike {@link #postModify()} and {@link #postInsert()} this
   * is not called during write down but can be called by
   * applications after individual field edits by the user
   * have been reflected in the instance.
   * <p>
   * It can be be overridden to enforce data model constraints
   * such as validity of columns relative to other columns.
   * These will be enforced when the admin system is used.
   * <p>
   * This is a higher level method than {@link #postModify()}
   * so is less likely to lead to infinite recursion or other
   * such problems.
   * <p>
   * Sorry for the lack of signature consistency with the
   * lower level methods but I got tired of having to call
   * my own application specific common method.
   *
   * @param creating Are we in the process of creating a new record?
   */
  void postEdit(boolean creating);

  /**
   * @return the dirty
   */
  boolean isDirty();
  
  /**
   * @param dirty the dirty to set
   */
  void setDirty(boolean dirty);


}

