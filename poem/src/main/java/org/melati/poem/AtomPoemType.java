package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public abstract class AtomPoemType extends BasePoemType {

  private String sqlTypeName;

  public AtomPoemType(int sqlTypeCode, String sqlTypeName, boolean nullable,
                      int width, int height) {
    super(sqlTypeCode, nullable, width, height);
    this.sqlTypeName = sqlTypeName;
  }

  public AtomPoemType(int sqlTypeCode, String sqlTypeName, boolean nullable,
                      int width) {
    this(sqlTypeCode, sqlTypeName, nullable, width, 1);
  }

  protected String _stringOfRaw(Object raw) {
    return raw.toString();
  }

  protected void _assertValidCooked(Object cooked)
      throws ValidationPoemException {
    _assertValidRaw(cooked);
  }

  protected Object _cookedOfRaw(Object raw) throws PoemException {
    return raw;
  }

  protected Object _rawOfCooked(Object cooked) {
    return cooked;
  }

  protected String _stringOfCooked(Object cooked,
				   MelatiLocale locale, int style) {
    return _stringOfRaw(_rawOfCooked(cooked));
  }

  protected String _sqlDefinition() {
    return sqlTypeName;
  }
}
