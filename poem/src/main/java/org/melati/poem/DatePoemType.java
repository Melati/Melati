package org.melati.poem;

import java.sql.*;
import org.melati.util.*;

public class DatePoemType extends AtomPoemType {

  DatePoemType(boolean nullable) {
    super(Types.TIMESTAMP, "DATE", nullable, 10);
  }

  protected void _assertValidRaw(Object raw) {
    if (raw != null && !(raw instanceof Date))
      throw new TypeMismatchPoemException(raw, this);
  }

  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    return rs.getDate(col);
  }

  protected void _setRaw(PreparedStatement ps, int col, Object raw)
      throws SQLException {
    ps.setDate(col, (Date)raw);
  }

  protected Object _rawOfString(String raw) {
    return Date.valueOf(raw);
  }

  protected String _stringOfCooked(Object cooked,
				   MelatiLocale locale, int style) {
    return locale.dateFormat(style).format((Date)cooked);
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof DatePoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.DATE);
  }
}
