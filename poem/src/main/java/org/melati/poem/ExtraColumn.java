package org.melati.poem;

import java.sql.*;

public class ExtraColumn extends Column {
  private final int extrasIndex;

  public ExtraColumn(Table table, String name, PoemType type,
                     DefinitionSource definitionSource,
                     int extrasIndex) {
    super(table, name, type, definitionSource);
    this.extrasIndex = extrasIndex;
  }

  public Object getIdent(Data data) {
    return data.extras[extrasIndex];
  }

  protected void setIdent(Data data, Object ident) {
    data.extras[extrasIndex] = ident;
  }

  public Object getIdent(Persistent g) throws AccessPoemException {
    return getIdent(g._dataForReading());
  }

  public void setIdent(Persistent g, Object ident)
      throws AccessPoemException, ValidationPoemException {
    getType().assertValidIdent(ident);
    setIdent(g._dataForWriting(), ident);
  }

  public Object getValue(Persistent g) throws AccessPoemException, PoemException {
    // FIXME revalidation
    return getType().valueOfIdent(getIdent(g));
  }

  public void setValue(Persistent g, Object value)
      throws AccessPoemException, ValidationPoemException {
    // FIXME revalidation
    getType().assertValidValue(value);
    setIdent(g, getType().identOfValue(value));
  }

  public static Column from(Table table, ColumnInfo columnInfo,
                            int extrasIndex, DefinitionSource source) {
    return new ExtraColumn(
        table,
        columnInfo.getName(),
        BasePoemType.ofColumnInfo(table.getDatabase(),
                                  columnInfo.dataSnapshot()),
        source,
        extrasIndex);
  }
}
