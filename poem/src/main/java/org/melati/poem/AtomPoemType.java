package org.melati.poem;

public abstract class AtomPoemType extends BasePoemType {

  private String sqlTypeName;

  public AtomPoemType(int sqlTypeCode, String sqlTypeName, boolean nullable) {
    super(sqlTypeCode, nullable);
    this.sqlTypeName = sqlTypeName;
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

  protected String _sqlDefinition() {
    return sqlTypeName;
  }
}
