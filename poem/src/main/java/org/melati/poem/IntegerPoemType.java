package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public class IntegerPoemType extends AtomPoemType {

  public IntegerPoemType(boolean nullable, int width) {
    super(Types.INTEGER, "INT", nullable, width);
  }

  public IntegerPoemType(boolean nullable) {
    this(nullable, 9);
  }

  /**
   * FIXME do down-counting??
   */

  protected Enumeration _possibleRaws() {
    Integer low = (Integer)getLowRaw();
    Integer limit = (Integer)getLimitRaw();
    return low == null ?
        null :
        new IntegerEnumeration(low.intValue(),
                               limit == null ?
                                   Integer.MAX_VALUE : limit.intValue());
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Integer))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    synchronized (rs) {
      int i = rs.getInt(col);
      return i == 0 && rs.wasNull() ? null : new Integer(i);
    }
  }

  protected void _setRaw(PreparedStatement ps, int col, Object integer)
      throws SQLException {
    ps.setInt(col, ((Integer)integer).intValue());
  }

  protected Object _rawOfString(String rawString)
      throws ParsingPoemException {
    try {
      return new Integer(rawString);
    }
    catch (NumberFormatException e) {
      throw new ParsingPoemException(this, rawString, e);
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
