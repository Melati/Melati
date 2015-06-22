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

import java.util.Enumeration;

/**
 * A data type.
 * <p>
 * PoemTypes come in two flavours: the predefined ones and those that can be 
 * defined by the application programmer; the former have a negative type code, 
 * the latter a positive one.
 */
public interface PoemType<T> {
  
  /**
   * Check if value is of the right type and an allowed value,
   * throw appropriate Exception if not.
   *  
   * @param raw the raw value to check
   * @throws TypeMismatchPoemException if the raw is of the wrong type
   * @throws ValidationPoemException if the raw has an illegal value
   */
  void assertValidRaw(Object raw)
      throws TypeMismatchPoemException, ValidationPoemException;

  /**
   * Get the possible values for this field, null for rangeable types with 
   * no range set.
   * NOTE Null is a possible value for nullable types 
   * @return an Enumeration of possibilities or null
   */
  Enumeration<T> possibleRaws();

  /**
   * The String representation of the Field.
   * 
   * @param object the value
   * @return a String representation
   * @throws TypeMismatchPoemException if the raw is of the wrong type
   * @throws ValidationPoemException if the raw has an illegal value
   */
  String stringOfRaw(Object object)
      throws TypeMismatchPoemException, ValidationPoemException;
  
  /**
   * Get an Object from its String representation. 
   * 
   * @param rawString the String representation to convert
   * @return an Object of the correct type
   * @throws ParsingPoemException if the String representation is not well formed
   * @throws ValidationPoemException if the raw has an illegal value
   */
  T rawOfString(String rawString)
      throws ParsingPoemException, ValidationPoemException;

  /**
   * Check if an Object is valid, throw appropriate Exception if not.
   * 
   * @param cooked the Object to check
   * @throws TypeMismatchPoemException if the raw is of the wrong type
   * @throws ValidationPoemException if the raw has an illegal value
   */
  void assertValidCooked(Object cooked)
      throws TypeMismatchPoemException, ValidationPoemException;

  /**
   * Create an Object from a raw Object, a no-op for all but ReferencePoemTypes.
   * @param raw the object, typically a troid
   * @return the Persistent or the raw unchanged
   * @throws TypeMismatchPoemException if the raw is of the wrong type
   * @throws PoemException if there is another problem, such as no object with that troid
   */
  Object cookedOfRaw(Object raw)
      throws TypeMismatchPoemException, PoemException;
  
  /**
   * Return the Object value, a no-op for all but ReferencePoemTypes, 
   * for which it returns the troid as an Integer.
   * 
   * @param cooked the Persistent or Object
   * @return a Persistent's troid or the raw unchanged
   * @throws TypeMismatchPoemException if the raw is of the wrong type
   */
  T rawOfCooked(Object cooked) throws TypeMismatchPoemException;

  /**
   * A localised String representation of the oject.
   *
   * @param cooked the PoemType to be stringified
   * @param locale to be used for dates
   * @param style as in <TT>java.text.DateFormat.SHORT</TT>, ...
   * @return a String representation of an Object
   * @throws TypeMismatchPoemException if the raw is of the wrong type
   * @throws PoemException if there is an access violation
   */
  String stringOfCooked(Object cooked, PoemLocale locale, int style)
      throws TypeMismatchPoemException, PoemException;

  /**
   * Whether the type is nullable.
   * 
   * @return the type's nullability
   */
  boolean getNullable();

  /**
   * Return a PoemType which can can represent another, 
   * or null.
   *
   * @param <O> the type of the PoemType
   * @param other the other type to check
   * @return other or null 
   */
  <O>PoemType<O> canRepresent(PoemType<O> other);

  /**
   * Get a new type with a nullablity, presumably different.
   *  
   * @param nullable the nullability we want
   * @return this or a clone with the desired nullability
   */
  PoemType<T> withNullable(boolean nullable);

  /**
   * Set the type of the ColumnInfo.
   * 
   * @param columnInfo the ColumnInfo to set the type of
   * @throws AccessPoemException if our AccessToken does not permit modification
   */
  void saveColumnInfo(ColumnInfo columnInfo) throws AccessPoemException;

  /**
   * The field type used in the Data Structure Definition language.
   * @return the Type name
   */
  String toDsdType();
}
