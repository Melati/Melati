package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

public abstract class BasePoemType implements PoemType, Cloneable {
  private int sqlTypeCode;
  private boolean nullable;
  private int width;
  private int height;

  BasePoemType(int sqlTypeCode, boolean nullable, int width, int height) {
    this.sqlTypeCode = sqlTypeCode;
    this.nullable = nullable;
    this.width = width;
    this.height = height;
  }

  BasePoemType(int sqlTypeCode, boolean nullable, int width) {
    this(sqlTypeCode, nullable, width, 1);
  }

  BasePoemType(int sqlTypeCode, boolean nullable) {
    this(sqlTypeCode, nullable, 8);
  }

  protected abstract void _assertValidIdent(Object ident)
      throws ValidationPoemException;

  public final void assertValidIdent(Object ident)
      throws ValidationPoemException {
    if (ident == null) {
      if (!nullable)
        throw new NullTypeMismatchPoemException(this);
    }
    else
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

  protected Enumeration _possibleIdents() {
    return null;
  }
  
  public Enumeration possibleIdents() {
    Enumeration them = _possibleIdents();
    return them == null ? null :
                   getNullable() ? new ConsEnumeration(null, them) :
                   them;
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
    if (value == null) {
      if (!nullable)
        throw new NullTypeMismatchPoemException(this);
    }
    else
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

  protected abstract String _stringOfValue(Object value)
      throws PoemException;

  public final String stringOfValue(Object value) throws PoemException {
    doubleCheckValidValue(value);
    return value == null ? "" : _stringOfValue(value);
  }

  public final boolean getNullable() {
    return nullable;
  }

  public final int sqlTypeCode() {
    return sqlTypeCode;
  }

  public final int getWidth() {
    return width;
  }

  public final int getHeight() {
    return height;
  }

  protected abstract String _sqlDefinition();

  public final String sqlDefinition() {
    return _sqlDefinition() + (nullable ? "" : " NOT NULL");
  }

  protected abstract boolean _canBe(PoemType other);

  public final boolean canBe(PoemType other) {
    return
        other.sqlTypeCode() == sqlTypeCode &&
        other.getNullable() == nullable &&
        _canBe(other);
  }

  public final PoemType withNullable(boolean nullable) {
    if (this.nullable == nullable)
      return this;
    else {
      BasePoemType it = (BasePoemType)clone();
      it.nullable = nullable;
      return it;
    }
  }

  protected abstract void _saveColumnInfo(ColumnInfo info)
      throws AccessPoemException;

  public void saveColumnInfo(ColumnInfo info) throws AccessPoemException {
    info.setNullable(nullable);
    info.setSize(0);
    info.setWidth(width);
    info.setHeight(height);
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

  public static PoemType ofColumnInfo(Database database,
                                      ColumnInfoData info) {
    return
        PoemTypeFactory.forCode(database,
                                info.type.intValue()).typeOf(database, info);
  }

  // 
  // --------
  //  Object
  // --------
  // 

  protected Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new PoemBugPoemException();
    }
  }
}
