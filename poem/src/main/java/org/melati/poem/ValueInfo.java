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

import org.melati.poem.generated.ValueInfoBase;

public class ValueInfo extends ValueInfoBase {
  public ValueInfo() {}

  public PoemTypeFactory.Parameter toTypeParameter() {
    final Boolean nullable = getNullable_unsafe();
    final Integer size = getSize_unsafe();
    return
        new PoemTypeFactory.Parameter() {
          public boolean getNullable() {
            return nullable == null || nullable.booleanValue();
          }

          public int getSize() {
            return size == null ? -1 : size.intValue();
          }
        };
  }

  private SQLPoemType poemType = null;

  public SQLPoemType getType() {
    if (poemType == null) {
      PoemTypeFactory f = getTypefactory();

      if (f == null) {
        // this can happen before we have been fully initialised
        // it's convenient to return the "most general" type available ...
        return StringPoemType.nullable;
      }

      poemType = f.typeOf(getDatabase(), toTypeParameter());
    }

    return poemType;
  }

 /**
  * @todo Make more efficient, add interface for ranged types
  */
  private SQLPoemType getRangeEndType(boolean nullable) {
    // FIXME a little inefficient, but rarely used; also relies on BasePoemType
    // should have interface for ranged type ...

    SQLPoemType t = getType();

    if (t instanceof BasePoemType) {
      BasePoemType unrangedType = (BasePoemType)((BasePoemType)t).clone();
      unrangedType.setRawRange(null, null);
      return (SQLPoemType)unrangedType.withNullable(nullable);
    }
    else
      return null;
  }

  private FieldAttributes fieldAttributesRenamedAs(FieldAttributes c,
                                                   PoemType type) {
    return new BaseFieldAttributes(
        c.getName(), c.getDisplayName(), c.getDescription(), type,
        width == null ? 12 : width.intValue(),
        height == null ? 1 : height.intValue(),
        renderinfo,
        false, // indexed
        usereditable == null ? true : usereditable.booleanValue(),
        true // usercreateable
    );
  }

  public FieldAttributes fieldAttributesRenamedAs(FieldAttributes c) {
    return fieldAttributesRenamedAs(c, getType());
  }

  private Field rangeEndField(Column c) {
    SQLPoemType unrangedType = getRangeEndType(c.getType().getNullable());

    if (unrangedType == null)
      return null;
    else {
      Object raw;
      try {
        raw = unrangedType.rawOfString((String)c.getRaw_unsafe(this));
      }
      catch (Exception e) {
        System.err.println("Found a bad entry for " + c + " in " +
                           getTable().getName() + "/" + troid() + ": " +
                           "solution is to null it out ...");
        e.printStackTrace();
        c.setRaw_unsafe(this, null);
        raw = null;
      }

      return new Field(
          raw,
          fieldAttributesRenamedAs(c, unrangedType));
    }
  }

  public Field getRangelow_stringField() {
    Field it = rangeEndField(getValueInfoTable().getRangelow_stringColumn());
    return it != null ? it : super.getRangelow_stringField();
  }

  public Field getRangelimit_stringField() {
    Field it = rangeEndField(getValueInfoTable().getRangelimit_stringColumn());
    return it != null ? it : super.getRangelimit_stringField();
  }

  public void setRangelow_string(String value) {
    boolean nullable = getValueInfoTable().getRangelow_stringColumn().
                        getType().getNullable();
    PoemType unrangedType = getRangeEndType(nullable);
    if (unrangedType != null)
      value = unrangedType.stringOfRaw(unrangedType.rawOfString(value));
    super.setRangelow_string(value);
  }

  public void setRangelimit_string(String value) {
    boolean nullable = getValueInfoTable().getRangelimit_stringColumn().
                        getType().getNullable();
    PoemType unrangedType = getRangeEndType(nullable);
    if (unrangedType != null)
      value = unrangedType.stringOfRaw(unrangedType.rawOfString(value));
    super.setRangelimit_string(value);
  }
}
