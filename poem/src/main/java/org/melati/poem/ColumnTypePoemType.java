package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class ColumnTypePoemType extends IntegerPoemType {

  private Database database;

  public ColumnTypePoemType(Database database) {
    super(false);
    this.database = database;
  }

  public Enumeration possibleIdents() {
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

  protected void _assertValidValue(Object value)
      throws ValidationPoemException {
    if (!(value instanceof PoemTypeFactory))
      throw new TypeMismatchPoemException(value, this);
  }

  protected Object _valueOfIdent(Object ident) throws NoSuchRowPoemException {
    return PoemTypeFactory.forCode(database, ((Integer)ident).intValue());
  }

  protected Object _identOfValue(Object value) {
    return ((PoemTypeFactory)value).code;
  }

  protected String _stringOfValue(Object value) throws PoemException {
    return ((PoemTypeFactory)value).getDisplayName();
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
