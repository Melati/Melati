package org.melati.poem;

import java.util.*;

public abstract class AtomPoemType extends BasePoemType {

  private String sqlTypeName;

  public AtomPoemType(int sqlTypeCode, String sqlTypeName, boolean nullable,
                      int width, int height) {
    super(sqlTypeCode, nullable, width, height);
    this.sqlTypeName = sqlTypeName;
  }

  public AtomPoemType(int sqlTypeCode, String sqlTypeName, boolean nullable,
                      int width) {
    this(sqlTypeCode, sqlTypeName, nullable, width, 1);
  }

  public Enumeration possibleIdents() {
    return null;
  }

  protected String _stringOfIdent(Object ident) {
    return ident.toString();
  }

  protected void _assertValidValue(Object value)
      throws ValidationPoemException {
    _assertValidIdent(value);
  }

  protected Object _valueOfIdent(Object ident) throws PoemException {
    return ident;
  }

  protected Object _identOfValue(Object value) {
    return value;
  }

  protected String _stringOfValue(Object value) {
    return _stringOfIdent(_identOfValue(value));
  }

  protected String _sqlDefinition() {
    return sqlTypeName;
  }
}
