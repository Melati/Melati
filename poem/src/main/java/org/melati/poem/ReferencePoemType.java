package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class ReferencePoemType extends IntegerPoemType {

  private Table targetTable;

  public ReferencePoemType(Table targetTable, boolean nullable) {
    super(nullable);
    if (targetTable == null)
      throw new NullPointerException();
    this.targetTable = targetTable;
  }

  public Table targetTable() {
    return targetTable;
  }

  public Enumeration possibleIdents() {
    Enumeration them = targetTable.troidSelection(null, false);
    return isNullable() ? new ConsEnumeration(null, them) : them;
  }

  protected void _assertValidValue(Object value)
      throws ValidationPoemException {
    if (!(value instanceof Persistent))
      throw new TypeMismatchPoemException(value, this);
    if (((Persistent)value).getTable() != targetTable)
      throw new ValidationPoemException(
          this, value,
          new TableMismatchPoemException((Persistent)value, targetTable));
  }

  protected Object _valueOfIdent(Object ident) throws NoSuchRowPoemException {
    return targetTable.getObject((Integer)ident);
  }

  protected Object _identOfValue(Object value) {
    return ((Persistent)value).troid();
  }

  protected String _stringOfValue(Object value) throws PoemException {
    return ((Persistent)value).displayString();
  }

  protected boolean _canBe(PoemType other) {
    return
        other instanceof ReferencePoemType &&
        ((ReferencePoemType)other).targetTable == targetTable;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypecode(BasePoemType.REFERENCE);
    columnInfo.setTargettableTroid(targetTable.tableInfoID());
  }

  public String toString() {
    return
        "reference to " + targetTable.getName() + " (" + super.toString() + ")";
  }
}
