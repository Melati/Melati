package org.melati.poem;

public class ReferencePoemType extends IntegerPoemType {

  private Table targetTable;

  public ReferencePoemType(Table targetTable, boolean nullable) {
    super(nullable);
    if (targetTable == null)
      throw new NullPointerException();
    this.targetTable = targetTable;
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
    return ((Persistent)value).getTroid();
  }

  protected boolean _canBe(PoemType other) {
    return
        other instanceof ReferencePoemType &&
        ((ReferencePoemType)other).targetTable == targetTable;
  }

  protected void _saveColumnInfo(ColumnInfo columnInfo)
      throws AccessPoemException {
    columnInfo.setTypecode(BasePoemType.REFERENCE);
    columnInfo.setTargettableTroid(targetTable.getTableInfoID());
  }

  public String toString() {
    return
        "reference to " + targetTable.getName() + " (" + super.toString() + ")";
  }
}
