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

import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import java.sql.ResultSet;

/**
 * This is how you run low-level SQL queries including joins, and get the
 * results back in the form of convenient Melati {@link Field}s which can be
 * rendered automatically in templates.  A complement to the high-level
 * {@link Table#selection()} on the one hand, and the low-level
 * <TT>ResultSet</TT> on the other.
 *
 * <p><quote>
 * 
 * TailoredQuery is specifically for when you want a few fields back
 * (possibly joined from several tables) rather than whole objects.
 * Suppose you want to do
 * <blockquote><code>
 *   SELECT a.foo, b.bar FROM a, b WHERE a.something AND b.id = a.b
 * </code></blockquote>
 * There is nothing to stop you doing this with a good old ResultSet =
 * Database.sqlQuery("SELECT ...").  However if you want to get the same
 * effect, without forgoing the nice features offered by
 * POEM---e.g. access control, rich metadata that makes rendering
 * trivial---you can use a TailoredQuery.
 *
 * </quote><P>
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
  Table[] tablesWithoutCanReadColumn;

  Hashtable<String, Integer> table_columnMap = new Hashtable<String,Integer>();

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
   *   System.out.println(fs.get("user_name").getCookedString(PoemLocale.HERE, DateFormat.MEDIUM) +
   *                 ", " +
   *                 fs.get("group_name").getCookedString(PoemLocale.HERE, DateFormat.MEDIUM));
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
   * @see BasePoemType#quotedRaw(java.lang.Object)
   */

  public TailoredQuery(Column[] selectedColumns, Table[] otherTables,
                       String whereClause, String orderByClause) {
    this(null, selectedColumns, otherTables, whereClause, orderByClause);
  }

  /**
   * Same as without the first argument except that it is inserted 
   * between <code>SELECT</code> and the column list.
   *
   * @param modifier  HACK Allow SQL modifier eg DISTINCT 
   * @param selectedColumns
   * @param otherTables
   * @param whereClause
   * @param orderByClause
   * @see #TailoredQuery(Column[], Table[], String, String)
   */
  public TailoredQuery(String modifier,  
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

    Vector columnsV = new Vector();
    Vector canReadColumnIndices = new Vector();
    Vector tablesWithoutCanReadColumnV = new Vector();

    selectedColumnsCount = selectedColumns.length;
    for (int c = 0; c < selectedColumns.length; ++c) {
      columnsV.addElement(selectedColumns[c]);
    }
    
    for (int t = 0; t < tables.length; t++) {
      Table table = tables[t];
      Column canRead = table.canReadColumn();
      if (canRead == null) {
        // No specific canRead column, revert to the table default protection
        if (!tablesWithoutCanReadColumnV.contains(table))
          tablesWithoutCanReadColumnV.addElement(table);
      }
      else {
        if (!columnsV.contains(canRead)) {
          canReadColumnIndices.addElement(new Integer(columnsV.size()));
          columnsV.addElement(canRead);
        } else { 
          canReadColumnIndices.addElement(new Integer(columnsV.indexOf(canRead)));          
        }
      }
    }     

    this.columns = new Column[columnsV.size()];
    columnsV.copyInto(this.columns);

    isCanReadColumn = new boolean[columnsV.size()];
    for (int i = 0; i < canReadColumnIndices.size(); ++i) {
      int c = ((Integer)canReadColumnIndices.elementAt(i)).intValue();
      isCanReadColumn[c] = true;
    }

    this.tablesWithoutCanReadColumn = new Table[tablesWithoutCanReadColumnV.size()];
    tablesWithoutCanReadColumnV.copyInto(this.tablesWithoutCanReadColumn);

    // Make up the SQL for the query

    StringBuffer sqlLocal = new StringBuffer();

    sqlLocal.append("SELECT ");

    if (modifier != null) {
      sqlLocal.append(modifier);
      sqlLocal.append(' ');
    }

    for (int c = 0; c < columnsV.size(); ++c) {
      if (c > 0) sqlLocal.append(", ");
      Column column = (Column)columnsV.elementAt(c);
      sqlLocal.append(column.getTable().quotedName());
      sqlLocal.append('.');
      sqlLocal.append(column.quotedName());
    }

    sqlLocal.append(" FROM ");

    for (int t = 0; t < tables.length; ++t) {
      if (t > 0) sqlLocal.append(", ");
      sqlLocal.append((tables[t]).quotedName());
    }

    if (whereClause != null && !whereClause.trim().equals("")) {
      sqlLocal.append(" WHERE ");
      sqlLocal.append(whereClause);
    }

    if (orderByClause != null && !orderByClause.trim().equals("")) {
      sqlLocal.append(" ORDER BY ");
      sqlLocal.append(orderByClause);
    }

    this.sql = sqlLocal.toString();

    // Set up mappings from column name (<table>_<column>) to position
    // (including the canRead columns, if anyone ever wants them)

    for (int c = 0; c < columnsV.size(); ++c) {
      Column column = (Column)columnsV.elementAt(c);
      table_columnMap.put(
          column.getTable().getName() + "_" + column.getName(),
          new Integer(c));
    }
  }

  /**
   * Run the query.
   *
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
   * &nbsp;&nbsp;&nbsp;&nbsp;&lt;TD>$ml.rendered($field.DisplayName)&lt;/TD><BR>
   * &nbsp;&nbsp;&nbsp;&nbsp;&lt;TD>$ml.rendered($field)&lt;/TD><BR>
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
   * <TT>AccessPoemException</TT> (<I>all</I> of them, because,
   * without parsing your <TT>whereClause</TT>, there's no way for POEM to
   * know which
   * columns are `tainted'; note that it's probably possible for you to bypass
   * access checks by using sub-SELECTs).
   *
   * <P>
   *
   * Normally, Melati's response to an "access-denied" fields is to terminate
   * template expansion and ask the user to log in, then continue where they
   * were left off.  If, however, you put 
   * <TT>$melati.setPassbackExceptionHandling()</TT> 
   * at the top of the template, or in the servlet, 
   * they will be rendered as warnings by <I>e.g.</I> <TT>HTMLMarkupLanguage</TT>.
   *
   * <P>
   *
   * NOTE Since this way of doing queries involves named fields rather
   * than whole <TT>Persistent</TT> objects, it inevitably bypasses any
   * Java-coded access rules put in place by the programmer by overriding
   * <TT>Persistent.assertCanRead</TT>.
   *
   * @see FieldSet#elements()
   * @see FieldSet#get(java.lang.String)
   * @see PoemThread#accessToken()
   * @see org.melati.template.MarkupLanguage#rendered(Object)
   * @see Persistent#assertCanRead(org.melati.poem.AccessToken)
   *
   * @return 
   * An <TT>Enumeration</TT> of <TT>FieldSet</TT>s, one per row returned from
   * the DBMS.  You can invoke each <TT>FieldSet</TT>'s <TT>elements</TT>
   * method to get an <TT>Enumeration</TT> of all the <TT>Field</TT>s in the
   * row, ready for rendering.  Or you can fetch them by name using the
   * <TT>FieldSet</TT>'s <TT>get</TT> method.  A field corresponding to
   * column <TT><I>col</I></TT> of table <TT><I>tab</I></TT> is named
   * <TT><I>tab</I>_<I>col</I></TT>.
   */

  public Enumeration<FieldSet> selection() {
    return new TailoredResultSetEnumeration(this, database.sqlQuery(sql));
  }

  /**
   * Return an Enumeration of the columns in the first row of 
   * a ResultSet.
   */
  public class FirstRawTailoredResultSetEnumeration<T>
      extends TailoredResultSetEnumeration<T> {

   /**
    * Retrieve the first row of a {@link TailoredQuery}.
    */
    public FirstRawTailoredResultSetEnumeration(TailoredQuery query,
                                                ResultSet resultSet) {
      super(query, resultSet);
    }

    protected Object mapped(ResultSet them) {
      checkTableAccess();
      for (int c = 1; c < query.columns.length; ++c)
        column(them, c);
      return column(them, 0);
    }
  }

  /**
   * @return the first row of this <code>TailoredQuery</code>
   */
  public Enumeration selection_firstRaw() {
    return new FirstRawTailoredResultSetEnumeration(this,
                                                    database.sqlQuery(sql));
  }
  
  /**
   * The SQL of the query.
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return super.toString() + "(SQL=" + sql +  ")";
  }
}
