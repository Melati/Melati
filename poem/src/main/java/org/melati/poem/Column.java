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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

import java.sql.*;
import java.util.*;
import java.text.*;
import org.melati.util.*;
import org.melati.poem.dbms.*;

public abstract class Column implements FieldAttributes {
  private Table table = null;
  private String name;
  private String quotedName;
  private SQLPoemType type;
  private DefinitionSource definitionSource;
  private ColumnInfo info = null;

  public Column(Table table, String name, SQLPoemType type,
                DefinitionSource definitionSource) {
    this.table = table;
    this.name = name;
    this.quotedName = table.getDatabase().quotedName(name);
    this.type = type;
    this.definitionSource = definitionSource;
  }

  // 
  // ================
  //  Initialisation
  // ================
  // 

  Dbms dbms() {
    return getDatabase().getDbms();
  }

  void unifyType(SQLPoemType storeType, DefinitionSource source) {
    PoemType unified = dbms().canRepresent(storeType, type);
    if (unified == null || !(unified instanceof SQLPoemType))
      throw new TypeDefinitionMismatchException(this, storeType, source);

    type = (SQLPoemType)unified;
  }

  void assertMatches(ResultSet colDesc)
      throws SQLException, TypeDefinitionMismatchException {
    PoemType dbType = getDatabase().defaultPoemTypeOfColumnMetaData(colDesc);

    if (dbms().canRepresent(dbType, type) == null)
      throw new TypeDefinitionMismatchException(this, dbType,
                                                DefinitionSource.sqlMetaData);
  }

  void setColumnInfo(ColumnInfo columnInfo) {
    try {
      unifyType(columnInfo.getType(), DefinitionSource.infoTables);
      columnInfo.setColumn(this);
      if (columnInfo.getDisplaylevel() == DisplayLevel.primary)
        table.setDisplayColumn(this);
      if (columnInfo.getSearchability() == Searchability.primary)
        table.setSearchColumn(this);
      info = columnInfo;
      table.notifyColumnInfo(info);
    }
    catch (Exception e) {
      throw new UnexpectedExceptionPoemException(
          e, "Setting column info for " + name + " to " + columnInfo);
    }
  }

  protected DisplayLevel defaultDisplayLevel() {
    return DisplayLevel.summary;
  }

  protected Searchability defaultSearchability() {
    return Searchability.yes;
  }

  protected Integer defaultDisplayOrderPriority() {
    return null;
  }

  protected boolean defaultSortDescending() {
    return false;
  }

  protected String defaultDisplayName() {
    return StringUtils.capitalised(getName());
  }

  protected int defaultDisplayOrder() {
    return 100;
  }

  protected String defaultDescription() {
    return null;
  }

  protected boolean defaultUserEditable() {
    return true;
  }

  protected boolean defaultUserCreateable() {
    return true;
  }

  protected boolean defaultIndexed() {
    return isTroidColumn();
  }

  protected boolean defaultUnique() {
    return isTroidColumn();
  }

  protected StandardIntegrityFix defaultIntegrityFix() {
    return StandardIntegrityFix.prevent;
  }

  protected int defaultWidth() {
    return 20;
  }

  protected int defaultHeight() {
    return 1;
  }

  protected String defaultRenderinfo() {
    return null;
  }

  void createColumnInfo() throws PoemException {
    if (info == null) {
      info =
          (ColumnInfo)getDatabase().getColumnInfoTable().create(
              new Initialiser() {
                public void init(Persistent g) throws AccessPoemException {
                  ColumnInfo i = (ColumnInfo)g;
                  i.setName(getName());
                  i.setDisplayname(defaultDisplayName());
                  i.setDisplayorder(defaultDisplayOrder());
                  i.setDescription(defaultDescription());
                  i.setDisplaylevel(defaultDisplayLevel());
                  i.setSearchability(defaultSearchability());
                  i.setSortdescending(defaultSortDescending());
                  i.setDisplayorderpriority(defaultDisplayOrderPriority());
                  i.setTableinfoTroid(table.tableInfoID());
                  i.setUsereditable(defaultUserEditable());
                  i.setUsercreateable(defaultUserCreateable());
                  i.setIndexed(defaultIndexed());
                  i.setUnique(defaultUnique());
		  i.setWidth(defaultWidth());
		  i.setHeight(defaultHeight());
		  i.setRenderinfo(defaultRenderinfo());
		  i.setIntegrityfix(defaultIntegrityFix());
                  getType().saveColumnInfo(i);
                }
              });

      // FIXME repeating this in several places is a bad sign

      if (defaultDisplayLevel() == DisplayLevel.primary)
        table.setDisplayColumn(this);
      if (defaultSearchability() == Searchability.primary)
        table.setSearchColumn(this);
    }
  }

  void unifyWithIndex(ResultSet index)
      throws SQLException, IndexUniquenessPoemException {
    boolean indexUnique = !index.getBoolean("NON_UNIQUE");
    if (indexUnique != getUnique())
      throw new IndexUniquenessPoemException(
          this, index.getString("INDEX_NAME"), getUnique());
  }

  // 
  // ===========
  //  Accessors
  // ===========
  // 

  public final Database getDatabase() {
    return getTable().getDatabase();
  }

  public final Table getTable() {
    return table;
  }

  final void setTable(Table table) {
    this.table = table;
  }

  public final String getName() {
    return name;
  }

  public final String quotedName() {
    return quotedName;
  }

  public final String getDisplayName() {
    return info.getDisplayname();
  }

  public final String getDescription() {
    return info.getDescription();
  }

  /**
   * The troid (<TT>id</TT>) of the column's entry in the <TT>columninfo</TT>
   * table.  It will always have one (except during initialisation, which the
   * application programmer will never see).
   */

  final Integer columnInfoID() {
    return info == null ? null : info.troid();
  }

  public final ColumnInfo getColumnInfo() {
    return info;
  }

  public DisplayLevel getDisplayLevel() {
    return info == null ? defaultDisplayLevel() : info.getDisplaylevel();
  }

  public void setDisplayLevel(DisplayLevel level) {
    if (info != null)
      info.setDisplaylevel(level);
  }

  public Searchability getSearchability() {
    return info == null ? defaultSearchability() : info.getSearchability();
  }

  public void setSearchability(Searchability searchability) {
    if (info != null)
      info.setSearchability(searchability);
  }

  public final boolean getUserEditable() {
    return !isTroidColumn() &&
           (info == null || info.getUsereditable().booleanValue());
  }

  public final boolean getUserCreateable() {
    return !isTroidColumn() &&
           (info == null || info.getUsercreateable().booleanValue());
  }

  public final SQLPoemType getSQLType() {
    return type;
  }

  public final PoemType getType() {
    return type;
  }

  public final boolean isTroidColumn() {
    return getType() instanceof TroidPoemType;
  }

  public final boolean isDeletedColumn() {
    return getType() instanceof DeletedPoemType;
  }

  public final boolean getIndexed() {
    return getUnique() || info.getIndexed().booleanValue();
  }

  public final boolean getUnique() {
    return isTroidColumn() || info.getUnique().booleanValue();
  }

  public IntegrityFix getIntegrityFix() {
    IntegrityFix it = info.getIntegrityfix();
    return it == null ? defaultIntegrityFix() : it;
  }

  public void setIntegrityFix(StandardIntegrityFix fix) {
    info.setIntegrityfix(fix);
  }

  public final String getRenderInfo() {
    return info.getRenderinfo();
  }

  public final int getWidth() {
    return info.getWidth().intValue();
  }

  public final int getHeight() {
    return info.getHeight().intValue();
  }

  public final Integer getDisplayOrderPriority() {
    return info == null ? null : info.getDisplayorderpriority();
  }

  public final boolean getSortDescending() {
    return info.getSortdescending() == null ? false : 
      info.getSortdescending().booleanValue();
  }

  // 
  // ===========
  //  Utilities
  // ===========
  // 

  public String toString() {
    return
        table.getName() + "." + name + ": " + getType().toString() +
        " (from " + definitionSource + ")";
  }

  public void dump() {
    System.out.println(toString());
  }

  public String eqClause(Object raw) {
    return quotedName + (raw == null ? " IS NULL" :
			               " = " + type.quotedRaw(raw));
  }

  private PreparedStatementFactory selectionWhereEq = null;

  private PreparedStatementFactory statementWhereEq() {
    if (selectionWhereEq == null)
      selectionWhereEq = new PreparedStatementFactory(
          getDatabase(),
          getTable().selectionSQL(quotedName + " = " +
	      dbms().getRhsValueTemplateSqlDefinition(getType()), 
                                  null, false));
    return selectionWhereEq;
  }

  ResultSet resultSetWhereEq(Object raw) {
    SessionToken token = PoemThread.sessionToken();
    PreparedStatement ps =
        statementWhereEq().preparedStatement(token.transaction);
    type.setRaw(ps, 1, raw);
    return statementWhereEq().resultSet(token, ps);
  }

  Enumeration troidSelectionWhereEq(Object raw) {
    return
        new ResultSetEnumeration(resultSetWhereEq(raw)) {
          public Object mapped(ResultSet rs) throws SQLException {
            return new Integer(rs.getInt(1));
          }
        };
  }

  public Enumeration selectionWhereEq(Object raw) {
    return
        new ResultSetEnumeration(resultSetWhereEq(raw)) {
          public Object mapped(ResultSet rs) throws SQLException {
            return getTable().getObject(rs.getInt(1));
          }
        };
  }

  public Persistent firstWhereEq(Object raw) {
    Enumeration them = selectionWhereEq(raw);
    return them.hasMoreElements() ? (Persistent)them.nextElement() : null;
  }

  public CachedSelection cachedSelectionWhereEq(Object raw) {
    return new CachedSelection(getTable(), eqClause(raw), null);
  }

  // 
  // =======================================
  //  Reading/setting the column in records
  // =======================================
  // 

  public abstract Object getRaw(Persistent g)
      throws AccessPoemException;
  public abstract Object getRaw_unsafe(Persistent g);
  public abstract void setRaw(Persistent g, Object raw)
      throws AccessPoemException, ValidationPoemException;
  public abstract void setRaw_unsafe(Persistent g, Object raw);
  public abstract Object getCooked(Persistent g)
      throws AccessPoemException, PoemException;
  public abstract void setCooked(Persistent g, Object cooked)
      throws AccessPoemException, ValidationPoemException;

  public static class LoadException extends UnexpectedExceptionPoemException {
    public Column column;

    public LoadException(Column column, Exception problem) {
      super(problem);
      this.column = column;
    }

    public String getMessage() {
      return "An unexpected problem arose loading " + column + " from the " +
             "database:\n" + subException;
    }
  }

  public void load_unsafe(ResultSet rs, int rsCol, Persistent g)
      throws LoadException {
    // FIXME double validation
    try {
      setRaw_unsafe(g, type.getRaw(rs, rsCol));
    }
    catch (Exception e) {
      throw new LoadException(this, e);
    }
  }

  public void save_unsafe(Persistent g, PreparedStatement ps, int psCol) {
    // FIXME double validation
    type.setRaw(ps, psCol, getRaw_unsafe(g));
  }

  // 
  // ============
  //  Operations
  // ============
  // 

  public abstract Field asField(Persistent g);

  public Field asEmptyField() {
    return new Field((Object)null, this);
  }

  public static class SettingException extends NormalPoemException {
    public Persistent persistent;
    public Column column;
    public String columnDesc;

    public SettingException(Persistent persistent, Column column,
                            Exception trouble) {
      super(trouble);
      this.persistent = persistent;
      this.column = column;
      columnDesc =
          "field `" + column.getDisplayName() + "' in object `" +
          persistent.displayString(MelatiLocale.here, DateFormat.MEDIUM) +
          "' of type `" + column.getTable().getDisplayName() + "'";
    }

    public String getMessage() {
      return "Unable to set " + columnDesc + "\n" + subException;
    }
  }

  public void setRawString(Persistent g, String rawString) {
    Object raw;
    try {
      raw = getType().rawOfString(rawString);
    }
    catch (Exception e) {
      throw new SettingException(g, this, e);
    }

    setRaw(g, raw);
  }

  public Enumeration referencesTo(Persistent object) {
    return
        getType() instanceof ReferencePoemType &&
            ((ReferencePoemType)getType()).targetTable() == object.getTable() ?
          selectionWhereEq(object.troid()) :
          EmptyEnumeration.it;
  }

  public Persistent ensure(Persistent orCreate) {
    Persistent there = firstWhereEq(getRaw_unsafe(orCreate));
    if (there == null) {
      getTable().create(orCreate);
      return orCreate;
    }
    else
      return there;
  }

  public int firstFree(String whereClause) {
    if (whereClause != null && whereClause.trim().equals(""))
      whereClause = null;
    getTable().readLock();
    ResultSet results = getDatabase().sqlQuery(
        "SELECT " + quotedName + " + 1 " +
        "FROM " + getTable().quotedName() + " AS t1 " +
        "WHERE " +
            (whereClause == null ? "" : "(t1." + whereClause + ") AND ") +
            "NOT EXISTS (" +
                "SELECT * FROM " + getTable().quotedName() + " AS t2 " +
                "WHERE " +
                    (whereClause == null ?
                      "" : "(t2." + whereClause + ") AND ") +
                      "t2." + quotedName + " = t1." + quotedName + " + 1) " +
        "LIMIT 1");
    try {
      if (results.next())
	return results.getInt(1);
      else
	return 0;
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }
}
