package org.melati.poem;

import java.sql.*;
import java.util.*;

class BooleanPossibleIdentEnumeration implements Enumeration {
  private int state = 0;

  public boolean hasMoreElements() {
    return state < 2;
  }

  public synchronized Object nextElement() {
    if (state == 2)
      throw new NoSuchElementException();
    else
      return state++ == 0 ? Boolean.FALSE : Boolean.TRUE;
  }
}

public class BooleanPoemType extends AtomPoemType {

  public BooleanPoemType(boolean nullable) {
    super(Types.BIT, "BOOLEAN", nullable, 5);
  }

  protected Enumeration _possibleIdents() {
    return new BooleanPossibleIdentEnumeration();
  }

  protected void _assertValidIdent(Object ident) {
    if (ident != null && !(ident instanceof Boolean))
      throw new TypeMismatchPoemException(ident, this);
  }

  protected Object _getIdent(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      boolean b = rs.getBoolean(col);
      return rs.wasNull() ? null : b ? Boolean.TRUE : Boolean.FALSE;
    }
  }

  protected void _setIdent(PreparedStatement ps, int col, Object bool)
      throws SQLException {
    ps.setBoolean(col, ((Boolean)bool).booleanValue());
  }

  protected Object _identOfString(String identString)
      throws ParsingPoemException {
    identString = identString.trim();
    if (identString.length() == 1)
      switch (identString.charAt(0)) {
        case 't': case 'T': case 'y': case 'Y': case '1':
          return Boolean.TRUE;
        case 'f': case 'F': case 'n': case 'N': case '0':
          return Boolean.FALSE;
        default:;
      }

    if (identString.regionMatches(0, "true", 0, 4) ||
             identString.regionMatches(0, "yes", 0, 3))
      return Boolean.TRUE;
    else if (identString.regionMatches(0, "false", 0, 5) ||
             identString.regionMatches(0, "no", 0, 2))
      return Boolean.FALSE;
    else
      throw new ParsingPoemException(this, identString);
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof BooleanPoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.BOOLEAN);
  }
}
