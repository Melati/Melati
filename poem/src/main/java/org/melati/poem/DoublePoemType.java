package org.melati.poem;

import java.sql.*;

public class DoublePoemType extends AtomPoemType {

  public DoublePoemType(boolean nullable, int width) {
    super(Types.DOUBLE, "DOUBLE", nullable, width);
  }

  public DoublePoemType(boolean nullable) {
    super(Types.DOUBLE, "DOUBLE", nullable, 9);
  }

  protected void _assertValidIdent(Object ident) {
    if (ident != null && !(ident instanceof Double))
      throw new TypeMismatchPoemException(ident, this);
  }

  protected Object _getIdent(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      double x = rs.getDouble(col);
      return rs.wasNull() ? null : new Double(x);
    }
  }

  protected void _setIdent(PreparedStatement ps, int col, Object real)
      throws SQLException {
    ps.setDouble(col, ((Double)real).doubleValue());
  }

  protected Object _identOfString(String identString)
      throws ParsingPoemException {
    try {
      return new Double(identString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, identString, e);
    }
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof DoublePoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypecode(BasePoemType.DOUBLE);
  }
}
