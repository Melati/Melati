package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class ColumnTypePoemType extends IntegerPoemType {

  private Database database;

  public ColumnTypePoemType(Database database) {
    super(false);
    this.database = database;
  }

  public Enumeration possibleRaws() {
    return
        new FlattenedEnumeration(
            new MappedEnumeration(
                new ArrayEnumeration(PoemTypeFactory.atomTypeFactories)) {
              public Object mapped(Object factory) {
                return ((PoemTypeFactory)factory).code;
              }
            },
            database.getTableInfoTable().troidSelection(null, null, false));
  }

  protected void _assertValidCooked(Object cooked)
      throws ValidationPoemException {
    if (!(cooked instanceof PoemTypeFactory))
      throw new TypeMismatchPoemException(cooked, this);
  }

  protected Object _cookedOfRaw(Object raw) throws NoSuchRowPoemException {
    return PoemTypeFactory.forCode(database, ((Integer)raw).intValue());
  }

  protected Object _rawOfCooked(Object cooked) {
    return ((PoemTypeFactory)cooked).code;
  }

  protected String _stringOfCooked(Object cooked, MelatiLocale locale, int style)
      throws PoemException {
    return ((PoemTypeFactory)cooked).getDisplayName();
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof ColumnTypePoemType;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setType(PoemTypeFactory.TYPE);
  }

  public String toString() {
    return "type code (" + super.toString() + ")";
  }
}
