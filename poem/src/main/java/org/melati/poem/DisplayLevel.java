package org.melati.poem;

import java.util.*;
import org.melati.util.*;

public class DisplayLevel {

  public final Integer index;
  public final String name;

  private DisplayLevel(int index, String name) {
    this.index = new Integer(index);
    this.name = name;
  }

  /**
   * Display level of the column used to refer concisely to a record
   * on the UI.
   *
   * @see Table#displayColumn()
   */
  public static final DisplayLevel primary;

  /**
   * Display level of columns included in a summary of records in
   * a set.
   * <p>
   * This is the default display level for a column.
   *
   * @see Table#getSummaryDisplayColumns()
   * @see Column#defaultDisplayLevel()
   * @see Persistent#getSummaryDisplayFields()
   */
  public static final DisplayLevel summary;

  /**
   * Display level of columns included in display focusing on a
   * single record, but without detail.
   *
   * @see Table#getRecordDisplayColumns()
   * @see Persistent#getRecordDisplayFields()
   */
  public static final DisplayLevel record;

  /**
   * Display level of columns included in a detailed display
   * of a single record.
   *
   * @see Table#getRecordDisplayColumns()
   * @see Persistent#getDetailDisplayFields()
   */
  public static final DisplayLevel detail;

  /**
   * Display level of columns hidden from users.
   */
  public static final DisplayLevel never;

  private static int n = 0;

  private static final DisplayLevel[] displayLevels =
    { primary = new DisplayLevel(n++, "primary"),
      summary = new DisplayLevel(n++, "summary"),
      record = new DisplayLevel(n++, "record"),
      detail = new DisplayLevel(n++, "detail"),
      never = new DisplayLevel(n++, "never") };

  private static final Hashtable levelOfName = new Hashtable();

  static {
    for (int i = 0; i < displayLevels.length; ++i)
      levelOfName.put(displayLevels[i].name, displayLevels[i]);
  }

  public static DisplayLevel forIndex(int index) {
    return displayLevels[index];
  }

  public static int count() {
    return displayLevels.length;
  }

  public static class NameUnrecognisedException extends PoemException {
    public String name;

    public NameUnrecognisedException(String name) {
      this.name = name;
    }

    public String getMessage() {
      return "No display level found which goes by the name `" + name + "'";
    }
  }

  public static DisplayLevel named(String name) {
    DisplayLevel it = (DisplayLevel)levelOfName.get(name);
    if (it == null)
      throw new NameUnrecognisedException(name);
    return it;
  }
}
