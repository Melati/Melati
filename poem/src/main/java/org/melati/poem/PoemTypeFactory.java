/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
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

  public static interface Parameter {
    boolean getNullable();
    int getSize();

    public static final Parameter generic =
        new Parameter() {
          public boolean getNullable() {
	    return false;
	  }

	  public int getSize() {
	    return -1;
	  }
        };
  }

  abstract PoemType typeOf(Database database, Parameter info);

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
      TIMESTAMP;

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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
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
      public PoemType typeOf(Database database, Parameter info) {
        return new TimestampPoemType(info.getNullable());
      }

      public String getName() {
        return "TIMESTAMP";
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
            public PoemType typeOf(Database db, Parameter info) {
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
