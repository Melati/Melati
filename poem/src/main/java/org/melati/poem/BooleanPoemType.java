package org.melati.poem;

import java.sql.*;
import java.util.*;

class BooleanPossibleRawEnumeration implements Enumeration {
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

  protected Enumeration _possibleRaws() {
    return new BooleanPossibleRawEnumeration();
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Boolean))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      boolean b = rs.getBoolean(col);
      return rs.wasNull() ? null : b ? Boolean.TRUE : Boolean.FALSE;
    }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object bool)
      throws SQLException {
    ps.setBoolean(col, ((Boolean)bool).booleanValue());
  }

  protected Object _rawOfString(String rawString)
      throws ParsingPoemException {
    rawString = rawString.trim();
    if (rawString.length() == 1)
      switch (rawString.charAt(0)) {
        case 't': case 'T': case 'y': case 'Y': case '1':
          return Boolean.TRUE;
        case 'f': case 'F': case 'n': case 'N': case '0':
          return Boolean.FALSE;
        default:;
      }

    if (rawString.regionMatches(0, "true", 0, 4) ||
             rawString.regionMatches(0, "yes", 0, 3))
      return Boolean.TRUE;
    else if (rawString.regionMatches(0, "false", 0, 5) ||
             rawString.regionMatches(0, "no", 0, 2))
      return Boolean.FALSE;
    else
      throw new ParsingPoemException(this, rawString);
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof BooleanPoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.BOOLEAN);
  }
}
