package org.melati.poem;

import java.sql.*;

public class DoublePoemType extends AtomPoemType {

  public DoublePoemType(boolean nullable, int width) {
    super(Types.DOUBLE, "FLOAT8", nullable, width);
  }

  public DoublePoemType(boolean nullable) {
    this(nullable, 9);
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Double))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      double x = rs.getDouble(col);
      return rs.wasNull() ? null : new Double(x);
    }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object real)
      throws SQLException {
    ps.setDouble(col, ((Double)real).doubleValue());
  }

  protected Object _rawOfString(String rawString)
      throws ParsingPoemException {
    try {
      return new Double(rawString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, rawString, e);
    }
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof DoublePoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.DOUBLE);
  }
}
