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

  protected void _assertValidRaw(Object raw)
      throws ValidationPoemException {
    if (raw != null && !(raw instanceof String))
      throw new TypeMismatchPoemException(raw, this);
    if (size >= 0 && ((String)raw).length() > size)
      throw new StringLengthValidationPoemException(this, (String)raw);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    return rs.getString(col);
  }

  protected void _setRaw(PreparedStatement ps, int col, Object string)
      throws SQLException {
    ps.setString(col, (String)string);
  }

  protected Object _rawOfString(String rawString) {
    return rawString;
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
    return (getNullable() ? "nullable " : "") + "String(" + size + ")";
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.STRING);
    columnInfo.setSize(size);
  }

  protected String _quotedRaw(Object raw) {
    return org.melati.util.StringUtils.quoted((String)raw, '\'');
  }
}
