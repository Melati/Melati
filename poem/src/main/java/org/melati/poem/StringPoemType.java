package org.melati.poem;

import java.sql.*;

public class StringPoemType extends AtomPoemType {

  private int size;

  public StringPoemType(boolean nullable, int size) {
    super(Types.VARCHAR, "VARCHAR", nullable);
    this.size = size;
  }

  protected void _assertValidIdent(Object ident)
      throws ValidationPoemException {
    if (ident != null && !(ident instanceof String))
      throw new TypeMismatchPoemException(ident, this);
    if (((String)ident).length() > size)
      throw new StringLengthValidationPoemException(this, (String)ident);
  }

  protected Object _getIdent(ResultSet rs, int col) throws SQLException {
    return rs.getString(col);
  }

  protected void _setIdent(PreparedStatement ps, int col, Object string)
      throws SQLException {
    ps.setString(col, (String)string);
  }

  protected Object _identOfString(String identString) {
    return identString;
  }

  protected String _sqlDefinition() {
    return "VARCHAR(" + size + ")";
  }

  protected boolean _canBe(PoemType other) {
    return
        other instanceof StringPoemType && 
        ((StringPoemType)other).size >= size;
  }

  public String toString() {
    return (isNullable() ? "nullable " : "") + "String(" + size + ")";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypecode(BasePoemType.STRING);
    columnInfo.setSize(size);
  }

  protected String _quotedIdent(Object ident) {
    return org.melati.util.StringUtils.quoted((String)ident, '"');
  }
}
