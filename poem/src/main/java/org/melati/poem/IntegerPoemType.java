package org.melati.poem;

import java.sql.*;

public class IntegerPoemType extends AtomPoemType {

  public IntegerPoemType(boolean nullable, int width) {
    super(Types.INTEGER, "INT", nullable, width);
  }

  public IntegerPoemType(boolean nullable) {
    super(Types.INTEGER, "INT", nullable, 9);
  }

  protected void _assertValidIdent(Object ident) {
    if (ident != null && !(ident instanceof Integer))
      throw new TypeMismatchPoemException(ident, this);
  }

  protected Object _getIdent(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      int i = rs.getInt(col);
      return i == 0 && rs.wasNull() ? null : new Integer(i);
    }
  }

  protected void _setIdent(PreparedStatement ps, int col, Object integer)
      throws SQLException {
    ps.setInt(col, ((Integer)integer).intValue());
  }

  protected Object _identOfString(String identString)
      throws ParsingPoemException {
    try {
      return new Integer(identString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, identString, e);
    }
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof IntegerPoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.INTEGER);
  }
}
