package org.melati.poem;

import java.sql.*;

public class TroidPoemType extends IntegerPoemType {

  public static final TroidPoemType it = new TroidPoemType();

  private TroidPoemType() {
    super(false);
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypecode(BasePoemType.TROID);
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof TroidPoemType;
  }

  public String toString() {
    return "troid (" + super.toString() + ")";
  }
}
