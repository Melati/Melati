package org.melati.poem;

import java.sql.*;

public class StringPoemType extends AtomPoemType {

  private int size;             // or, < 0 for "unlimited"

  public StringPoemType(boolean nullable, int size, int width, int height) {
    super(Types.VARCHAR, "VARCHAR", nullable, width, height);
    this.size = size;
  }

  public StringPoemType(boolean nullable, int size) {
    this(nullable, size, size < 0 ? 40 : size, 1);
  }

  public int getSize() {
    return size;
  }

  protected void _assertValidIdent(Object ident)
      throws ValidationPoemException {
    if (ident != null && !(ident instanceof String))
      throw new TypeMismatchPoemException(ident, this);
    if (size >= 0 && ((String)ident).length() > size)
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
    // FIXME Postgres-specific---aargh (have PostgresStringPoemType etc.)
    return size < 0 ? "TEXT" : "VARCHAR(" + size + ")";
  }

  protected boolean _canBe(PoemType other) {
    if (!(other instanceof StringPoemType))
      return false;
    int otherSize = ((StringPoemType)other).size;
    return otherSize < 0 || size >= 0 && otherSize >= size;
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
