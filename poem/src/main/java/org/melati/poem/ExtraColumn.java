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

  public Object getRaw(Persistent g) throws AccessPoemException {
    g.readLock();
    return getRaw_unsafe(g);
  }

  public Object getRaw_unsafe(Persistent g) {
    return g.extras()[extrasIndex];
  }

  public void setRaw(Persistent g, Object raw)
      throws AccessPoemException, ValidationPoemException {
    getType().assertValidRaw(raw);
    g.writeLock();
    setRaw_unsafe(g, raw);
  }

  public void setRaw_unsafe(Persistent g, Object raw) {
    g.extras()[extrasIndex] = raw;
  }

  public Object getCooked(Persistent g)
      throws AccessPoemException, PoemException {
    // FIXME revalidation
    return getType().cookedOfRaw(getRaw(g));
  }

  public void setCooked(Persistent g, Object cooked)
      throws AccessPoemException, ValidationPoemException {
    // FIXME revalidation
    getType().assertValidCooked(cooked);
    setRaw(g, getType().rawOfCooked(cooked));
  }

  public static Column from(Table table, ColumnInfo columnInfo,
                            int extrasIndex, DefinitionSource source) {
    return new ExtraColumn(
        table,
        columnInfo.getName(),
        BasePoemType.ofColumnInfo(table.getDatabase(), columnInfo),
        source,
        extrasIndex);
  }
}
