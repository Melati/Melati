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

package org.melati.util;

import java.util.*;
import java.text.*;

public class MelatiLocale {

  public static final MelatiLocale here = new MelatiLocale(Locale.UK);

  private final Locale locale;

  private final DateFormatSymbols dateFormatSymbols;
  private final String[] months, shortMonths;
  private final DateFormat[] dateFormats;
  private final DateFormat[] timestampFormats;

  public MelatiLocale(Locale locale) {
    if (locale == null)
      throw new NullPointerException();
    this.locale = locale;

    dateFormatSymbols = new DateFormatSymbols(locale);
    months = dateFormatSymbols.getMonths();
    shortMonths = dateFormatSymbols.getShortMonths();

    dateFormats = new DateFormat[4]; // don't tell me this will break
    dateFormats[DateFormat.FULL] =
        DateFormat.getDateInstance(DateFormat.FULL, locale);
    dateFormats[DateFormat.LONG] =
        DateFormat.getDateInstance(DateFormat.LONG, locale);
    dateFormats[DateFormat.MEDIUM] =
        DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    dateFormats[DateFormat.SHORT] =
        DateFormat.getDateInstance(DateFormat.SHORT, locale);

    timestampFormats = new DateFormat[4]; // don't tell me this will break
    timestampFormats[DateFormat.FULL] =
        DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL,
				       locale);
    timestampFormats[DateFormat.LONG] =
        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,
				       locale);
    timestampFormats[DateFormat.MEDIUM] =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,
				       locale);
    timestampFormats[DateFormat.SHORT] =
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
				       locale);
  }

  public final Locale locale() {
    return locale;
  }

  public String monthName(int monthNum) {
    return months[monthNum - 1];
  }

  public String shortMonthName(int monthNum) {
    return shortMonths[monthNum - 1];
  }

  public DateFormat dateFormat(int style) {
    return dateFormats[style];
  }

  public DateFormat timestampFormat(int style) {
    return timestampFormats[style];
  }
}
