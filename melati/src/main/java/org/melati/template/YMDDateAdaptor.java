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

package org.melati.template;

import java.sql.Date;
import java.text.DateFormat;

import org.melati.util.MelatiLocale;
import org.melati.poem.Field;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.PoemType;
import org.melati.poem.BaseFieldAttributes;

class YearPoemType extends IntegerPoemType {
  public YearPoemType(boolean nullable, int low, int limit) {
    super(nullable);
    setRawRange(new Integer(low), new Integer(limit));
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof YearPoemType;
  }

  public String toString() {
    return super.toString() + " (year)";
  }
}

class MonthPoemType extends IntegerPoemType {

  public MonthPoemType(boolean nullable) {
    super(nullable);
    setRawRange(new Integer(1), new Integer(13));
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof MonthPoemType;
  }

  protected String _stringOfCooked(Object raw,
                                   MelatiLocale locale, int style) {
    int m = ((Integer)raw).intValue();
    switch (style) {
      case DateFormat.FULL: case DateFormat.LONG:
        return locale.monthName(m);
      case DateFormat.MEDIUM:
        return locale.shortMonthName(m);
      default:
        return "" + m;
    }
  }

  public String toString() {
    return super.toString() + " (month)";
  }
}

class DayPoemType extends IntegerPoemType {

  public DayPoemType(boolean nullable) {
    super(nullable);
    setRawRange(new Integer(1), new Integer(32));
  }

  protected boolean _canBe(PoemType other) {
    return other instanceof DayPoemType;
  }

  public String toString() {
    return super.toString() + " (day)";
  }
}

public class YMDDateAdaptor implements TempletAdaptor {

  protected static final String
      yearSuffix = "-year",
      monthSuffix = "-month",
      daySuffix = "-day";

  public static final YMDDateAdaptor it = new YMDDateAdaptor();

  protected String getFormOrDie(TemplateContext context,
                              String fieldName, String suffix) {
    String fullName = fieldName + suffix;
    String value = context.getForm(fullName);
    if (value == null)
      throw new MissingFieldException(this, fieldName, fullName);
    return value;
  }

  public Object rawFrom(TemplateContext context, String fieldName) {
    String year = getFormOrDie(context, fieldName, yearSuffix);
    String month = getFormOrDie(context, fieldName, monthSuffix);
    String day = getFormOrDie(context, fieldName, daySuffix);

    if (year.equals("") && month.equals("") && day.equals(""))
      return null;
    else if (!year.equals("") && !month.equals("") && !day.equals(""))
      return new Date(Integer.parseInt(year) - 1900,
                      Integer.parseInt(month) - 1,
                      Integer.parseInt(day));
    else
      throw new PartlyNullException(fieldName);
  }

  public Field yearField(Field field) {

    java.util.Date when = (java.util.Date)field.getRaw();

    int firstYear = 2000; // FIXME put these in ColumnInfo
    int limitYear = 2005;

    // This isn't meant to be used, so we don't try to localize it

    String displayName = field.getDisplayName() + " (year)";

    return new Field(
        when == null ? null : new Integer(when.getYear() + 1900),
        new BaseFieldAttributes(
            field.getName() + yearSuffix,
            displayName,
            null,
            new YearPoemType(field.getType().getNullable(),
			     firstYear, limitYear),
	    5, 1,
            null, false, true, true));
  }

  public Field monthField(Field field) {

    java.util.Date when = (java.util.Date)field.getRaw();

    // This isn't meant to be used, so we don't try to localize it

    String displayName = field.getDisplayName() + " (month)";

    return new Field(
        when == null ? null : new Integer(when.getMonth() + 1),
        new BaseFieldAttributes(
            field.getName() + monthSuffix, displayName, null,
            field.getType().getNullable() ? new MonthPoemType(true) :
	                                    new MonthPoemType(false),
	    3, 1,
            null, false, true, true));
  }

  public Field dayField(Field field) {

    java.util.Date when = (java.util.Date)field.getRaw();

    // This isn't meant to be used, so we don't try to localize it

    String displayName = field.getDisplayName() + " (day)";

    return new Field(
        when == null ? null : new Integer(when.getDate()),
        new BaseFieldAttributes(
            field.getName() + daySuffix, displayName, null,
            field.getType().getNullable() ? new DayPoemType(true) :
                                            new DayPoemType(false),
	    2, 1,
            null, false, true, true));
  }
}
