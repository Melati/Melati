package org.melati.poem;

/**
 * I'd just like you to know that I had to type this file in again after
 * deleting it.  It's not even very nice is it?
 */

public abstract class PoemTypeFactory {
  final Integer code;

  public PoemTypeFactory(int c) {
    this.code = new Integer(c);
  }

  abstract PoemType typeOf(Database database, ColumnInfo info);

  public Integer getCode() {
    return code;
  }

  public abstract String getName();

  public String getDisplayName() {
    return getName();
  }

  public abstract String getDescription();

  public static final PoemTypeFactory
      TROID, DELETED, TYPE, BOOLEAN, INTEGER, DOUBLE, STRING, DATE;

  private static int n = -1;

//   private static final void extractRange(ColumnInfo info, BasePoemType type) {
//     try {
//       type.setRawRange(type.rawOfString(info.getRangelow_string()),
//                        type.rawOfString(into.getRangehigh_string()));
//     }
//     catch (ValidationPoemException e) {
//       throw new RangeExtractionException(info, type, e);
//     }
//   }

  static final PoemTypeFactory[] atomTypeFactories = {
    TROID = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return TroidPoemType.it;
      }

      public String getName() {
        return "TROID";
      }

      public String getDescription() {
        return "...";
      }
    },

    DELETED = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return DeletedPoemType.it;
      }

      public String getName() {
        return "DELETED";
      }

      public String getDescription() {
        return "...";
      }
    },

    TYPE = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return new ColumnTypePoemType(database);
      }

      public String getName() {
        return "TYPE";
      }

      public String getDescription() {
        return "...";
      }
    },

    BOOLEAN = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return new BooleanPoemType(info.nullable.booleanValue());
      }

      public String getName() {
        return "BOOLEAN";
      }

      public String getDescription() {
        return "...";
      }
    },

    INTEGER = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return new IntegerPoemType(info.nullable.booleanValue(),
                                   info.width.intValue());
      }

      public String getName() {
        return "INTEGER";
      }

      public String getDescription() {
        return "...";
      }
    },

    DOUBLE = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return new DoublePoemType(info.nullable.booleanValue(),
                                  info.size.intValue());
      }

      public String getName() {
        return "DOUBLE";
      }

      public String getDescription() {
        return "...";
      }
    },

    STRING = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return new StringPoemType(info.nullable.booleanValue(),
                                  info.size.intValue(),
                                  info.width.intValue(),
                                  info.height.intValue());
      }

      public String getName() {
        return "STRING";
      }

      public String getDescription() {
        return "...";
      }
    },

    DATE = new PoemTypeFactory(n--) {
      public PoemType typeOf(Database database, ColumnInfo info) {
        return new DatePoemType(info.nullable.booleanValue());
      }

      public String getName() {
        return "DATE";
      }

      public String getDescription() {
        return "...";
      }
    }
  };

  static PoemTypeFactory forCode(Database database, int code) {
    if (code < 0)
      return atomTypeFactories[(-code)-1];
    else {
      final Table table = database.tableWithTableInfoID(code);
      return
          new PoemTypeFactory(code) {
            public PoemType typeOf(Database db, ColumnInfo info) {
              return new ReferencePoemType(table,
                                           info.nullable.booleanValue());
            }

            public String getName() {
              return table.getName();
            }

            public String getDisplayName() {
              return table.getDisplayName();
            }

            public String getDescription() {
              return table.getDescription();
            }
          };
    }
  }
}
