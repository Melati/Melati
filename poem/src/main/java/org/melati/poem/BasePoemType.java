package org.melati.poem;

import java.sql.*;

public abstract class BasePoemType implements PoemType {
  private int sqlTypeCode;
  private boolean nullable;

  BasePoemType(int sqlTypeCode, boolean nullable) {
    this.sqlTypeCode = sqlTypeCode;
    this.nullable = nullable;
  }

  protected abstract void _assertValidIdent(Object ident)
      throws ValidationPoemException;

  public final void assertValidIdent(Object ident)
      throws ValidationPoemException {
    if (ident == null && !nullable)
      throw new NullTypeMismatchPoemException(this);
    _assertValidIdent(ident);
  }

  public final void doubleCheckValidIdent(Object ident) {
    try {
      assertValidIdent(ident);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  protected abstract Object _getIdent(ResultSet rs, int col)
      throws SQLException;

  public final Object getIdent(ResultSet rs, int col)
      throws ValidationPoemException {
    Object o;
    try {
      o = _getIdent(rs, col);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }

    assertValidIdent(o);
    return o;
  }

  protected abstract void _setIdent(PreparedStatement ps, int col,
                                    Object ident)
      throws SQLException;

  public final void setIdent(PreparedStatement ps, int col, Object ident) {
    doubleCheckValidIdent(ident);
    try {
      if (ident == null)
        ps.setNull(col, sqlTypeCode());
      else
        _setIdent(ps, col, ident);
    }
    catch (SQLException e) {
      throw new SQLSeriousPoemException(e);
    }
  }

  protected abstract String _stringOfIdent(Object ident);

  public final String stringOfIdent(Object ident)
      throws ValidationPoemException {
    assertValidIdent(ident);
    return _stringOfIdent(ident);
  }

  protected abstract Object _identOfString(String string)
      throws ParsingPoemException;

  public final Object identOfString(String string)
      throws ParsingPoemException, ValidationPoemException {
    Object ident = _identOfString(string);
    assertValidIdent(ident);
    return ident;
  }

  protected abstract void _assertValidValue(Object value)
      throws ValidationPoemException;

  public final void assertValidValue(Object value)
      throws ValidationPoemException {
    if (value == null && !nullable)
      throw new NullTypeMismatchPoemException(this);
    _assertValidValue(value);
  }

  public final void doubleCheckValidValue(Object value) {
    try {
      assertValidValue(value);
    }
    catch (ValidationPoemException e) {
      throw new UnexpectedValidationPoemException(e);
    }
  }

  protected abstract Object _valueOfIdent(Object ident) throws PoemException;

  public final Object valueOfIdent(Object ident) throws PoemException {
    doubleCheckValidIdent(ident);
    return ident == null ? null : _valueOfIdent(ident);
  }

  protected abstract Object _identOfValue(Object ident) throws PoemException;

  public final Object identOfValue(Object value) {
    doubleCheckValidValue(value);
    return value == null ? null : _identOfValue(value);
  }

  public final boolean isNullable() {
    return nullable;
  }

  public final int sqlTypeCode() {
    return sqlTypeCode;
  }

  protected abstract String _sqlDefinition();

  public final String sqlDefinition() {
    return _sqlDefinition() + (nullable ? "" : " NOT NULL");
  }

  protected abstract boolean _canBe(PoemType other);

  public final boolean canBe(PoemType other) {
    return
        other.sqlTypeCode() == sqlTypeCode &&
        other.isNullable() == nullable &&
        _canBe(other);
  }

  protected abstract void _saveColumnInfo(ColumnInfo info)
      throws AccessPoemException;

  public void saveColumnInfo(ColumnInfo info) throws AccessPoemException {
    info.setNullable(nullable);
    _saveColumnInfo(info);
  }

  protected String _quotedIdent(Object ident) {
    return ident.toString();
  }

  public String quotedIdent(Object ident) throws ValidationPoemException {
    assertValidIdent(ident);
    return ident == null ? "NULL" : _quotedIdent(ident);
  }

  public String toString() {
    return sqlDefinition();
  }

  static final int
      TROID = 0, BOOLEAN = 1, INTEGER = 2, STRING = 3, DOUBLE = 4, REFERENCE = 6;

  static PoemType ofColumnInfo(Database database,
                               ColumnInfoFields info) {
    // FIXME null checking ...
    switch (info.typecode.intValue()) {
      case TROID:
        return TroidPoemType.it;
      case BOOLEAN:
        return new BooleanPoemType(info.nullable.booleanValue());
      case INTEGER:
        return new IntegerPoemType(info.nullable.booleanValue());
      case STRING:
        return new StringPoemType(info.nullable.booleanValue(),
                                  info.size.intValue());
      case DOUBLE:
        return new DoublePoemType(info.nullable.booleanValue());
      case REFERENCE:
        Table table =
          database.tableWithTableInfoID(info.targettable.intValue());
        return new ReferencePoemType(table,
                                     info.nullable.booleanValue());
      default:
        throw new InvalidColumnInfoTypecodePoemException(info);
    }
  }
}
