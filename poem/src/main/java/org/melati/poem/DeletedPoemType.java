package org.melati.poem;

import java.sql.*;

public class DeletedPoemType extends BooleanPoemType {

  public static final DeletedPoemType it = new DeletedPoemType();

  private DeletedPoemType() {
    super(false);
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.DELETED);
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof DeletedPoemType;
  }

  public String toString() {
    return "deleted (" + super.toString() + ")";
  }
}
