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

  protected Enumeration _possibleRaws() {
    return targetTable.troidSelection(null, null, false);
  }

  protected void _assertValidCooked(Object cooked)
      throws ValidationPoemException {
    if (!(cooked instanceof Persistent))
      throw new TypeMismatchPoemException(cooked, this);
    if (((Persistent)cooked).getTable() != targetTable)
      throw new ValidationPoemException(
          this, cooked,
          new TableMismatchPoemException((Persistent)cooked, targetTable));
  }

  protected Object _cookedOfRaw(Object raw) throws NoSuchRowPoemException {
    return targetTable.getObject((Integer)raw);
  }

  protected Object _rawOfCooked(Object cooked) {
    return ((Persistent)cooked).troid();
  }

  protected String _stringOfCooked(Object cooked, MelatiLocale locale, int style)
      throws PoemException {
    return ((Persistent)cooked).displayString(locale, style);
  }

  protected boolean _canBe(PoemType other) {
    return
        other instanceof ReferencePoemType &&
        ((ReferencePoemType)other).targetTable == targetTable;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypeCode(targetTable.tableInfoID());
  }

  public String toString() {
    return
        "reference to " + targetTable.getName() + " (" + super.toString() + ")";
  }
}
