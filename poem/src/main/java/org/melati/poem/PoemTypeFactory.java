/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

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

  public interface Parameter {
    boolean getNullable();
    int getSize();
  }

  abstract SQLPoemType typeOf(Database database, Parameter info);

  public Integer getCode() {
    return code;
  }

  public abstract String getName();

  public String getDisplayName() {
    return getName();
  }

  public abstract String getDescription();

  public static final PoemTypeFactory
      TROID, DELETED, TYPE, BOOLEAN, INTEGER, DOUBLE, STRING, DATE, PASSWORD,
      TIMESTAMP, DISPLAYLEVEL, SEARCHABILITY, BINARY, LONG;

  private static int n = -1;

//   private static final void extractRange(Parameter info, BasePoemType type) {
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
      public SQLPoemType typeOf(Database database, Parameter info) {
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
      public SQLPoemType typeOf(Database database, Parameter info) {
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
      public SQLPoemType typeOf(Database database, Parameter info) {
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
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new BooleanPoemType(info.getNullable());
      }

      public String getName() {
        return "BOOLEAN";
      }

      public String getDescription() {
        return "...";
      }
    },

    INTEGER = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new IntegerPoemType(info.getNullable());
      }

      public String getName() {
        return "INTEGER";
      }

      public String getDescription() {
        return "...";
      }
    },

    DOUBLE = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new DoublePoemType(info.getNullable());
      }

      public String getName() {
        return "DOUBLE";
      }

      public String getDescription() {
        return "...";
      }
    },

    STRING = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new StringPoemType(info.getNullable(), info.getSize());
      }

      public String getName() {
        return "STRING";
      }

      public String getDescription() {
        return "...";
      }
    },

    DATE = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new DatePoemType(info.getNullable());
      }

      public String getName() {
        return "DATE";
      }

      public String getDescription() {
        return "...";
      }
    },

    PASSWORD = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new PasswordPoemType(info.getNullable(), info.getSize());
      }

      public String getName() {
        return "PASSWORD";
      }

      public String getDescription() {
        return "...";
      }
    },

    TIMESTAMP = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new TimestampPoemType(info.getNullable());
      }

      public String getName() {
        return "TIMESTAMP";
      }

      public String getDescription() {
        return "...";
      }
    },

    DISPLAYLEVEL = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new DisplayLevelPoemType();
      }

      public String getName() {
        return "DISPLAYLEVEL";
      }

      public String getDescription() {
        return "...";
      }
    },

    SEARCHABILITY = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new SearchabilityPoemType();
      }

      public String getName() {
        return "SEARCHABILITY";
      }

      public String getDescription() {
        return "...";
      }
    },

    BINARY = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new BinaryPoemType(info.getNullable(), info.getSize());
      }

      public String getName() {
        return "BINARY";
      }

      public String getDescription() {
        return "...";
      }
    },

    LONG = new PoemTypeFactory(n--) {
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new LongPoemType(info.getNullable());
      }

      public String getName() {
        return "LONG";
      }

      public String getDescription() {
        return "...";
      }
    }
  };

  public static PoemTypeFactory forCode(Database database, int code) {
    if (code < 0)
      return atomTypeFactories[(-code)-1];
    else {
      final Table table = database.tableWithTableInfoID(code);
      return
          new PoemTypeFactory(code) {
            public SQLPoemType typeOf(Database db, Parameter info) {
              return new ReferencePoemType(table, info.getNullable());
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
