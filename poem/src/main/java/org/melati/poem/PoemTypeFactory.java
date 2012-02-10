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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem;

/**
 * An object factory which produces {@link PoemType} objects
 * given a {@link Database} and a code.
 *
 * See {@link #forCode(Database, int)}.
 *
 * I'd just like you to know that I had to type this file in again after
 * deleting it.  It's not even very nice is it?
 */

public abstract class PoemTypeFactory {

  /**
   * Integer code for this type factory.
   * <p>
   * Negative for atomic types and the troid of the table metadata
   * object for row types.
   */
  final Integer code;

  /**
   * Constructor.
   * @param c PoemType code
   */
  public PoemTypeFactory(int c) {
    this.code = new Integer(c);
  }

  /** 
   * A Parameter object which knows whether a {@link PoemType} 
   * is <tt>nullable</tt> and its <tt>size</tt>.
   */
  public interface Parameter {
    /**
     * @return the nullability of the parameter
     */
    boolean getNullable();
    /**
     * @return its size 
     */
    int getSize();
  }

  abstract <T>SQLPoemType<T> typeOf(Database database, Parameter info);

  /**
   * @return the arbetary code for this type
   */
  public Integer getCode() {
    return code;
  }

  /**
   * @return the machine name of this type
   */
  public abstract String getName();

  /**
   * @return the display name of this type
   */
  public String getDisplayName() {
    return getName();
  }

 /**
  * @return a description for this type
  */
  public abstract String getDescription();

  /** Troid column factory. */
  public static final PoemTypeFactory TROID;
  /** Deleted column factory. */
  public static final PoemTypeFactory DELETED;
  /** Type column factory. */
  public static final PoemTypeFactory TYPE;
    
  /* Base type factories. */
  /** Boolean base-type factory. */
  public static final PoemTypeFactory BOOLEAN;
  /** Integer base-type factory. */
  public static final PoemTypeFactory INTEGER;
  /** Double base-type factory. */
  public static final PoemTypeFactory DOUBLE;
  /** Long base-type factory. */
  public static final PoemTypeFactory LONG;
  /** BigDecimal base-type factory. */
  public static final PoemTypeFactory BIGDECIMAL;
  /** String base-type factory. */
  public static final PoemTypeFactory STRING;
  /** Password base-type factory. */
  public static final PoemTypeFactory PASSWORD;
  /** Date base-type factory. */
  public static final PoemTypeFactory DATE;
  /** Timestamp base-type factory. */
  public static final PoemTypeFactory TIMESTAMP;
  /** Binary base-type factory. */
  public static final PoemTypeFactory BINARY;

  /** Poem Displaylevel factory. */
  public static final PoemTypeFactory  DISPLAYLEVEL;
  /** Poem Searchability factory. */
  public static final PoemTypeFactory  SEARCHABILITY;
  /** Poem IntegrityFix factory. */
  public static final PoemTypeFactory  INTEGRITYFIX;

  /** Time base-type factory. */
  public static final PoemTypeFactory TIME;

  //   private static final void extractRange(Parameter info, BasePoemType type) {
  //     try {
  //       type.setRawRange(type.rawOfString(info.getRangelow_string()),
  //                        type.rawOfString(into.getRangehigh_string()));
  //     }
  //     catch (ValidationPoemException e) {
  //       throw new RangeExtractionException(info, type, e);
  //     }
  //   }

  // YUCK this counter means you will need to add new types to the end of the list
  private static int n = -1;
  static final PoemTypeFactory[] atomTypeFactories =
    {
      TROID = new PoemTypeFactory(n--) {
        /**
         * {@inheritDoc}
         * @see org.melati.poem.PoemTypeFactory#typeOf
         * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
         */
        public SQLPoemType typeOf(Database database, Parameter info) {
          return TroidPoemType.it;
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "TROID";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, DELETED = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return DeletedPoemType.it;
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "DELETED";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, TYPE = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new ColumnTypePoemType(database);
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "TYPE";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, BOOLEAN = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new BooleanPoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "BOOLEAN";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, INTEGER = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new IntegerPoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "INTEGER";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, DOUBLE = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new DoublePoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "DOUBLE";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, STRING = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new StringPoemType(info.getNullable(), info.getSize());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "STRING";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, DATE = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new DatePoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "DATE";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, PASSWORD = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new PasswordPoemType(info.getNullable(), info.getSize());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "PASSWORD";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, TIMESTAMP = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new TimestampPoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "TIMESTAMP";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, DISPLAYLEVEL = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new DisplayLevelPoemType();
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "DISPLAYLEVEL";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, SEARCHABILITY = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new SearchabilityPoemType();
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "SEARCHABILITY";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, BINARY = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new BinaryPoemType(info.getNullable(), info.getSize());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "BINARY";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, LONG = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new LongPoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "LONG";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, INTEGRITYFIX = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new IntegrityFixPoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "INTEGRITYFIX";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, BIGDECIMAL = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new BigDecimalPoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "BIGDECIMAL";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    }, TIME = new PoemTypeFactory(n--) {
      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#typeOf
       * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
       */
      public SQLPoemType typeOf(Database database, Parameter info) {
        return new TimePoemType(info.getNullable());
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getName()
       */
      public String getName() {
        return "TIME";
      }

      /**
       * {@inheritDoc}
       * @see org.melati.poem.PoemTypeFactory#getDescription()
       */
      public String getDescription() {
        return "...";
      }
    },
    };

  /**
   * Returns an instance given a database and the integer code for
   * the instance.
   * @param database to get tables from
   * @param code TypeCode
   * @return a new PoemTypeFactory
   */
  public static PoemTypeFactory forCode(Database database, int code) {
    if (code < 0)
      return atomTypeFactories[(-code) - 1];
    else {
      final Table table = database.tableWithTableInfoID(code);
      return new PoemTypeFactory(code) {
        /**
         * {@inheritDoc}
         * @see org.melati.poem.PoemTypeFactory#typeOf
         * (org.melati.poem.Database, org.melati.poem.PoemTypeFactory.Parameter)
         */
        public SQLPoemType typeOf(Database db, Parameter info) {
          return new ReferencePoemType(table, info.getNullable());
        }

        /**
         * {@inheritDoc}
         * @see org.melati.poem.PoemTypeFactory#getName()
         */
        public String getName() {
          return table.getName();
        }

        /**
         * {@inheritDoc}
         * @see org.melati.poem.PoemTypeFactory#getDisplayName()
         */
        public String getDisplayName() {
          return table.getDisplayName();
        }

        /**
         * {@inheritDoc}
         * @see org.melati.poem.PoemTypeFactory#getDescription()
         */
        public String getDescription() {
          return table.getDescription();
        }
      };
    }
  }
}
