/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
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
 *     Tim Joyce <timj At paneris.org>
 *     http://paneris.org/~timj
 */

package org.melati.template;

import java.util.Calendar;
import java.sql.Timestamp;

import org.melati.poem.Field;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.BaseFieldAttributes;
import org.melati.poem.SQLPoemType;

/**
 * An hour type.
 */
class HourPoemType extends IntegerPoemType {
  /**
   * Constructor.
   * @param nullable whether null is an allowed value
   */
  public HourPoemType(boolean nullable) {
    super(nullable);
    setRawRange(new Integer(0), new Integer(24));
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return other instanceof HourPoemType;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return super.toString() + " (hour)";
  }
}

/**
 * A minute type.
 */
class MinutePoemType extends IntegerPoemType {
  /**
   * Constructor.
   * @param nullable whether null is an allowed value
   */
  public MinutePoemType(boolean nullable) {
    super(nullable);
    setRawRange(new Integer(0), new Integer(60));
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return other instanceof MinutePoemType;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return super.toString() + " (minutes)";
  }
}

/**
 * A second.
 */
class SecondPoemType extends IntegerPoemType {
  /**
   * Constructor.
   * @param nullable whether null is an allowed value
   */
  public SecondPoemType(boolean nullable) {
    super(nullable);
    setRawRange(new Integer(0), new Integer(60));
  }

  protected boolean _canRepresent(SQLPoemType<?> other) {
    return other instanceof SecondPoemType;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return super.toString() + " (seconds)";
  }
}


/**
 * An adaptor for a string date in YMDHMST format.
 */
public class YMDHMSTimestampAdaptor extends YMDDateAdaptor {
  private static final String
      hourSuffix = "-hour",
      minuteSuffix = "-minute",
      secondSuffix = "-second";

  private static final YMDHMSTimestampAdaptor me = new YMDHMSTimestampAdaptor();

  /**
   * @return the instance.
   */
  public static YMDHMSTimestampAdaptor getIt() {
    return me;
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TempletAdaptor#rawFrom(
   *          org.melati.template.ServletTemplateContext, java.lang.String)
   */
  public Object rawFrom(ServletTemplateContext context, String fieldName) {
    String year = getFormOrDie(context, fieldName, yearSuffix);
    String month = getFormOrDie(context, fieldName, monthSuffix);
    String day = getFormOrDie(context, fieldName, daySuffix);
    String hour = getFormOrDie(context, fieldName, hourSuffix);
    String minute = getFormOrDie(context, fieldName, minuteSuffix);
    String second = getFormOrDie(context, fieldName, secondSuffix);

    if (year.equals("") && month.equals("") && day.equals("") &&
        hour.equals("") && minute.equals("") && second.equals(""))
      return null;
    else if (!year.equals("") && !month.equals("") && !day.equals("") &&
             !hour.equals("") && !minute.equals("") && !second.equals("")) {
      Calendar cal = Calendar.getInstance();
      cal.set(Integer.parseInt(year),
              Integer.parseInt(month) - 1,
              Integer.parseInt(day),
              Integer.parseInt(hour),
              Integer.parseInt(minute),
              Integer.parseInt(second));
      return new Timestamp(cal.getTime().getTime());
    } else {
      throw new PartlyNullException(fieldName);
    }
  }

  /**
   * @param field the field to copy
   * @return an hour field
   */
  public Field hourField(Field field) {

    Calendar when = when(field);

    // This isn't meant to be used, so we don't try to localize it

    String displayName = field.getDisplayName() + " (hour)";

    return new Field(
        when == null ? null : new Integer(when.get(Calendar.HOUR_OF_DAY)),
        new BaseFieldAttributes(
            field.getName() + hourSuffix, displayName, null,
            field.getType().getNullable() ? new HourPoemType(true) :
                                            new HourPoemType(false),
            2, 1,
            null, false, true, true));
  }

  /**
   * @param field the field to copy
   * @return a minute field
   */
  public Field minuteField(Field field) {

    Calendar when = when(field);

    // This isn't meant to be used, so we don't try to localize it

    String displayName = field.getDisplayName() + " (minutes)";

    return new Field(
        when == null ? null : new Integer(when.get(Calendar.MINUTE)),
        new BaseFieldAttributes(
            field.getName() + minuteSuffix, displayName, null,
            field.getType().getNullable() ? new MinutePoemType(true) :
                                            new MinutePoemType(false),
            2, 1,
            null, false, true, true));
  }

  /**
   * @param field the field to copy
   * @return a second field
   */
  public Field secondField(Field field) {

    Calendar when = when(field);

    // This isn't meant to be used, so we don't try to localize it

    String displayName = field.getDisplayName() + " (seconds)";

    return new Field(
        when == null ? null : new Integer(when.get(Calendar.SECOND)),
        new BaseFieldAttributes(
            field.getName() + secondSuffix, displayName, null,
            field.getType().getNullable() ? new SecondPoemType(true) :
                                            new SecondPoemType(false),
            2, 1,
            null, false, true, true));
  }
}
