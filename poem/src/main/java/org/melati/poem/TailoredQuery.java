/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

import java.util.*;
import java.sql.*;

/**
 * This is how you run low-level SQL queries including joins, and get the
 * results back in the form of convenient Melati <TT>Field</TT>s which can be
 * rendered automatically in templates.  A complement to the high-level
 * <TT>Table.selection</TT> on the one hand, and the low-level
 * <TT>ResultSet</TT> on the other.
 *
 * <P>
 *
 * If Postgresql's <TT>ResultSetMetaData</TT> supported <TT>getTableName</TT>
 * even approximately, this would all be "even simpler" to use and somewhat
 * more flexible.  Because it doesn't, and because of the requirement to
 * perform (as far as possible) read access checks on the records accessed
 * during the query, the interface necessarily takes a slightly structured form
 * rather than just being raw SQL---although the programmer does get complete
 * freedom as far as the core <TT>WHERE</TT> clause is concerned.
 *
 * @see Table#selection(java.lang.String, java.lang.String, boolean)
 */

public class TailoredQuery {

  protected Database database;
  protected String sql;
  int selectedColumnsCount;
  Column[] columns;
  boolean[] isCanReadColumn;
  Table[] tables;
  Table[] canReadTables;

  Hashtable table_columnMap = new Hashtable();

  /**
   * Construct a low-level SQL query, possibly including joins, from which
   * results come back in form of automatically-renderable Melati
   * <TT>Field</TT>s.  The queries you can construct are of the form
   *
   * <BLOCKQUOTE><TT>
   * SELECT <I>t1</I>.<I>c1</I>, <I>t2</I>.<I>c2</I> </TT>...<TT>
   * FROM <I>t1</I>, <I>t2</I> </TT>...<TT>,
   *      <I>t10</I>, <I>t11</I> </TT>...<TT>
   * WHERE <I>whereClause</I> 
   * ORDER BY <I>orderByClause</I>
   * </TT></BLOCKQUOTE>
   *
   * You specify the columns you want to return
   * (<TT><I>t1</I>.<I>c1</I></TT>&nbsp;...) in the <TT>selectedColumns</TT>
   * parameter, the selection criteria (including joins) in the
   * <TT>whereClause</TT> parameter, and the ordering criteria in the
   * <TT>orderByClause</TT>.  If your <TT>whereClause</TT> or
   * <TT>orderByClause</TT> use tables <TT><I>t10</I></TT>&nbsp;... not implied
   * by <TT>selectedColumns</TT>, you must include them in the
   * <TT>otherTables</TT> parameter.
   *
   * <P>
   *
   * Note that there is no provision for aliasing, which does restrict the
   * available queries somewhat.
   *
   * <P>
   *
   * To get the results of the query, use <TT>selection()</TT> (below).
   *
   * <P>
   *
   * Example:
   * <BLOCKQUOTE><PRE>
   * Column[] columns = {
   *     database.getUserTable().getNameColumn(),
   *     database.getGroupTable().getNameColumn(),
   * };
   * Table[] tables = { database.getGroupMembershipTable() };
   * 
   * TailoredQuery q =
   *     new TailoredQuery(
   *    columns, tables,
   *    "\"user\" = \"user\".id AND \"group\" = \"group\".id",
   *    null);
   * 
   * for (Enumeration ms = q.selection(); ms.hasMoreElements();) {
   *   FieldSet fs = (FieldSet)ms.nextElement();
   *   System.out.println(fs.get("user_name").getCookedString() +
   *                 ", " +
   *                 fs.get("group_name").getCookedString());
   * }
   * </PRE></BLOCKQUOTE>
   *
   * @param selectedColumns     The columns you want to select out
   * @param otherTables         Tables aside from those to which your
   *                            <TT>selectedColumns</TT> are attached which
   *                            you need to use in the <TT>whereClause</TT>
   * @param whereClause         Search criteria for your query; note that
   *                            you will have to do any necessary quoting of
   *                            identifiers/values yourself (or use
   *                            <TT>Column.quotedName</TT> and
   *                            <TT>PoemType.quotedRaw</TT>)
   * @param orderByClause       Ordering criteria for your query
   *
   * @see #selection
   * @see Column#quotedName()
   * @see PoemType#quotedRaw(java.lang.Object)
   */

  public TailoredQuery(Column[] selectedColumns, Table[] otherTables,
                       String whereClause, String orderByClause) {
    this(null, selectedColumns, otherTables, whereClause, orderByClause);
  }

  public TailoredQuery(String modifier, // FIXME hack
		       Column[] selectedColumns, Table[] otherTables,
                       String whereClause, String orderByClause) {

    this.database = selectedColumns[0].getDatabase();

    // Make a list of all the tables used

    Vector tablesV = new Vector();

    for (int c = 0; c < selectedColumns.length; ++c) {
      Table table = selectedColumns[c].getTable();
      if (!tablesV.contains(table))
        tablesV.addElement(table);
    }

    for (int t = 0; t < otherTables.length; ++t)
      if (!tablesV.contains(otherTables[t]))
        tablesV.addElement(otherTables[t]);

    tables = new Table[tablesV.size()];
    tablesV.copyInto(tables);

    // Record the access protection sources for all the tables used (of course
    // this doesn't include computed `Persistent.assertCanRead's written by the
    // programmer).  Make up a list of all the columns we need, included any
    // `canRead' access control columns for tables.

    Vector columns = new Vector();
    Vector canReadColumnIndices = new Vector();
    Vector canReadTables = new Vector();

    selectedColumnsCount = selectedColumns.length;
    for (int c = 0; c < selectedColumns.length; ++c)
      columns.addElement(selectedColumns[c]);

    for (int t = 0; t < tables.length; ++t) {
      Table table = tables[t];
      Column canRead = table.canReadColumn();
      if (canRead == null) {
        // No specific canRead column, revert to the table default protection

        if (!canReadTables.contains(table))
          canReadTables.addElement(table);
      }
      else
        if (!columns.contains(canRead)) {
          canReadColumnIndices.addElement(new Integer(columns.size()));
          columns.addElement(canRead);
        }
    }     

    this.columns = new Column[columns.size()];
    columns.copyInto(this.columns);

    isCanReadColumn = new boolean[columns.size()];
    for (int i = 0; i < canReadColumnIndices.size(); ++i) {
      int c = ((Integer)canReadColumnIndices.elementAt(i)).intValue();
      isCanReadColumn[c] = true;
    }

    this.canReadTables = new Table[canReadTables.size()];
    canReadTables.copyInto(this.canReadTables);

    // Make up the SQL for the query

    StringBuffer sql = new StringBuffer();

    sql.append("SELECT ");

    if (modifier != null) {
      sql.append(modifier);
      sql.append(' ');
    }

    for (int c = 0; c < columns.size(); ++c) {
      if (c > 0) sql.append(", ");
      Column column = (Column)columns.elementAt(c);
      sql.append(column.getTable().quotedName());
      sql.append('.');
      sql.append(column.quotedName());
    }

    sql.append(" FROM ");

    for (int t = 0; t < tables.length; ++t) {
      if (t > 0) sql.append(", ");
      sql.append((tables[t]).quotedName());
    }

    if (whereClause != null && !whereClause.trim().equals("")) {
      sql.append(" WHERE ");
      sql.append(whereClause);
    }

    if (orderByClause != null && !orderByClause.trim().equals("")) {
      sql.append(" ORDER BY ");
      sql.append(orderByClause);
    }

    this.sql = sql.toString();

    // Set up mappings from column name (<table>_<column>) to position
    // (including the canRead columns, if anyone ever wants them)

    for (int c = 0; c < columns.size(); ++c) {
      Column column = (Column)columns.elementAt(c);
      table_columnMap.put(
          column.getTable().getName() + "_" + column.getName(),
          new Integer(c));
    }
  }

  /**
   * Run the query.
   *
   * @return 
   *
   * An <TT>Enumeration</TT> of <TT>FieldSet</TT>s, one per row returned from
   * the DBMS.  You can invoke each <TT>FieldSet</TT>'s <TT>elements</TT>
   * method to get an <TT>Enumeration</TT> of all the <TT>Field</TT>s in the
   * row, ready for rendering.  Or you can fetch them by name using the
   * <TT>FieldSet</TT>'s <TT>get</TT> method.  A field corresponding to
   * column <TT><I>col</I></TT> of table <TT><I>tab</I></TT> is named
   * <TT><I>tab</I>_<I>col</I></TT>.
   *
   * <P>
   *
   * Here's an example of presenting the results of a <TT>TailoredQuery</TT> in
   * a WebMacro template:
   *
   * <BLOCKQUOTE><TT>
   * &lt;TABLE&gt;<BR>
   * &nbsp;#foreach $fieldSet in $tailoredQuery.selection() #begin<BR>
   * &nbsp;&nbsp;&lt;TR&gt;<BR>
   * &nbsp;&nbsp;&nbsp;#foreach $field in $fieldSet #begin<BR>
   * &nbsp;&nbsp;&nbsp;&nbsp;<TD>$ml.rendered($field.DisplayName)</TD><BR>
   * &nbsp;&nbsp;&nbsp;&nbsp;<TD>$ml.rendered($field)</TD><BR>
   * &nbsp;&nbsp;&nbsp;#end<BR>
   * &nbsp;&nbsp;&lt;/TR&gt;<BR>
   * &nbsp;#end<BR>
   * &lt;/TABLE&gt;
   * </TT></BLOCKQUOTE>
   *
   * <P>
   *
   * Read access checks are performed against the POEM access token associated
   * with the thread running this method (see
   * <TT>PoemThread.accessToken()</TT>) on all the tables implied by the
   * <TT>selectedColumns</TT> and <TT>otherTables</TT> arguments given at
   * construct time.  If the checks fail for a given row, all the fields in the
   * corresponding <TT>FieldSet</TT> are booby-trapped to throw the relevant
   * <TT>AccessPoemException</TT> (<I>all</I> of them, because there's no way
   * for POEM to know without parsing your <TT>whereClause</TT> to know which
   * columns are `tainted'; note that it's probably possible for you to bypass
   * access checks by using sub-SELECTs).
   *
   * <P>
   *
   * Normally, Melati's response to an "access-denied" fields is to terminate
   * template expansion and ask the user to log in, then continue where they
   * were left off.  If, however, you put <TT>#set $onVariableException =
   * $melati.PassbackVariableExceptionHandler</TT> at the top of the template,
   * they will be rendered as warning notices (by <I>e.g.</I>
   * <TT>HTMLMarkupLanguage</TT>).
   *
   * <P>
   *
   * <B>Note.</B> Since this way of doing queries involves named fields rather
   * than whole <TT>Persistent</TT> objects, it inevitably bypasses any
   * Java-coded access rules put in place by the programmer by overriding
   * <TT>Persistent.assertCanRead</TT>.
   *
   * @see FieldSet#elements()
   * @see FieldSet#get(java.lang.String)
   * @see PoemThread#accessToken()
   * @see org.melati.HTMLMarkupLanguage#rendered(org.melati.poem.AccessPoemException)
   * @see Persistent#assertCanRead(org.melati.poem.AccessToken)
   */

  public Enumeration selection() {
    return new TailoredResultSetEnumeration(this, database.sqlQuery(sql));
  }

  public class FirstRawTailoredResultSetEnumeration
      extends TailoredResultSetEnumeration {

    public FirstRawTailoredResultSetEnumeration(TailoredQuery query,
						ResultSet resultSet) {
      super(query, resultSet);
    }

    protected Object mapped(ResultSet them) {
      checkTableAccess(them);
      for (int c = 1; c < query.columns.length; ++c)
	column(them, c);
      return column(them, 0);
    }
  }

  public Enumeration selection_firstRaw() {
    return new FirstRawTailoredResultSetEnumeration(this,
						    database.sqlQuery(sql));
  }
}
